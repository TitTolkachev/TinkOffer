package ru.tinkoff.tinkoffer.presentation.screen.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.booleanResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.tinkoff.tinkoffer.presentation.common.avatars
import ru.tinkoff.tinkoffer.presentation.theme.AppTheme

@Composable
fun ActiveMembers(
    modifier: Modifier = Modifier,
    memberIcons: List<Int> = listOf(),
    remainingPeople: Int = 0
) {
    Row(
        modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        with(memberIcons.getOrNull(0)) {
            if (this != null) {
                Image(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .border(1.dp, MaterialTheme.colorScheme.primary, CircleShape)
                        .background(Color.White),
                    painter = painterResource(id = avatars[this@with]),
                    contentDescription = null
                )
            }
        }
        with(memberIcons.getOrNull(1)) {
            if (this != null) {
                Image(
                    modifier = Modifier
                        .offset(x = (-5).dp)
                        .size(36.dp)
                        .clip(CircleShape)
                        .border(1.dp, MaterialTheme.colorScheme.primary, CircleShape)
                        .background(Color.Blue),
                    painter = painterResource(id = avatars[this@with]),
                    contentDescription = null
                )
            }
        }
        with(memberIcons.getOrNull(2)) {
            if (this != null) {
                Image(
                    modifier = Modifier
                        .offset(x = (-10).dp)
                        .size(36.dp)
                        .clip(CircleShape)
                        .border(1.dp, MaterialTheme.colorScheme.primary, CircleShape)
                        .background(Color.Red),
                    painter = painterResource(id = avatars[this@with]),
                    contentDescription = null
                )
            }
        }
        if (remainingPeople > 0) {
            Text(text = "и ещё ")
            Text(text = remainingPeople.toString(), fontWeight = FontWeight.Bold)
        }
    }
}


@Composable
@Preview
private fun ActiveMembersPreview() {
    AppTheme {
        Surface(color = MaterialTheme.colorScheme.surface) {
            Column {
                ActiveMembers()
                ActiveMembers(
                    memberIcons = listOf(1),
                    remainingPeople = 0
                )
                ActiveMembers(
                    memberIcons = listOf(1, 2),
                    remainingPeople = 0
                )

                ActiveMembers(
                    memberIcons = listOf(1, 2, 3),
                    remainingPeople = 0
                )

                ActiveMembers(
                    memberIcons = listOf(1, 2, 3),
                    remainingPeople = 10
                )
            }
        }
    }
}