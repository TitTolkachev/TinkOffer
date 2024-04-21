package ru.tinkoff.tinkoffer.presentation.screen.projectusers.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import ru.tinkoff.tinkoffer.data.models.users.response.UserDto
import ru.tinkoff.tinkoffer.presentation.common.avatars
import ru.tinkoff.tinkoffer.presentation.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersBottomSheet(showBottomSheet: Boolean, users: List<UserDto>, hideBottomSheet: () -> Unit) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    if (showBottomSheet) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = hideBottomSheet
        ) {
            LazyColumn {
                items(items = users, key = { it.id }) {
                    UserInBottomSheet(
                        avatar = avatars[it.avatarNumber],
                        nickname = "${it.firstName} ${it.lastName}",
                        onClick = {
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) hideBottomSheet()
                            }
                        }
                    )
                }
            }
        }
    }
}

//@Preview
//@Composable
//private fun Preview() {
//    AppTheme {
//        Surface(color = MaterialTheme.colorScheme.background) {
//            UsersBottomSheet(
//
//            )
//        }
//    }
//}