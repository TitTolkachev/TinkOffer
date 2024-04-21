package ru.tinkoff.tinkoffer.presentation.screen.signin

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import ru.tinkoff.tinkoffer.R
import ru.tinkoff.tinkoffer.presentation.common.SnackbarError
import ru.tinkoff.tinkoffer.presentation.common.SnackbarSuccess
import ru.tinkoff.tinkoffer.presentation.theme.AppTheme

@Composable
fun SignInScreen(navigateToHome: () -> Unit) {

    val viewModel: SignInViewModel = koinViewModel()
    val shackBarHostState = remember { SnackbarHostState() }
    var isSnackBarMessageError by remember { mutableStateOf<Boolean?>(null) }

    LaunchedEffect(Unit) {
        viewModel.navigateToHome.collect {
            navigateToHome()
        }
    }

    LaunchedEffect(true) {
        viewModel.error.collect {
            isSnackBarMessageError = true
            shackBarHostState.showSnackbar(it)
        }
    }

    LaunchedEffect(true) {
        viewModel.success.collect {
            isSnackBarMessageError = false
            shackBarHostState.showSnackbar(it)
        }
    }

    val context = LocalContext.current
    LaunchedEffect(true) {
        viewModel.link.collect {
            val customIntent = CustomTabsIntent.Builder()
                .setDefaultColorSchemeParams(
                    CustomTabColorSchemeParams.Builder()
                        .setToolbarColor(0xFFDD2D)
                        .build()
                )
                .setColorSchemeParams(
                    CustomTabsIntent.COLOR_SCHEME_DARK,
                    CustomTabColorSchemeParams.Builder()
                        .setToolbarColor(0xFFDD2D)
                        .build()
                )
            openCustomTab(
                context,
                customIntent.build(),
                Uri.parse(it)
            )
        }
    }

    Screen(
        loading = viewModel.loading.collectAsState().value,
        shackBarHostState = shackBarHostState,
        isSnackBarMessageError = isSnackBarMessageError,

        onSignInClick = remember { { viewModel.onSignInClick() } },
    )
}

fun openCustomTab(context: Context, customTabsIntent: CustomTabsIntent, uri: Uri?) {
    customTabsIntent.launchUrl(context, uri!!)
}

@Composable
private fun Screen(
    loading: Boolean = false,
    shackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    isSnackBarMessageError: Boolean? = null,

    onSignInClick: () -> Unit = {},
) {
    Scaffold(
        modifier = Modifier
            .imePadding()
            .animateContentSize(),
        snackbarHost = {
            SnackbarHost(hostState = shackBarHostState, snackbar = {
                if (isSnackBarMessageError == false) SnackbarSuccess(it)
                else SnackbarError(it)
            })
        }
    ) { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.weight(0.5f))

            Image(
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(2f),
                painter = painterResource(id = R.drawable.logotin),
                contentDescription = null,
            )

            Column(
                modifier = Modifier
                    .defaultMinSize(48.dp)
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(1f))
                if (loading) {
                    CircularProgressIndicator()
                }
                Spacer(modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier,
                onClick = onSignInClick,
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(vertical = 16.dp, horizontal = 48.dp),
            ) {
                Text(
                    text = "Войти с Tinkoff",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.width(9.dp))
                Icon(
                    modifier = Modifier
                        .height(25.dp)
                        .width(47.dp),
                    painter = painterResource(id = R.drawable.ic_tinkoff_id),
                    tint = Color.Unspecified,
                    contentDescription = null
                )
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
            Screen()
        }
    }
}