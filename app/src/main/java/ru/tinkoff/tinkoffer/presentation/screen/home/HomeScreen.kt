package ru.tinkoff.tinkoffer.presentation.screen.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import ru.tinkoff.tinkoffer.presentation.screen.home.components.BottomNavBar
import ru.tinkoff.tinkoffer.presentation.screen.home.components.Fab
import ru.tinkoff.tinkoffer.presentation.screen.home.pages.AcceptedProposalsPage
import ru.tinkoff.tinkoffer.presentation.screen.home.pages.ActiveProposalsPage
import ru.tinkoff.tinkoffer.presentation.screen.home.pages.NewProposalsPage
import ru.tinkoff.tinkoffer.presentation.screen.home.pages.ProjectPage
import ru.tinkoff.tinkoffer.presentation.screen.home.pages.RejectedProposalsPage

@Composable
fun HomeScreen() {

    val viewModel: HomeViewModel = koinViewModel()
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val activeScreen by viewModel.activeScreen.collectAsState()
    val fabVisible by viewModel.fabVisible.collectAsState()

    // Bottom Navigation Actions
    LaunchedEffect(Unit) {
        viewModel.bottomNavActions.collect { route ->
            navController.navigate(route) {
                navController.graph.startDestinationRoute?.let { startRoute ->
                    popUpTo(startRoute) {
                        saveState = true
                    }
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    // Active Screen Handling
    LaunchedEffect(backStackEntry) {
        viewModel.onActiveScreenChange(backStackEntry?.destination?.route)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            Fab(
                visible = fabVisible,
                onClick = remember { { viewModel.onFabClick() } }
            )
        },
        bottomBar = {
            BottomNavBar(
                active = activeScreen,
                onClick = remember { { viewModel.onBottomNavItemClick(it) } }
            )
        }
    ) { paddingValues ->
        Screen()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Screen() {
    HorizontalPager(
        state = rememberPagerState(0, 0f) { 5 },
        beyondBoundsPageCount = 4,
    ) { page ->
        when (page) {
            0 -> {
                ProjectPage()
            }

            1 -> {
                NewProposalsPage()
            }

            2 -> {
                ActiveProposalsPage()
            }

            3 -> {
                AcceptedProposalsPage()
            }

            4 -> {
                RejectedProposalsPage()
            }
        }
    }
}