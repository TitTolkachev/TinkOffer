package ru.tinkoff.tinkoffer.presentation.screen.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import ru.tinkoff.tinkoffer.data.models.projects.response.ProjectInfoDto
import ru.tinkoff.tinkoffer.data.models.proposals.response.ProposalInListDto
import ru.tinkoff.tinkoffer.presentation.common.InputField
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
    navigateToProjectSettings: (String) -> Unit,
    navigateToProjectList: () -> Unit,
    navigateToProposal: (ProposalShort) -> Unit,
) {
    val viewModel: HomeViewModel = koinViewModel()
    val fabVisible by viewModel.fabVisible.collectAsState()
    val pagerState = rememberPagerState(0, 0f) { 5 }
    val scope = rememberCoroutineScope()
    val selectedIndex by viewModel.selectedIndex.collectAsState()

    // Data on screen
    val userId by viewModel.userId.collectAsState()
    val activeProjectInfo by viewModel.activeProjectInfo.collectAsState()
    val proposalsForActiveProject by viewModel.proposalsForActiveProject.collectAsState()

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect {
            viewModel.onBottomNavItemClick(it)
        }
    }

    LaunchedEffect(true) {
        viewModel.scrollToIndex.collect {
            scope.launch {
                pagerState.animateScrollToPage(it)
            }
        }
    }

    val dialogState by viewModel.dialogState.collectAsState()
    if (dialogState != null) {
        Dialog(onDismissRequest = remember { { viewModel.hideDialog() } }) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(32.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .padding(24.dp)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Новое предложение",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(24.dp))
                InputField(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .heightIn(0.dp, 200.dp),
                    value = dialogState?.text ?: "",
                    onValueChange = remember { { viewModel.changeDialogText(it) } },
                    singleLine = false,
                    placeholder = "Текст..."
                )
                Spacer(modifier = Modifier.height(16.dp))
                InputField(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    value = dialogState?.link ?: "",
                    onValueChange = remember { { viewModel.changeDialogLink(it) } },
                    placeholder = "Ссылка"
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    modifier = Modifier.align(Alignment.End),
                    onClick = remember { { viewModel.createProposal() } },
                ) {
                    Text(text = "Создать")
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(vertical = 16.dp, horizontal = 24.dp)
                    .windowInsetsPadding(WindowInsets.statusBars),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                SelectProjectElement(
                    projectInfo = activeProjectInfo,
                    onChangeProjectClick = navigateToProjectList,
                )
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
                text = "Предложение",
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
            userId = userId ?: "",
            activeProjectInfoDto = activeProjectInfo,
            proposalsForActiveProject = proposalsForActiveProject,
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
    userId: String,
    activeProjectInfoDto: ProjectInfoDto?,
    proposalsForActiveProject: List<ProposalInListDto>,
    navigateToProjectSettings: (String) -> Unit,
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
                    activeProjectInfoDto = activeProjectInfoDto,
                    countOfProposals = proposalsForActiveProject.size,
                    voteAvailable = activeProjectInfoDto?.users?.firstOrNull { it.id == userId }?.availableVotes?.toInt()
                        ?: 0,
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