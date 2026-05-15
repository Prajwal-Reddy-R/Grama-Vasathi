package com.yourname.gramavasathi.ui.host

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.yourname.gramavasathi.util.BadgeUtils
import com.yourname.gramavasathi.util.ScoreCalculator
import com.yourname.gramavasathi.viewmodel.HostViewModel

@Composable
fun ScoreScreen(
    onListingPublished: () -> Unit,
    onRetakeChecklist: () -> Unit,
    viewModel: HostViewModel = hiltViewModel()
) {
    val score by viewModel.readinessScore.collectAsState()
    val items by viewModel.checklistItems.collectAsState()
    val draftListing by viewModel.draftListing.collectAsState()
    val isPublishing by viewModel.isPublishing.collectAsState()

    var showSuccess by remember { mutableStateOf(false) }

    val scoreColor = when {
        score >= 80 -> Color(0xFF4A7C59)
        score >= 50 -> Color(0xFFD4A017)
        else -> Color(0xFFD85A30)
    }

    val badges = ScoreCalculator.getBadges(score, draftListing.amenities)
    val suggestions = ScoreCalculator.getSuggestions(items)

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFAF7F2))
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF4A7C59))
                    .padding(top = 40.dp, start = 8.dp, end = 8.dp, bottom = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.your_readiness_score),
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Score Meter
                com.yourname.gramavasathi.ui.components.ReadinessScoreMeter(
                    score = score,
                    size = 160
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Badges
                if (badges.isNotEmpty()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFEAF3DE)
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = stringResource(R.string.badges_earned),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF2C2C2A)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                badges.forEach { badge ->
                                    Box(
                                        modifier = Modifier
                                            .background(
                                                Color(0xFF4A7C59),
                                                RoundedCornerShape(20.dp)
                                            )
                                            .padding(
                                                horizontal = 12.dp,
                                                vertical = 6.dp
                                            )
                                    ) {
                                        Text(
                                            text = "${BadgeUtils.getBadgeEmoji(badge)} ${BadgeUtils.getBadgeLabel(badge)}",
                                            fontSize = 12.sp,
                                            color = Color.White
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Warning or success card
                if (score < 50) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFFCEBEB)
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = stringResource(R.string.score_low_msg),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFFA32D2D)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = stringResource(R.string.top_improvements),
                                fontSize = 12.sp,
                                color = Color(0xFF5F5E5A)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            suggestions.forEach { suggestion ->
                                Text(
                                    text = "• $suggestion",
                                    fontSize = 12.sp,
                                    color = Color(0xFF5F5E5A),
                                    modifier = Modifier.padding(vertical = 2.dp)
                                )
                            }
                        }
                    }
                } else {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFEAF3DE)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = stringResource(R.string.great_work_ready),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF3B6D11),
                                textAlign = TextAlign.Center
                            )
                            if (suggestions.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = stringResource(R.string.optional_improvements),
                                    fontSize = 12.sp,
                                    color = Color(0xFF5F5E5A)
                                )
                                suggestions.forEach { suggestion ->
                                    Text(
                                        text = "• $suggestion",
                                        fontSize = 12.sp,
                                        color = Color(0xFF5F5E5A),
                                        modifier = Modifier.padding(vertical = 2.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Action buttons
                Button(
                    onClick = {
                        viewModel.publishListing(draftListing) {
                            showSuccess = true
                        }
                    },
                    enabled = score >= 50 && !isPublishing,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4A7C59),
                        disabledContainerColor = Color(0xFFE2DDD5)
                    )
                ) {
                    if (isPublishing) {
                        androidx.compose.material3.CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            text = stringResource(R.string.publish_listing_btn),
                            fontSize = 15.sp,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedButton(
                    onClick = {
                        viewModel.resetChecklist()
                        onRetakeChecklist()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFF4A7C59)
                    )
                ) {
                    Text(text = stringResource(R.string.retake_checklist))
                }
            }
        }

        // Success overlay
        if (showSuccess) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f))
                    .clickable(enabled = false) {},
                contentAlignment = Alignment.Center
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.padding(24.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("🎉", fontSize = 56.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = stringResource(R.string.stay_published_title),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2C2C2A)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = stringResource(R.string.live_msg),
                            fontSize = 14.sp,
                            color = Color(0xFF5F5E5A),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(
                            onClick = { onListingPublished() },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF4A7C59)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                stringResource(R.string.see_guest_view),
                                fontSize = 15.sp,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}