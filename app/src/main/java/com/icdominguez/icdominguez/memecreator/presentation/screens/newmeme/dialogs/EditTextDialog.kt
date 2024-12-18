package com.icdominguez.icdominguez.memecreator.presentation.screens.newmeme.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun EditTextDialog(
    onDismissRequest: () -> Unit,
    onAcceptRequest: (String) -> Unit,
    textMeme: String,
    onTextChange: (String) -> Unit
) {
    val keyboard = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    var textFieldValue by remember { mutableStateOf(TextFieldValue(text = textMeme, selection = TextRange(0, textMeme.length))) }

    AlertDialog(
        title = { Text(text = "Text") },
        text = {
            Column {
                BasicTextField(
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                    textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.onSurface),
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester = focusRequester),
                    value = textFieldValue,
                    onValueChange = { newValue ->
                        textFieldValue = newValue.copy(
                            text = newValue.text
                        )
                    },
                    decorationBox = { innerTextField ->
                        Column {
                            innerTextField()
                            HorizontalDivider(
                                modifier = Modifier
                                    .padding(top = 6.dp),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    },
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onAcceptRequest(textFieldValue.text)
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onDismissRequest() }
            ) {
                Text("Cancel")
            }
        },
        onDismissRequest = {
            onDismissRequest()
        }
    )

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboard?.show()
    }
}

@Composable
@Preview(showBackground = true)
private fun EditTextDialogPreview() {
    EditTextDialog(
        onDismissRequest = {},
        onAcceptRequest = {},
        textMeme = "",
        onTextChange = {}
    )
}