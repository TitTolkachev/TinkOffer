package ru.tinkoff.tinkoffer.presentation.screen.home.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
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
fun BottomNavBar(active: Int, onClick: (Int) -> Unit) {
    Column {
        HorizontalDivider(
            thickness = 0.5.dp,
            color = if (isSystemInDarkTheme()) Color(0xFF3F3F3F) else Color(0xFFE1E1E1)
        )
        Row {
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
}

@Composable
private fun RowScope.NavBarItem(item: HomePage, selected: Boolean, onClick: () -> Unit) {
    NavigationBarItem(
        icon = { NavBarIcon(item, selected) },
        label = { NavBarText(item.title) },
        selected = selected,
        onClick = onClick,
        colors = NavigationBarItemDefaults.colors(
            selectedTextColor = Color(0xFF4A87F8),
            unselectedTextColor = Color(0xFF6C6C6C)
        )
    )
}

@Composable
private fun NavBarIcon(item: HomePage, selected: Boolean) {
    Icon(
        modifier = Modifier.size(32.dp),
        painter = if (selected) painterResource(id = item.darkIcon) else painterResource(id = item.icon),
        tint = Color.Unspecified,
        contentDescription = item.title
    )
}

@Composable
private fun NavBarText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelSmall,
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