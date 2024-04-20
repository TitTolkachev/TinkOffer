package ru.tinkoff.tinkoffer.presentation.screen.projectlist.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.tinkoff.tinkoffer.R
import ru.tinkoff.tinkoffer.presentation.common.ProjectShort
import ru.tinkoff.tinkoffer.presentation.fromEpochDate
import ru.tinkoff.tinkoffer.presentation.shadowCustom
import ru.tinkoff.tinkoffer.presentation.theme.AppTheme

@Composable
fun ProjectItem(item: ProjectShort, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .then(
                if (!isSystemInDarkTheme()) Modifier.shadowCustom(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    blurRadius = 32.dp,
                    shapeRadius = 16.dp,
                ) else Modifier
            ), onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = fromEpochDate(item.creationDate) ?: "",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(Modifier.weight(1f))
            Text(text = item.members.toString())
            Spacer(Modifier.width(8.dp))
            Icon(
                painter = painterResource(id = R.drawable.ic_group_24),
                contentDescription = null,
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            ProjectItem(
                item = ProjectShort(
                    id = "123",
                    name = "Project Name",
                    members = 14,
                    creationDate = 2343242
                )
            ) {}
        }
    }
}