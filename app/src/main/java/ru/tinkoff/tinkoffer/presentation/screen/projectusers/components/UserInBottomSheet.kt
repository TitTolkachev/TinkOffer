package ru.tinkoff.tinkoffer.presentation.screen.projectusers.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.tinkoff.tinkoffer.presentation.common.avatars
import ru.tinkoff.tinkoffer.presentation.shadowCustom
import ru.tinkoff.tinkoffer.presentation.theme.AppTheme

@Composable
fun UserInBottomSheet(
    avatar: Int,
    nickname: String,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .then(
                if (!isSystemInDarkTheme()) Modifier.shadowCustom(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    blurRadius = 32.dp,
                    shapeRadius = 16.dp,
                ) else Modifier
            )
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable { onClick() }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(40.dp),
                painter = painterResource(id = avatars[avatar]),
                contentDescription = null,
                tint = Color.Unspecified,
            )
            Spacer(Modifier.width(8.dp))
            Text(
                modifier = Modifier.weight(1f),
                text = nickname,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            UserInBottomSheet(
                avatar = 12,
                nickname = "Nickname"
            ) {}
        }
    }
}