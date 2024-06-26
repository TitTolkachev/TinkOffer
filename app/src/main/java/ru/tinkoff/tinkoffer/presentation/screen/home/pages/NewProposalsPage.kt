package ru.tinkoff.tinkoffer.presentation.screen.home.pages

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.tinkoff.tinkoffer.data.models.proposals.response.ProposalInListDto
import ru.tinkoff.tinkoffer.presentation.common.EmptyList
import ru.tinkoff.tinkoffer.presentation.screen.home.components.ProposalElement

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NewProposalsPage(
    modifier: Modifier = Modifier,
    proposals: List<ProposalInListDto>,
    onProposalClick: (ProposalInListDto) -> Unit,
    onLike: (id: String) -> Unit,
    onDislike: (id: String) -> Unit,
    onCancel: (id: String) -> Unit

) {
    if (proposals.isNotEmpty()) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.Absolute.spacedBy(8.dp),
        ) {
            items(items = proposals, key = { it.id }) { item ->
                ProposalElement(
                    modifier = Modifier.animateItemPlacement(),
                    item = item,
                    showVoteButtons = true,
                    onClick = { onProposalClick(item) },
                    onLike = { onLike(item.id) },
                    onDislike = { onDislike(item.id) },
                    onCancel = { onCancel(item.id) }
                )
            }
        }
    } else {
        EmptyList()
    }
}