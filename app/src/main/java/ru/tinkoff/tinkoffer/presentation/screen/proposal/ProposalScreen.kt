package ru.tinkoff.tinkoffer.presentation.screen.proposal

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
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
import ru.tinkoff.tinkoffer.presentation.common.Proposal
import ru.tinkoff.tinkoffer.presentation.common.ProposalStatus
import ru.tinkoff.tinkoffer.presentation.common.SnackbarError
import ru.tinkoff.tinkoffer.presentation.common.UserShort
import ru.tinkoff.tinkoffer.presentation.screen.proposal.components.ActiveMembers
import ru.tinkoff.tinkoffer.presentation.theme.AppTheme

@Composable
fun ProposalScreen(navigateBack: () -> Unit) {
    val viewModel: ProposalViewModel = koinViewModel()
    val shackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.navigateBack.collect {
            navigateBack()
        }
    }

    val proposal = Proposal(
        id = "123",
        text = "dsfsfsdfsdf",
        author = UserShort(
            id = "UserId",
            avatar = 1,
            nickname = "Nickname"
        ),
        status = ProposalStatus.NEW,
        userVoice = null,
        goodUsers = listOf(),
        badUsers = listOf(),
        voiceEnabled = true,
        link = "http://asdasd",
        draftComment = "AdAsDsD",
        canBeVoiceCanceled = false,
    )

    Screen(
        item = proposal,
        snackbarHostState = shackBarHostState,

        onBackClick = remember { { viewModel.navigateBack() } },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Screen(
    item: Proposal,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },

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
                            text = "Предложение",
                            style = MaterialTheme.typography.headlineSmall,
                        )
                    }
                })
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState, snackbar = {
                SnackbarError(it)
            })
        }
    ) { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Card {
                Column(Modifier.padding(8.dp)) {

                    Row(modifier = Modifier.padding(horizontal = 8.dp)) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = item.text
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    ActiveMembers(
                        memberIcons = listOf(1, 2, 3),
                        remainingPeople = 123,
                    )

//                    DropdownMenu(
//                        expanded = false,
//                        onDismissRequest = { /*TODO*/ }
//                    ) {
//
//                    }

                    Text(text = item.toString())
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            val snackbarHostState = remember { SnackbarHostState() }

            val proposal = Proposal(
                id = "123",
                text = "dsfsfsdfsdf",
                author = UserShort(
                    id = "UserId",
                    avatar = 1,
                    nickname = "Nickname"
                ),
                status = ProposalStatus.NEW,
                userVoice = null,
                goodUsers = listOf(),
                badUsers = listOf(),
                voiceEnabled = true,
                link = "http://asdasd",
                draftComment = "AdAsDsD",
                canBeVoiceCanceled = false,
            )
            Screen(
                item = proposal,
                snackbarHostState = snackbarHostState,
                onBackClick = {},
            )
        }
    }
}