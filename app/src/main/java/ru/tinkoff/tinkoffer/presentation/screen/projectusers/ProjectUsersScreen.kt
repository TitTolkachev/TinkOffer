package ru.tinkoff.tinkoffer.presentation.screen.projectusers

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import ru.tinkoff.tinkoffer.R
import ru.tinkoff.tinkoffer.data.models.users.response.UserDto
import ru.tinkoff.tinkoffer.presentation.common.SnackbarError
import ru.tinkoff.tinkoffer.presentation.screen.home.components.Fab
import ru.tinkoff.tinkoffer.presentation.screen.projectusers.components.UserBlock
import ru.tinkoff.tinkoffer.presentation.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectUsersScreen(
    navigateBack: () -> Unit,
) {
    val viewModel: ProjectUsersViewModel = koinViewModel()
    val shackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.navigateBack.collect {
            navigateBack()
        }
    }

    val scope = rememberCoroutineScope()
    val sheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.Hidden,
        skipHiddenState = false
    )
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            scope.launch {
                sheetState.hide()
            }
        }
    ) {
        listOf(
            UserDto("1", "FirstName", "LastName", "mName", "943820348", 12),
            UserDto("2", "FirstName", "LastName", "mName", "943820348", 12),
            UserDto("3", "FirstName", "LastName", "mName", "943820348", 12),
            UserDto("4", "FirstName", "LastName", "mName", "943820348", 12),
            UserDto("5", "FirstName", "LastName", "mName", "943820348", 12),
            UserDto("6", "FirstName", "LastName", "mName", "943820348", 12),
            UserDto("7", "FirstName", "LastName", "mName", "943820348", 12),
            UserDto("8", "FirstName", "LastName", "mName", "943820348", 12),
            UserDto("9", "FirstName", "LastName", "mName", "943820348", 12),
        )
    }

    Screen(
        items = listOf(
            UserDto("1", "FirstName", "LastName", "mName", "943820348", 12),
            UserDto("2", "FirstName", "LastName", "mName", "943820348", 12),
            UserDto("3", "FirstName", "LastName", "mName", "943820348", 12),
        ),
        loading = viewModel.loading.collectAsState().value,
        shackBarHostState = shackBarHostState,

        onFabClick = { scope.launch { sheetState.partialExpand() } },
        onBackClick = remember { { viewModel.navigateBack() } },
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Screen(
    items: List<UserDto> = emptyList(),

    loading: Boolean,
    shackBarHostState: SnackbarHostState = remember { SnackbarHostState() },

    onBackClick: () -> Unit = {},
    onFabClick: () -> Unit = {},
) {
    Scaffold(
        modifier = Modifier
            .imePadding()
            .animateContentSize(),
        floatingActionButton = {
            Fab(visible = true, onClick = onFabClick)
        },
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
                            text = "Участники",
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
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = spacedBy(8.dp)
        ) {
            items(items = items, key = { it.id }) {
                UserBlock(
                    avatar = 12, nickname = "Nickname", admin = true, {}, {}, {}
                )
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
                items = listOf(
                    UserDto("1", "FirstName", "LastName", "mName", "943820348", 12),
                    UserDto("2", "FirstName", "LastName", "mName", "943820348", 12),
                    UserDto("3", "FirstName", "LastName", "mName", "943820348", 12),

                    ),
                loading = false,
            )
        }
    }
}