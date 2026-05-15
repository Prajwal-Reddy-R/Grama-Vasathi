package com.yourname.gramavasathi.ui.guest

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yourname.gramavasathi.data.model.Review
import com.yourname.gramavasathi.viewmodel.ReviewViewModel

@Composable
fun ReviewListScreen(
    listingId: String,
    onWriteReview: () -> Unit,
    onBack: () -> Unit,
    viewModel: ReviewViewModel = hiltViewModel()
) {
    val reviews by viewModel.reviews.collectAsState()
    val averageRating by viewModel.averageRating.collectAsState()
    val ratingDistribution by viewModel.ratingDistribution.collectAsState()

    LaunchedEffect(listingId) {
        viewModel.loadReviews(listingId)
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
            Text(
                "Guest Reviews",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Rating summary
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = String.format("%.1f", averageRating),
                                        fontSize = 40.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF2C2C2A)
                                    )
                                    Text(
                                        "⭐".repeat(
                                            averageRating.toInt().coerceIn(0, 5)
                                        ),
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        "${reviews.size} reviews",
                                        fontSize = 12.sp,
                                        color = Color(0xFF888780)
                                    )
                                }
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    (5 downTo 1).forEach { star ->
                                        val count = ratingDistribution[star] ?: 0
                                        val pct = if (reviews.isNotEmpty())
                                            count.toFloat() / reviews.size
                                        else 0f
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                                        ) {
                                            Text(
                                                "$star",
                                                fontSize = 11.sp,
                                                color = Color(0xFF888780)
                                            )
                                            LinearProgressIndicator(
                                                progress = { pct },
                                                modifier = Modifier
                                                    .size(width = 100.dp, height = 6.dp)
                                                    .clip(RoundedCornerShape(3.dp)),
                                                color = Color(0xFFD4A017),
                                                trackColor = Color(0xFFE2DDD5)
                                            )
                                            Text(
                                                "$count",
                                                fontSize = 11.sp,
                                                color = Color(0xFF888780)
                                            )
                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Button(
                                onClick = onWriteReview,
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF4A7C59)
                                )
                            ) {
                                Text("Write a Review")
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "All reviews",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF2C2C2A)
                    )
                }
            }

            if (reviews.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("💬", fontSize = 40.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "No reviews yet",
                                fontSize = 15.sp,
                                color = Color(0xFF5F5E5A)
                            )
                            Text(
                                "Be the first to review this stay",
                                fontSize = 13.sp,
                                color = Color(0xFF888780)
                            )
                        }
                    }
                }
            } else {
                items(reviews) { review ->
                    ReviewCard(
                        review = review,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
fun ReviewCard(
    review: Review,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFEAF3DE)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = review.guestName.firstOrNull()
                            ?.uppercaseChar()?.toString() ?: "G",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF3B6D11)
                    )
                }
                Column {
                    Text(
                        text = review.guestName,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF2C2C2A)
                    )
                    Text(
                        text = "⭐".repeat(review.overallRating),
                        fontSize = 12.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = review.reviewText,
                fontSize = 13.sp,
                color = Color(0xFF5F5E5A),
                lineHeight = 20.sp
            )
            if (review.tags.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    review.tags.take(3).forEach { tag ->
                        Box(
                            modifier = Modifier
                                .background(
                                    Color(0xFFEAF3DE),
                                    RoundedCornerShape(20.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = tag,
                                fontSize = 10.sp,
                                color = Color(0xFF3B6D11)
                            )
                        }
                    }
                }
            }
        }
    }
}