package com.yourname.gramavasathi.ui.shared

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CulturalGuideScreen(onBack: () -> Unit) {
    val guides = listOf(
        Triple(
            "🙏",
            "Greetings",
            "Namaste or Namaskara is the standard greeting. Use both hands with a slight bow. Avoid handshakes unless the host offers first. Addressing elders respectfully is very important."
        ),
        Triple(
            "👗",
            "Dress code",
            "Wear modest, comfortable cotton clothes. Avoid shorts or sleeveless tops in shared family spaces. Carry a light shawl or dupatta. Simple, practical clothing is appreciated."
        ),
        Triple(
            "🍛",
            "Meal etiquette",
            "Wait for the host to serve before eating. Eat with your right hand if joining a traditional meal. Complimenting the food is deeply respectful. Do not waste food — take only what you can eat."
        ),
        Triple(
            "📷",
            "Photography",
            "Always ask before photographing family members, especially women and elders. Never photograph religious items, rituals, or sacred spaces without explicit permission. Respect privacy."
        ),
        Triple(
            "🌾",
            "Farming respect",
            "Do not walk on crops or enter fields without the host's guidance. Watch and learn before joining any farm activity. Treat farming tools and animals with care and respect."
        ),
        Triple(
            "🏠",
            "Shared spaces",
            "Remove footwear before entering the home. Keep voices low in the evenings. The kitchen may be considered sacred — enter only when invited. Maintain cleanliness in shared bathrooms."
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAF7F2))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF4A7C59))
//                .padding(8.dp),
                .padding(top = 40.dp, start = 8.dp, end = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
            Column {
                Text(
                    "Know before you go",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    "Your guide to respectful rural living",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 12.sp
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            guides.forEach { (emoji, title, content) ->
                var expanded by remember { mutableStateOf(false) }
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expanded = !expanded }
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(emoji, fontSize = 24.sp)
                                Text(
                                    title,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF2C2C2A)
                                )
                            }
                            Text(
                                if (expanded) "▲" else "▼",
                                fontSize = 12.sp,
                                color = Color(0xFF888780)
                            )
                        }
                        AnimatedVisibility(visible = expanded) {
                            Column {
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = content,
                                    fontSize = 13.sp,
                                    color = Color(0xFF5F5E5A),
                                    lineHeight = 20.sp
                                )
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}