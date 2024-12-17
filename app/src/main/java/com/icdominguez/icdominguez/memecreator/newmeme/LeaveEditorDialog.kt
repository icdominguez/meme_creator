package com.icdominguez.icdominguez.memecreator.newmeme

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.icdominguez.icdominguez.memecreator.R

@Composable
fun LeaveEditorDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
) {
    AlertDialog(
        title = {
            Text(text = stringResource(R.string.leave_editor_dialog_title))
        },
        text = {
            Text(text = stringResource(R.string.leave_editor_dialog_description))
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirmation() }
            ) {
                Text(text = stringResource(R.string.leave_editor_leave_button))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(text = stringResource(R.string.dialog_cancel_button))
            }
        }
    )
}

@Composable
@Preview(showBackground = true)
fun LeaveEditorDialogPreview() {
    LeaveEditorDialog(
        onDismissRequest = {},
        onConfirmation = {}
    )
}