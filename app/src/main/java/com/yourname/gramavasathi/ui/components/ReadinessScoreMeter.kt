package com.yourname.gramavasathi.ui.components

import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ReadinessScoreMeter(
    score: Int,
    modifier: Modifier = Modifier,
    size: Int = 80
) {
    var animationPlayed by remember { mutableStateOf(false) }
    
    val animatedProgress = animateFloatAsState(
        targetValue = if (animationPlayed) score / 100f else 0f,
        animationSpec = tween(durationMillis = 800, easing = EaseOutCubic),
        label = "progress"
    )

    val animatedScore = animateIntAsState(
        targetValue = if (animationPlayed) score else 0,
        animationSpec = tween(durationMillis = 800, easing = EaseOutCubic),
        label = "score"
    )

    LaunchedEffect(Unit) {
        animationPlayed = true
    }

    val color = when {
        score >= 80 -> Color(0xFF4A7C59) // Green
        score >= 50 -> Color(0xFFD4A017) // Amber
        else -> Color(0xFFB22222) // Red
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(size.dp)
    ) {
        CircularProgressIndicator(
            progress = { animatedProgress.value },
            modifier = Modifier.size(size.dp),
            color = color,
            strokeWidth = 6.dp,
            trackColor = color.copy(alpha = 0.1f)
        )
        Text(
            text = "${animatedScore.value}%",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = color,
            fontSize = (size / 4).sp
        )
    }
}
