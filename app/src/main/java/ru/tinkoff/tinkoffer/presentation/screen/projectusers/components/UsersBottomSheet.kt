package ru.tinkoff.tinkoffer.presentation.screen.projectusers.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.tinkoff.tinkoffer.data.models.users.response.UserDto

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
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.Absolute.spacedBy(8.dp)
            ) {
                items(items = users, key = { it.id }) {
                    UserInBottomSheet(
                        avatar = it.avatarNumber,
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