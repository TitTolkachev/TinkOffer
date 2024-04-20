package ru.tinkoff.tinkoffer.presentation.common

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.tinkoff.tinkoffer.presentation.theme.AppTheme

@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String? = null,
    singleLine: Boolean = true,
) {
    TextField(
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