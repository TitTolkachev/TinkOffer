package ru.tinkoff.tinkoffer.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import ru.tinkoff.tinkoffer.R


// Declare the font families
object AppFont {
    val TinkoffFont = FontFamily(
        Font(R.font.tinkoff_sans_regular),
        Font(R.font.tinkoff_sans_medium, FontWeight.Medium),
        Font(R.font.tinkoff_sans_bold, FontWeight.Bold),
    )
}

private val defaultTypography = Typography()
val Typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = AppFont.TinkoffFont),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = AppFont.TinkoffFont),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = AppFont.TinkoffFont),

    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = AppFont.TinkoffFont),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = AppFont.TinkoffFont),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = AppFont.TinkoffFont),

    titleLarge = defaultTypography.titleLarge.copy(fontFamily = AppFont.TinkoffFont),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = AppFont.TinkoffFont),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = AppFont.TinkoffFont),

    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = AppFont.TinkoffFont),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = AppFont.TinkoffFont),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = AppFont.TinkoffFont),

    labelLarge = defaultTypography.labelLarge.copy(fontFamily = AppFont.TinkoffFont),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = AppFont.TinkoffFont),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = AppFont.TinkoffFont)
)