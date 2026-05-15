package com.yourname.gramavasathi.ui.guest

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import coil.compose.AsyncImage
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import android.content.Intent
import com.yourname.gramavasathi.R
import com.yourname.gramavasathi.viewmodel.ListingDetailViewModel
import com.yourname.gramavasathi.ui.components.ReadinessScoreMeter
import com.yourname.gramavasathi.ui.components.HostBadgeRow

@OptIn(ExperimentalLayoutApi::class, androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun ListingDetailScreen(
    listingId: String,
    onBookNow: () -> Unit,
    onSeeReviews: () -> Unit,
    onWriteReview: () -> Unit,
    onBack: () -> Unit,
    viewModel: ListingDetailViewModel = hiltViewModel()
) {
    val listing by viewModel.listing.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(listingId) {
        viewModel.loadListing(listingId)
    }

    if (isLoading && listing == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color(0xFF4A7C59))
        }
        return
    }

    val listingData = listing ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAF7F2))
    ) {
        // Top bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF4A7C59))
                .padding(top = 40.dp, start = 8.dp, end = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    tint = Color.White
                )
            }
            Text(
                text = listingData.title,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, context.getString(R.string.share_msg, listingData.title, listingData.villageName))
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                context.startActivity(shareIntent)
            }) {
                Icon(Icons.Default.Share, contentDescription = "Share", tint = Color.White)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            // Horizontal Pager for Images
            if (listingData.imageUrls.isNotEmpty()) {
                val pagerState = rememberPagerState(pageCount = { listingData.imageUrls.size })
                Box(modifier = Modifier.fillMaxWidth().height(240.dp)) {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxSize()
                    ) { page ->
                        AsyncImage(
                            model = listingData.imageUrls[page],
                            contentDescription = listingData.title,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                    // Page indicator
                    Surface(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(12.dp),
                        color = Color.Black.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "${pagerState.currentPage + 1} / ${listingData.imageUrls.size}",
                            color = Color.White,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color(0xFFEAF3DE)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🌾", fontSize = 64.sp)
                }
            }
            
            // Score Meter Section
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = listingData.title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2C2C2A)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${listingData.villageName}, ${listingData.taluk}, ${listingData.district}",
                        fontSize = 13.sp,
                        color = Color(0xFF5F5E5A)
                    )
                }
                ReadinessScoreMeter(score = listingData.readinessScore, size = 60)
            }

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                // Host info
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF4A7C59)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = listingData.hostName.firstOrNull()
                                ?.toString() ?: "H",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = listingData.hostName,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF2C2C2A)
                        )
                        Text(
                            text = stringResource(R.string.your_host),
                            fontSize = 12.sp,
                            color = Color(0xFF5F5E5A)
                        )
                    }
                }

                // Badges
                if (listingData.badges.isNotEmpty()) {
                    HostBadgeRow(badges = listingData.badges, modifier = Modifier.padding(vertical = 8.dp))
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Price and booking
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "₹${listingData.pricePerNight}",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF4A7C59)
                            )
                            Text(
                                text = stringResource(R.string.per_night),
                                fontSize = 12.sp,
                                color = Color(0xFF5F5E5A)
                            )
                        }
                        Button(
                            onClick = onBookNow,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF4A7C59)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                stringResource(R.string.book_now),
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Description
                if (listingData.description.isNotEmpty()) {
                    DetailSection(stringResource(R.string.about_stay)) {
                        Text(
                            text = listingData.description,
                            fontSize = 13.sp,
                            color = Color(0xFF5F5E5A),
                            lineHeight = 20.sp
                        )
                    }
                }

                // Activities
                if (listingData.activities.isNotEmpty()) {
                    DetailSection(stringResource(R.string.farm_experiences)) {
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            listingData.activities.forEach { activity ->
                                Box(
                                    modifier = Modifier
                                        .background(
                                            Color(0xFFEAF3DE),
                                            RoundedCornerShape(20.dp)
                                        )
                                        .padding(
                                            horizontal = 12.dp,
                                            vertical = 6.dp
                                        )
                                ) {
                                    Text(
                                        text = activity.replace("_", " ")
                                            .replaceFirstChar { it.uppercase() },
                                        fontSize = 12.sp,
                                        color = Color(0xFF3B6D11)
                                    )
                                }
                            }
                        }
                    }
                }

                // Amenities
                if (listingData.amenities.isNotEmpty()) {
                    DetailSection(stringResource(R.string.amenities)) {
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            listingData.amenities.forEach { amenity ->
                                Box(
                                    modifier = Modifier
                                        .background(
                                            Color(0xFFE6F1FB),
                                            RoundedCornerShape(20.dp)
                                        )
                                        .padding(
                                            horizontal = 12.dp,
                                            vertical = 6.dp
                                        )
                                ) {
                                    Text(
                                        text = amenity.replace("_", " ")
                                            .replaceFirstChar { it.uppercase() },
                                        fontSize = 12.sp,
                                        color = Color(0xFF185FA5)
                                    )
                                }
                            }
                        }
                    }
                }

                // Village story
                if (listingData.hostBio.isNotEmpty()) {
                    DetailSection(stringResource(R.string.about_village)) {
                        Text(
                            text = listingData.hostBio,
                            fontSize = 13.sp,
                            color = Color(0xFF5F5E5A),
                            lineHeight = 20.sp
                        )
                        if (listingData.nearestLandmark.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "📍 ${listingData.nearestLandmark}",
                                fontSize = 12.sp,
                                color = Color(0xFF4A7C59)
                            )
                        }
                    }
                }

                // Reviews section
                DetailSection(stringResource(R.string.guest_reviews)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "⭐ ${listingData.avgRating}",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2C2C2A)
                            )
                            Text(
                                text = stringResource(R.string.reviews_count, listingData.reviewCount),
                                fontSize = 12.sp,
                                color = Color(0xFF5F5E5A)
                            )
                        }
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedButton(
                                onClick = onSeeReviews,
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = Color(0xFF4A7C59)
                                )
                            ) {
                                Text(stringResource(R.string.see_all_reviews))
                            }
                            Button(
                                onClick = onWriteReview,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF4A7C59)
                                )
                            ) {
                                Text(stringResource(R.string.write_review))
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun DetailSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        Text(
            text = title,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF2C2C2A),
            modifier = Modifier.padding(bottom = 10.dp)
        )
        content()
    }
}