package com.yourname.gramavasathi.util

import androidx.compose.ui.graphics.Color

object BadgeUtils {

    fun getBadgeLabel(key: String): String = when (key) {
        "verified_hygiene" -> "Verified Hygiene"
        "top_host"         -> "Top Host"
        "family_friendly"  -> "Family Friendly"
        "home_cooked_food" -> "Home Cooked Food"
        else               -> key.replace("_", " ")
            .replaceFirstChar { it.uppercase() }
    }

    fun getBadgeEmoji(key: String): String = when (key) {
        "verified_hygiene" -> "✓"
        "top_host"         -> "★"
        "family_friendly"  -> "♥"
        "home_cooked_food" -> "◆"
        else               -> "•"
    }

    fun getBadgeColor(key: String): Color = when (key) {
        "verified_hygiene" -> Color(0xFF4A7C59)
        "top_host"         -> Color(0xFFD4A017)
        "family_friendly"  -> Color(0xFF185FA5)
        "home_cooked_food" -> Color(0xFF993C1D)
        else               -> Color(0xFF888780)
    }

    fun getBadgeIcon(key: String): String = getBadgeEmoji(key)
}