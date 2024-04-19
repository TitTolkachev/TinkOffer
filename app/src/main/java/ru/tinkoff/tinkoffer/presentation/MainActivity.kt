package ru.tinkoff.tinkoffer.presentation

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import org.koin.java.KoinJavaComponent.inject
import ru.tinkoff.tinkoffer.presentation.navigation.RootNavGraph
import ru.tinkoff.tinkoffer.presentation.navigation.Screen
import ru.tinkoff.tinkoffer.presentation.theme.AppTheme

class MainActivity : ComponentActivity() {
    val viewModel: MainViewModel by inject(MainViewModel::class.java)
    private lateinit var splashScreen: SplashScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        // Инициализация SplashScreen
        splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    val startDestination = viewModel.startDestination.value
                    return if (startDestination != null) {
                        initCompose(startDestination = startDestination)
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        false
                    }
                }
            },
        )
    }

    private fun initCompose(startDestination: Screen) {
        setContent {
            AppTheme(darkTheme = isSystemInDarkTheme()) {
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onBackground,
                ) {
                    RootNavGraph(
                        navController = rememberNavController(),
                        startDestination = startDestination.route,
                        modifier = Modifier,
                    )
                }
            }
        }
    }
}
