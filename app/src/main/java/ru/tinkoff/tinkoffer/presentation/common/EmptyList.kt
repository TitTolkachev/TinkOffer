package ru.tinkoff.tinkoffer.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.tinkoff.tinkoffer.presentation.theme.AppTheme

@Composable
fun EmptyList() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Список пуст",
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        Surface {
            EmptyList()
        }
    }
}