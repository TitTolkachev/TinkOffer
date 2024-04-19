package ru.tinkoff.tinkoffer.presentation.screen.home.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.tinkoff.tinkoffer.presentation.screen.home.HomePage
import ru.tinkoff.tinkoffer.presentation.theme.AppTheme

@Composable
fun BottomNavBar(active: HomePage, onClick: (HomePage) -> Unit) {
    NavigationBar {
        listOf(
            HomePage.Main,
            HomePage.NewProposals,
            HomePage.ActiveProposals,
            HomePage.AcceptedProposals,
            HomePage.RejectedProposals,
        ).forEach { item ->
            NavBarItem(
                item = item,
                selected = active == item,
                onClick = remember { { onClick(item) } }
            )
        }
    }
}

@Composable
private fun RowScope.NavBarItem(item: HomePage, selected: Boolean, onClick: () -> Unit) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = { NavBarIcon(item, selected) },
        label = { NavBarText(item.title) },
    )
}

@Composable
private fun NavBarIcon(item: HomePage, selected: Boolean) {
    Icon(
        modifier = Modifier.size(24.dp),
        painter = painterResource(id = item.icon),
        tint = if (selected) Color(0xFF396BBB) else Color(0xFF7F838D),
        contentDescription = item.title
    )
}

@Composable
private fun NavBarText(text: String) {
    Text(text = text)
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        Surface {
            BottomNavBar(
                active = HomePage.Main,
                onClick = {}
            )
        }
    }
}