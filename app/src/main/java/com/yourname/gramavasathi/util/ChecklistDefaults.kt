package com.yourname.gramavasathi.util

import com.yourname.gramavasathi.data.model.ChecklistItem
import com.yourname.gramavasathi.data.model.ChecklistState

object ChecklistDefaults {

    fun createItems(): List<ChecklistItem> = listOf(
        ChecklistItem(
            id = "water",
            label = "Safe drinking water available",
            weight = 20,
            category = "Hygiene",
            state = ChecklistState.NOT_COMPLETED
        ),
        ChecklistItem(
            id = "toilet",
            label = "Clean toilet and bathroom",
            weight = 20,
            category = "Hygiene",
            state = ChecklistState.NOT_COMPLETED
        ),
        ChecklistItem(
            id = "bedsheets",
            label = "Clean bedsheets and room",
            weight = 15,
            category = "Hygiene",
            state = ChecklistState.NOT_COMPLETED
        ),
        ChecklistItem(
            id = "food",
            label = "Food hygiene and serving",
            weight = 15,
            category = "Hygiene",
            state = ChecklistState.NOT_COMPLETED
        ),
        ChecklistItem(
            id = "ventilation",
            label = "Ventilation and lighting",
            weight = 10,
            category = "Comfort",
            state = ChecklistState.NOT_COMPLETED
        ),
        ChecklistItem(
            id = "western",
            label = "Western toilet available",
            weight = 10,
            category = "Comfort",
            state = ChecklistState.NOT_COMPLETED
        ),
        ChecklistItem(
            id = "waste",
            label = "Waste disposal and surroundings",
            weight = 10,
            category = "Cleanliness",
            state = ChecklistState.NOT_COMPLETED
        )
    )
}