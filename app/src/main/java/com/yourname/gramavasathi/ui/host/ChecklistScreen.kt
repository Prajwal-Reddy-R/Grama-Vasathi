package com.yourname.gramavasathi.ui.host

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yourname.gramavasathi.R
import com.yourname.gramavasathi.data.model.ChecklistState
import com.yourname.gramavasathi.util.ScoreCalculator
import com.yourname.gramavasathi.viewmodel.HostViewModel

@Composable
fun ChecklistScreen(
    onFinished: () -> Unit,
    onBack: () -> Unit,
    viewModel: HostViewModel = hiltViewModel()
) {
    val items by viewModel.checklistItems.collectAsState()
    val score by viewModel.readinessScore.collectAsState()
    val currentStep by viewModel.currentStep.collectAsState()

    val scoreColor = when {
        score >= 80 -> Color(0xFF4A7C59)
        score >= 50 -> Color(0xFFD4A017)
        else -> Color(0xFFD85A30)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAF7F2))
    ) {
        // Header with score
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF4A7C59))
//                .padding(16.dp),
                .padding(top = 40.dp, start = 8.dp, end = 8.dp, bottom = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.host_checklist_title),
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "$score%",
                color = Color.White,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = ScoreCalculator.getBandLabel(score),
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 13.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            LinearProgressIndicator(
                progress = { score / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = when {
                    score >= 80 -> Color(0xFF9FE1CB)
                    score >= 50 -> Color(0xFFFAC775)
                    else -> Color(0xFFF09595)
                },
                trackColor = Color.White.copy(alpha = 0.3f)
            )
        }

        // Step dots
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items.forEachIndexed { index, _ ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(
                            if (index <= currentStep)
                                Color(0xFF4A7C59)
                            else
                                Color(0xFFE2DDD5)
                        )
                )
            }
        }

        // Step counter
        Text(
            text = stringResource(R.string.step_counter, currentStep + 1, items.size),
            modifier = Modifier.padding(horizontal = 16.dp),
            fontSize = 12.sp,
            color = Color(0xFF888780)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Current checklist item
        items.getOrNull(currentStep)?.let { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.label,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF2C2C2A),
                            modifier = Modifier.weight(1f)
                        )
                        Box(
                            modifier = Modifier
                                .background(
                                    Color(0xFFEAF3DE),
                                    RoundedCornerShape(20.dp)
                                )
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.pts_label, item.weight),
                                fontSize = 11.sp,
                                color = Color(0xFF3B6D11),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.category_label, item.category),
                        fontSize = 12.sp,
                        color = Color(0xFF888780)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Three state buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        StateButton(
                            label = stringResource(R.string.done),
                            isSelected = item.state == ChecklistState.COMPLETED,
                            selectedColor = Color(0xFF4A7C59),
                            modifier = Modifier.weight(1f),
                            onClick = {
                                viewModel.updateItemState(
                                    item.id,
                                    ChecklistState.COMPLETED
                                )
                            }
                        )
                        StateButton(
                            label = stringResource(R.string.not_yet),
                            isSelected = item.state == ChecklistState.NOT_COMPLETED,
                            selectedColor = Color(0xFFD85A30),
                            modifier = Modifier.weight(1f),
                            onClick = {
                                viewModel.updateItemState(
                                    item.id,
                                    ChecklistState.NOT_COMPLETED
                                )
                            }
                        )
                        StateButton(
                            label = stringResource(R.string.na),
                            isSelected = item.state == ChecklistState.NOT_APPLICABLE,
                            selectedColor = Color(0xFF888780),
                            modifier = Modifier.weight(1f),
                            onClick = {
                                viewModel.updateItemState(
                                    item.id,
                                    ChecklistState.NOT_APPLICABLE
                                )
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Navigation buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (currentStep > 0) {
                OutlinedButton(
                    onClick = { viewModel.previousStep() },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFF4A7C59)
                    )
                ) {
                    Text("← " + stringResource(R.string.back))
                }
            } else {
                OutlinedButton(
                    onClick = onBack,
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFF4A7C59)
                    )
                ) {
                    Text(stringResource(R.string.exit))
                }
            }

            Button(
                onClick = {
                    if (currentStep < items.size - 1) {
                        viewModel.nextStep()
                    } else {
                        onFinished()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4A7C59)
                )
            ) {
                Text(
                    if (currentStep < items.size - 1)
                        stringResource(R.string.next)
                    else
                        stringResource(R.string.see_score)
                )
            }
        }
    }
}

@Composable
private fun StateButton(
    label: String,
    isSelected: Boolean,
    selectedColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(if (isSelected) selectedColor else Color(0xFFF5F3EF))
            .border(
                width = 1.dp,
                color = if (isSelected) selectedColor else Color(0xFFE2DDD5),
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
            color = if (isSelected) Color.White else Color(0xFF5F5E5A),
            textAlign = TextAlign.Center
        )
    }
}