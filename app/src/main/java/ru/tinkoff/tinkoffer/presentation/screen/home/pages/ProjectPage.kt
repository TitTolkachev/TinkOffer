package ru.tinkoff.tinkoffer.presentation.screen.home.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ProjectPage(
    modifier: Modifier = Modifier,
) {
    Box(modifier.fillMaxSize()) {
        Text(modifier = Modifier.align(Alignment.Center), text = "ProjectPage")
    }
}