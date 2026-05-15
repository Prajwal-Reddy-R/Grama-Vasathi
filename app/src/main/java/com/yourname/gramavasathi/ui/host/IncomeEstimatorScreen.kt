package com.yourname.gramavasathi.ui.host

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourname.gramavasathi.ui.theme.ForestGreen
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncomeEstimatorScreen(
    onBack: () -> Unit
) {
    var nightsPerMonth by remember { mutableStateOf(8f) }
    var pricePerNight by remember { mutableStateOf(1200f) }
    var occupancyRate by remember { mutableStateOf(50f) }

    val monthlyIncome = (nightsPerMonth * pricePerNight * (occupancyRate / 100f)).roundToInt()
    val annualIncome = monthlyIncome * 12
    val avgDailyWage = 400
    val multiplier = if (monthlyIncome > 0) (monthlyIncome.toFloat() / (avgDailyWage * 30)) else 0f

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Your earning potential") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        containerColor = Color(0xFFFAF7F2)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    EstimatorSlider(
                        label = "Nights hosted per month",
                        value = nightsPerMonth,
                        onValueChange = { nightsPerMonth = it },
                        valueRange = 1f..30f,
                        displayValue = "${nightsPerMonth.toInt()} nights"
                    )

                    EstimatorSlider(
                        label = "Price per night",
                        value = pricePerNight,
                        onValueChange = { pricePerNight = it },
                        valueRange = 500f..5000f,
                        steps = 44, // (5000-500)/100 - 1 = 44 steps for 100 increments
                        displayValue = "₹${pricePerNight.toInt()}"
                    )

                    EstimatorSlider(
                        label = "Occupancy rate",
                        value = occupancyRate,
                        onValueChange = { occupancyRate = it },
                        valueRange = 20f..100f,
                        steps = 7, // (100-20)/10 - 1 = 7 steps for 10% increments
                        displayValue = "${occupancyRate.toInt()}%"
                    )
                }
            }

            // Results
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ResultCard(
                    label = "Monthly income",
                    value = "₹$monthlyIncome",
                    modifier = Modifier.weight(1f)
                )
                ResultCard(
                    label = "Annual income",
                    value = "₹$annualIncome",
                    modifier = Modifier.weight(1f)
                )
            }

            // Comparison
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = ForestGreen.copy(alpha = 0.1f)),
                border = androidx.compose.foundation.BorderStroke(1.dp, ForestGreen.copy(alpha = 0.2f))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Info, null, tint = ForestGreen)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            "vs. average daily wage (₹400/day)",
                            style = MaterialTheme.typography.bodySmall,
                            color = ForestGreen
                        )
                        Text(
                            "${String.format("%.1f", multiplier)}x the average local monthly income",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = ForestGreen
                        )
                    }
                }
            }

            // Motivational Message
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = ForestGreen,
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Hosting ${nightsPerMonth.toInt()} nights a month could earn you ₹$monthlyIncome — that's ${String.format("%.1f", multiplier * 30 / 30)} months of daily wage income.",
                    modifier = Modifier.padding(20.dp),
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 24.sp
                )
            }
        }
    }
}

@Composable
fun EstimatorSlider(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
    steps: Int = 0,
    displayValue: String
) {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(label, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            Text(displayValue, fontWeight = FontWeight.Bold, color = ForestGreen)
        }
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            steps = steps,
            colors = SliderDefaults.colors(
                thumbColor = ForestGreen,
                activeTrackColor = ForestGreen,
                inactiveTrackColor = ForestGreen.copy(alpha = 0.2f)
            )
        )
    }
}

@Composable
fun ResultCard(label: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(label, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
            Text(value, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.ExtraBold, color = ForestGreen)
        }
    }
}
