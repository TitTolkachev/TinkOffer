package ru.tinkoff.tinkoffer.presentation.screen.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import ru.tinkoff.tinkoffer.presentation.common.ProposalShort
import ru.tinkoff.tinkoffer.presentation.common.avatars
import ru.tinkoff.tinkoffer.presentation.screen.home.components.BottomNavBar
import ru.tinkoff.tinkoffer.presentation.screen.home.components.Fab
import ru.tinkoff.tinkoffer.presentation.screen.home.components.SelectProjectElement
import ru.tinkoff.tinkoffer.presentation.screen.home.pages.AcceptedProposalsPage
import ru.tinkoff.tinkoffer.presentation.screen.home.pages.ActiveProposalsPage
import ru.tinkoff.tinkoffer.presentation.screen.home.pages.NewProposalsPage
import ru.tinkoff.tinkoffer.presentation.screen.home.pages.ProjectPage
import ru.tinkoff.tinkoffer.presentation.screen.home.pages.RejectedProposalsPage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navigateToProfile: () -> Unit,
    navigateToProjectSettings: () -> Unit,
    navigateToProposal: (ProposalShort) -> Unit,
) {
    val viewModel: HomeViewModel = koinViewModel()
    val fabVisible by viewModel.fabVisible.collectAsState()
    val pagerState = rememberPagerState(0, 0f) { 5 }
    val scope = rememberCoroutineScope()
    val selectedIndex by viewModel.selectedIndex.collectAsState()

    // Data on screen
    val projectInfo by viewModel.projectInfo.collectAsState()

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect {
            viewModel.onBottomNavItemClick(it)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                SelectProjectElement(
                    projectInfo,
                ){
                    // TODO navigation to select project
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = navigateToProfile) {
                    Image(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape),
                        painter = painterResource(id = avatars[1]),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                    )
                }
            }
        },
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
            pagerState = pagerState,

            navigateToProjectSettings = navigateToProjectSettings,
            navigateToProposal = navigateToProposal,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Screen(
    modifier: Modifier = Modifier,
    pagerState: PagerState,

    navigateToProjectSettings: () -> Unit,
    navigateToProposal: (ProposalShort) -> Unit,
) {
    HorizontalPager(
        modifier = modifier,
        state = pagerState,
        beyondBoundsPageCount = 4,
    ) { page ->
        when (page) {
            0 -> {
                ProjectPage(
                    admin = true,
                    navigateToProjectSettings = navigateToProjectSettings,
                )
            }

            1 -> {
                NewProposalsPage()
            }

            2 -> {
                ActiveProposalsPage(
                    onProposalClick = navigateToProposal
                )
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