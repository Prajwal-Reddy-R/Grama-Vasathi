package com.yourname.gramavasathi.ui.guest

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.yourname.gramavasathi.R
import com.yourname.gramavasathi.data.model.Listing
import com.yourname.gramavasathi.util.SnackbarController
import com.yourname.gramavasathi.viewmodel.GuestViewModel

@Composable
fun GuestHomeScreen(
    onListingClick: (String) -> Unit,
    onWishlistClick: () -> Unit,
    onImpactClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onCulturalGuideClick: () -> Unit,
    onMyBookingsClick: () -> Unit,
    onHostLoginClick: () -> Unit,
    onLogout: () -> Unit,
    viewModel: GuestViewModel = hiltViewModel()
) {
    val filteredListings by viewModel.filteredListings.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedActivityFilters by viewModel.selectedActivityFilters.collectAsState()
    val selectedAmenityFilters by viewModel.selectedAmenityFilters.collectAsState()
    val wishlistIds by viewModel.wishlistIds.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val recentlyAddedStay by viewModel.recentlyAddedStay.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    if (recentlyAddedStay != null) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissNewStayNotification() },
            title = { Text("✨ Stay Added Successfully!") },
            text = {
                Text(
                    "Your stay \"${recentlyAddedStay?.title}\" has been published and is now live."
                )
            },
            confirmButton = {
                TextButton(onClick = { viewModel.dismissNewStayNotification() }) {
                    Text("Great!")
                }
            },
            containerColor = Color.White,
            titleContentColor = Color(0xFF4A7C59)
        )
    }

    val activityKeys = listOf(
        "cow_milking", "birdwatching", "local_cooking",
        "field_plowing", "fishing", "nature_walk"
    )
    val activityLabels = mapOf(
        "cow_milking" to "🐄 Cow Milking",
        "birdwatching" to "🦜 Birdwatching",
        "local_cooking" to "🍛 Local Cooking",
        "field_plowing" to "🌾 Field Plowing",
        "fishing" to "🎣 Fishing",
        "nature_walk" to "🌿 Nature Walk"
    )
    val amenityKeys = listOf(
        "safe_water", "western_toilet",
        "food_included", "family_friendly"
    )
    val amenityLabels = mapOf(
        "safe_water" to "💧 Safe Water",
        "western_toilet" to "🚿 Western Toilet",
        "food_included" to "🍽 Food Included",
        "family_friendly" to "👨‍👩‍👧 Family Friendly"
    )

    LaunchedEffect(Unit) {
        SnackbarController.events.collect { event ->
            snackbarHostState.showSnackbar(
                message = event.message,
                actionLabel = event.actionLabel,
                duration = SnackbarDuration.Short
            )
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFAF7F2))
        ) {
            // Top bar
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF4A7C59))
                    .padding(
                        top = 40.dp,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 12.dp
                    )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = stringResource(R.string.app_name),
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1
                        )
                        Text(
                            text = stringResource(R.string.find_stays_subtitle),
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 12.sp,
                            maxLines = 1
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = onSettingsClick) {
                            Icon(
                                Icons.Default.Settings,
                                contentDescription = "Settings",
                                tint = Color.White,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        IconButton(onClick = onLogout) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                contentDescription = stringResource(R.string.logout),
                                tint = Color.White,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                
                // Secondary navigation icons in a scrollable row to prevent disappearing
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    NavIconButton(onClick = onCulturalGuideClick, icon = "🙏", label = stringResource(R.string.cultural_guide_title))
                    NavIconButton(onClick = onMyBookingsClick, icon = "📅", label = stringResource(R.string.my_bookings))
                    NavIconButton(onClick = onWishlistClick, icon = "❤️", label = stringResource(R.string.wishlist))
                    NavIconButton(onClick = onImpactClick, icon = "📊", label = stringResource(R.string.impact_label))
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { viewModel.updateSearch(it) },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.search_placeholder),
                            fontSize = 13.sp,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = Color.White
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(
                                onClick = { viewModel.updateSearch("") }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear",
                                    tint = Color.White
                                )
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color.White.copy(alpha = 0.5f),
                        unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
                        cursorColor = Color.White
                    ),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )
            }

            // Activity filters
            LazyRow(
                modifier = Modifier.padding(
                    horizontal = 12.dp,
                    vertical = 8.dp
                ),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(activityKeys) { key ->
                    val label = activityLabels[key] ?: key
                    FilterChip(
                        selected = key in selectedActivityFilters,
                        onClick = { viewModel.toggleActivityFilter(key) },
                        label = { Text(text = label, fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFF4A7C59),
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            // Amenity filters
            LazyRow(
                modifier = Modifier.padding(
                    start = 12.dp,
                    end = 12.dp,
                    bottom = 4.dp
                ),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(amenityKeys) { key ->
                    val label = amenityLabels[key] ?: key
                    FilterChip(
                        selected = key in selectedAmenityFilters,
                        onClick = { viewModel.toggleAmenityFilter(key) },
                        label = { Text(text = label, fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFF4A7C59),
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            // Clear filters button
            if (selectedActivityFilters.isNotEmpty() ||
                selectedAmenityFilters.isNotEmpty() ||
                searchQuery.isNotEmpty()
            ) {
                TextButton(
                    onClick = { viewModel.clearFilters() },
                    modifier = Modifier.padding(horizontal = 12.dp)
                ) {
                    Text(
                        text = "✕ " + stringResource(R.string.clear_filters),
                        color = Color(0xFF4A7C59),
                        fontSize = 12.sp
                    )
                }
            }

            // Content
            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "🌾", fontSize = 40.sp)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = stringResource(R.string.loading_stays),
                                color = Color(0xFF5F5E5A),
                                fontSize = 14.sp
                            )
                        }
                    }
                }

                filteredListings.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(24.dp)
                        ) {
                            Text(text = "🔍", fontSize = 40.sp)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = stringResource(R.string.no_stays_found),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF2C2C2A)
                            )
                            Text(
                                text = stringResource(R.string.adjust_filters_hint),
                                fontSize = 13.sp,
                                color = Color(0xFF5F5E5A)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            TextButton(
                                onClick = { viewModel.clearFilters() }
                            ) {
                                Text(
                                    text = stringResource(R.string.clear_filters),
                                    color = Color(0xFF4A7C59)
                                )
                            }
                        }
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = filteredListings,
                            key = { it.id }
                        ) { listing ->
                            GuestListingCard(
                                listing = listing,
                                isWishlisted = listing.id in wishlistIds,
                                onWishlistToggle = {
                                    viewModel.toggleWishlist(listing.id)
                                },
                                onClick = { onListingClick(listing.id) }
                            )
                        }
                    }
                }
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun NavIconButton(
    onClick: () -> Unit,
    icon: String,
    label: String
) {
    Column(
        modifier = Modifier
            .clickable { onClick() }
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = icon, fontSize = 18.sp)
        Text(
            text = label,
            fontSize = 9.sp,
            color = Color.White.copy(alpha = 0.9f),
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun GuestListingCard(
    listing: Listing,
    isWishlisted: Boolean,
    onWishlistToggle: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .background(
                        Color(0xFFEAF3DE),
                        RoundedCornerShape(
                            topStart = 14.dp,
                            topEnd = 14.dp
                        )
                    )
            ) {
                if (listing.imageUrls.isNotEmpty()) {
                    AsyncImage(
                        model = listing.imageUrls.first(),
                        contentDescription = listing.title,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = androidx.compose.ui.layout.ContentScale.Crop
                    )
                } else {
                    Text(
                        text = "🌾",
                        fontSize = 48.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(10.dp)
                        .background(
                            Color(0xFF4A7C59),
                            RoundedCornerShape(20.dp)
                        )
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = stringResource(R.string.ready_badge, listing.readinessScore),
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp)
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.9f))
                        .clickable { onWishlistToggle() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isWishlisted)
                            Icons.Default.Favorite
                        else
                            Icons.Default.FavoriteBorder,
                        contentDescription = "Wishlist",
                        tint = if (isWishlisted)
                            Color(0xFFD85A30)
                        else
                            Color(0xFF888780),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = listing.title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF2C2C2A)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "${listing.villageName}, ${listing.taluk}, ${listing.district}",
                    fontSize = 12.sp,
                    color = Color(0xFF5F5E5A)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "⭐", fontSize = 13.sp)
                        Text(
                            text = " ${listing.avgRating} " +
                                    "(${listing.reviewCount})",
                            fontSize = 12.sp,
                            color = Color(0xFF5F5E5A)
                        )
                    }
                    Text(
                        text = stringResource(R.string.price_per_night, listing.pricePerNight),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF4A7C59)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    listing.activities.take(3).forEach { activity ->
                        Box(
                            modifier = Modifier
                                .background(
                                    Color(0xFFEAF3DE),
                                    RoundedCornerShape(20.dp)
                                )
                                .padding(
                                    horizontal = 8.dp,
                                    vertical = 3.dp
                                )
                        ) {
                            Text(
                                text = activity
                                    .replace("_", " ")
                                    .replaceFirstChar { it.uppercase() },
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
