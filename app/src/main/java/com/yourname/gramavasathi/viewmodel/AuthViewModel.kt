package com.yourname.gramavasathi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yourname.gramavasathi.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    // replay = 1 ensures event is never lost
    val authEvent = MutableSharedFlow<AuthEvent>(replay = 1)

    val isLoggedIn: Boolean get() = authRepository.isLoggedIn

    sealed class AuthEvent {
        data class NavigateToHost(val name: String) : AuthEvent()
        data class NavigateToGuest(val name: String) : AuthEvent()
        object NavigateToRoleSelection : AuthEvent()
        data class ShowVerification(val email: String) : AuthEvent()
        object Verified : AuthEvent()
    }

    fun registerHost(
        name: String,
        email: String,
        phone: String,
        password: String
    ) {
        if (!validateFields(name, email, phone, password)) return
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            authRepository.registerHost(name, email, phone, password)
                .onSuccess {
                    authEvent.emit(AuthEvent.ShowVerification(email))
                }
                .onFailure {
                    _errorMessage.value = getFriendlyError(it.message)
                }
            _isLoading.value = false
        }
    }

    fun registerGuest(
        name: String,
        email: String,
        phone: String,
        password: String
    ) {
        if (!validateFields(name, email, phone, password)) return
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            authRepository.registerGuest(name, email, phone, password)
                .onSuccess {
                    authEvent.emit(AuthEvent.ShowVerification(email))
                }
                .onFailure {
                    _errorMessage.value = getFriendlyError(it.message)
                }
            _isLoading.value = false
        }
    }

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _errorMessage.value = "Email and password are required"
            return
        }
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            authRepository.login(email, password)
                .onSuccess { role ->
                    val name = authRepository.getUserName()
                    if (role == "host") {
                        authEvent.emit(AuthEvent.NavigateToHost(name))
                    } else {
                        authEvent.emit(AuthEvent.NavigateToGuest(name))
                    }
                }
                .onFailure {
                    _errorMessage.value = getFriendlyError(it.message)
                }
            _isLoading.value = false
        }
    }

    fun checkEmailVerifiedAndProceed() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                authRepository.currentUser?.reload()?.await()
                if (authRepository.isEmailVerified()) {
                    val role = authRepository.getUserRole()
                    val name = authRepository.getUserName()
                    if (role == "host") {
                        authEvent.emit(AuthEvent.NavigateToHost(name))
                    } else {
                        authEvent.emit(AuthEvent.NavigateToGuest(name))
                    }
                } else {
                    _errorMessage.value =
                        "Email not verified yet. Please click the link in your email first, then come back and tap Continue."
                }
            } catch (e: Exception) {
                _errorMessage.value = "Could not check verification. Please try again."
            }
            _isLoading.value = false
        }
    }

    suspend fun resendVerificationEmail() {
        authRepository.resendVerificationEmail()
            .onFailure {
                _errorMessage.value = getFriendlyError(it.message)
            }
    }

    fun checkIfLoggedIn() {
        viewModelScope.launch {
            if (authRepository.isLoggedIn) {
                val role = authRepository.getUserRole()
                val name = authRepository.getUserName()
                if (role == "host") {
                    authEvent.emit(AuthEvent.NavigateToHost(name))
                } else {
                    authEvent.emit(AuthEvent.NavigateToGuest(name))
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            authEvent.emit(AuthEvent.NavigateToRoleSelection)
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }

    private fun validateFields(
        name: String,
        email: String,
        phone: String,
        password: String
    ): Boolean {
        return when {
            name.isBlank() -> {
                _errorMessage.value = "Full name is required"
                false
            }
            email.isBlank() || !email.contains("@") -> {
                _errorMessage.value = "Valid email address is required"
                false
            }
            phone.isBlank() || phone.length < 10 -> {
                _errorMessage.value = "Valid 10-digit phone number required"
                false
            }
            password.length < 6 -> {
                _errorMessage.value = "Password must be at least 6 characters"
                false
            }
            else -> true
        }
    }

    private fun getFriendlyError(message: String?): String {
        return when {
            message == null ->
                "Something went wrong. Please try again."
            message.contains("email address is already in use") ->
                "This email is already registered. Please login instead."
            message.contains("network") ->
                "No internet connection. Please check your network."
            message.contains("password is invalid") ||
                    message.contains("INVALID_LOGIN_CREDENTIALS") ->
                "Incorrect email or password. Please try again."
            message.contains("no user record") ->
                "No account found. Please register first."
            message.contains("badly formatted") ->
                "Please enter a valid email address."
            message.contains("operation-not-allowed") || 
            message.contains("provider is disabled") ->
                "Email/Password sign-in is not enabled in Firebase. Please enable it in Firebase Console > Build > Authentication > Sign-in method."
            else -> message
        }
    }
}
