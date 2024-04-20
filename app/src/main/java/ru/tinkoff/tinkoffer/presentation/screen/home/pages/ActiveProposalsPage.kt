package ru.tinkoff.tinkoffer.presentation.screen.home.pages

import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.tinkoff.tinkoffer.presentation.common.ProposalShort
import ru.tinkoff.tinkoffer.presentation.common.proposals
import ru.tinkoff.tinkoffer.presentation.screen.home.components.ProposalElement

@Composable
fun ActiveProposalsPage(
    modifier: Modifier = Modifier,

    onProposalClick: (ProposalShort) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = spacedBy(8.dp),
    ) {
        items(items = proposals, key = { it.id }) { item ->
            ProposalElement(item, onClick = { onProposalClick(item) })
        }
    }
}