package ru.tinkoff.tinkoffer.presentation.common

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.tinkoff.tinkoffer.presentation.theme.AppTheme

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String? = null,
    singleLine: Boolean = true,
) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        placeholder = placeholder?.let {
            {
                Text(text = placeholder)
            }
        },
        singleLine = singleLine,
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedLabelColor = MaterialTheme.colorScheme.surfaceTint,
            focusedLeadingIconColor = MaterialTheme.colorScheme.surfaceTint,
            focusedTrailingIconColor = MaterialTheme.colorScheme.surfaceTint,

            disabledTextColor = MaterialTheme.colorScheme.surfaceTint,
            disabledTrailingIconColor = MaterialTheme.colorScheme.outline,
            disabledLabelColor = MaterialTheme.colorScheme.surfaceTint,

            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedLabelColor = MaterialTheme.colorScheme.surfaceTint,
            unfocusedLeadingIconColor = MaterialTheme.colorScheme.surfaceTint,
            unfocusedTrailingIconColor = MaterialTheme.colorScheme.surfaceTint,

            cursorColor = MaterialTheme.colorScheme.onBackground,

            focusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),

            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        )
    )
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            InputField(value = "Text", onValueChange = {})
        }
    }
}