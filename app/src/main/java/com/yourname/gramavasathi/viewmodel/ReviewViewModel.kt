package com.yourname.gramavasathi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yourname.gramavasathi.data.model.Review
import com.yourname.gramavasathi.data.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository
) : ViewModel() {

    private val _reviews = MutableStateFlow<List<Review>>(emptyList())
    val reviews: StateFlow<List<Review>> = _reviews.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    val averageRating: StateFlow<Double> = _reviews.map { list ->
        if (list.isEmpty()) 0.0
        else list.sumOf { it.overallRating }.toDouble() / list.size
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val ratingDistribution: StateFlow<Map<Int, Int>> = _reviews.map { list ->
        (1..5).associateWith { star ->
            list.count { it.overallRating == star }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())

    private val _overallRating = MutableStateFlow(0)
    val overallRating: StateFlow<Int> = _overallRating.asStateFlow()

    private val _aspectRatings = MutableStateFlow(
        mapOf("cleanliness" to 0, "food" to 0,
            "hospitality" to 0, "activities" to 0)
    )
    val aspectRatings: StateFlow<Map<String, Int>> = _aspectRatings.asStateFlow()

    private val _selectedTags = MutableStateFlow<List<String>>(emptyList())
    val selectedTags: StateFlow<List<String>> = _selectedTags.asStateFlow()

    private val _reviewText = MutableStateFlow("")
    val reviewText: StateFlow<String> = _reviewText.asStateFlow()

    sealed class SubmitEvent {
        object Success : SubmitEvent()
        data class Error(val message: String) : SubmitEvent()
    }

    val submitEvent = MutableSharedFlow<SubmitEvent>()

    fun loadReviews(listingId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            reviewRepository.getReviews(listingId).collect { list ->
                _reviews.value = list
                _isLoading.value = false
            }
        }
    }

    fun setOverallRating(rating: Int) { _overallRating.value = rating }

    fun setAspectRating(aspect: String, rating: Int) {
        _aspectRatings.value = _aspectRatings.value
            .toMutableMap().also { it[aspect] = rating }
    }

    fun toggleTag(tag: String) {
        val current = _selectedTags.value.toMutableList()
        if (tag in current) current.remove(tag) else current.add(tag)
        _selectedTags.value = current
    }

    fun updateReviewText(text: String) { _reviewText.value = text }

    fun submitReview(listingId: String, guestId: String, guestName: String) {
        viewModelScope.launch {
            val review = Review(
                listingId = listingId,
                guestId = guestId.ifEmpty { "guest_demo" },
                guestName = guestName.ifEmpty { "Guest" },
                overallRating = _overallRating.value,
                aspects = _aspectRatings.value,
                tags = _selectedTags.value,
                reviewText = _reviewText.value
            )
            reviewRepository.submitReview(review)
                .onSuccess { submitEvent.emit(SubmitEvent.Success) }
                .onFailure {
                    submitEvent.emit(
                        SubmitEvent.Error(it.message ?: "Failed to submit")
                    )
                }
        }
    }
}