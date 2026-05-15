//package com.yourname.gramavasathi.viewmodel
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.yourname.gramavasathi.data.model.Booking
//import com.yourname.gramavasathi.data.repository.BookingRepository
//import com.yourname.gramavasathi.data.repository.ListingRepository
//import com.yourname.gramavasathi.data.model.Listing
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.flow.MutableSharedFlow
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.SharingStarted
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.flow.combine
//import kotlinx.coroutines.flow.stateIn
//import kotlinx.coroutines.launch
//import java.time.LocalDate
//import java.time.temporal.ChronoUnit
//import javax.inject.Inject
//
//@HiltViewModel
//class BookingViewModel @Inject constructor(
//    private val bookingRepository: BookingRepository,
//    private val listingRepository: ListingRepository
//) : ViewModel() {
//
//    private val _listingState = MutableStateFlow<Listing?>(null)
//    val listingState: StateFlow<Listing?> = _listingState.asStateFlow()
//
//    private val _reservedDates = MutableStateFlow<Set<String>>(emptySet())
//    val reservedDates: StateFlow<Set<String>> = _reservedDates.asStateFlow()
//
//    private val _checkInDate = MutableStateFlow<LocalDate?>(null)
//    val checkInDate: StateFlow<LocalDate?> = _checkInDate.asStateFlow()
//
//    private val _checkOutDate = MutableStateFlow<LocalDate?>(null)
//    val checkOutDate: StateFlow<LocalDate?> = _checkOutDate.asStateFlow()
//
//    private val _numGuests = MutableStateFlow(1)
//    val numGuests: StateFlow<Int> = _numGuests.asStateFlow()
//
//    private val _roomPreference = MutableStateFlow("Standard")
//    val roomPreference: StateFlow<String> = _roomPreference.asStateFlow()
//
//    private val _pricePerNight = MutableStateFlow(0)
//
//    val totalAmount: StateFlow<Int> = combine(
//        _checkInDate, _checkOutDate, _pricePerNight
//    ) { checkIn, checkOut, price ->
//        if (checkIn != null && checkOut != null) {
//            val nights = ChronoUnit.DAYS.between(checkIn, checkOut).toInt()
//            if (nights > 0) nights * price else 0
//        } else 0
//    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)
//
//    val isValid: StateFlow<Boolean> = combine(
//        _checkInDate, _checkOutDate, _reservedDates, _numGuests
//    ) { checkIn, checkOut, reserved, guests ->
//        checkIn != null &&
//                checkOut != null &&
//                checkOut.isAfter(checkIn) &&
//                guests >= 1
//    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)
//
//    sealed class BookingEvent {
//        data class Success(val bookingRef: String) : BookingEvent()
//        data class Error(val message: String) : BookingEvent()
//    }
//
//    val bookingEvent = MutableSharedFlow<BookingEvent>()
//
//    fun setListing(listingId: String, pricePerNight: Int) {
//        _pricePerNight.value = pricePerNight
//        viewModelScope.launch {
//            listingRepository.getListing(listingId).collect {
//                _listingState.value = it
//            }
//        }
//        viewModelScope.launch {
//            bookingRepository.getReservedDates(listingId).collect {
//                _reservedDates.value = it
//            }
//        }
//    }
//
//    fun selectDate(date: LocalDate) {
//        if (date.isBefore(LocalDate.now())) return
//        if (_reservedDates.value.contains(date.toString())) return
//        val currentCheckIn = _checkInDate.value
//        val currentCheckOut = _checkOutDate.value
//        when {
//            currentCheckIn == null -> _checkInDate.value = date
//            currentCheckOut == null && date.isAfter(currentCheckIn) ->
//                _checkOutDate.value = date
//            else -> {
//                _checkInDate.value = date
//                _checkOutDate.value = null
//            }
//        }
//    }
//
//    fun incrementGuests() { if (_numGuests.value < 6) _numGuests.value++ }
//    fun decrementGuests() { if (_numGuests.value > 1) _numGuests.value-- }
//    fun setRoomPreference(pref: String) { _roomPreference.value = pref }
//
//    fun confirmBooking(listingId: String, guestId: String) {
//        viewModelScope.launch {
//            val checkIn = _checkInDate.value ?: return@launch
//            val checkOut = _checkOutDate.value ?: return@launch
//            val ref = "GV-${LocalDate.now().year}-${(1000..9999).random()}"
//            val booking = Booking(
//                listingId = listingId,
//                guestId = guestId.ifEmpty { "guest_demo" },
//                checkIn = checkIn.toString(),
//                checkOut = checkOut.toString(),
//                numGuests = _numGuests.value,
//                roomPreference = _roomPreference.value,
//                totalAmount = totalAmount.value,
//                status = "confirmed",
//                bookingRef = ref
//            )
//            bookingRepository.confirmBooking(booking)
//                .onSuccess {
//                    bookingEvent.emit(BookingEvent.Success(ref))
//                }
//                .onFailure {
//                    bookingEvent.emit(
//                        BookingEvent.Error(it.message ?: "Booking failed")
//                    )
//                }
//        }
//    }
//}

