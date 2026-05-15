package com.yourname.gramavasathi.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val WarmBackground = Color(0xFFFAF7F2)
val PrimaryGreen = Color(0xFF4A7C59)

@Composable
fun RoleSelectionScreen(
    onHostSelected: () -> Unit,
    onHostLogin: () -> Unit,
    onGuestSelected: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmBackground)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Grama-Vasathi",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryGreen,
            textAlign = TextAlign.Center
        )
        
        Text(
            text = "Matti-Vasane — Scent of the soil",
            fontSize = 18.sp,
            fontStyle = FontStyle.Italic,
            color = Color.Gray,
            modifier = Modifier.padding(top = 8.dp, bottom = 48.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                RoleCard(
                    title = "I am a Host",
                    subtitle = "Prepare your farm stay",
                    icon = Icons.Default.Home,
                    onClick = onHostSelected
                )
                TextButton(
                    onClick = onHostLogin,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Host Login", fontSize = 12.sp)
                }
            }
            
            RoleCard(
                modifier = Modifier.weight(1f),
                title = "I am a Guest",
                subtitle = "Discover rural stays",
                icon = Icons.Default.Person,
                onClick = onGuestSelected
            )
        }
    }
}

@Composable
fun RoleCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(200.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = PrimaryGreen,
                modifier = Modifier.size(48.dp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = PrimaryGreen,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                lineHeight = 16.sp
            )
        }
    }
}
