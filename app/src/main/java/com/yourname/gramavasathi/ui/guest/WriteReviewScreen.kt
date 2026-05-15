package com.yourname.gramavasathi.ui.guest

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yourname.gramavasathi.viewmodel.ReviewViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WriteReviewScreen(
    listingId: String,
    onReviewSubmitted: () -> Unit,
    onBack: () -> Unit,
    viewModel: ReviewViewModel = hiltViewModel()
) {
    val overallRating by viewModel.overallRating.collectAsState()
    val aspectRatings by viewModel.aspectRatings.collectAsState()
    val selectedTags by viewModel.selectedTags.collectAsState()
    val reviewText by viewModel.reviewText.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val starHints = listOf("", "Poor", "Fair", "Good", "Great", "Excellent!")
    val tags = listOf(
        "Scenic views", "Home-cooked food", "Friendly hosts",
        "Farm activities", "Clean rooms", "Safe water", "Nature walks"
    )
    val aspects = listOf("cleanliness", "food", "hospitality", "activities")

    LaunchedEffect(Unit) {
        viewModel.submitEvent.collect { event ->
            when (event) {
                is ReviewViewModel.SubmitEvent.Success -> {
                    onReviewSubmitted()
                }
                is ReviewViewModel.SubmitEvent.Error -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(event.message)
                    }
                }
            }
        }
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
                "Write a Review",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Overall rating
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Overall rating",
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        (1..5).forEach { star ->
                            Icon(
                                imageVector = if (star <= overallRating)
                                    Icons.Default.Star
                                else
                                    Icons.Outlined.Star,
                                contentDescription = "$star stars",
                                tint = if (star <= overallRating)
                                    Color(0xFFD4A017)
                                else
                                    Color(0xFFE2DDD5),
                                modifier = Modifier
                                    .size(36.dp)
                                    .clickable {
                                        viewModel.setOverallRating(star)
                                    }
                            )
                        }
                    }
                    if (overallRating > 0) {
                        Text(
                            starHints[overallRating],
                            fontSize = 12.sp,
                            color = Color(0xFF4A7C59),
                            modifier = Modifier.padding(top = 6.dp)
                        )
                    }
                }
            }

            // Aspect ratings
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Rate specific aspects",
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    aspects.forEach { aspect ->
                        val rating = aspectRatings[aspect] ?: 0
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                aspect.replaceFirstChar { it.uppercase() },
                                fontSize = 13.sp,
                                modifier = Modifier.weight(1f)
                            )
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                (1..5).forEach { star ->
                                    Icon(
                                        imageVector = if (star <= rating)
                                            Icons.Default.Star
                                        else
                                            Icons.Outlined.Star,
                                        contentDescription = null,
                                        tint = if (star <= rating)
                                            Color(0xFFD4A017)
                                        else
                                            Color(0xFFE2DDD5),
                                        modifier = Modifier
                                            .size(22.dp)
                                            .clickable {
                                                viewModel.setAspectRating(
                                                    aspect, star
                                                )
                                            }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Tags
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "What did you enjoy?",
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        tags.forEach { tag ->
                            FilterChip(
                                selected = tag in selectedTags,
                                onClick = { viewModel.toggleTag(tag) },
                                label = { Text(tag, fontSize = 12.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = Color(0xFF4A7C59),
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }
                }
            }

            // Review text
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Your experience",
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = reviewText,
                        onValueChange = { viewModel.updateReviewText(it) },
                        placeholder = {
                            Text(
                                "Share what made your stay special...",
                                fontSize = 12.sp
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 4
                    )
                    Text(
                        "${reviewText.length}/20 minimum",
                        fontSize = 11.sp,
                        color = if (reviewText.length >= 20)
                            Color(0xFF4A7C59)
                        else
                            Color(0xFF888780),
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(top = 4.dp)
                    )
                }
            }

            Button(
                onClick = {
                    viewModel.submitReview(
                        listingId = listingId,
                        guestId = "guest_demo",
                        guestName = "Guest"
                    )
                },
                enabled = overallRating > 0 && reviewText.length >= 20,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4A7C59),
                    disabledContainerColor = Color(0xFFE2DDD5)
                )
            ) {
                Text(
                    "Submit Review",
                    fontSize = 15.sp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}