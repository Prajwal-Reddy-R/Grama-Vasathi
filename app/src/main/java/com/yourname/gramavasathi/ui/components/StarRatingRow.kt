package com.yourname.gramavasathi.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StarRatingRow(
    rating: Double,
    modifier: Modifier = Modifier,
    reviewCount: Int? = null,
    starSize: Int = 16,
    starColor: Color = Color(0xFFD4A017) // HarvestAmber
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..5) {
            val icon = when {
                rating >= i -> Icons.Filled.Star
                rating >= i - 0.5 -> Icons.AutoMirrored.Filled.StarHalf
                else -> Icons.Filled.StarOutline
            }
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(starSize.dp),
                tint = starColor
            )
        }
        if (reviewCount != null) {
            Text(
                text = " ($reviewCount reviews)",
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray,
                modifier = Modifier.padding(start = 4.dp),
                fontSize = (starSize * 0.75).sp
            )
        }
    }
}
