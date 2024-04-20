package ru.tinkoff.tinkoffer.presentation.screen.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.tinkoff.tinkoffer.presentation.screen.home.HomePage
import ru.tinkoff.tinkoffer.presentation.theme.AppTheme

@Composable
fun BottomNavBar(active: Int, onClick: (Int) -> Unit) {
    NavigationBar {
        listOf(
            HomePage.Main,
            HomePage.NewProposals,
            HomePage.ActiveProposals,
            HomePage.AcceptedProposals,
            HomePage.RejectedProposals,
        ).forEachIndexed { index, item ->
            NavBarItem(
                item = item,
                selected = active == index,
                onClick = remember { { onClick(index) } }
            )
        }
    }
}

@Composable
private fun RowScope.NavBarItem(item: HomePage, selected: Boolean, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .weight(1f)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
    ) {
        NavBarIcon(item)
        NavBarText(item.title)
    }
}

@Composable
private fun NavBarIcon(item: HomePage) {
    Icon(
        modifier = Modifier.size(24.dp),
        painter = painterResource(id = item.icon),
        tint = Color.Unspecified,
        contentDescription = item.title
    )
}

@Composable
private fun NavBarText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelSmall
    )
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        Surface {
            BottomNavBar(
                active = 0,
                onClick = {}
            )
        }
    }
}