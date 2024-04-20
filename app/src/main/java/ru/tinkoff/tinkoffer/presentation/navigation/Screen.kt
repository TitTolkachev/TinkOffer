package ru.tinkoff.tinkoffer.presentation.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object SignIn : Screen("sign_in")
    data object Profile : Screen("profile")
}