package com.yourname.gramavasathi.data.model

import com.google.firebase.Timestamp

/**
 * Represents a review for a homestay listing.
 *
 * @property id Unique identifier for the review.
 * @property listingId ID of the listing being reviewed.
 * @property guestId ID of the guest who wrote the review.
 * @property guestName Name of the guest.
 * @property overallRating Overall score given by the guest.
 * @property aspects Detailed ratings for cleanliness, food, hospitality, and activities.
 * @property tags Keywords describing the experience (e.g., "Authentic", "Scenic").
 * @property reviewText The written review content.
 * @property createdAt Timestamp when the review was submitted.
 */


data class Review(
    val id: String = "",
    val listingId: String = "",
    val guestId: String = "",
    val guestName: String = "",
    val overallRating: Int = 0,
    val aspects: Map<String, Int> = emptyMap(),
    val tags: List<String> = emptyList(),
    val reviewText: String = "",
    val createdAt: Timestamp = Timestamp.now()
)