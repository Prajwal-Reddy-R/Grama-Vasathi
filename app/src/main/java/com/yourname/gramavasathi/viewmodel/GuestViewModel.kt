package com.yourname.gramavasathi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yourname.gramavasathi.data.model.Listing
import com.yourname.gramavasathi.data.repository.ListingRepository
import com.yourname.gramavasathi.data.repository.WishlistRepository
import com.yourname.gramavasathi.util.SnackbarController
import com.yourname.gramavasathi.util.SnackbarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GuestViewModel @Inject constructor(
    private val listingRepository: ListingRepository,
    private val wishlistRepository: WishlistRepository
) : ViewModel() {

    private val _allListings = MutableStateFlow<List<Listing>>(emptyList())
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedActivityFilters = MutableStateFlow<List<String>>(emptyList())
    val selectedActivityFilters: StateFlow<List<String>> =
        _selectedActivityFilters.asStateFlow()

    private val _selectedAmenityFilters = MutableStateFlow<List<String>>(emptyList())
    val selectedAmenityFilters: StateFlow<List<String>> =
        _selectedAmenityFilters.asStateFlow()

    val filteredListings: StateFlow<List<Listing>> = combine(
        _allListings,
        _searchQuery,
        _selectedActivityFilters,
        _selectedAmenityFilters
    ) { listings, query, activities, amenities ->
        listings.filter { listing ->
            val matchesQuery = query.isEmpty() ||
                    listing.title.contains(query, ignoreCase = true) ||
                    listing.villageName.contains(query, ignoreCase = true) ||
                    listing.district.contains(query, ignoreCase = true)
            val matchesActivities = activities.isEmpty() ||
                    activities.all { it in listing.activities }
            val matchesAmenities = amenities.isEmpty() ||
                    amenities.all { it in listing.amenities }
            matchesQuery && matchesActivities && matchesAmenities
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val wishlistIds: StateFlow<Set<String>> = wishlistRepository
        .getWishlistIds()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    private val _recentlyAddedStay = MutableStateFlow<Listing?>(null)
    val recentlyAddedStay: StateFlow<Listing?> = _recentlyAddedStay.asStateFlow()

    init {
        loadListings()
    }

    private fun loadListings() {
        viewModelScope.launch {
            _isLoading.value = true
            listingRepository.getListings().collect { listings ->
                if (_allListings.value.isNotEmpty() && listings.size > _allListings.value.size) {
                    val newListing = listings.firstOrNull { it.id !in _allListings.value.map { l -> l.id } }
                    newListing?.let {
                        _recentlyAddedStay.value = it
                        SnackbarController.sendEvent(
                            SnackbarEvent("New stay available: ${it.title} in ${it.villageName}!")
                        )
                    }
                }
                _allListings.value = listings
                _isLoading.value = false
            }
        }
    }

    fun dismissNewStayNotification() {
        _recentlyAddedStay.value = null
    }

    fun updateSearch(query: String) { _searchQuery.value = query }

    fun toggleActivityFilter(activity: String) {
        val current = _selectedActivityFilters.value.toMutableList()
        if (activity in current) current.remove(activity) else current.add(activity)
        _selectedActivityFilters.value = current
    }

    fun toggleAmenityFilter(amenity: String) {
        val current = _selectedAmenityFilters.value.toMutableList()
        if (amenity in current) current.remove(amenity) else current.add(amenity)
        _selectedAmenityFilters.value = current
    }

    fun clearFilters() {
        _selectedActivityFilters.value = emptyList()
        _selectedAmenityFilters.value = emptyList()
        _searchQuery.value = ""
    }

    fun toggleWishlist(listingId: String) {
        viewModelScope.launch {
            wishlistRepository.toggleWishlist(listingId)
            SnackbarController.sendEvent(SnackbarEvent("Wishlist updated"))
        }
    }
}