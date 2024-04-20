package ru.tinkoff.tinkoffer.presentation.screen.createproject

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import ru.tinkoff.tinkoffer.R
import ru.tinkoff.tinkoffer.presentation.common.InputField
import ru.tinkoff.tinkoffer.presentation.common.SnackbarError
import ru.tinkoff.tinkoffer.presentation.theme.AppTheme

@Composable
fun CreateProjectScreen(
    navigateBack: () -> Unit,
    navigateToProject: () -> Unit,
) {
    val viewModel: CreateProjectViewModel = koinViewModel()
    val shackBarHostState = remember { SnackbarHostState() }

    val state by viewModel.state.collectAsState()

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

    Screen(
        state = state,
        shackBarHostState = shackBarHostState,

        changeName = remember { { viewModel.changeName(it) } },
        changeSchedule = remember { { viewModel.changeSchedule(it) } },
        changeVoices = remember { { viewModel.changeVoices(it.toIntOrNull() ?: 0) } },
        changeRefreshDays = remember { { viewModel.changeRefreshDays(it.toIntOrNull() ?: 0) } },
        onBackClick = remember { { viewModel.navigateBack() } },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Screen(
    state: CreateProjectState,
    shackBarHostState: SnackbarHostState = remember { SnackbarHostState() },

    changeName: (String) -> Unit = {},
    changeSchedule: (String) -> Unit = {},
    changeVoices: (String) -> Unit = {},
    changeRefreshDays: (String) -> Unit = {},
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
                            painter = painterResource(id = R.drawable.ic_cancel),
                            contentDescription = null,
                            tint = Color(0xFF548BF7)
                        )
                    }
                },
                title = {
                    Row {
                        Spacer(Modifier.width(12.dp))
                        Text(
                            text = "Создание проекта",
                            style = MaterialTheme.typography.headlineSmall,
                        )
                    }
                })
        },
        snackbarHost = {
            SnackbarHost(hostState = shackBarHostState, snackbar = {
                SnackbarError(it)
            })
        }
    ) { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            InputField(
                modifier = Modifier.fillMaxWidth(),
                value = state.name,
                onValueChange = changeName,
                placeholder = "Название",
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Screen(state = CreateProjectState())
        }
    }
}