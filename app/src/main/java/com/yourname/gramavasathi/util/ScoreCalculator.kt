package com.yourname.gramavasathi.util

import com.yourname.gramavasathi.data.model.ChecklistItem
import com.yourname.gramavasathi.data.model.ChecklistState

object ScoreCalculator {

fun calculate(items: List<ChecklistItem>): Int {
    val applicable = items.filter {
        it.state != ChecklistState.NOT_APPLICABLE
    }
    if (applicable.isEmpty()) return 0
    val totalWeight = applicable.sumOf { it.weight }
    if (totalWeight == 0) return 0
    val completedWeight = applicable
        .filter { it.state == ChecklistState.COMPLETED }
        .sumOf { it.weight }
    val score = (completedWeight.toDouble() / totalWeight.toDouble() * 100)
        .toInt()
    return score.coerceIn(0, 100)
}

    fun getBand(score: Int): String = when {
        score >= 90 -> "excellent"
        score >= 75 -> "good"
        score >= 50 -> "fair"
        else -> "needs_work"
    }

    fun getBandLabel(score: Int): String = when {
        score >= 90 -> "Excellent"
        score >= 75 -> "Good"
        score >= 50 -> "Fair"
        else -> "Needs Work"
    }

    fun getBandColor(score: Int): androidx.compose.ui.graphics.Color = when {
        score >= 90 -> androidx.compose.ui.graphics.Color(0xFF4A7C59)
        score >= 75 -> androidx.compose.ui.graphics.Color(0xFF8BA888)
        score >= 50 -> androidx.compose.ui.graphics.Color(0xFFD4A017)
        else -> androidx.compose.ui.graphics.Color(0xFFD85A30)
    }

    fun getSuggestions(items: List<ChecklistItem>): List<String> {
        return items
            .filter { it.state == ChecklistState.NOT_COMPLETED }
            .sortedByDescending { it.weight }
            .take(3)
            .map { "Improve: ${it.label} (${it.weight} points)" }
    }

    fun getBadges(score: Int, amenities: List<String>): List<String> {
        val badges = mutableListOf<String>()
        if (score >= 85) badges.add("verified_hygiene")
        if (score >= 75) badges.add("top_host")
        if ("family_friendly" in amenities) badges.add("family_friendly")
        if ("food_included" in amenities) badges.add("home_cooked_food")
        return badges
    }
}