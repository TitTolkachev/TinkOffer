package ru.tinkoff.tinkoffer.presentation.screen.createproject

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.input.KeyboardType
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
        loading = viewModel.loading.collectAsState().value,
        state = state,
        shackBarHostState = shackBarHostState,

        changeName = remember { { viewModel.changeName(it) } },
        changeSchedule = remember { { viewModel.changeSchedule(it) } },
        changeVoices = remember { { viewModel.changeVoices(it.toIntOrNull() ?: 0) } },
        changeRefreshDays = remember { { viewModel.changeRefreshDays(it.toIntOrNull() ?: 0) } },
        create = remember { { viewModel.create() } },
        onBackClick = remember { { viewModel.navigateBack() } },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Screen(
    loading: Boolean,
    state: CreateProjectState,
    shackBarHostState: SnackbarHostState = remember { SnackbarHostState() },

    changeName: (String) -> Unit = {},
    changeSchedule: (String) -> Unit = {},
    changeVoices: (String) -> Unit = {},
    changeRefreshDays: (String) -> Unit = {},
    create: () -> Unit = {},
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
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Название")
            InputField(
                modifier = Modifier.fillMaxWidth(),
                value = state.name,
                onValueChange = changeName,
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Ближайшая встреча")
            InputField(
                modifier = Modifier.fillMaxWidth(),
                value = state.schedule,
                onValueChange = changeSchedule,
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Лимит голосов")
            InputField(
                modifier = Modifier.fillMaxWidth(),
                value = state.voices.toString(),
                onValueChange = changeVoices,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Частота пополнений голосов, дн")
            InputField(
                modifier = Modifier.fillMaxWidth(),
                value = state.refreshDays.toString(),
                onValueChange = changeRefreshDays,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = create
            ) {
                Text(text = "Создать")
            }

            if (loading) {
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Screen(loading = false, state = CreateProjectState())
        }
    }
}