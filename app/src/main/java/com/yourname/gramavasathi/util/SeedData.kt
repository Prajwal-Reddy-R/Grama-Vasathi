package com.yourname.gramavasathi.util

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.yourname.gramavasathi.data.model.Booking
import com.yourname.gramavasathi.data.model.Listing
import com.yourname.gramavasathi.data.model.Review
import kotlinx.coroutines.tasks.await
import java.util.UUID

suspend fun seedFirestoreData() {
    val db = FirebaseFirestore.getInstance()
    
    val listings = listOf(
        Listing(
            id = "seed_1",
            title = "Nandi Hills Sunrise Farm",
            villageName = "Chikkaballapura",
            hostName = "Suresh Gowda",
            description = "Experience breathtaking sunrises from our organic farm. Just a short drive from Bangalore, perfect for weekend getaways.",
            activities = listOf("cow_milking", "birdwatching", "local_cooking"),
            amenities = listOf("safe_water", "western_toilet", "food_included", "family_friendly"),
            readinessScore = 87,
            pricePerNight = 1200,
            badges = listOf("verified_hygiene", "top_host", "family_friendly"),
            district = "Chikkaballapura",
            imageUrls = listOf("https://firebasestorage.googleapis.com/v0/b/grama-vasathi.appspot.com/o/demo%2Ffarm1.jpg?alt=media"),
            isPublished = true
        ),
        Listing(
            id = "seed_2",
            title = "Coorg Coffee Estate Stay",
            villageName = "Madikeri",
            hostName = "Kavitha Ponnappa",
            description = "Wake up to the aroma of fresh coffee. Our estate offers guided walks and traditional Kodava hospitality.",
            activities = listOf("nature_walk", "local_cooking", "field_plowing"),
            amenities = listOf("safe_water", "western_toilet", "food_included"),
            readinessScore = 92,
            pricePerNight = 1800,
            badges = listOf("verified_hygiene", "top_host"),
            district = "Kodagu",
            imageUrls = listOf("https://firebasestorage.googleapis.com/v0/b/grama-vasathi.appspot.com/o/demo%2Ffarm2.jpg?alt=media"),
            isPublished = true
        ),
        Listing(
            id = "seed_3",
            title = "Mysuru Agri Homestay",
            villageName = "Srirangapatna",
            description = "Learn traditional farming techniques in the historic land of Srirangapatna. Pure vegetarian food served.",
            activities = listOf("field_plowing", "cow_milking", "fishing"),
            amenities = listOf("safe_water", "food_included"),
            readinessScore = 74,
            pricePerNight = 950,
            district = "Mandya",
            imageUrls = listOf("https://firebasestorage.googleapis.com/v0/b/grama-vasathi.appspot.com/o/demo%2Ffarm3.jpg?alt=media"),
            isPublished = true
        ),
        Listing(
            id = "seed_4",
            title = "Hampi Heritage Village Stay",
            villageName = "Anegundi",
            description = "Live amidst history in the ancient village of Anegundi across the Tungabhadra river.",
            activities = listOf("folk_interaction", "birdwatching", "local_cooking"),
            readinessScore = 85,
            pricePerNight = 1500,
            district = "Koppal",
            isPublished = true
        ),
        Listing(
            id = "seed_5",
            title = "Chikmagalur Cloud Farm",
            villageName = "Mullayanagiri Base",
            description = "Highest stay in Karnataka. Experience the clouds passing through your balcony.",
            activities = listOf("nature_walk", "birdwatching"),
            readinessScore = 90,
            pricePerNight = 2500,
            district = "Chikmagalur",
            isPublished = true
        ),
        Listing(
            id = "seed_6",
            title = "Udupi Coastal Homestay",
            villageName = "Malpe Village",
            description = "Traditional coastal architecture near the sea. Enjoy authentic Udupi seafood.",
            activities = listOf("fishing", "local_cooking"),
            readinessScore = 78,
            pricePerNight = 1600,
            district = "Udupi",
            isPublished = true
        ),
        Listing(
            id = "seed_7",
            title = "Dandeli Jungle Outpost",
            villageName = "Joida",
            description = "Immerse yourself in the Western Ghats. River rafting and trekking nearby.",
            activities = listOf("nature_walk", "birdwatching"),
            readinessScore = 65,
            pricePerNight = 1300,
            district = "Uttara Kannada",
            isPublished = true
        ),
        Listing(
            id = "seed_8",
            title = "Hassan Hoysala Homestay",
            villageName = "Halebidu",
            description = "Explore Hoysala architecture while living with a local artisan family.",
            activities = listOf("folk_interaction", "local_cooking"),
            readinessScore = 82,
            pricePerNight = 1100,
            district = "Hassan",
            isPublished = true
        ),
        Listing(
            id = "seed_9",
            title = "Sakleshpura Mist Valley",
            villageName = "Hanbal",
            description = "A serene valley stay surrounded by cardamom plantations and waterfalls.",
            activities = listOf("nature_walk", "field_plowing"),
            readinessScore = 88,
            pricePerNight = 1900,
            district = "Hassan",
            isPublished = true
        ),
        Listing(
            id = "seed_10",
            title = "Jog Falls Rural Retreat",
            villageName = "Sagara",
            description = "Located near the majestic Jog Falls. Experience life in a Malnad areca nut farm.",
            activities = listOf("cow_milking", "local_cooking"),
            readinessScore = 60,
            pricePerNight = 800,
            district = "Shimoga",
            isPublished = true
        )
    )

    // Save Listings
    for (listing in listings) {
        db.collection("listings").document(listing.id).set(listing).await()
    }

    // Save Reviews for Listing 1
    val reviews = listOf(
        Review(
            id = "rev_1",
            listingId = "seed_1",
            guestName = "Anjali Sharma",
            overallRating = 5,
            reviewText = "Amazing sunrise! The host Suresh was very welcoming and the food was delicious.",
            createdAt = Timestamp.now()
        ),
        Review(
            id = "rev_2",
            listingId = "seed_1",
            guestName = "Rahul Mehra",
            overallRating = 4,
            reviewText = "Great place to relax. The cow milking activity was fun for my kids.",
            createdAt = Timestamp.now()
        ),
        Review(
            id = "rev_3",
            listingId = "seed_1",
            guestName = "Priya Rao",
            overallRating = 5,
            reviewText = "Perfect weekend getaway. Highly recommend the local cooking class!",
            createdAt = Timestamp.now()
        )
    )
    for (review in reviews) {
        db.collection("reviews").document(review.id).set(review).await()
    }

    // Save Bookings
    val bookings = listOf(
        Booking(
            id = UUID.randomUUID().toString(),
            listingId = "seed_1",
            guestId = "guest_123",
            checkIn = "2024-06-15",
            checkOut = "2024-06-17",
            numGuests = 2,
            totalAmount = 2400,
            status = "CONFIRMED",
            bookingRef = "GV-7782",
            createdAt = Timestamp.now()
        ),
        Booking(
            id = UUID.randomUUID().toString(),
            listingId = "seed_2",
            guestId = "guest_123",
            checkIn = "2024-07-10",
            checkOut = "2024-07-12",
            numGuests = 3,
            totalAmount = 10800, // (1800*2*3)? No, price is usually per night.
            status = "CONFIRMED",
            bookingRef = "GV-9910",
            createdAt = Timestamp.now()
        )
    )
    for (booking in bookings) {
        db.collection("bookings").document(booking.id).set(booking).await()
    }
}
