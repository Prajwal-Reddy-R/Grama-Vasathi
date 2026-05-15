package com.yourname.gramavasathi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yourname.gramavasathi.data.model.Listing
import com.yourname.gramavasathi.data.repository.ListingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImpactViewModel @Inject constructor(
    private val listingRepository: ListingRepository
) : ViewModel() {

    private val _listings = MutableStateFlow<List<Listing>>(emptyList())

    val totalVillages: StateFlow<Int> = _listings.map { list ->
        list.map { it.villageName }.distinct().size
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val totalHosts: StateFlow<Int> = _listings.map { it.size }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val averageReadinessScore: StateFlow<Int> = _listings.map { list ->
        if (list.isEmpty()) 0
        else list.sumOf { it.readinessScore } / list.size
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val topActivities: StateFlow<List<Pair<String, Int>>> = _listings.map { list ->
        list.flatMap { it.activities }
            .groupingBy { it }
            .eachCount()
            .entries
            .sortedByDescending { it.value }
            .take(5)
            .map { it.key to it.value }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            listingRepository.getListings().collect { listings ->
                _listings.value = listings
            }
        }
    }
}