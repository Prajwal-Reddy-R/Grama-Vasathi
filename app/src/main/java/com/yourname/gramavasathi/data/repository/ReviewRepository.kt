package com.yourname.gramavasathi.data.repository

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.yourname.gramavasathi.data.model.Review
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReviewRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    fun getReviews(listingId: String): Flow<List<Review>> = callbackFlow {
        val listener = firestore.collection("reviews")
            .whereEqualTo("listingId", listingId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(emptyList())
                    return@addSnapshotListener
                }
                val reviews = snapshot?.toObjects(Review::class.java) ?: emptyList()
                trySend(reviews)
            }
        awaitClose { listener.remove() }
    }

    suspend fun submitReview(review: Review): Result<Unit> = runCatching {
        val batch = firestore.batch()
        val reviewRef = firestore.collection("reviews").document()
        batch.set(reviewRef, review.copy(id = reviewRef.id))
        val listingRef = firestore.collection("listings").document(review.listingId)
        batch.update(
            listingRef,
            "reviewCount", FieldValue.increment(1)
        )
        batch.commit().await()
    }
}