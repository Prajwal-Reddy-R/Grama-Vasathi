package com.yourname.gramavasathi.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yourname.gramavasathi.viewmodel.AuthViewModel

@Composable
fun GuestAuthScreen(
    onAuthSuccess: () -> Unit,
    onBack: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showVerification by remember { mutableStateOf(false) }
    var verificationEmail by remember { mutableStateOf("") }

    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.checkIfLoggedIn()
        viewModel.authEvent.collect { event ->
            when (event) {
                is AuthViewModel.AuthEvent.NavigateToGuest -> {
                    onAuthSuccess()
                }
                is AuthViewModel.AuthEvent.ShowVerification -> {
                    verificationEmail = event.email
                    showVerification = true
                }
                else -> {}
            }
        }
    }

    if (showVerification) {
        EmailVerificationScreen(
            email = verificationEmail,
            onContinue = {
                viewModel.checkEmailVerifiedAndProceed()
            },
            onResend = { viewModel.resendVerificationEmail() }
        )
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAF7F2))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF4A7C59))
                .padding(
                    top = 40.dp,
                    start = 8.dp,
                    end = 8.dp,
                    bottom = 8.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
            Column {
                Text(
                    text = "Guest Portal 🧳",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Register or login to explore stays",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 12.sp
                )
            }
        }

        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color.White,
            contentColor = Color(0xFF4A7C59)
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = {
                    selectedTab = 0
                    viewModel.clearError()
                },
                text = {
                    Text(
                        "Register",
                        fontWeight = if (selectedTab == 0)
                            FontWeight.Bold else FontWeight.Normal
                    )
                }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = {
                    selectedTab = 1
                    viewModel.clearError()
                },
                text = {
                    Text(
                        "Login",
                        fontWeight = if (selectedTab == 1)
                            FontWeight.Bold else FontWeight.Normal
                    )
                }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = if (selectedTab == 0)
                    "Create your guest account"
                else
                    "Welcome back!",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2C2C2A)
            )

            Text(
                text = if (selectedTab == 0)
                    "Discover authentic rural Karnataka"
                else
                    "Login to explore farm stays",
                fontSize = 13.sp,
                color = Color(0xFF5F5E5A)
            )

            if (selectedTab == 0) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Full name *") },
                    placeholder = { Text("eg. Suresh Gowda") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = phone,
                    onValueChange = {
                        if (it.length <= 10) phone = it
                    },
                    label = { Text("Phone number *") },
                    placeholder = { Text("10-digit mobile number") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone
                    )
                )
            }

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email address *") },
                placeholder = { Text("your@email.com") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                )
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password *") },
                placeholder = { Text("Minimum 6 characters") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                )
            )

            if (errorMessage != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color(0xFFFCEBEB),
                            RoundedCornerShape(8.dp)
                        )
                        .padding(12.dp)
                ) {
                    Text(
                        text = "⚠ ${errorMessage ?: ""}",
                        color = Color(0xFFA32D2D),
                        fontSize = 13.sp
                    )
                }
            }

            Button(
                onClick = {
                    if (selectedTab == 0) {
                        viewModel.registerGuest(
                            name, email, phone, password
                        )
                    } else {
                        viewModel.login(email, password)
                    }
                },
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4A7C59)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = if (selectedTab == 0)
                            "Register as Guest"
                        else
                            "Login",
                        fontSize = 15.sp,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = if (selectedTab == 0)
                        "Already have an account? "
                    else
                        "New guest? ",
                    fontSize = 13.sp,
                    color = Color(0xFF5F5E5A)
                )
                Text(
                    text = if (selectedTab == 0) "Login" else "Register",
                    fontSize = 13.sp,
                    color = Color(0xFF4A7C59),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        selectedTab = if (selectedTab == 0) 1 else 0
                        viewModel.clearError()
                    }
                )
            }
        }
    }
}
