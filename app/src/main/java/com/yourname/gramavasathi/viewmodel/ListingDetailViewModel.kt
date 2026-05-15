package com.yourname.gramavasathi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yourname.gramavasathi.data.model.Listing
import com.yourname.gramavasathi.data.repository.ListingRepository
import com.yourname.gramavasathi.data.repository.WishlistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListingDetailViewModel @Inject constructor(
    private val listingRepository: ListingRepository,
    private val wishlistRepository: WishlistRepository
) : ViewModel() {

    private val _listing = MutableStateFlow<Listing?>(null)
    val listing: StateFlow<Listing?> = _listing.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun loadListing(listingId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            listingRepository.getListing(listingId).collect { listing ->
                _listing.value = listing
                _isLoading.value = false
            }
        }
    }

    fun toggleWishlist(listingId: String) {
        viewModelScope.launch {
            wishlistRepository.toggleWishlist(listingId)
        }
    }

    fun isWishlisted(listingId: String): Flow<Boolean> =
        wishlistRepository.isWishlisted(listingId)
}