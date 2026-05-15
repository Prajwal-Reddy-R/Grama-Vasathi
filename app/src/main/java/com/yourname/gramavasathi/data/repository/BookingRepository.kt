package com.yourname.gramavasathi.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.yourname.gramavasathi.data.model.Booking
import com.yourname.gramavasathi.util.DateUtils
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookingRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    suspend fun confirmBooking(booking: Booking): Result<Unit> = runCatching {
        val docRef = firestore.collection("bookings").document()
        docRef.set(booking.copy(id = docRef.id)).await()
    }

    fun getReservedDates(listingId: String): Flow<Set<String>> = callbackFlow {
        val listener = firestore.collection("bookings")
            .whereEqualTo("listingId", listingId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(emptySet())
                    return@addSnapshotListener
                }
                val dates = mutableSetOf<String>()
                snapshot?.toObjects(Booking::class.java)
                    ?.forEach { booking ->
                        try {
                            val checkIn = LocalDate.parse(booking.checkIn)
                            val checkOut = LocalDate.parse(booking.checkOut)
                            DateUtils.getDatesInRange(checkIn, checkOut)
                                .forEach { dates.add(it) }
                        } catch (e: Exception) {
                            // skip invalid dates
                        }
                    }
                trySend(dates)
            }
        awaitClose { listener.remove() }
    }

    fun getGuestBookings(guestId: String): Flow<List<Booking>> = callbackFlow {
        val listener = firestore.collection("bookings")
            .whereEqualTo("guestId", guestId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(emptyList<Booking>())
                    return@addSnapshotListener
                }
                val bookings = snapshot
                    ?.toObjects(Booking::class.java)
                    ?: emptyList()
                trySend(bookings)
            }
        awaitClose { listener.remove() }
    }

    fun getHostBookings(listingId: String): Flow<List<Booking>> = callbackFlow {
        val listener = firestore.collection("bookings")
            .whereEqualTo("listingId", listingId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(emptyList<Booking>())
                    return@addSnapshotListener
                }
                val bookings = snapshot
                    ?.toObjects(Booking::class.java)
                    ?: emptyList()
                trySend(bookings)
            }
        awaitClose { listener.remove() }
    }
}