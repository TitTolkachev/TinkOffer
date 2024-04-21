package ru.tinkoff.tinkoffer.presentation.screen.home.pages

import android.graphics.Color
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import ru.tinkoff.tinkoffer.data.models.projects.response.ProjectInfoDto
import ru.tinkoff.tinkoffer.presentation.shadowCustom
import ru.tinkoff.tinkoffer.presentation.theme.AppTheme

@Composable
fun ProjectPage(
    modifier: Modifier = Modifier,
    admin: Boolean,

    newProposalsCount: Int,
    inProgressProposalsCount: Int,
    acceptedProposalsCount: Int,
    rejectedProposalsCount: Int,

    activeProjectInfoDto: ProjectInfoDto?,
    countOfProposals: Int,
    voteAvailable: Int,
    navigateToProjectSettings: (String) -> Unit = {},
    navigateToProjectUsers: () -> Unit = {},
    navigateToDrafts: () -> Unit = {},
) {
    Column(modifier = modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier
                .then(
                    if (!isSystemInDarkTheme()) Modifier.shadowCustom(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        blurRadius = 32.dp,
                        shapeRadius = 16.dp,
                    ) else Modifier
                )
                .padding(horizontal = 16.dp)
        ) {
            if (admin) {
                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = {
                        activeProjectInfoDto?.id?.let {
                            navigateToProjectSettings(
                                it
                            )
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.Settings,
                            contentDescription = null,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            } else {
                Spacer(modifier = Modifier.height(16.dp))
            }

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Row {
                    Text(text = "Всего предложений:")
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = countOfProposals.toString())
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row {
                    Text(text = "Доступно голосов:")
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = voteAvailable.toString())
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        Card(
            modifier = Modifier
                .padding(16.dp)
                .then(
                    if (!isSystemInDarkTheme()) Modifier.shadowCustom(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        blurRadius = 32.dp,
                        shapeRadius = 16.dp,
                    ) else Modifier
                ),
            onClick = navigateToProjectUsers,
        ) {
            if (admin) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Участники")
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                        contentDescription = null
                    )
                }
            }
        }

        Card(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .then(
                    if (!isSystemInDarkTheme()) Modifier.shadowCustom(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        blurRadius = 32.dp,
                        shapeRadius = 16.dp,
                    ) else Modifier
                ),
            onClick = navigateToDrafts,
        ) {
            if (admin) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Черновики")
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                        contentDescription = null
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            ProjectPieChart(
                newProposalsCount = newProposalsCount,
                inProgressProposalsCount = inProgressProposalsCount,
                acceptedProposalsCount = acceptedProposalsCount,
                rejectedProposalsCount = rejectedProposalsCount,
            )
        }
    }
}

@Composable
private fun ProjectPieChart(
    newProposalsCount: Int,
    inProgressProposalsCount: Int,
    acceptedProposalsCount: Int,
    rejectedProposalsCount: Int,
) {
    AndroidView(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxSize(),
        factory = { context ->
            val pieChart = PieChart(context)
            pieChart.isDrawHoleEnabled = true
            pieChart.setUsePercentValues(true)
            pieChart.setEntryLabelTextSize(12f)
            pieChart.setEntryLabelColor(Color.BLACK)
            pieChart.centerText = "Статистика предложений"
            pieChart.setCenterTextSize(16f)
            pieChart.description.isEnabled = false

            val legend = pieChart.legend
            legend.isEnabled = false

            pieChart.animateX(800)

            pieChart
        },
    ) { pieChart ->
        val all =
            newProposalsCount + inProgressProposalsCount + acceptedProposalsCount + rejectedProposalsCount
        val pieEntries = mutableListOf<PieEntry>()
        if (newProposalsCount > 0) {
            pieEntries.add(PieEntry(newProposalsCount.toFloat() / all, "Новые"))
        }
        if (inProgressProposalsCount > 0) {
            pieEntries.add(PieEntry(inProgressProposalsCount.toFloat() / all, "Активные"))
        }
        if (acceptedProposalsCount > 0) {
            pieEntries.add(PieEntry(acceptedProposalsCount.toFloat() / all, "Принятые"))
        }
        if (rejectedProposalsCount > 0) {
            pieEntries.add(PieEntry(rejectedProposalsCount.toFloat() / all, "Закрытые"))
        }

        val colors = ArrayList<Int>()
        for (color in ColorTemplate.MATERIAL_COLORS) {
            colors.add(color)
        }

        for (color in ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color)
        }

        val pieDataset = PieDataSet(pieEntries, "Вклад в проект")
        pieDataset.colors = colors
        pieDataset.valueTextSize = 15f
        pieDataset.sliceSpace = 5f
        pieDataset.setDrawValues(false)

        val pieData = PieData(pieDataset)
        pieChart.data = pieData
    }
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            ProjectPage(
                modifier = Modifier,
                admin = true,
                activeProjectInfoDto = null,
                countOfProposals = 100,
                voteAvailable = 100,
                newProposalsCount = 5,
                inProgressProposalsCount = 10,
                acceptedProposalsCount = 3,
                rejectedProposalsCount = 2,
            )
        }
    }
}