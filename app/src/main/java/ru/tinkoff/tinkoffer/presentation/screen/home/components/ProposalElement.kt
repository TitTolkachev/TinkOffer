package ru.tinkoff.tinkoffer.presentation.screen.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.tinkoff.tinkoffer.R
import ru.tinkoff.tinkoffer.presentation.common.ProposalShort
import ru.tinkoff.tinkoffer.presentation.common.UserShort
import ru.tinkoff.tinkoffer.presentation.common.avatars
import ru.tinkoff.tinkoffer.presentation.shadowCustom
import ru.tinkoff.tinkoffer.presentation.theme.AppTheme

@Composable
fun ProposalElement(
    item: ProposalShort,
    onClick: () -> Unit = {},
    onLike: () -> Unit = {},
    onDislike: () -> Unit = {},
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
            .padding(12.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier.size(27.dp),
                painter = painterResource(id = avatars[item.user.avatar]),
                contentDescription = null,
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                modifier = Modifier.weight(1f),
                text = item.user.nickname,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Text(
                text = "-${item.dislikes}",
                color = if (isSystemInDarkTheme()) Color.White else Color(0xFF3F3F3F),
            )
            Text(
                text = " | ",
            )
            Text(
                text = "+${item.likes}",
                color = if (isSystemInDarkTheme()) Color(0xFFFEDD2D) else Color(0xFF4A87F8),
            )

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(onClick = onLike) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_thumb_up),
                    contentDescription = null,
                    tint = if (item.voice == true && isSystemInDarkTheme()) {
                        Color(0xFFFEDD2D)
                    } else if (item.voice == true && !isSystemInDarkTheme()) {
                        Color(0xFF4A87F8)
                    } else if (isSystemInDarkTheme()) {
                        Color.White
                    } else {
                        Color(0xFFA0A0A0)
                    }
                )
            }

            IconButton(onClick = onDislike) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_thumb_down),
                    contentDescription = null,
                    tint = if (item.voice == false && isSystemInDarkTheme()) {
                        Color(0xFFFEDD2D)
                    } else if (item.voice == false && !isSystemInDarkTheme()) {
                        Color(0xFF4A87F8)
                    } else if (isSystemInDarkTheme()) {
                        Color.White
                    } else {
                        Color(0xFFA0A0A0)
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = item.text,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
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