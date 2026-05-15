package com.yourname.gramavasathi.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourname.gramavasathi.util.BadgeUtils

@Composable
fun HostBadgeRow(
    badges: List<String>,
    modifier: Modifier = Modifier
) {
    if (badges.isEmpty()) return

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        badges.forEach { badge ->
            BadgeChip(badgeKey = badge)
        }
    }
}

@Composable
private fun BadgeChip(badgeKey: String) {
    val backgroundColor = when (badgeKey) {
        "verified_hygiene" -> Color(0xFF4A7C59)
        "top_host"         -> Color(0xFFD4A017)
        "family_friendly"  -> Color(0xFF185FA5)
        "home_cooked_food" -> Color(0xFF993C1D)
        else               -> Color(0xFF888780)
    }

    Box(
        modifier = Modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 10.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "${BadgeUtils.getBadgeEmoji(badgeKey)} ${BadgeUtils.getBadgeLabel(badgeKey)}",
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )
    }
}