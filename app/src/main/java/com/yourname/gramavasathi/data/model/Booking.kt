package com.yourname.gramavasathi.data.model

import com.google.firebase.Timestamp


/**
 * Represents a booking for a homestay.
 *
 * @property id Unique identifier for the booking.
 * @property listingId ID of the homestay listing.
 * @property guestId ID of the user who made the booking.
 * @property checkIn Check-in date in yyyy-MM-dd format.
 * @property checkOut Check-out date in yyyy-MM-dd format.
 * @property numGuests Number of guests for the stay.
 * @property roomPreference Specific room request or preference.
 * @property totalAmount Total price paid for the booking.
 * @property status Status of the booking (e.g., "CONFIRMED", "PENDING", "CANCELLED").
 * @property bookingRef Human-readable reference code for the booking.
 * @property createdAt Timestamp when the booking was made.
 */

data class Booking(
    val id: String = "",
    val listingId: String = "",
    val guestId: String = "",
    val checkIn: String = "",
    val checkOut: String = "",
    val numGuests: Int = 1,
    val roomPreference: String = "Standard",
    val totalAmount: Int = 0,
    val status: String = "confirmed",
    val bookingRef: String = "",
    val createdAt: Timestamp = Timestamp.now()
)
