package ru.tinkoff.tinkoffer.presentation.screen.proposal

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import ru.tinkoff.tinkoffer.R
import ru.tinkoff.tinkoffer.data.models.proposals.response.CommentDto
import ru.tinkoff.tinkoffer.data.models.proposals.response.ProposalInListDto
import ru.tinkoff.tinkoffer.data.models.proposals.response.ProposalInfoDto
import ru.tinkoff.tinkoffer.data.models.users.response.UserDto
import ru.tinkoff.tinkoffer.data.models.users.response.UserInfoDto
import ru.tinkoff.tinkoffer.presentation.common.Proposal
import ru.tinkoff.tinkoffer.presentation.common.ProposalStatus
import ru.tinkoff.tinkoffer.presentation.common.SnackbarError
import ru.tinkoff.tinkoffer.presentation.common.UserShort
import ru.tinkoff.tinkoffer.presentation.common.avatars
import ru.tinkoff.tinkoffer.presentation.screen.proposal.components.ActiveMembers
import ru.tinkoff.tinkoffer.presentation.shadowCustom
import ru.tinkoff.tinkoffer.presentation.theme.AppTheme

@Composable
fun ProposalScreen(navigateBack: () -> Unit) {
    val viewModel: ProposalViewModel = koinViewModel()
    val shackBarHostState = remember { SnackbarHostState() }

    val state by viewModel.state.collectAsState()
    val parentOfComment by viewModel.parentOfComment.collectAsState()
    val commentValue by viewModel.comment.collectAsState()


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

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        state?.let {
            Screen(
                item = it,
                snackbarHostState = shackBarHostState,

                onBackClick = remember { { viewModel.navigateBack() } },
                onLike = viewModel::onLikeClick,
                onDislike = viewModel::onDislikeClick,
                onCancelVote = viewModel::onDismissVoteClick,
                onChangeStatus = viewModel::changeStatus
            )
        } ?: CircularProgressIndicator()

    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Screen(
    item: CombinedProposal,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },

    onChangeStatus: (ProposalStatus) -> Unit,
    
    onLike: () -> Unit = {},
    onDislike: () -> Unit = {},
    onCancelVote: () -> Unit = {},
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
        Column(Modifier.fillMaxSize()) {
            Column(
                Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    Modifier
                        .then(
                            if (!isSystemInDarkTheme()) Modifier.shadowCustom(
                                color = MaterialTheme.colorScheme.secondaryContainer,
                                blurRadius = 32.dp,
                                shapeRadius = 16.dp,
                            ) else Modifier
                        )
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(8.dp)
                ) {

                    Row(modifier = Modifier.padding(horizontal = 8.dp)) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = item.infoDto.text
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                StatusBlock(currentStatus = item.infoDto.proposalStatus, onClick = onChangeStatus)
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .then(
                            if (!isSystemInDarkTheme()) Modifier.shadowCustom(
                                color = MaterialTheme.colorScheme.secondaryContainer,
                                blurRadius = 32.dp,
                                shapeRadius = 16.dp,
                            ) else Modifier
                        )
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surface),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Spacer(Modifier.width(8.dp))
                    ActiveMembers(
                        memberIcons = listOf(1, 2, 3),
                        remainingPeople = 123,
                    )

                    Spacer(Modifier.weight(1f))

                    Text(
                        text = "-${item.inListData.votesAgainst}",
                        color = if (isSystemInDarkTheme()) Color.White else Color(0xFF3F3F3F),
                    )
                    Text(
                        text = " | ",
                    )
                    Text(
                        text = "+${item.inListData.votesFor}",
                        color = if (isSystemInDarkTheme()) Color(0xFFFEDD2D) else Color(0xFF4A87F8),
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(onClick = {
                        if (item.inListData.userVote == true) {
                            onCancelVote()
                        } else {
                            onLike()
                        }
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_thumb_up),
                            contentDescription = null,
                            tint = if (item.inListData.userVote == true && isSystemInDarkTheme()) {
                                Color(0xFFFEDD2D)
                            } else if (item.inListData.userVote == true && !isSystemInDarkTheme()) {
                                Color(0xFF4A87F8)
                            } else if (isSystemInDarkTheme()) {
                                Color.White
                            } else {
                                Color(0xFFA0A0A0)
                            }
                        )
                    }

                    IconButton(onClick = {
                        if (item.inListData.userVote == true) {
                            onCancelVote()
                        } else {
                            onDislike()
                        }
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_thumb_down),
                            contentDescription = null,
                            tint = if (item.inListData.userVote == false && isSystemInDarkTheme()) {
                                Color(0xFFFEDD2D)
                            } else if (item.inListData.userVote == false && !isSystemInDarkTheme()) {
                                Color(0xFF4A87F8)
                            } else if (isSystemInDarkTheme()) {
                                Color.White
                            } else {
                                Color(0xFFA0A0A0)
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Comments(items = comments)
            }

            Input(
                value = "",
                onValueChange = {},
                sendComment = {},
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StatusBlock(
    currentStatus: ProposalStatus = ProposalStatus.ACCEPTED,
    onClick: (ProposalStatus) -> Unit = {}
) {
    SingleChoiceSegmentedButtonRow(
        modifier = if (!isSystemInDarkTheme()) Modifier.shadowCustom(
            color = MaterialTheme.colorScheme.secondaryContainer,
            blurRadius = 32.dp,
            shapeRadius = 16.dp,
        ) else Modifier
    ) {
        SegmentedButton(
            selected = currentStatus == ProposalStatus.NEW,
            onClick = { onClick(ProposalStatus.NEW) },
            colors = SegmentedButtonDefaults.colors(
                activeContainerColor = MaterialTheme.colorScheme.primary,
                activeContentColor = MaterialTheme.colorScheme.onPrimary,
            ),
            shape = RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp)
        ) {
            Text(text = "New")
        }
        SegmentedButton(
            selected = currentStatus == ProposalStatus.IN_PROGRESS,
            onClick = { onClick(ProposalStatus.IN_PROGRESS) },
            colors = SegmentedButtonDefaults.colors(
                activeContainerColor = MaterialTheme.colorScheme.primary,
                activeContentColor = MaterialTheme.colorScheme.onPrimary,
            ),
            shape = RoundedCornerShape(0.dp)
        ) {
            Text(text = "Active")
        }
        SegmentedButton(
            selected = currentStatus == ProposalStatus.ACCEPTED,
            onClick = { onClick(ProposalStatus.ACCEPTED) },
            colors = SegmentedButtonDefaults.colors(
                activeContainerColor = MaterialTheme.colorScheme.primary,
                activeContentColor = MaterialTheme.colorScheme.onPrimary,
            ),
            shape = RoundedCornerShape(0.dp)
        ) {
            Text(text = "Accepted")
        }
        SegmentedButton(
            selected = currentStatus == ProposalStatus.REJECTED,
            onClick = { onClick(ProposalStatus.REJECTED) },
            colors = SegmentedButtonDefaults.colors(
                activeContainerColor = MaterialTheme.colorScheme.primary,
                activeContentColor = MaterialTheme.colorScheme.onPrimary,
            ),
            shape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)
        ) {
            Text(text = "Rejected")
        }
    }
}

@Composable
private fun Comments(
    items: List<CommentDto>,
) {
    if (items.isNotEmpty()) {
        Column(Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.align(Alignment.Start),
                text = "Комментарии",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            items.forEach {
                Row {
                    if (it.proposalId.isBlank())
                        Spacer(modifier = Modifier.width(24.dp))

                    Comment(
                        avatar = it.user.avatarNumber,
                        nickname = it.user.firstName,
                        comment = it.text,
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    } else {
        Text(
            text = "Комментарии отсутствуют",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun Comment(
    avatar: Int,
    nickname: String,
    comment: String,
) {
    Icon(
        modifier = Modifier
            .clip(CircleShape)
            .size(40.dp),
        painter = painterResource(id = avatars[avatar]),
        contentDescription = null,
        tint = Color.Unspecified,
    )
    Spacer(modifier = Modifier.width(8.dp))
    Column {
        Text(
            text = nickname,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(text = comment)
        TextButton(
            modifier = Modifier.align(Alignment.Start),
            colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF928F8F)),
            onClick = { /*TODO*/ }
        ) {
            Text(text = "ответить")
        }
    }
}

@Composable
private fun Input(
    value: String,
    onValueChange: (String) -> Unit,
    sendComment: () -> Unit,
) {
    Row {
        TextField(
            modifier = Modifier.weight(1f),
            value = value,
            onValueChange = onValueChange
        )

        IconButton(onClick = sendComment) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.Send,
                contentDescription = null
            )
        }
    }
}

val comments = listOf(
    CommentDto(
        id = "123",
        text = "AADAsDASdasdasdasSd",
        user = UserInfoDto(
            id = "12",
            firstName = "Egor",
            lastName = "Шамов",
            middleName = "name",
            phone = "134234",
            avatarNumber = 2
        ),
        proposalId = "1",
        parentCommentId = "",
        createdAt = 1241315235
    ),
    CommentDto(
        id = "123",
        text = "AADAsDASdasdasdasSdAADAsDASdasdasdasSdAADAsDASdasdasdasSd",
        user = UserInfoDto(
            id = "12",
            firstName = "Egor",
            lastName = "Шамов",
            middleName = "name",
            phone = "134234",
            avatarNumber = 2
        ),
        proposalId = "",
        parentCommentId = "",
        createdAt = 1241315235
    ),
    CommentDto(
        id = "123",
        text = "AADAsDASdasdasdasSd",
        user = UserInfoDto(
            id = "12",
            firstName = "Egor",
            lastName = "Шамов",
            middleName = "name",
            phone = "134234",
            avatarNumber = 2
        ),
        proposalId = "",
        parentCommentId = "",
        createdAt = 1241315235
    ),
)

@Preview
@Composable
private fun Preview() {
    AppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            val snackbarHostState = remember { SnackbarHostState() }


            val prop = CombinedProposal(
                infoDto = ProposalInfoDto(
                    id = "123",
                    text = "dsfsfsdfsdf",
                    user = UserInfoDto(
                        id = "12",
                        firstName = "Egor",
                        lastName = "Шамов",
                        middleName = "name",
                        phone = "134234",
                        avatarNumber = 2
                    ),
                    proposalStatus = ProposalStatus.REJECTED,
                    userVote = null,
                    canVote = true,
                    jiraLink = "http://asdasd",
                    canBeVoteCanceled = true,
                    comments = comments,
                    createdAt = 1241221312,
                ),
                inListData = ProposalInListDto(
                    id = "123",
                    text = " asasdsads",
                    user = UserDto(
                        id = "0",
                        avatarNumber = 1,
                        firstName = "Егор",
                        lastName = "Шамов"
                    ),
                    userVote = null,
                    votesFor = 123,
                    votesAgainst = 33,
                    proposalStatus = ProposalStatus.REJECTED,
                    createdAt = 1241221312,
                )
            )

//            val proposal = Proposal(
//                id = "123",
//                text = "dsfsfsdfsdf",
//                author = UserShort(
//                    id = "UserId",
//                    avatar = 1,
//                    nickname = "Nickname"
//                ),
//                status = ProposalStatus.NEW,
//                userVoice = null,
//                goodUsers = listOf(),
//                badUsers = listOf(),
//                voiceEnabled = true,
//                link = "http://asdasd",
//                draftComment = "AdAsDsD",
//                canBeVoiceCanceled = false,
//            )
            Screen(
                item = prop,
                snackbarHostState = snackbarHostState,
                onBackClick = {},
                onChangeStatus = {}
            )
        }
    }
}