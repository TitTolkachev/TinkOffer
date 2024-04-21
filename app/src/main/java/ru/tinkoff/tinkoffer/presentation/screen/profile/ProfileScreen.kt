package ru.tinkoff.tinkoffer.presentation.screen.profile

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import ru.tinkoff.tinkoffer.R
import ru.tinkoff.tinkoffer.presentation.common.SnackbarError
import ru.tinkoff.tinkoffer.presentation.common.avatars
import ru.tinkoff.tinkoffer.presentation.theme.AppTheme

@Composable
fun ProfileScreen(
    navigateBack: () -> Unit,
    navigateToSignIn: () -> Unit
) {
    val viewModel: ProfileViewModel = koinViewModel()
    val shackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.navigateBack.collect {
            navigateBack()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.navigateToSignIn.collect {
            navigateToSignIn()
        }
    }

    Screen(
        shackBarHostState = shackBarHostState,

        avatar = 12,//TODO()

        saveAvatar = remember { { viewModel.save(it) } },
        logout = remember { { viewModel.logout() } },

        onBackClick = remember { { viewModel.navigateBack() } },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Screen(
    shackBarHostState: SnackbarHostState = remember { SnackbarHostState() },

    avatar: Int,

    saveAvatar: (Int) -> Unit = {},
    logout: () -> Unit = {},

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
                            text = "Профиль",
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

        var selectedAvatar by remember {
            mutableIntStateOf(avatar)
        }

        Column(
            Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Image(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(124.dp),
                painter = painterResource(id = avatars[selectedAvatar]),
                contentDescription = null,
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(horizontalArrangement = spacedBy(24.dp)) {
                repeat(5) {
                    Image(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(48.dp)
                            .clickable { selectedAvatar = it },
                        painter = painterResource(id = avatars[it]),
                        contentDescription = null,
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(horizontalArrangement = spacedBy(24.dp)) {
                repeat(5) {
                    Image(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(48.dp)
                            .clickable { selectedAvatar = it + 5 },
                        painter = painterResource(id = avatars[it + 5]),
                        contentDescription = null,
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(horizontalArrangement = spacedBy(24.dp)) {
                repeat(3) {
                    Image(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(48.dp)
                            .clickable { selectedAvatar = it + 10 },
                        painter = painterResource(id = avatars[it + 10]),
                        contentDescription = null,
                    )
                }
            }

            Spacer(modifier = Modifier.height(64.dp))

            Column(modifier = Modifier.width(IntrinsicSize.Min)) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { saveAvatar(selectedAvatar) }) {
                    Text(text = "Сохранить")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = logout
                ) {
                    Text(text = "Выйти")
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Screen(
                avatar = 12,
            )
        }
    }
}