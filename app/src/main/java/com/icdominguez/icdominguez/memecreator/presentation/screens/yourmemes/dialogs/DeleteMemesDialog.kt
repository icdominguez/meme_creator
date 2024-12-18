package com.icdominguez.icdominguez.memecreator.presentation.screens.yourmemes.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.icdominguez.icdominguez.memecreator.R

@Composable
fun DeleteMemesDialog(
    onConfirmButtonClicked: () -> Unit,
    onDismissRequest: () -> Unit,
    memesToDelete: Int,
) {
    AlertDialog(
        title = {
            Text(text = stringResource(R.string.delete_dialog_title, memesToDelete))
        },
        text = {
            Text(text = stringResource(R.string.delete_dialog_description))
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirmButtonClicked() }
            ) {
                Text(stringResource(R.string.delete_dialog_cancel_button))
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onDismissRequest() }
            ) {
                Text(text = stringResource(R.string.dialog_cancel_button))
            }
        }
    )
}