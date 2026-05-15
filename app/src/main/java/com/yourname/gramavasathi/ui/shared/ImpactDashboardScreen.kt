package com.yourname.gramavasathi.ui.shared

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yourname.gramavasathi.viewmodel.ImpactViewModel

@Composable
fun ImpactDashboardScreen(
    onBack: () -> Unit,
    viewModel: ImpactViewModel = hiltViewModel()
) {
    val totalVillages by viewModel.totalVillages.collectAsState()
    val totalHosts by viewModel.totalHosts.collectAsState()
    val averageScore by viewModel.averageReadinessScore.collectAsState()
    val topActivities by viewModel.topActivities.collectAsState()

    val activityLabels = mapOf(
        "cow_milking" to "Cow Milking",
        "birdwatching" to "Birdwatching",
        "local_cooking" to "Local Cooking",
        "field_plowing" to "Field Plowing",
        "fishing" to "Fishing",
        "nature_walk" to "Nature Walk",
        "folk_interaction" to "Folk Interaction"
    )

    val hostStories = listOf(
        Triple("S", "Suresh — Nandi Hills",
            "I earned ₹14,400 last month hosting city guests."),
        Triple("L", "Lakshmi — Coorg",
            "My daughter now helps run our homestay. She is learning English."),
        Triple("R", "Raju — Chikmagalur",
            "We fixed our bathroom because of the readiness checklist.")
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
                    "Grama-Vasathi Impact",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    "Powering Vikshit Bharat through rural tourism",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 11.sp
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Platform stats",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF5F5E5A)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MetricCard("Villages", "$totalVillages", Modifier.weight(1f))
                MetricCard("Hosts", "$totalHosts", Modifier.weight(1f))
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MetricCard("Avg score", "$averageScore%", Modifier.weight(1f))
                MetricCard("Districts", "10+", Modifier.weight(1f))
            }

            if (topActivities.isNotEmpty()) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Popular activities",
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        val maxCount = topActivities.maxOf { it.second }
                        topActivities.forEach { (activity, count) ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    activityLabels[activity] ?: activity,
                                    fontSize = 12.sp,
                                    modifier = Modifier.width(110.dp),
                                    color = Color(0xFF5F5E5A)
                                )
                                LinearProgressIndicator(
                                    progress = {
                                        count.toFloat() / maxCount
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(8.dp)
                                        .clip(RoundedCornerShape(4.dp)),
                                    color = Color(0xFF4A7C59),
                                    trackColor = Color(0xFFE2DDD5)
                                )
                                Text(
                                    " $count",
                                    fontSize = 11.sp,
                                    color = Color(0xFF888780),
                                    modifier = Modifier.width(24.dp)
                                )
                            }
                        }
                    }
                }
            }

            Text(
                "Host stories",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF5F5E5A)
            )

            hostStories.forEach { (initial, name, story) ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFEAF3DE)
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF4A7C59)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                initial,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Column {
                            Text(
                                "\"$story\"",
                                fontSize = 13.sp,
                                fontStyle = FontStyle.Italic,
                                color = Color(0xFF2C2C2A),
                                lineHeight = 20.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "— $name",
                                fontSize = 11.sp,
                                color = Color(0xFF3B6D11)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun MetricCard(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                value,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4A7C59)
            )
            Text(
                label,
                fontSize = 12.sp,
                color = Color(0xFF5F5E5A)
            )
        }
    }
}