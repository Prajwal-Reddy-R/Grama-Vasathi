package com.yourname.gramavasathi.data.model

import com.google.firebase.Timestamp

data class Listing(
    val id: String = "",
    val title: String = "",
    val villageName: String = "",
    val hostName: String = "",
    val hostId: String = "",
    val hostBio: String = "",
    val description: String = "",
    val nearestLandmark: String = "",
    val amenities: List<String> = emptyList(),
    val activities: List<String> = emptyList(),
    val readinessScore: Int = 0,
    val pricePerNight: Int = 0,
    val imageUrls: List<String> = emptyList(),
    val avgRating: Double = 0.0,
    val reviewCount: Int = 0,
    val badges: List<String> = emptyList(),
    val district: String = "",
    val taluk: String = "",
    val isPublished: Boolean = false,
    val createdAt: Timestamp = Timestamp.now()
)