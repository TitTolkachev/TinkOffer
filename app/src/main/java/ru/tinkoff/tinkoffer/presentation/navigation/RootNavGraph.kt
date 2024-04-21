package ru.tinkoff.tinkoffer.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import ru.tinkoff.tinkoffer.presentation.screen.createproject.CreateProjectScreen
import ru.tinkoff.tinkoffer.presentation.screen.home.HomeScreen
import ru.tinkoff.tinkoffer.presentation.screen.profile.ProfileScreen
import ru.tinkoff.tinkoffer.presentation.screen.projectlist.ProjectListScreen
import ru.tinkoff.tinkoffer.presentation.screen.projectsettings.ProjectSettingsScreen
import ru.tinkoff.tinkoffer.presentation.screen.projectusers.ProjectUsersScreen
import ru.tinkoff.tinkoffer.presentation.screen.proposal.ProposalScreen
import ru.tinkoff.tinkoffer.presentation.screen.signin.SignInScreen

@Composable
fun RootNavGraph(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier.fillMaxSize()
    ) {
        composable(
            route = Screen.Home.route,
            enterTransition = { enterTransition() },
            popEnterTransition = { popEnterTransition() },
            exitTransition = { exitTransition() },
            popExitTransition = { popExitTransition() },
        ) {
            HomeScreen(
                navigateToProfile = {
                    navController.navigate(route = Screen.Profile.route) {
                        launchSingleTop = true
                    }
                },
                navigateToProjectSettings = { projectId ->
                    navController.navigate(route = Screen.ProjectSettings.route + "/$projectId") {
                        launchSingleTop = true
                    }
                },
                navigateToProjectList = {
                    navController.navigate(route = Screen.ProjectList.route) {
                        launchSingleTop = true
                    }
                },
                navigateToProposal = { proposal, projectId, isAdmin ->
                    navController.navigate(route = Screen.Proposal.route + "/${proposal.id}/${projectId}/$isAdmin") {
                        launchSingleTop = true
                    }
                },
                navigateToProjectUsers = { projectId, userId ->
                    navController.navigate(route = Screen.ProjectUsers.route + "/$projectId/$userId") {
                        launchSingleTop = true
                    }
                },
            )
        }
        composable(
            route = Screen.SignIn.route,
            enterTransition = { enterTransition() },
            popEnterTransition = { popEnterTransition() },
            exitTransition = { exitTransition() },
            popExitTransition = { popExitTransition() },
            deepLinks = listOf(
                navDeepLink { uriPattern = "tinkoffer://sign-in/{token}" },
            ),
            arguments = listOf(
                navArgument("token") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) {
            SignInScreen(
                navigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        launchSingleTop = true
                        popUpTo(Screen.SignIn.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(
            route = Screen.Profile.route,
            enterTransition = { enterTransition() },
            popEnterTransition = { popEnterTransition() },
            exitTransition = { exitTransition() },
            popExitTransition = { popExitTransition() },
        ) {
            ProfileScreen(
                navigateBack = { navController.popBackStack() },
                navigateToSignIn = {
                    navController.navigate(Screen.SignIn.route) {
                        launchSingleTop = true
                        popUpTo(0) { inclusive = true }
                    }
                },
            )
        }
        composable(
            route = Screen.ProjectSettings.route + "/{project_id}",
            enterTransition = { enterTransition() },
            popEnterTransition = { popEnterTransition() },
            exitTransition = { exitTransition() },
            popExitTransition = { popExitTransition() },
            arguments = listOf(
                navArgument("project_id") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) {
            ProjectSettingsScreen(
                navigateBack = { navController.popBackStack() },
                navigateToProject = {
                    navController.navigate(Screen.Home.route) {
                        launchSingleTop = true
                        popUpTo(0) { inclusive = true }
                    }
                },
            )
        }
        composable(
            route = Screen.ProjectList.route,
            enterTransition = { enterTransition() },
            popEnterTransition = { popEnterTransition() },
            exitTransition = { exitTransition() },
            popExitTransition = { popExitTransition() },
        ) {
            ProjectListScreen(
                navigateBack = { navController.popBackStack() },
                navigateToProject = {
                    navController.navigate(Screen.Home.route) {
                        launchSingleTop = true
                        popUpTo(0) { inclusive = true }
                    }
                },
                navigateToCreateProject = {
                    navController.navigate(Screen.CreateProject.route) {
                        launchSingleTop = true
                    }
                },
            )
        }
        composable(
            route = Screen.Proposal.route + "/{proposal_id}/{project_id}/{is_admin}",
            enterTransition = { enterTransition() },
            popEnterTransition = { popEnterTransition() },
            exitTransition = { exitTransition() },
            popExitTransition = { popExitTransition() },
            arguments = listOf(
                navArgument("proposal_id") {
                    type = NavType.StringType
                    nullable = false
                },
                navArgument("project_id") {
                    type = NavType.StringType
                    nullable = false
                },
                navArgument("is_admin") {
                    type = NavType.BoolType
                    nullable = false
                },

                )
        ) {
            ProposalScreen(
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(
            route = Screen.CreateProject.route,
            enterTransition = { enterTransition() },
            popEnterTransition = { popEnterTransition() },
            exitTransition = { exitTransition() },
            popExitTransition = { popExitTransition() },
        ) {
            CreateProjectScreen(
                navigateBack = { navController.popBackStack() },
                navigateToProject = {
                    navController.navigate(Screen.Home.route) {
                        launchSingleTop = true
                        popUpTo(0) { inclusive = true }
                    }
                },
            )
        }
        composable(
            route = Screen.ProjectUsers.route + "/{project_id}/{user_id}",
            enterTransition = { enterTransition() },
            popEnterTransition = { popEnterTransition() },
            exitTransition = { exitTransition() },
            popExitTransition = { popExitTransition() },
            arguments = listOf(
                navArgument("project_id") {
                    type = NavType.StringType
                    nullable = false
                },
                navArgument("user_id") {
                    type = NavType.StringType
                    nullable = false
                },
                )
        ) {
            ProjectUsersScreen(
                navigateBack = { navController.popBackStack() },
            )
        }
    }
}

/**
 * Shows transition animation after navigating to screen.
 */
private fun AnimatedContentTransitionScope<NavBackStackEntry>.enterTransition(): EnterTransition {
    return slideIntoContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Left,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
    )
}

/**
 * Shows transition animation after navigating from screen.
 */
private fun AnimatedContentTransitionScope<NavBackStackEntry>.exitTransition(): ExitTransition {
    return slideOutOfContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Left,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        targetOffset = { it / 5 }
    )
}

/**
 * Shows transition animation after navigating to screen.
 */
private fun AnimatedContentTransitionScope<NavBackStackEntry>.popEnterTransition(): EnterTransition {
    return slideIntoContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Right,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        initialOffset = { it / 5 }
    )
}

/**
 * Shows transition animation after navigating from screen.
 */
private fun AnimatedContentTransitionScope<NavBackStackEntry>.popExitTransition(): ExitTransition {
    return slideOutOfContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Right,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
    )
}