package com.yourname.gramavasathi.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourname.gramavasathi.ui.theme.ForestGreen

@Composable
fun EmptyState(
    title: String,
    subtitle: String,
    buttonText: String? = null,
    onButtonClick: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Custom Canvas Drawing: House and Trees
        Canvas(modifier = Modifier.size(200.dp)) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            
            // Tree 1 (Left)
            val tree1Path = Path().apply {
                moveTo(canvasWidth * 0.2f, canvasHeight * 0.7f)
                lineTo(canvasWidth * 0.3f, canvasHeight * 0.4f)
                lineTo(canvasWidth * 0.4f, canvasHeight * 0.7f)
                close()
            }
            drawPath(tree1Path, color = ForestGreen.copy(alpha = 0.3f))
            drawRect(
                color = Color(0xFF5D4037),
                topLeft = Offset(canvasWidth * 0.28f, canvasHeight * 0.7f),
                size = Size(canvasWidth * 0.04f, canvasHeight * 0.1f)
            )

            // House
            drawRect(
                color = Color(0xFFD7CCC8),
                topLeft = Offset(canvasWidth * 0.4f, canvasHeight * 0.5f),
                size = Size(canvasWidth * 0.3f, canvasHeight * 0.3f)
            )
            val roofPath = Path().apply {
                moveTo(canvasWidth * 0.35f, canvasHeight * 0.5f)
                lineTo(canvasWidth * 0.55f, canvasHeight * 0.3f)
                lineTo(canvasWidth * 0.75f, canvasHeight * 0.5f)
                close()
            }
            drawPath(roofPath, color = Color(0xFF8D6E63))
            
            // Door
            drawRect(
                color = Color(0xFF5D4037),
                topLeft = Offset(canvasWidth * 0.5f, canvasHeight * 0.65f),
                size = Size(canvasWidth * 0.1f, canvasHeight * 0.15f)
            )

            // Tree 2 (Right)
            val tree2Path = Path().apply {
                moveTo(canvasWidth * 0.7f, canvasHeight * 0.75f)
                lineTo(canvasWidth * 0.85f, canvasHeight * 0.45f)
                lineTo(canvasWidth * 1.0f, canvasHeight * 0.75f)
                close()
            }
            drawPath(tree2Path, color = ForestGreen.copy(alpha = 0.5f))
            drawRect(
                color = Color(0xFF5D4037),
                topLeft = Offset(canvasWidth * 0.83f, canvasHeight * 0.75f),
                size = Size(canvasWidth * 0.04f, canvasHeight * 0.12f)
            )
            
            // Ground Line
            drawLine(
                color = Color.LightGray,
                start = Offset(0f, canvasHeight * 0.85f),
                end = Offset(canvasWidth, canvasHeight * 0.85f),
                strokeWidth = 2.dp.toPx()
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2D2926),
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        
        if (buttonText != null && onButtonClick != null) {
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = onButtonClick,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ForestGreen)
            ) {
                Text(text = buttonText)
            }
        }
    }
}
