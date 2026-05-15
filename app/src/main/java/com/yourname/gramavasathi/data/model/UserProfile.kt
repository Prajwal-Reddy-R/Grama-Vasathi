package com.yourname.gramavasathi.data.model

import com.google.firebase.Timestamp

data class UserProfile(
    val uid: String = "",
    val displayName: String = "",
    val email: String = "",
    val role: UserRole = UserRole.GUEST,
    val createdAt: Timestamp = Timestamp.now()
)

enum class UserRole {
    HOST,
    GUEST
}

data class User(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val role: String = "",
    val profileImageUrl: String = "",
    val createdAt: Timestamp = Timestamp.now()
)
