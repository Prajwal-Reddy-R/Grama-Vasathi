package com.yourname.gramavasathi.ui

sealed class Screen(val route: String) {
    object RoleSelection : Screen("role_selection")
    object Login : Screen("login")
    
    // Host screens
    object HostChecklist : Screen("host_checklist")
    object HostScore : Screen("host_score")
    object CreateListing : Screen("create_listing")
    object IncomeEstimator : Screen("income_estimator")
    
    // Guest screens
    object GuestHome : Screen("guest_home")
    object ListingDetail : Screen("listing_detail/{listingId}") {
        fun createRoute(listingId: String) = "listing_detail/$listingId"
    }
    object Booking : Screen("booking/{listingId}") {
        fun createRoute(listingId: String) = "booking/$listingId"
    }
    object BookingConfirmation : Screen("booking_confirmation/{ref}/{name}/{in}/{out}/{guests}/{total}") {
        fun createRoute(ref: String, name: String, checkIn: String, checkOut: String, guests: Int, total: Int) =
            "booking_confirmation/$ref/$name/$checkIn/$checkOut/$guests/$total"
    }
    object ReviewList : Screen("review_list/{listingId}") {
        fun createRoute(listingId: String) = "review_list/$listingId"
    }
    object WriteReview : Screen("write_review/{listingId}") {
        fun createRoute(listingId: String) = "write_review/$listingId"
    }
    
    // Shared / Extra
    object CulturalGuide : Screen("cultural_guide")
    object Wishlist : Screen("wishlist")
    object ImpactDashboard : Screen("impact_dashboard")
    object Settings : Screen("settings")
    object GalleryScreen : Screen("gallery/{listingId}") {
        fun createRoute(listingId: String) = "gallery/$listingId"
    }
}
