package com.yourname.gramavasathi.util

import com.google.common.truth.Truth.assertThat
import com.yourname.gramavasathi.data.model.ChecklistState
import org.junit.Test

class ScoreCalculatorTest {

    private val defaultItems = ChecklistDefaults.createItems()

    @Test
    fun allCompleted_returns100() {
        val items = defaultItems.map { it.copy(state = ChecklistState.COMPLETED) }
        val score = ScoreCalculator.calculate(items)
        assertThat(score).isEqualTo(100)
    }

    @Test
    fun allNotCompleted_returns0() {
        val items = defaultItems.map { it.copy(state = ChecklistState.NOT_COMPLETED) }
        val score = ScoreCalculator.calculate(items)
        assertThat(score).isEqualTo(0)
    }

    @Test
    fun allNotApplicable_returns0() {
        val items = defaultItems.map { it.copy(state = ChecklistState.NOT_APPLICABLE) }
        val score = ScoreCalculator.calculate(items)
        assertThat(score).isEqualTo(0)
    }

    @Test
    fun criticalItemsOnly_returnsCorrectPercentage() {
        // safe_water(20) and clean_toilet(20) completed. Total weight 100.
        val items = defaultItems.map { item ->
            when (item.id) {
                "safe_water", "clean_toilet" -> item.copy(state = ChecklistState.COMPLETED)
                else -> item.copy(state = ChecklistState.NOT_COMPLETED)
            }
        }
        val score = ScoreCalculator.calculate(items)
        assertThat(score).isEqualTo(40)
    }

    @Test
    fun highScoreExcludesNA_recalculatesDenominator() {
        // safe_water(20) and clean_toilet(20) COMPLETED
        // western_toilet(10) NOT_APPLICABLE
        // rest NOT_COMPLETED. 
        // Denominator = 100 - 10 = 90. Numerator = 20 + 20 = 40. 
        // Score = 40/90 * 100 = 44.44 -> 44
        val items = defaultItems.map { item ->
            when (item.id) {
                "safe_water", "clean_toilet" -> item.copy(state = ChecklistState.COMPLETED)
                "western_toilet" -> item.copy(state = ChecklistState.NOT_APPLICABLE)
                else -> item.copy(state = ChecklistState.NOT_COMPLETED)
            }
        }
        val score = ScoreCalculator.calculate(items)
        assertThat(score).isEqualTo(44)
    }

    @Test
    fun fullScoreExcludesNA_returns100() {
        val items = defaultItems.map { item ->
            if (item.id == "western_toilet") {
                item.copy(state = ChecklistState.NOT_APPLICABLE)
            } else {
                item.copy(state = ChecklistState.COMPLETED)
            }
        }
        val score = ScoreCalculator.calculate(items)
        assertThat(score).isEqualTo(100)
    }

    @Test
    fun partialCompletion_verifyExactPercentage() {
        // 4 items: safe_water(20), clean_toilet(20), bedsheets(15), food_hygiene(15) COMPLETED. Total 70.
        val items = defaultItems.map { item ->
            when (item.id) {
                "safe_water", "clean_toilet", "bedsheets", "food_hygiene" -> item.copy(state = ChecklistState.COMPLETED)
                else -> item.copy(state = ChecklistState.NOT_COMPLETED)
            }
        }
        val score = ScoreCalculator.calculate(items)
        assertThat(score).isEqualTo(70)
    }

    @Test
    fun badgesAtHighScore_containsVerifiedHygieneAndTopHost() {
        val badges = ScoreCalculator.getBadges(87, emptyList())
        assertThat(badges).containsExactly("verified_hygiene", "top_host")
    }

    @Test
    fun badgesAtMidScore_containsTopHostOnly() {
        val badges = ScoreCalculator.getBadges(76, emptyList())
        assertThat(badges).containsExactly("top_host")
    }

    @Test
    fun badgesAtLowScore_containsNoScoreBadges() {
        val badges = ScoreCalculator.getBadges(40, emptyList())
        assertThat(badges).isEmpty()
    }

    @Test
    fun familyFriendlyBadge_addedWhenInAmenities() {
        val badges = ScoreCalculator.getBadges(40, listOf("family_friendly"))
        assertThat(badges).contains("family_friendly")
    }

    @Test
    fun getSuggestionsForMissingCritical_containsImprovementTip() {
        val items = defaultItems.map { item ->
            if (item.id == "safe_water") item.copy(state = ChecklistState.NOT_COMPLETED)
            else item.copy(state = ChecklistState.COMPLETED)
        }
        val suggestions = ScoreCalculator.getSuggestions(items)
        assertThat(suggestions.joinToString()).contains("Safe Drinking Water")
    }

    @Test
    fun getBandLabels_returnsCorrectBands() {
        assertThat(ScoreCalculator.getBand(95)).isEqualTo("excellent")
        assertThat(ScoreCalculator.getBand(80)).isEqualTo("good")
        assertThat(ScoreCalculator.getBand(60)).isEqualTo("fair")
        assertThat(ScoreCalculator.getBand(30)).isEqualTo("needs_work")
    }
}
