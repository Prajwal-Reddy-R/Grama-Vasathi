package com.yourname.gramavasathi.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.yourname.gramavasathi.ui.auth.GuestAuthScreen
import com.yourname.gramavasathi.ui.auth.HostAuthScreen
import com.yourname.gramavasathi.ui.auth.RoleSelectionScreen
import com.yourname.gramavasathi.ui.guest.BookingConfirmationScreen
import com.yourname.gramavasathi.ui.guest.BookingScreen
import com.yourname.gramavasathi.ui.guest.GuestBookingsScreen
import com.yourname.gramavasathi.ui.guest.GuestHomeScreen
import com.yourname.gramavasathi.ui.guest.ListingDetailScreen
import com.yourname.gramavasathi.ui.guest.ReviewListScreen
import com.yourname.gramavasathi.ui.guest.WishlistScreen
import com.yourname.gramavasathi.ui.guest.WriteReviewScreen
import com.yourname.gramavasathi.ui.host.ChecklistScreen
import com.yourname.gramavasathi.ui.host.CreateListingScreen
import com.yourname.gramavasathi.ui.host.HostDashboardScreen
import com.yourname.gramavasathi.ui.host.HostGuidanceScreen
import com.yourname.gramavasathi.ui.host.ScoreScreen
import com.yourname.gramavasathi.ui.shared.CulturalGuideScreen
import com.yourname.gramavasathi.ui.shared.ImpactDashboardScreen
import com.yourname.gramavasathi.ui.shared.SettingsScreen
import com.yourname.gramavasathi.viewmodel.HostViewModel

object Routes {
    const val ROLE_SELECTION = "role_selection"
    const val HOST_AUTH = "host_auth"
    const val GUEST_AUTH = "guest_auth"
    const val HOST_SECTION = "host_section"
    const val HOST_DASHBOARD = "host_dashboard"
    const val HOST_CHECKLIST = "host_checklist"
    const val HOST_SCORE = "host_score"
    const val HOST_GUIDANCE = "host_guidance"
    const val CREATE_LISTING = "create_listing"
    const val GUEST_HOME = "guest_home"
    const val GUEST_BOOKINGS = "guest_bookings"
    const val LISTING_DETAIL = "listing_detail/{listingId}"
    const val BOOKING = "booking/{listingId}"
    const val BOOKING_CONFIRMATION = "booking_confirmation/{bookingRef}"
    const val REVIEW_LIST = "review_list/{listingId}"
    const val WRITE_REVIEW = "write_review/{listingId}"
    const val CULTURAL_GUIDE = "cultural_guide"
    const val WISHLIST = "wishlist"
    const val IMPACT_DASHBOARD = "impact_dashboard"
    const val SETTINGS = "settings"

    fun listingDetail(id: String) = "listing_detail/$id"
    fun booking(id: String) = "booking/$id"
    fun bookingConfirmation(ref: String) = "booking_confirmation/$ref"
    fun reviewList(id: String) = "review_list/$id"
    fun writeReview(id: String) = "write_review/$id"
}

