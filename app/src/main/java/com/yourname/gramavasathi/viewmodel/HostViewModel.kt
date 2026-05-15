package com.yourname.gramavasathi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.yourname.gramavasathi.data.model.ChecklistItem
import com.yourname.gramavasathi.data.model.ChecklistState
import com.yourname.gramavasathi.data.model.Listing
import com.yourname.gramavasathi.data.repository.ListingRepository
import com.yourname.gramavasathi.util.ChecklistDefaults
import com.yourname.gramavasathi.util.ScoreCalculator
import com.yourname.gramavasathi.util.SnackbarController
import com.yourname.gramavasathi.util.SnackbarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HostViewModel @Inject constructor(
    private val listingRepository: ListingRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _checklistItems = MutableStateFlow(
        ChecklistDefaults.createItems()
    )
    val checklistItems: StateFlow<List<ChecklistItem>> =
        _checklistItems.asStateFlow()

    private val _currentStep = MutableStateFlow(0)
    val currentStep: StateFlow<Int> = _currentStep.asStateFlow()

    private val _readinessScore = MutableStateFlow(0)
    val readinessScore: StateFlow<Int> =
        _readinessScore.asStateFlow()

    private val _draftListing = MutableStateFlow(Listing())
    val draftListing: StateFlow<Listing> = _draftListing.asStateFlow()

    private val _isPublishing = MutableStateFlow(false)
    val isPublishing: StateFlow<Boolean> =
        _isPublishing.asStateFlow()

    fun updateDraft(listing: Listing) {
        _draftListing.value = listing
    }

    fun loadDraft(listing: Listing) {
        _draftListing.value = listing
        _readinessScore.value = listing.readinessScore
    }

    fun startNewDraft() {
        _draftListing.value = Listing()
        _readinessScore.value = 0
        _checklistItems.value = ChecklistDefaults.createItems()
        _currentStep.value = 0
    }

    fun updateItemState(itemId: String, state: ChecklistState) {
        // Map to new list then toMutableList() forces new reference
        val updatedList = _checklistItems.value
            .map { item ->
                if (item.id == itemId) item.copy(state = state)
                else item
            }
            .toMutableList()
        _checklistItems.value = updatedList
        // Calculate from the updated list immediately
        _readinessScore.value = ScoreCalculator.calculate(updatedList)
    }

    fun nextStep() {
        if (_currentStep.value < _checklistItems.value.size - 1) {
            _currentStep.value = _currentStep.value + 1
        }
    }

    fun previousStep() {
        if (_currentStep.value > 0) {
            _currentStep.value = _currentStep.value - 1
        }
    }

    fun canPublish(): Boolean = _readinessScore.value >= 50

    fun resetChecklist() {
        _checklistItems.value = ChecklistDefaults.createItems()
        _currentStep.value = 0
        _readinessScore.value = 0
    }

    fun publishListing(
        listing: Listing,
        onSuccess: () -> Unit
    ) {
        val uid = auth.currentUser?.uid ?: ""
        viewModelScope.launch {
            _isPublishing.value = true
            val finalListing = listing.copy(
                hostId = uid,
                readinessScore = _readinessScore.value,
                isPublished = true,
                badges = ScoreCalculator.getBadges(
                    _readinessScore.value,
                    listing.amenities
                )
            )
            listingRepository.publishListing(finalListing)
                .onSuccess {
                    SnackbarController.sendEvent(
                        SnackbarEvent("✓ Listing published!")
                    )
                    onSuccess()
                }
                .onFailure {
                    SnackbarController.sendEvent(
                        SnackbarEvent("Failed: ${it.message}")
                    )
                }
            _isPublishing.value = false
        }
    }
}