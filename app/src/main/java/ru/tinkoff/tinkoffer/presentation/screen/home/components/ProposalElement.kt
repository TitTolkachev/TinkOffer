package ru.tinkoff.tinkoffer.presentation.screen.home.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.tinkoff.tinkoffer.presentation.common.ProposalShort
import ru.tinkoff.tinkoffer.presentation.common.UserShort
import ru.tinkoff.tinkoffer.presentation.common.avatars
import ru.tinkoff.tinkoffer.presentation.theme.AppTheme

@Composable
fun ProposalElement(item: ProposalShort, onClick: () -> Unit = {}) {
    Card(onClick = onClick) {
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.padding(horizontal = 8.dp)) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = avatars[item.user.avatar]),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                modifier = Modifier.weight(1f),
                text = item.user.nickname
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "-${item.dislikes}",
                color = if (isSystemInDarkTheme()) Color(0xFFD32524) else Color(0xFFE64353),
            )
            Text(
                text = " / ",
            )
            Text(
                text = "+${item.likes}",
                color = if (isSystemInDarkTheme()) Color(0xFF06951E) else Color(0xFF2AC756),
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.padding(horizontal = 8.dp)) {
            Text(
                modifier = Modifier.weight(1f),
                text = item.text
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            ProposalElement(
                ProposalShort(
                    id = "12",
                    text = "Предложение супер крутое",
                    likes = 11,
                    dislikes = 123,
                    user = UserShort(id = "4", avatar = 1, nickname = "Android"),
                )
            )
        }
    }
}