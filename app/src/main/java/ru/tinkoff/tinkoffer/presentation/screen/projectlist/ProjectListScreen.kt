package ru.tinkoff.tinkoffer.presentation.screen.projectlist

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import ru.tinkoff.tinkoffer.R
import ru.tinkoff.tinkoffer.presentation.common.ProjectShort
import ru.tinkoff.tinkoffer.presentation.common.SnackbarError
import ru.tinkoff.tinkoffer.presentation.common.projects
import ru.tinkoff.tinkoffer.presentation.screen.home.components.Fab
import ru.tinkoff.tinkoffer.presentation.screen.projectlist.components.ProjectItem
import ru.tinkoff.tinkoffer.presentation.theme.AppTheme

@Composable
fun ProjectListScreen(
    navigateBack: () -> Unit,
    navigateToProject: () -> Unit,
    navigateToCreateProject: () -> Unit,
) {
    val viewModel: ProjectListViewModel = koinViewModel()
    val shackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.navigateBack.collect {
            navigateBack()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.navigateToProject.collect {
            navigateToProject()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.navigateToCreateProject.collect {
            navigateToCreateProject()
        }
    }

    Screen(
        items = projects,
        shackBarHostState = shackBarHostState,

        onProjectClick = remember { { viewModel.onProjectClick(it) } },
        onFabClick = remember { { viewModel.onCreateProjectClick() } },
        onBackClick = remember { { viewModel.navigateBack() } },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Screen(
    items: List<ProjectShort>,
    shackBarHostState: SnackbarHostState = remember { SnackbarHostState() },

    onProjectClick: (ProjectShort) -> Unit = {},
    onFabClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    Scaffold(
        modifier = Modifier
            .imePadding()
            .animateContentSize(),
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.close),
                            contentDescription = null,
                            tint = Color(0xFF548BF7)
                        )
                    }
                },
                title = {
                    Row {
                        Spacer(Modifier.width(12.dp))
                        Text(
                            text = "Список проектов",
                            style = MaterialTheme.typography.headlineSmall,
                        )
                    }
                })
        },
        floatingActionButton = {
            Fab(visible = true, onClick = onFabClick)
        },
        snackbarHost = {
            SnackbarHost(hostState = shackBarHostState, snackbar = {
                SnackbarError(it)
            })
        }
    ) { paddingValues ->
        LazyColumn(
            Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = spacedBy(8.dp),
            contentPadding = PaddingValues(16.dp),
        ) {
            items(items = items, key = { it.id }) {
                ProjectItem(item = it, onClick = { onProjectClick(it) })
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Screen(
                items = projects,
            )
        }
    }
}