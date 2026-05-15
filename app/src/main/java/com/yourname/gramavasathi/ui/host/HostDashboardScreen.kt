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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yourname.gramavasathi.R
import com.yourname.gramavasathi.viewmodel.HostDashboardViewModel
import com.yourname.gramavasathi.viewmodel.HostViewModel

@Composable
fun HostDashboardScreen(
    onCreateListing: () -> Unit,
    onViewGuidance: () -> Unit,
    onViewChecklist: () -> Unit,
    onEditListing: (com.yourname.gramavasathi.data.model.Listing) -> Unit,
    onSettingsClick: () -> Unit,
    onLogout: () -> Unit,
    viewModel: HostDashboardViewModel = hiltViewModel()
) {
    val listings by viewModel.hostListings.collectAsState()
    val bookings by viewModel.hostBookings.collectAsState()
    val hostName by viewModel.hostName.collectAsState()
    val revenue by viewModel.totalRevenue.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAF7F2))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF4A7C59))
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.namaskara, hostName),
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = stringResource(R.string.manage_stays),
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 12.sp
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onSettingsClick) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = Color.White
                        )
                    }
                    OutlinedButton(
                        onClick = onLogout,
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.White
                        )
                    ) {
                        Text(stringResource(R.string.logout), fontSize = 12.sp)
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Quick actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ActionCard(
                    emoji = "➕",
                    label = stringResource(R.string.add_stay),
                    color = Color(0xFFEAF3DE),
                    modifier = Modifier.weight(1f),
                    onClick = onCreateListing
                )
                ActionCard(
                    emoji = "📖",
                    label = stringResource(R.string.guidance),
                    color = Color(0xFFFAEEDA),
                    modifier = Modifier.weight(1f),
                    onClick = onViewGuidance
                )
            }

            // Stats row
            if (listings.isNotEmpty() || bookings.isNotEmpty()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StatCard(
                        label = stringResource(R.string.my_listings),
                        value = "${listings.size}",
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        label = stringResource(R.string.bookings),
                        value = "${bookings.size}",
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        label = stringResource(R.string.revenue),
                        value = "₹$revenue",
                        modifier = Modifier.weight(1f)
                    )
                }

                // Revenue Chart Section
                RevenueChart(bookings = bookings, totalRevenue = revenue)
            }

            // My listings section
            Text(
                text = stringResource(R.string.my_listings),
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF2C2C2A)
            )

            if (listings.isEmpty()) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFEAF3DE)
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("🏠", fontSize = 36.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = stringResource(R.string.no_listings_yet),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF2C2C2A)
                        )
                        Text(
                            text = stringResource(R.string.create_first_listing_hint),
                            fontSize = 13.sp,
                            color = Color(0xFF5F5E5A),
                            modifier = Modifier.padding(top = 4.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = onCreateListing,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF4A7C59)
                            )
                        ) {
                            Text(stringResource(R.string.add_stay))
                        }
                    }
                }
            } else {
                listings.forEach { listing ->
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Top
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = listing.title,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color(0xFF2C2C2A)
                                    )
                                    Text(
                                        text = listing.villageName,
                                        fontSize = 12.sp,
                                        color = Color(0xFF5F5E5A)
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
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
                                                text = stringResource(R.string.ready_badge, listing.readinessScore),
                                                fontSize = 11.sp,
                                                color = Color(0xFF3B6D11)
                                            )
                                        }
                                        Box(
                                            modifier = Modifier
                                                .background(
                                                    if (listing.isPublished)
                                                        Color(0xFFEAF3DE)
                                                    else
                                                        Color(0xFFFAEEDA),
                                                    RoundedCornerShape(20.dp)
                                                )
                                                .padding(
                                                    horizontal = 8.dp,
                                                    vertical = 3.dp
                                                )
                                        ) {
                                            Text(
                                                text = if (listing.isPublished)
                                                    stringResource(R.string.published)
                                                else
                                                    stringResource(R.string.draft),
                                                fontSize = 11.sp,
                                                color = if (listing.isPublished)
                                                    Color(0xFF3B6D11)
                                                else
                                                    Color(0xFF854F0B)
                                            )
                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                OutlinedButton(
                                    onClick = { onEditListing(listing) },
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        contentColor = Color(0xFF4A7C59)
                                    )
                                ) {
                                    Text(stringResource(R.string.edit), fontSize = 12.sp)
                                }
                                OutlinedButton(
                                    onClick = {
                                        viewModel.deleteListing(listing.id)
                                    },
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        contentColor = Color(0xFFD85A30)
                                    )
                                ) {
                                    Text(stringResource(R.string.remove), fontSize = 12.sp)
                                }
                                Button(
                                    onClick = {
                                        viewModel.togglePublish(listing)
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (listing.isPublished)
                                            Color(0xFFD85A30)
                                        else
                                            Color(0xFF4A7C59)
                                    )
                                ) {
                                    Text(
                                        text = if (listing.isPublished)
                                            stringResource(R.string.unpublish)
                                        else
                                            stringResource(R.string.publish),
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Recent bookings
            if (bookings.isNotEmpty()) {
                Text(
                    text = stringResource(R.string.recent_bookings),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF2C2C2A)
                )
                bookings.take(5).forEach { booking ->
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = stringResource(R.string.guest_booked_msg),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF4A7C59)
                                )
                                Text(
                                    text = stringResource(R.string.booking_ref, booking.bookingRef),
                                    fontSize = 12.sp,
                                    color = Color(0xFF5F5E5A)
                                )
                                Text(
                                    text = stringResource(R.string.booking_dates, booking.checkIn, booking.checkOut),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF2C2C2A)
                                )
                                Text(
                                    text = stringResource(R.string.num_guests_label, booking.numGuests),
                                    fontSize = 12.sp,
                                    color = Color(0xFF888780)
                                )
                            }
                            Column(
                                horizontalAlignment = Alignment.End
                            ) {
                                Text(
                                    text = "₹${booking.totalAmount}",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF4A7C59)
                                )
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
                                        text = booking.status,
                                        fontSize = 11.sp,
                                        color = Color(0xFF3B6D11)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun RevenueChart(
    bookings: List<com.yourname.gramavasathi.data.model.Booking>,
    totalRevenue: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(R.string.earnings_overview),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF2C2C2A)
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            if (bookings.isEmpty()) {
                Text(
                    text = stringResource(R.string.no_earnings),
                    fontSize = 12.sp,
                    color = Color(0xFF888780)
                )
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.Bottom
                ) {
                    // Simple visual bars for last 5 bookings
                    bookings.takeLast(5).forEach { booking ->
                        val barHeight = if (totalRevenue > 0) 
                            (booking.totalAmount.toFloat() / totalRevenue.toFloat() * 100).coerceAtLeast(10f)
                        else 10f
                        
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Box(
                                modifier = Modifier
                                    .width(24.dp)
                                    .height(barHeight.dp)
                                    .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                                    .background(Color(0xFF4A7C59))
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "₹${booking.totalAmount}",
                                fontSize = 9.sp,
                                color = Color(0xFF5F5E5A)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ActionCard(
    emoji: String,
    label: String,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(80.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = color),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = emoji, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF2C2C2A)
            )
        }
    }
}

@Composable
private fun StatCard(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4A7C59)
            )
            Text(
                text = label,
                fontSize = 11.sp,
                color = Color(0xFF5F5E5A)
            )
        }
    }
}