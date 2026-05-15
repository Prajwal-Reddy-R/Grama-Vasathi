package com.yourname.gramavasathi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.yourname.gramavasathi.data.model.Booking
import com.yourname.gramavasathi.data.model.Listing
import com.yourname.gramavasathi.data.repository.AuthRepository
import com.yourname.gramavasathi.data.repository.ListingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest

import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltViewModel
class HostDashboardViewModel @Inject constructor(
    private val listingRepository: ListingRepository,
    private val authRepository: AuthRepository,
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _hostListings = MutableStateFlow<List<Listing>>(emptyList())
    val hostListings: StateFlow<List<Listing>> = _hostListings.asStateFlow()

    private val _hostBookings = MutableStateFlow<List<Booking>>(emptyList())
    val hostBookings: StateFlow<List<Booking>> = _hostBookings.asStateFlow()

    private val _hostName = MutableStateFlow("Host")
    val hostName: StateFlow<String> = _hostName.asStateFlow()

    private val _totalRevenue = MutableStateFlow(0)
    val totalRevenue: StateFlow<Int> = _totalRevenue.asStateFlow()

    private var listingsJob: Job? = null
    private var bookingsListener: ListenerRegistration? = null

    fun loadData() {
        if (listingsJob != null) return 
        
        val uid = auth.currentUser?.uid ?: return
        
        viewModelScope.launch {
            _hostName.value = authRepository.getUserName()
        }

        // Properly collect listings as a flow
        listingsJob = viewModelScope.launch {
            listingRepository.getListings().collectLatest { allListings ->
                val mine = withContext(Dispatchers.Default) {
                    allListings.filter { it.hostId == uid }
                }
                _hostListings.value = mine
            }
        }

        // Manage bookings listener with explicit registration cleanup
        bookingsListener = firestore.collection("bookings")
            .addSnapshotListener { snapshot, _ ->
                viewModelScope.launch(Dispatchers.Default) {
                    val allBookings = snapshot?.toObjects(Booking::class.java) ?: emptyList()
                    val hostListingIds = _hostListings.value.map { it.id }
                    val relevant = allBookings.filter { it.listingId in hostListingIds }
                    
                    withContext(Dispatchers.Main) {
                        _hostBookings.value = relevant
                        _totalRevenue.value = relevant.sumOf { it.totalAmount }
                    }
                }
            }
    }

    override fun onCleared() {
        super.onCleared()
        listingsJob?.cancel()
        bookingsListener?.remove()
    }

    fun togglePublish(listing: Listing) {
        viewModelScope.launch {
            try {
                firestore.collection("listings")
                    .document(listing.id)
                    .update("isPublished", !listing.isPublished)
                    .await()
            } catch (e: Exception) {
                // handle error
            }
        }
    }

    fun deleteListing(listingId: String) {
        viewModelScope.launch {
            listingRepository.deleteListing(listingId)
        }
    }
}