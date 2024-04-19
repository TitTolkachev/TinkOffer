package ru.tinkoff.tinkoffer.presentation.screen.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import ru.tinkoff.tinkoffer.presentation.screen.home.components.BottomNavBar
import ru.tinkoff.tinkoffer.presentation.screen.home.components.Fab
import ru.tinkoff.tinkoffer.presentation.screen.home.pages.AcceptedProposalsPage
import ru.tinkoff.tinkoffer.presentation.screen.home.pages.ActiveProposalsPage
import ru.tinkoff.tinkoffer.presentation.screen.home.pages.NewProposalsPage
import ru.tinkoff.tinkoffer.presentation.screen.home.pages.ProjectPage
import ru.tinkoff.tinkoffer.presentation.screen.home.pages.RejectedProposalsPage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = koinViewModel()
    val fabVisible by viewModel.fabVisible.collectAsState()
    val pagerState = rememberPagerState(0, 0f) { 5 }
    val scope = rememberCoroutineScope()
    val selectedIndex by viewModel.selectedIndex.collectAsState()

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect {
            viewModel.onBottomNavItemClick(it)
        }
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
                active = selectedIndex,
                onClick = remember {
                    { scope.launch { pagerState.animateScrollToPage(it) } }
                }
            )
        }
    ) { paddingValues ->
        Screen(
            modifier = Modifier.padding(paddingValues),
            pagerState = pagerState
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Screen(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
) {
    HorizontalPager(
        modifier = modifier,
        state = pagerState,
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