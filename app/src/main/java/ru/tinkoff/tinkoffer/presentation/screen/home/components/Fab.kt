package ru.tinkoff.tinkoffer.presentation.screen.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Fab(text: String? = null, visible: Boolean, onClick: () -> Unit) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInHorizontally(initialOffsetX = { it * 2 }),
        exit = slideOutHorizontally(targetOffsetX = { it * 2 }),
    ) {
        if (text != null) {
            ExtendedFloatingActionButton(onClick = onClick) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = text)
            }
        } else {
            FloatingActionButton(onClick = onClick) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
    }
}