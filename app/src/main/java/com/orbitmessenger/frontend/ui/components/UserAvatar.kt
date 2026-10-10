package com.orbitmessenger.frontend.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.orbitmessenger.frontend.ui.theme.SuccessGreen

@Composable
fun UserAvatar(
    name: String,
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    isOnline: Boolean = false
) {
    val initial = name.firstOrNull()?.uppercase() ?: "?"
    
    Box(modifier = modifier.size(size)) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = initial,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = (size.value * 0.4).sp
            )
        }
        
        if (isOnline) {
            Box(
                modifier = Modifier
                    .size(size * 0.28f)
                    .clip(CircleShape)
                    .background(SuccessGreen)
                    .align(Alignment.BottomEnd)
                    .padding(2.dp) // creates a border effect if we added a border, but we can also use a Surface
            )
        }
    }
}
