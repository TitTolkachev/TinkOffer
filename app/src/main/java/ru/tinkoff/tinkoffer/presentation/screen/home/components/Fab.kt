package ru.tinkoff.tinkoffer.presentation.screen.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Fab(text: String? = null, visible: Boolean, onClick: () -> Unit) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInHorizontally(initialOffsetX = { it * 2 }),
        exit = slideOutHorizontally(targetOffsetX = { it * 2 }),
    ) {
        if (text != null) {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { onClick() }
                    .background(Color(0xFFECECEC))
                    .padding(horizontal = 10.dp, vertical = 12.dp),
            ) {
                Text(
                    text = "+",
                    color = Color(0xFF4A87F8),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = text,
                    color = Color(0xFF4A87F8),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400
                )
            }
        } else {
            FloatingActionButton(
                containerColor = Color(0xFFECECEC),
                contentColor = Color(0xFF4A87F8),
                onClick = onClick
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
    }
}