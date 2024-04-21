package ru.tinkoff.tinkoffer.presentation.screen.home

import ru.tinkoff.tinkoffer.R

sealed class HomePage(val icon: Int, val darkIcon: Int, val title: String) {
    data object Main : HomePage(
        icon = R.drawable.home,
        darkIcon = R.drawable.home_dark,
        title = "Главная"
    )

    data object NewProposals : HomePage(
        icon = R.drawable.resource_new,
        darkIcon = R.drawable.resource_new_dark,
        title = "Новые"
    )

    data object ActiveProposals : HomePage(
        icon = R.drawable.in_work,
        darkIcon = R.drawable.in_work_dark,
        title = "Активные"
    )

    data object AcceptedProposals : HomePage(
        icon = R.drawable.approve,
        darkIcon = R.drawable.approve_dark,
        title = "Принятые"
    )

    data object RejectedProposals : HomePage(
        icon = R.drawable.deny,
        darkIcon = R.drawable.deny_dark,
        title = "Закрытые"
    )
}