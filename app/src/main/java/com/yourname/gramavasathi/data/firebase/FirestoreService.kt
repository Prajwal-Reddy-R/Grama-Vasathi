package com.yourname.gramavasathi.data.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Singleton service that holds Firebase instances.
 * Initialized via Hilt Dependency Injection.
 */
@Singleton
class FirestoreService @Inject constructor(
    val db: FirebaseFirestore,
    val storage: FirebaseStorage,
    val auth: FirebaseAuth
) {
    // Basic helper getters
    val currentUser get() = auth.currentUser
    val isUserLoggedIn get() = auth.currentUser != null
}
