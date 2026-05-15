package com.yourname.gramavasathi.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerEffect(
    modifier: Modifier = Modifier,
    shape: androidx.compose.ui.graphics.Shape = RoundedCornerShape(0.dp)
) {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f),
    )

    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer"
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )

    Box(
        modifier = modifier
            .clip(shape)
            .background(brush)
    )
}

@Composable
fun ShimmerListingCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(bottom = 16.dp)
    ) {
        ShimmerEffect(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ShimmerEffect(
                modifier = Modifier
                    .weight(0.6f)
                    .height(20.dp),
                shape = RoundedCornerShape(4.dp)
            )
            Spacer(modifier = Modifier.width(32.dp))
            ShimmerEffect(
                modifier = Modifier
                    .weight(0.2f)
                    .height(20.dp),
                shape = RoundedCornerShape(4.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        ShimmerEffect(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .width(100.dp)
                .height(14.dp),
            shape = RoundedCornerShape(4.dp)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(3) {
                ShimmerEffect(
                    modifier = Modifier
                        .width(60.dp)
                        .height(24.dp),
                    shape = RoundedCornerShape(8.dp)
                )
            }
        }
    }
}