package com.yourname.gramavasathi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yourname.gramavasathi.data.model.Booking
import com.yourname.gramavasathi.data.model.Listing
import com.yourname.gramavasathi.data.repository.AuthRepository
import com.yourname.gramavasathi.data.repository.BookingRepository
import com.yourname.gramavasathi.data.repository.ListingRepository
import com.yourname.gramavasathi.util.EmailService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(
    private val bookingRepository: BookingRepository,
    private val listingRepository: ListingRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _listingState = MutableStateFlow<Listing?>(null)
    val listingState: StateFlow<Listing?> = _listingState.asStateFlow()

    private val _reservedDates = MutableStateFlow<Set<String>>(emptySet())
    val reservedDates: StateFlow<Set<String>> =
        _reservedDates.asStateFlow()

    private val _checkInDate = MutableStateFlow<LocalDate?>(null)
    val checkInDate: StateFlow<LocalDate?> = _checkInDate.asStateFlow()

    private val _checkOutDate = MutableStateFlow<LocalDate?>(null)
    val checkOutDate: StateFlow<LocalDate?> =
        _checkOutDate.asStateFlow()

    private val _numGuests = MutableStateFlow(1)
    val numGuests: StateFlow<Int> = _numGuests.asStateFlow()

    private val _roomPreference = MutableStateFlow("Standard")
    val roomPreference: StateFlow<String> =
        _roomPreference.asStateFlow()

    private val _pricePerNight = MutableStateFlow(0)

    val totalAmount: StateFlow<Int> = combine(
        _checkInDate, _checkOutDate, _pricePerNight
    ) { checkIn, checkOut, price ->
        if (checkIn != null && checkOut != null) {
            val nights = ChronoUnit.DAYS.between(checkIn, checkOut).toInt()
            if (nights > 0) nights * price else 0
        } else 0
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        0
    )

    val isValid: StateFlow<Boolean> = combine(
        _checkInDate, _checkOutDate, _numGuests
    ) { checkIn, checkOut, guests ->
        checkIn != null &&
                checkOut != null &&
                checkOut.isAfter(checkIn) &&
                guests >= 1
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        false
    )

    sealed class BookingEvent {
        data class Success(val bookingRef: String) : BookingEvent()
        data class Error(val message: String) : BookingEvent()
    }

    val bookingEvent = MutableSharedFlow<BookingEvent>()

    fun setListing(listingId: String, pricePerNight: Int) {
        _pricePerNight.value = pricePerNight
        viewModelScope.launch {
            listingRepository.getListing(listingId).collect {
                _listingState.value = it
            }
        }
        viewModelScope.launch {
            bookingRepository.getReservedDates(listingId).collect {
                _reservedDates.value = it
            }
        }
    }

    fun selectDate(date: LocalDate) {
        if (date.isBefore(LocalDate.now())) return
        if (_reservedDates.value.contains(date.toString())) return
        val currentCheckIn = _checkInDate.value
        val currentCheckOut = _checkOutDate.value
        when {
            currentCheckIn == null -> {
                _checkInDate.value = date
            }
            currentCheckOut == null && date.isAfter(currentCheckIn) -> {
                _checkOutDate.value = date
            }
            else -> {
                _checkInDate.value = date
                _checkOutDate.value = null
            }
        }
    }

    fun incrementGuests() {
        if (_numGuests.value < 6) _numGuests.value++
    }

    fun decrementGuests() {
        if (_numGuests.value > 1) _numGuests.value--
    }

    fun setRoomPreference(pref: String) {
        _roomPreference.value = pref
    }

    fun confirmBooking(listingId: String) {
        viewModelScope.launch {
            val checkIn = _checkInDate.value ?: return@launch
            val checkOut = _checkOutDate.value ?: return@launch
            val ref = "GV-${LocalDate.now().year}-${(1000..9999).random()}"
            val guestId = authRepository.currentUser?.uid ?: "guest_demo"
            val guestName = authRepository.getUserName()
            val guestEmail = authRepository.getUserEmail()

            val booking = Booking(
                listingId = listingId,
                guestId = guestId,
                checkIn = checkIn.toString(),
                checkOut = checkOut.toString(),
                numGuests = _numGuests.value,
                roomPreference = _roomPreference.value,
                totalAmount = totalAmount.value,
                status = "confirmed",
                bookingRef = ref
            )

            bookingRepository.confirmBooking(booking)
                .onSuccess {
                    // Send email to guest
                    val listing = _listingState.value
                    if (guestEmail.isNotEmpty() && listing != null) {
                        EmailService.sendBookingConfirmationToGuest(
                            toEmail = guestEmail,
                            guestName = guestName,
                            bookingRef = ref,
                            listingTitle = listing.title,
                            hostName = listing.hostName,
                            checkIn = checkIn.toString(),
                            checkOut = checkOut.toString(),
                            numGuests = _numGuests.value,
                            totalAmount = totalAmount.value
                        )
                    }
                    bookingEvent.emit(BookingEvent.Success(ref))
                }
                .onFailure {
                    bookingEvent.emit(
                        BookingEvent.Error(
                            it.message ?: "Booking failed"
                        )
                    )
                }
        }
    }
}