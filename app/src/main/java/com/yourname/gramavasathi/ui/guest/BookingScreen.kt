package com.yourname.gramavasathi.ui.guest

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yourname.gramavasathi.viewmodel.BookingViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun BookingScreen(
    listingId: String,
    onBookingConfirmed: (String) -> Unit,
    onBack: () -> Unit,
    viewModel: BookingViewModel = hiltViewModel()
) {
    val listing by viewModel.listingState.collectAsState()
    val reservedDates by viewModel.reservedDates.collectAsState()
    val checkInDate by viewModel.checkInDate.collectAsState()
    val checkOutDate by viewModel.checkOutDate.collectAsState()
    val numGuests by viewModel.numGuests.collectAsState()
    val totalAmount by viewModel.totalAmount.collectAsState()
    val isValid by viewModel.isValid.collectAsState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(listingId) {
        val price = listing?.pricePerNight ?: 0
        viewModel.setListing(listingId, price)
    }

    LaunchedEffect(listing) {
        listing?.let {
            viewModel.setListing(listingId, it.pricePerNight)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.bookingEvent.collect { event ->
            when (event) {
                is BookingViewModel.BookingEvent.Success -> {
                    onBookingConfirmed(event.bookingRef)
                }
                is BookingViewModel.BookingEvent.Error -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(event.message)
                    }
                }
            }
        }
    }

    if (listing == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color(0xFF4A7C59))
        }
        return
    }

    val listingData = listing!!

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
                    text = "Book your stay",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = listingData.title,
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 12.sp
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Calendar
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Select dates",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF2C2C2A)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    SimpleCalendar(
                        reservedDates = reservedDates,
                        checkInDate = checkInDate,
                        checkOutDate = checkOutDate,
                        onDateSelected = { date -> viewModel.selectDate(date) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "Check-in",
                                fontSize = 11.sp,
                                color = Color(0xFF888780)
                            )
                            Text(
                                text = checkInDate?.toString() ?: "Select",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = if (checkInDate != null)
                                    Color(0xFF4A7C59)
                                else
                                    Color(0xFFE2DDD5)
                            )
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "Check-out",
                                fontSize = 11.sp,
                                color = Color(0xFF888780)
                            )
                            Text(
                                text = checkOutDate?.toString() ?: "Select",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = if (checkOutDate != null)
                                    Color(0xFF4A7C59)
                                else
                                    Color(0xFFE2DDD5)
                            )
                        }
                    }
                }
            }

            // Guest count
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Number of guests",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFEAF3DE))
                                .clickable { viewModel.decrementGuests() },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "−",
                                fontSize = 20.sp,
                                color = Color(0xFF4A7C59),
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Text(
                            text = "$numGuests",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2C2C2A)
                        )
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF4A7C59))
                                .clickable { viewModel.incrementGuests() },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "+",
                                fontSize = 20.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Booking summary
            if (isValid) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFEAF3DE)
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Booking summary",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF2C2C2A)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        SummaryRow(
                            "Price per night",
                            "₹${listingData.pricePerNight}"
                        )
                        SummaryRow("Guests", "$numGuests")
                        SummaryRow("Total", "₹$totalAmount", bold = true)
                    }
                }
            }

            // Confirm button
            Button(
                onClick = {
                    viewModel.confirmBooking(listingId)
                },
                enabled = isValid,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4A7C59),
                    disabledContainerColor = Color(0xFFE2DDD5)
                )
            ) {
                Text(
                    text = "Confirm Reservation",
                    fontSize = 15.sp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun SummaryRow(label: String, value: String, bold: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            label,
            fontSize = 13.sp,
            color = Color(0xFF5F5E5A)
        )
        Text(
            value,
            fontSize = 13.sp,
            fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal,
            color = Color(0xFF2C2C2A)
        )
    }
}

@Composable
private fun SimpleCalendar(
    reservedDates: Set<String>,
    checkInDate: LocalDate?,
    checkOutDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit
) {
    val today = LocalDate.now()
    val yearMonth = YearMonth.of(today.year, today.month)
    val daysInMonth = yearMonth.lengthOfMonth()
    val firstDayOfWeek = yearMonth.atDay(1).dayOfWeek.value % 7

    Column {
        Text(
            text = "${today.month.getDisplayName(TextStyle.FULL, Locale.ENGLISH)} ${today.year}",
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF2C2C2A),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        // Day headers
        Row(modifier = Modifier.fillMaxWidth()) {
            listOf("S", "M", "T", "W", "T", "F", "S").forEach { day ->
                Text(
                    text = day,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontSize = 11.sp,
                    color = Color(0xFF888780),
                    fontWeight = FontWeight.Medium
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        // Days grid
        val totalCells = firstDayOfWeek + daysInMonth
        val rows = (totalCells + 6) / 7
        for (row in 0 until rows) {
            Row(modifier = Modifier.fillMaxWidth()) {
                for (col in 0 until 7) {
                    val dayNumber = row * 7 + col - firstDayOfWeek + 1
                    if (dayNumber < 1 || dayNumber > daysInMonth) {
                        Box(modifier = Modifier.weight(1f))
                    } else {
                        val date = yearMonth.atDay(dayNumber)
                        val dateStr = date.toString()
                        val isPast = date.isBefore(today)
                        val isReserved = dateStr in reservedDates
                        val isCheckIn = date == checkInDate
                        val isCheckOut = date == checkOutDate
                        val isInRange = checkInDate != null &&
                                checkOutDate != null &&
                                date.isAfter(checkInDate) &&
                                date.isBefore(checkOutDate)

                        val bgColor = when {
                            isCheckIn || isCheckOut -> Color(0xFF4A7C59)
                            isInRange -> Color(0xFFEAF3DE)
                            isReserved -> Color(0xFFFCEBEB)
                            else -> Color.Transparent
                        }
                        val textColor = when {
                            isPast || isReserved -> Color(0xFFE2DDD5)
                            isCheckIn || isCheckOut -> Color.White
                            else -> Color(0xFF2C2C2A)
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .padding(2.dp)
                                .clip(CircleShape)
                                .background(bgColor)
                                .clickable(
                                    enabled = !isPast && !isReserved
                                ) { onDateSelected(date) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = dayNumber.toString(),
                                fontSize = 12.sp,
                                color = textColor,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}