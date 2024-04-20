package ru.tinkoff.tinkoffer.presentation.screen.home

import ru.tinkoff.tinkoffer.R

sealed class HomePage(val icon: Int, val title: String) {
    data object Main : HomePage(
        icon = R.drawable.home,
        title = "Главная"
    )

    data object NewProposals : HomePage(
        icon = R.drawable.resource_new,
        title = "Новые"
    )

    data object ActiveProposals : HomePage(
        icon = R.drawable.in_work,
        title = "Активные"
    )

    data object AcceptedProposals : HomePage(
        icon = R.drawable.approve,
        title = "Принятые"
    )

    data object RejectedProposals : HomePage(
        icon = R.drawable.deny,
        title = "Отклоненные"
    )
}