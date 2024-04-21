package ru.tinkoff.tinkoffer.presentation.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object SignIn : Screen("sign_in")
    data object Profile : Screen("profile")
    data object ProjectSettings : Screen("project_settings")
    data object ProjectList : Screen("project_list")
    data object Proposal : Screen("proposal")
    data object CreateProject : Screen("create_project")
    data object ProjectUsers : Screen("project_users")
    data object Drafts : Screen("drafts")
}