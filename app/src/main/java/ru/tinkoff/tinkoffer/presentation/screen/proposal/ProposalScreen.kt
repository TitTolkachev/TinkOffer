package ru.tinkoff.tinkoffer.presentation.screen.proposal

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.mutableStateOf
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
                onChangeStatus = viewModel::changeStatus,
                comment = commentValue ?: "",
                onCommentChanged = { viewModel.onChangeCommentText(it) },
                onSendComment = { viewModel.sendComment() },
                onSelectParent = { viewModel.selectParentOfComment(it) },
                currentParent = parentOfComment ?: "",
            )
        } ?: CircularProgressIndicator()

    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Screen(
    item: CombinedProposal,
    comment: String,
    onCommentChanged: (String) -> Unit,
    onSendComment: () -> Unit,
    onSelectParent: (String) -> Unit,
    currentParent: String,

    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },

    onChangeStatus: (ProposalStatus) -> Unit,

    onLike: () -> Unit = {},
    onDislike: () -> Unit = {},
    onCancelVote: () -> Unit = {},
    onBackClick: () -> Unit = {},
) {

    var scrollToLast by remember {
        mutableStateOf(false)
    }


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
                    .padding(horizontal = 16.dp),
//                    .verticalScroll(rememberScrollState())
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
                        if (item.inListData.userVote == false) {
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

                Comments(
                    scrollToLast = scrollToLast,
                    modifier = Modifier.weight(1f),
                    items = item.infoDto.comments,
                    currentParent = currentParent,
                    onSelectParent = onSelectParent
                )
            }

            Input(
                { state -> scrollToLast = state },
                value = comment,
                onValueChange = onCommentChanged,
                sendComment = onSendComment,
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Comments(
    scrollToLast: Boolean,
    modifier: Modifier,
    items: List<CommentDto>,
    currentParent: String,
    onSelectParent: (String) -> Unit
) {
    val state = rememberLazyListState()

    LaunchedEffect(key1 = scrollToLast) {
        if (scrollToLast)
            state.animateScrollToItem(items.lastIndex)
    }

    if (items.isNotEmpty()) {
        LazyColumn(
            state = state,
            userScrollEnabled = true,
            modifier = modifier
                .wrapContentHeight(align = Alignment.Top)
                .fillMaxWidth()
        ) {
            item {
                Text(
                    text = "Комментарии",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            items.forEach {
                item {
                    Comment(
                        modifier = Modifier.animateItemPlacement(),
                        avatar = it.user.avatarNumber,
                        nickname = it.user.firstName,
                        comment = it.text,
                        commentId = it.id,
                        currentParent = currentParent,
                        onSelectParent = onSelectParent,
                    )
                }
                it.replies.forEach { firstLevelComment ->
                    item {
                        Comment(
                            modifier = Modifier
                                .animateItemPlacement()
                                .padding(start = 36.dp),
                            avatar = firstLevelComment.user.avatarNumber,
                            nickname = firstLevelComment.user.firstName,
                            comment = firstLevelComment.text,
                            commentId = firstLevelComment.id,
                            currentParent = currentParent,
                            onSelectParent = onSelectParent,
                        )
                    }

                    firstLevelComment.replies.forEach { secondLevelComment ->
                        item {
                            Comment(
                                modifier = Modifier
                                    .animateItemPlacement()
                                    .padding(start = (36 * 2).dp),
                                avatar = secondLevelComment.user.avatarNumber,
                                nickname = secondLevelComment.user.firstName,
                                comment = secondLevelComment.text,
                                commentId = secondLevelComment.id,
                                currentParent = currentParent,
                                onSelectParent = onSelectParent,
                            )
                        }

                        secondLevelComment.replies.forEach { thirdLevelComment ->
                            item {
                                Comment(
                                    modifier = Modifier
                                        .animateItemPlacement()
                                        .padding(start = (36 * 3).dp),
                                    avatar = thirdLevelComment.user.avatarNumber,
                                    nickname = thirdLevelComment.user.firstName,
                                    comment = thirdLevelComment.text,
                                    commentId = thirdLevelComment.id,
                                    currentParent = currentParent,
                                    onSelectParent = onSelectParent,
                                )
                            }

                        }


                    }

                }
            }

        }

//            items(items) {
//                Comment(
//                    modifier = Modifier.animateItemPlacement(),
//                    avatar = it.user.avatarNumber,
//                    nickname = it.user.firstName,
//                    comment = it.text,
//                    commentId = it.id,
//                    currentParent = currentParent,
//                    onSelectParent = onSelectParent,
//                )
//
////                    Spacer(modifier = Modifier.width(36.dp))
////                    Column {
//                        RecursiveComment(
//                            modifier = Modifier.animateItemPlacement().padding(36.dp),
//                            it.replies,
//                            currentParent,
//                            onSelectParent
//                        )
////                    }
//            }
//    }
//} else {
//    Text(
//        text = "Комментарии отсутствуют",
//        style = MaterialTheme.typography.bodyLarge
//    )
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun Recusia(
    modifier: Modifier,
    lazyScope: LazyListScope,
    comment: CommentDto,
    currentParent: String,
    onSelectParent: (String) -> Unit
) {
    lazyScope.apply {
        item {
            Comment(
                modifier = modifier.animateItemPlacement(),
                avatar = comment.user.avatarNumber,
                nickname = comment.user.firstName,
                comment = comment.text,
                commentId = comment.id,
                currentParent = currentParent,
                onSelectParent = onSelectParent,
            )
        }
        comment.replies.forEach {
            item {
                Recusia(
                    modifier = modifier,
                    lazyScope = lazyScope,
                    comment = it,
                    currentParent = currentParent,
                    onSelectParent = onSelectParent,
                )
            }
        }


    }
}

@Composable
fun RecursiveComment(
    modifier: Modifier = Modifier,
    comments: List<CommentDto>,
    currentParent: String,
    onSelectParent: (String) -> Unit
) {
    comments.forEach { comment ->
        Comment(
            modifier = modifier,
            avatar = comment.user.avatarNumber,
            nickname = comment.user.firstName,
            comment = comment.text,
            commentId = comment.id,
            currentParent = currentParent,
            onSelectParent = onSelectParent,
        )

        comment.replies.forEach {
            Comment(
                modifier = modifier,
                avatar = it.user.avatarNumber,
                nickname = it.user.firstName,
                comment = it.text,
                commentId = it.id,
                currentParent = currentParent,
                onSelectParent = onSelectParent,
            )
            RecursiveComment(
                modifier = modifier,
                comments = it.replies,
                currentParent = currentParent,
                onSelectParent
            )
        }
        Spacer(modifier = Modifier.height(8.dp))


    }
}

@Composable
private fun Comment(
    modifier: Modifier = Modifier,
    avatar: Int,
    nickname: String,
    commentId: String,
    comment: String,
    currentParent: String,
    onSelectParent: (String) -> Unit
) {
    Column(modifier = modifier) {
        Row {
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
                    modifier = Modifier
                        .clip(CircleShape)
                        .align(Alignment.Start)
                        .border(
                            1.dp,
                            if (commentId == currentParent) Color.Black else Color.Transparent,
                            CircleShape
                        ),
                    colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF928F8F)),
                    onClick = { onSelectParent(commentId) }
                ) {
                    Text(text = "ответить")
                }
            }
        }


    }

}

@Composable
private fun Input(
    click: (Boolean) -> Unit,
    value: String,
    onValueChange: (String) -> Unit,
    sendComment: () -> Unit,
) {
    val source = remember {
        MutableInteractionSource()
    }
    val state = source.collectIsPressedAsState()
    click(state.value)

    Row {
        TextField(
            modifier = Modifier.weight(1f),
            value = value,
            interactionSource = source,
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
        createdAt = 1241315235,
        replies = listOf()
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
        createdAt = 1241315235,
        replies = listOf(
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
                createdAt = 1241315235,
                replies = listOf()

            )
        )

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
        createdAt = 1241315235,
        replies = listOf()

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
                onChangeStatus = {},
                comment = "",
                onCommentChanged = {},
                onSendComment = {},
                onSelectParent = {},
                currentParent = "",
            )
        }
    }
}