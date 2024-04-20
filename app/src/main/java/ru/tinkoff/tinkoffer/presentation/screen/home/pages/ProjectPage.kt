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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

    activeProjectInfoDto: ProjectInfoDto?,
    countOfProposals: Int,
    voteAvailable: Int,
    navigateToProjectSettings: (String) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
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
        Spacer(modifier = Modifier.height(16.dp))


        Box(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            ProjectPieChart()

        }
    }
}

@Composable
private fun ProjectPieChart() {
    AndroidView(
        modifier = Modifier
            .fillMaxSize(),
        factory = { context ->
            val pieChart = PieChart(context)
            pieChart.isDrawHoleEnabled = true
            pieChart.setUsePercentValues(true)
            pieChart.setEntryLabelTextSize(12f)
            pieChart.setEntryLabelColor(Color.BLACK)
            pieChart.centerText = "Вклад в проект"
            pieChart.setCenterTextSize(24f)
            pieChart.description.isEnabled = false

            val legend = pieChart.legend
            legend.isEnabled = false

            pieChart.animateX(800)

            pieChart
        },
    ) { pieChart ->
        val pieEntries = listOf(
            PieEntry(0.2f, "Лёня"),
            PieEntry(0.2f, "Тит"),
            PieEntry(0.2f, "Полина"),
            PieEntry(0.1f, "Максим"),
            PieEntry(0.3f, "Дима"),
        )

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

        pieChart.data = PieData(pieDataset)
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
                voteAvailable = 100
            )
        }
    }
}