package com.yourname.gramavasathi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.yourname.gramavasathi.data.model.Booking
import com.yourname.gramavasathi.data.repository.BookingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GuestBookingsViewModel @Inject constructor(
    private val bookingRepository: BookingRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _bookings = MutableStateFlow<List<Booking>>(emptyList())
    val bookings: StateFlow<List<Booking>> = _bookings.asStateFlow()

    fun loadBookings() {
        val guestId = auth.currentUser?.uid ?: return
        viewModelScope.launch {
            bookingRepository.getGuestBookings(guestId)
                .collect { list: List<Booking> ->
                    _bookings.value = list
                }
        }
    }
}