package com.yourname.gramavasathi.ui.host

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
fun HostGuidanceScreen(onBack: () -> Unit) {
    val sections = listOf(
        GuidanceSection(
            emoji = "🏠",
            title = "Room Setup",
            color = Color(0xFFEAF3DE),
            tips = listOf(
                "Clean and air the room at least 2 hours before guest arrival",
                "Provide fresh bedsheets, pillow covers, and a blanket",
                "Keep at least 2 towels per guest — one bath, one hand towel",
                "Ensure the room has a working light, fan, and power socket",
                "Add a small table and chair for guests to place their belongings",
                "Place a dustbin with a liner inside the room",
                "Provide a small mirror and coat hooks near the entrance",
                "Keep the floor swept and mopped before every check-in"
            )
        ),
        GuidanceSection(
            emoji = "🚿",
            title = "Bathroom & Hygiene",
            color = Color(0xFFE6F1FB),
            tips = listOf(
                "Clean the toilet bowl, floor, and sink before every guest arrival",
                "Provide a full bottle of liquid soap or a fresh bar of soap",
                "Ensure safe drinking water is always available — filtered or boiled",
                "Keep the bathroom well-ventilated to prevent odour",
                "Provide toilet paper and a waste bin in the bathroom",
                "Fix any leaking taps or drains before accepting bookings",
                "Use phenyl or disinfectant when cleaning the bathroom floor",
                "Check that the geyser or bucket bath system works properly"
            )
        ),
        GuidanceSection(
            emoji = "🍛",
            title = "Food Service",
            color = Color(0xFFFAEEDA),
            tips = listOf(
                "Always ask guests about food allergies before cooking",
                "Serve meals on clean plates — wash in hot water and dry in sun",
                "Cover all food with lids or mesh covers to prevent flies",
                "Use filtered or boiled water for cooking and drinking",
                "Offer a simple breakfast — idli, dosa, or roti with chutney works well",
                "Inform guests of meal timings in advance — do not keep them waiting",
                "Never serve food that has been kept overnight without refrigeration",
                "Offer local seasonal fruits and vegetables — guests love authenticity"
            )
        ),
        GuidanceSection(
            emoji = "🤝",
            title = "Guest Behavior",
            color = Color(0xFFEEEDFE),
            tips = listOf(
                "Greet guests with Namaskara and a warm smile on arrival",
                "Show guests around — room, bathroom, dining area, and farm",
                "Explain house rules politely — meal times, quiet hours, water usage",
                "Do not enter the guest room without knocking and waiting",
                "Respect guests' privacy — avoid unnecessary conversations at night",
                "If guests speak only English, keep communication simple and clear",
                "Always be available on phone during the guest's stay",
                "Ask guests if they need anything before they have to ask you"
            )
        ),
        GuidanceSection(
            emoji = "📱",
            title = "Marketing Your Stay",
            color = Color(0xFFF0F7EB),
            tips = listOf(
                "Take high-quality photos in natural daylight — no dark photos",
                "Photograph the room, bathroom, food, farm, and outdoor area",
                "Write a warm description that mentions your unique activities",
                "Ask every satisfied guest to leave a review on the app",
                "Share your listing on WhatsApp family and friend groups",
                "Keep your readiness score above 80% to appear higher in search",
                "Respond to guest inquiries within 2 hours for better trust",
                "Update your listing description for each season — monsoon, harvest"
            )
        ),
        GuidanceSection(
            emoji = "💰",
            title = "Pricing & Income",
            color = Color(0xFFFAEEDA),
            tips = listOf(
                "Start with a competitive price — check similar stays in your district",
                "Offer a discount for stays longer than 3 nights to attract bookings",
                "Include breakfast in the price — guests prefer all-inclusive stays",
                "Increase price during festivals, harvest season, and long weekends",
                "Calculate your costs first — electricity, water, food, cleaning",
                "Set a minimum stay of 2 nights during peak season",
                "Offer a special price for groups of 4 or more guests",
                "Track monthly income using the Income Estimator in this app"
            )
        ),
        GuidanceSection(
            emoji = "🌾",
            title = "Farm Activities",
            color = Color(0xFFEAF3DE),
            tips = listOf(
                "Explain each activity to guests before starting — safety first",
                "Let guests try milking, planting, or harvesting at their own pace",
                "Never force guests into activities they seem hesitant about",
                "Provide boots or slippers for guests going into the field",
                "Give guests a small souvenir — fresh vegetables, honey, or spices",
                "Create a simple activity schedule and share it on check-in",
                "Add birdwatching walks in the early morning — 6 to 7 AM is ideal",
                "Teach guests one local recipe — they will always remember it"
            )
        ),
        GuidanceSection(
            emoji = "⚠️",
            title = "Safety & Emergencies",
            color = Color(0xFFFCEBEB),
            tips = listOf(
                "Keep a basic first aid kit with bandages, antiseptic, and paracetamol",
                "Save the nearest hospital number and share it with guests on arrival",
                "Ensure all electrical sockets are safe — no exposed wiring",
                "Keep a torch available for power cuts during the night",
                "Do not allow open fires near guest areas without supervision",
                "Inform guests about safe areas of the farm and off-limit zones",
                "Keep emergency numbers visible in the guest room",
                "Install a simple bolt lock on the inside of the guest room door"
            )
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
                .padding(8.dp),
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
                    text = "Host Guidance",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "Everything you need to be a great host",
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
            sections.forEach { section ->
                GuidanceSectionCard(section = section)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

data class GuidanceSection(
    val emoji: String,
    val title: String,
    val color: Color,
    val tips: List<String>
)

@Composable
private fun GuidanceSectionCard(section: GuidanceSection) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        section.color,
                        if (expanded) RoundedCornerShape(
                            topStart = 12.dp,
                            topEnd = 12.dp
                        )
                        else RoundedCornerShape(12.dp)
                    )
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = section.emoji, fontSize = 24.sp)
                    Text(
                        text = section.title,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF2C2C2A)
                    )
                }
                Text(
                    text = if (expanded) "▲" else "▼",
                    fontSize = 12.sp,
                    color = Color(0xFF888780)
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    section.tips.forEachIndexed { index, tip ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(
                                text = "${index + 1}.",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF4A7C59)
                            )
                            Text(
                                text = tip,
                                fontSize = 13.sp,
                                color = Color(0xFF5F5E5A),
                                lineHeight = 20.sp
                            )
                        }
                    }
                }
            }
        }
    }
}