@Composable
fun GramaVasathiNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.ROLE_SELECTION,
        modifier = modifier
    ) {
        composable(Routes.ROLE_SELECTION) {
            RoleSelectionScreen(
                onHostSelected = {
                    navController.navigate(Routes.HOST_AUTH)
                },
                onGuestSelected = {
                    navController.navigate(Routes.GUEST_AUTH)
                }
            )
        }

        composable(Routes.HOST_AUTH) {
            HostAuthScreen(
                onAuthSuccess = {
                    navController.navigate(Routes.HOST_SECTION) {
                        popUpTo(Routes.ROLE_SELECTION) {
                            inclusive = false
                        }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.GUEST_AUTH) {
            GuestAuthScreen(
                onAuthSuccess = {
                    navController.navigate(Routes.GUEST_HOME) {
                        popUpTo(Routes.ROLE_SELECTION) {
                            inclusive = false
                        }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        navigation(
            route = Routes.HOST_SECTION,
            startDestination = Routes.HOST_DASHBOARD
        ) {
            composable(Routes.HOST_DASHBOARD) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(Routes.HOST_SECTION)
                }
                val sharedViewModel: HostViewModel = hiltViewModel(parentEntry)
                HostDashboardScreen(
                    onCreateListing = {
                        sharedViewModel.startNewDraft()
                        navController.navigate(Routes.CREATE_LISTING)
                    },
                    onViewGuidance = {
                        navController.navigate(Routes.HOST_GUIDANCE)
                    },
                    onViewChecklist = {
                        navController.navigate(Routes.HOST_CHECKLIST)
                    },
                    onEditListing = { listing ->
                        sharedViewModel.loadDraft(listing)
                        navController.navigate(Routes.CREATE_LISTING)
                    },
                    onSettingsClick = {
                        navController.navigate(Routes.SETTINGS)
                    },
                    onLogout = {
                        com.google.firebase.auth.FirebaseAuth.getInstance().signOut()
                        navController.navigate(Routes.ROLE_SELECTION) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }

            composable(Routes.HOST_CHECKLIST) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(Routes.HOST_SECTION)
                }
                val viewModel: HostViewModel = hiltViewModel(parentEntry)
                ChecklistScreen(
                    onFinished = {
                        navController.navigate(Routes.HOST_SCORE)
                    },
                    onBack = { navController.popBackStack() },
                    viewModel = viewModel
                )
            }

            composable(Routes.HOST_SCORE) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(Routes.HOST_SECTION)
                }
                val viewModel: HostViewModel = hiltViewModel(parentEntry)
                ScoreScreen(
                    onListingPublished = {
                        navController.navigate(Routes.GUEST_HOME) {
                            popUpTo(Routes.ROLE_SELECTION) {
                                inclusive = false
                            }
                        }
                    },
                    onRetakeChecklist = {
                        navController.popBackStack()
                    },
                    viewModel = viewModel
                )
            }

            composable(Routes.HOST_GUIDANCE) {
                HostGuidanceScreen(
                    onBack = { navController.popBackStack() }
                )
            }

            composable(Routes.CREATE_LISTING) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(Routes.HOST_SECTION)
                }
                val viewModel: HostViewModel = hiltViewModel(parentEntry)
                CreateListingScreen(
                    onListingPublished = {
                        navController.navigate(Routes.HOST_DASHBOARD) {
                            popUpTo(Routes.HOST_DASHBOARD) {
                                inclusive = true
                            }
                        }
                    },
                    onContinueToChecklist = {
                        navController.navigate(Routes.HOST_CHECKLIST)
                    },
                    onBack = { navController.popBackStack() },
                    viewModel = viewModel
                )
            }
        }

        composable(Routes.GUEST_HOME) {
            GuestHomeScreen(
                onListingClick = { listingId ->
                    navController.navigate(Routes.listingDetail(listingId))
                },
                onWishlistClick = {
                    navController.navigate(Routes.WISHLIST)
                },
                onImpactClick = {
                    navController.navigate(Routes.IMPACT_DASHBOARD)
                },
                onSettingsClick = {
                    navController.navigate(Routes.SETTINGS)
                },
                onCulturalGuideClick = {
                    navController.navigate(Routes.CULTURAL_GUIDE)
                },
                onMyBookingsClick = {
                    navController.navigate(Routes.GUEST_BOOKINGS)
                },
                onHostLoginClick = {
                    navController.navigate(Routes.HOST_AUTH)
                },
                onLogout = {
                    com.google.firebase.auth.FirebaseAuth.getInstance().signOut()
                    navController.navigate(Routes.ROLE_SELECTION) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.GUEST_BOOKINGS) {
            GuestBookingsScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.LISTING_DETAIL,
            arguments = listOf(
                navArgument("listingId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val listingId = backStackEntry.arguments
                ?.getString("listingId") ?: ""
            ListingDetailScreen(
                listingId = listingId,
                onBookNow = {
                    navController.navigate(Routes.booking(listingId))
                },
                onSeeReviews = {
                    navController.navigate(Routes.reviewList(listingId))
                },
                onWriteReview = {
                    navController.navigate(Routes.writeReview(listingId))
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.BOOKING,
            arguments = listOf(
                navArgument("listingId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val listingId = backStackEntry.arguments
                ?.getString("listingId") ?: ""
            BookingScreen(
                listingId = listingId,
                onBookingConfirmed = { bookingRef ->
                    navController.navigate(
                        Routes.bookingConfirmation(bookingRef)
                    ) {
                        popUpTo(Routes.GUEST_HOME) {
                            inclusive = false
                        }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.BOOKING_CONFIRMATION,
            arguments = listOf(
                navArgument("bookingRef") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val bookingRef = backStackEntry.arguments
                ?.getString("bookingRef") ?: ""
            BookingConfirmationScreen(
                bookingRef = bookingRef,
                onBackToHome = {
                    navController.navigate(Routes.GUEST_HOME) {
                        popUpTo(Routes.ROLE_SELECTION) {
                            inclusive = false
                        }
                    }
                }
            )
        }

        composable(
            route = Routes.REVIEW_LIST,
            arguments = listOf(
                navArgument("listingId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val listingId = backStackEntry.arguments
                ?.getString("listingId") ?: ""
            ReviewListScreen(
                listingId = listingId,
                onWriteReview = {
                    navController.navigate(Routes.writeReview(listingId))
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.WRITE_REVIEW,
            arguments = listOf(
                navArgument("listingId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val listingId = backStackEntry.arguments
                ?.getString("listingId") ?: ""
            WriteReviewScreen(
                listingId = listingId,
                onReviewSubmitted = {
                    navController.popBackStack()
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.CULTURAL_GUIDE) {
            CulturalGuideScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.WISHLIST) {
            WishlistScreen(
                onListingClick = { listingId ->
                    navController.navigate(
                        Routes.listingDetail(listingId)
                    )
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.IMPACT_DASHBOARD) {
            ImpactDashboardScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.SETTINGS) {
            SettingsScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}