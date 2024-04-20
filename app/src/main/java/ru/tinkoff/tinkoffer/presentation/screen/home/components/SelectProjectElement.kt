package ru.tinkoff.tinkoffer.presentation.screen.home.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.tinkoff.tinkoffer.presentation.theme.AppTheme

@Composable
fun SelectProjectElement() {
    // Максим
    Text("Максим")
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            SelectProjectElement()
        }
    }
}