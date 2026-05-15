package com.yourname.gramavasathi.data.model

/**
 * Represents an item in the host readiness checklist.
 *
 * @property id Unique identifier for the checklist item.
 * @property label Description of the requirement (e.g., "Clean Drinking Water").
 * @property weight Importance of the item used to calculate the readiness score.
 * @property category Category of the item (e.g., "Hygiene", "Comfort").
 * @property state Current completion state of the item.
 */


data class ChecklistItem(
    val id: String = "",
    val label: String = "",
    val weight: Int = 0,
    val category: String = "",
    val state: ChecklistState = ChecklistState.NOT_COMPLETED
)

enum class ChecklistState {
    COMPLETED,
    NOT_COMPLETED,
    NOT_APPLICABLE
}
