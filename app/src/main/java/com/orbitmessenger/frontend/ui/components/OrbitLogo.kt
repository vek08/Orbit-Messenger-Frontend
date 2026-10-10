package com.orbitmessenger.frontend.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.orbitmessenger.frontend.ui.theme.PrimaryPurple

@Composable
fun OrbitLogo(modifier: Modifier = Modifier, size: Dp = 80.dp, color: Color = PrimaryPurple) {
    Canvas(modifier = modifier.size(size)) {
        val strokeWidth = size.toPx() * 0.1f
        val radius = (size.toPx() - strokeWidth) / 2f
        val center = Offset(size.toPx() / 2f, size.toPx() / 2f)

        // Draw the main circle
        drawCircle(
            color = color,
            radius = radius,
            center = center,
            style = Stroke(width = strokeWidth)
        )

        // Draw the slanted "orbit" ring (a line cutting across)
        drawLine(
            color = color,
            start = Offset(0f, size.toPx() * 0.8f),
            end = Offset(size.toPx(), size.toPx() * 0.2f),
            strokeWidth = strokeWidth * 0.8f,
            cap = StrokeCap.Round
        )
    }
}
