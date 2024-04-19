package ru.tinkoff.tinkoffer.presentation.screen.home

import ru.tinkoff.tinkoffer.R

sealed class HomePage(val route: String, val icon: Int, val title: String) {
    data object Main : HomePage(
        route = "main",
        icon = R.drawable.ic_launcher_foreground,
        title = "Главная"
    )

    data object NewProposals : HomePage(
        route = "new",
        icon = R.drawable.ic_launcher_foreground,
        title = "Новые"
    )

    data object ActiveProposals : HomePage(
        route = "active",
        icon = R.drawable.ic_launcher_foreground,
        title = "Активные"
    )

    data object AcceptedProposals : HomePage(
        route = "accepted",
        icon = R.drawable.ic_launcher_foreground,
        title = "Принятые"
    )

    data object RejectedProposals : HomePage(
        route = "rejected",
        icon = R.drawable.ic_launcher_foreground,
        title = "Отклоненные"
    )
}