package com.yourname.gramavasathi.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.yourname.gramavasathi.data.model.User
import com.yourname.gramavasathi.util.EmailService
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    val currentUser: FirebaseUser? get() = auth.currentUser
    val isLoggedIn: Boolean get() = auth.currentUser != null

    suspend fun registerHost(
        name: String,
        email: String,
        phone: String,
        password: String
    ): Result<Unit> = runCatching {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        val uid = result.user?.uid ?: throw Exception("Registration failed")
        val user = User(
            uid = uid,
            name = name,
            email = email,
            phone = phone,
            role = "host"
        )
        firestore.collection("users").document(uid).set(user).await()
        // Send email verification
        result.user?.sendEmailVerification()?.await()
        // Send welcome email
        EmailService.sendWelcomeHostEmail(email, name)
    }

    suspend fun registerGuest(
        name: String,
        email: String,
        phone: String,
        password: String
    ): Result<Unit> = runCatching {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        val uid = result.user?.uid ?: throw Exception("Registration failed")
        val user = User(
            uid = uid,
            name = name,
            email = email,
            phone = phone,
            role = "guest"
        )
        firestore.collection("users").document(uid).set(user).await()
        // Send email verification
        result.user?.sendEmailVerification()?.await()
        // Send welcome email
        EmailService.sendWelcomeGuestEmail(email, name)
    }

    suspend fun login(
        email: String,
        password: String
    ): Result<String> = runCatching {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        val uid = result.user?.uid ?: throw Exception("Login failed")
        val doc = firestore.collection("users").document(uid).get().await()
        doc.getString("role") ?: "guest"
    }

    suspend fun getUserRole(): String {
        val uid = auth.currentUser?.uid ?: return "guest"
        return try {
            val doc = firestore.collection("users").document(uid).get().await()
            doc.getString("role") ?: "guest"
        } catch (e: Exception) {
            "guest"
        }
    }

    suspend fun getUserName(): String {
        val uid = auth.currentUser?.uid ?: return "Guest"
        return try {
            val doc = firestore.collection("users").document(uid).get().await()
            doc.getString("name") ?: "Guest"
        } catch (e: Exception) {
            "Guest"
        }
    }

    fun getUserEmail(): String {
        return auth.currentUser?.email ?: ""
    }

    fun isEmailVerified(): Boolean =
        auth.currentUser?.isEmailVerified == true

    suspend fun resendVerificationEmail(): Result<Unit> = runCatching {
        auth.currentUser?.sendEmailVerification()?.await()
            ?: throw Exception("User not found")
    }

    fun logout() = auth.signOut()
}
