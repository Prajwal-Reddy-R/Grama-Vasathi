
package com.yourname.gramavasathi.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun EmailVerificationScreen(
    email: String,
    onContinue: () -> Unit,
    onResend: suspend () -> Unit
) {
    val scope = rememberCoroutineScope()
    var resendCooldown by remember { mutableStateOf(0) }
    var resendMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAF7F2))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Icon
        androidx.compose.foundation.layout.Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color(0xFFEAF3DE)),
            contentAlignment = Alignment.Center
        ) {
            Text("📧", fontSize = 48.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Verify your email",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2C2C2A),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "We sent a verification link to",
            fontSize = 14.sp,
            color = Color(0xFF5F5E5A),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = email,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF4A7C59),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFEAF3DE)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "What to do next:",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF2C2C2A)
                )
                Spacer(modifier = Modifier.height(8.dp))
                listOf(
                    "1. Check your email inbox",
                    "2. Click the verification link",
                    "3. Come back and tap Continue",
                    "4. Check spam folder if not found"
                ).forEach { step ->
                    Text(
                        text = step,
                        fontSize = 13.sp,
                        color = Color(0xFF5F5E5A),
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

//        Button(
//            onClick = onContinue,
//            modifier = Modifier.fillMaxWidth(),
//            colors = ButtonDefaults.buttonColors(
//                containerColor = Color(0xFF4A7C59)
//            ),
//            shape = RoundedCornerShape(12.dp)
//        ) {
//            Text(
//                text = "I verified my email — Continue",
//                fontSize = 15.sp,
//                modifier = Modifier.padding(vertical = 4.dp)
//            )
//        }
        Button(
            onClick = onContinue,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4A7C59)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "I verified my email — Continue →",
                fontSize = 15.sp,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = {
                if (resendCooldown == 0) {
                    scope.launch {
                        onResend()
                        resendMessage = "Verification email sent!"
                        resendCooldown = 60
                        while (resendCooldown > 0) {
                            delay(1000)
                            resendCooldown--
                        }
                        resendMessage = ""
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = resendCooldown == 0,
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color(0xFF4A7C59)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = if (resendCooldown > 0)
                    "Resend in ${resendCooldown}s"
                else
                    "Resend verification email",
                fontSize = 14.sp
            )
        }

        if (resendMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = resendMessage,
                fontSize = 12.sp,
                color = Color(0xFF4A7C59)
            )
        }
    }
}