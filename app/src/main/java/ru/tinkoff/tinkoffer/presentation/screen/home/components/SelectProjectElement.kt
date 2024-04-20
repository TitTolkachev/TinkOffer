package ru.tinkoff.tinkoffer.presentation.screen.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.tinkoff.tinkoffer.R
import ru.tinkoff.tinkoffer.data.models.ShortProjectInfo
import ru.tinkoff.tinkoffer.presentation.theme.AppTheme

@Composable
fun SelectProjectElement(
    projectInfo: ShortProjectInfo? = null,
    onChangeProjectClick: () -> Unit
) {
    val configuration = LocalConfiguration.current

    Column(
        modifier = Modifier.wrapContentWidth(),
        horizontalAlignment = Alignment.Start
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = projectInfo?.name ?: "Проект не выбран",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.widthIn(0.dp, configuration.screenWidthDp.dp / 1.8f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 14.sp
            )
            IconButton(onClick = onChangeProjectClick) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        if (projectInfo == null) {
            Text(text = "Выберите проект для отображения", fontSize = 14.sp)
        } else {
            with(projectInfo) {
                Row {
                    Text(text = "Участники: ", fontSize = 14.sp)
                    Icon(
                        painter = painterResource(id = R.drawable.ic_group_24),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(text = numberOfPeople.toString(), fontSize = 14.sp)
                }

            }

        }


    }
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column {
                SelectProjectElement(
                    ShortProjectInfo(
                        "",
                        "Великолепный проект",
                        10,
                    ),
                    {}
                )
                SelectProjectElement(
                    ShortProjectInfo(
                        "",
                        "Очень длинное название проекта",
                        10,
                    ),
                    {}
                )


                SelectProjectElement(
                    null,
                    {}
                )

            }

        }
    }
}