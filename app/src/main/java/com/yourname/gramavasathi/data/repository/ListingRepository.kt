package com.yourname.gramavasathi.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.yourname.gramavasathi.data.model.Listing
import com.yourname.gramavasathi.util.MockData
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ListingRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    fun getListings(): Flow<List<Listing>> = callbackFlow {
        val listener = firestore.collection("listings")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(MockData.listings)
                    return@addSnapshotListener
                }
                
                val listings = try {
                    snapshot?.toObjects(Listing::class.java) ?: emptyList()
                } catch (e: Exception) {
                    emptyList()
                }

                // Filter for published items in Kotlin to avoid field name mismatch issues in Firestore queries
                val publishedListings = listings.filter { it.isPublished }

                // Avoid duplicates: filter out any Firestore listings that use mock IDs
                val mockIds = MockData.listings.map { it.id }.toSet()
                val realListings = publishedListings.filter { it.id !in mockIds }
                
                val result = (MockData.listings + realListings).sortedByDescending { it.createdAt }
                trySend(result)
            }
        awaitClose { listener.remove() }
    }

    fun getListing(listingId: String): Flow<Listing?> = callbackFlow {
        val mockListing = MockData.listings.find { it.id == listingId }
        val listener = firestore.collection("listings")
            .document(listingId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(mockListing)
                    return@addSnapshotListener
                }
                val listing = snapshot?.toObject(Listing::class.java)
                trySend(listing ?: mockListing)
            }
        awaitClose { listener.remove() }
    }

//    suspend fun publishListing(listing: Listing): Result<Unit> = runCatching {
//        val docRef = if (listing.id.isEmpty())
//            firestore.collection("listings").document()
//        else
//            firestore.collection("listings").document(listing.id)
//
//        val finalListing = listing.copy(
//            id = docRef.id,
//            isPublished = true
//        )
//        docRef.set(finalListing).await()
//    }
suspend fun publishListing(listing: Listing): Result<Unit> = runCatching {
    val docRef = if (listing.id.isEmpty())
        firestore.collection("listings").document()
    else
        firestore.collection("listings").document(listing.id)

    val finalListing = listing.copy(
        id = docRef.id,
        isPublished = true,
        createdAt = com.google.firebase.Timestamp.now()
    )
    docRef.set(finalListing).await()
}

    suspend fun updateListing(listing: Listing): Result<Unit> = runCatching {
        firestore.collection("listings")
            .document(listing.id)
            .set(listing)
            .await()
    }

    suspend fun deleteListing(listingId: String): Result<Unit> = runCatching {
        firestore.collection("listings")
            .document(listingId)
            .delete()
            .await()
    }
}
