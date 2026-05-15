package com.yourname.gramavasathi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yourname.gramavasathi.data.model.Listing
import com.yourname.gramavasathi.data.repository.ListingRepository
import com.yourname.gramavasathi.data.repository.WishlistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val wishlistRepository: WishlistRepository,
    private val listingRepository: ListingRepository
) : ViewModel() {

    val wishlistIds: StateFlow<Set<String>> = wishlistRepository
        .getWishlistIds()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    private val _wishlistedListings = MutableStateFlow<List<Listing>>(emptyList())
    val wishlistedListings: StateFlow<List<Listing>> = 
        _wishlistedListings.asStateFlow()

    init {
        viewModelScope.launch {
            wishlistIds.collect { ids ->
                if (ids.isEmpty()) {
                    _wishlistedListings.value = emptyList()
                    return@collect
                }
                listingRepository.getListings().collect { allListings ->
                    _wishlistedListings.value = allListings.filter { 
                        it.id in ids 
                    }
                }
            }
        }
    }

    fun removeFromWishlist(listingId: String) {
        viewModelScope.launch {
            wishlistRepository.toggleWishlist(listingId)
        }
    }
}
