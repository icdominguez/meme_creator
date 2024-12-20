package com.icdominguez.icdominguez.memecreator.presentation.screens.newmeme

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.icdominguez.icdominguez.memecreator.R
import com.icdominguez.icdominguez.memecreator.presentation.screens.newmeme.composables.DraggableComponent
import com.icdominguez.icdominguez.memecreator.presentation.screens.newmeme.composables.EditTextExtendedComponent
import com.icdominguez.icdominguez.memecreator.presentation.screens.newmeme.dialogs.EditTextDialog
import com.icdominguez.icdominguez.memecreator.presentation.screens.newmeme.dialogs.LeaveEditorDialog
import com.icdominguez.icdominguez.memecreator.presentation.screens.newmeme.composables.OptionsComponent
import com.icdominguez.icdominguez.memecreator.presentation.screens.newmeme.composables.SaveMemeComponent
import com.icdominguez.icdominguez.memecreator.ui.theme.MemeCreatorTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewMemeScreen(
    state: NewMemeViewModel.State = NewMemeViewModel.State(),
    uiEvent: (NewMemeViewModel.Event) -> Unit = {},
    navController: NavHostController,
    memeTemplateId: Int,
) {
    var showSaveMemeBottomSheetDialog by remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if(result.resultCode == Activity.RESULT_OK || result.resultCode == Activity.RESULT_CANCELED) {
            uiEvent(NewMemeViewModel.Event.OnShareMemeResult)
        }
    }
    val sheetState = rememberModalBottomSheetState()
    val graphicsLayer = rememberGraphicsLayer()

    MemeCreatorTheme {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(text = stringResource(R.string.new_meme_screen))
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                if(state.texts.isNotEmpty()) {
                                    uiEvent(NewMemeViewModel.Event.OnBackClicked)
                                } else {
                                    navController.popBackStack()
                                }
                            }
                        ) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                        }
                    }
                )
            }
        ) { paddingValues ->

            BackHandler { uiEvent(NewMemeViewModel.Event.OnBackClicked) }

            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .drawWithContent {
                            graphicsLayer.record { this@drawWithContent.drawContent() }
                            drawLayer(graphicsLayer)
                        }
                ) {
                    DraggableComponent(
                        memeTemplateId = memeTemplateId,
                        items = state.texts,
                        onRemoveTextButtonClicked = { uiEvent(NewMemeViewModel.Event.OnRemoveTextButtonClicked(it)) },
                        onTextTapped = { uiEvent(NewMemeViewModel.Event.OnTextTapped(textMeme = it)) },
                        selectedMeme = state.selectedMeme,
                        onTextSwaped = { uiEvent(NewMemeViewModel.Event.OnTextMemeMoved(textMeme = it)) },
                        onTextDoubleTapped = { uiEvent(NewMemeViewModel.Event.OnTextDoubleTapped(textMeme = it)) }
                    )
                }

                Column(
                    modifier = Modifier.align(Alignment.BottomEnd)
                ) {
                    if (state.showOptionsComponent || state.texts.isEmpty() || state.selectedMeme == null) {
                        OptionsComponent(
                            onSaveMemeButtonClicked = { showSaveMemeBottomSheetDialog = true },
                            onAddTextButtonClicked = { uiEvent(NewMemeViewModel.Event.AddTextButtonClicked) }
                        )
                    }
                    if (state.showEditTextComponent && state.selectedMeme != null) {
                        EditTextExtendedComponent(
                            selectedMeme = state.selectedMeme,
                            onCustomFontClicked = { uiEvent(NewMemeViewModel.Event.OnCustomFontClicked(it)) },
                            onSliderValueChanged = { uiEvent(NewMemeViewModel.Event.OnSliderValueChanged(it)) },
                            onColorClicked = { uiEvent(NewMemeViewModel.Event.OnColorClicked(it)) },
                            onCloseButtonClicked = { uiEvent(NewMemeViewModel.Event.OnEditTextComponentCloseButtonClicked) },
                            onCheckButtonClicked = { uiEvent(NewMemeViewModel.Event.OnEditTextComponentCheckButtonClicked) },
                        )
                    }

                    if(state.showEditTextDialog) {
                        EditTextDialog(
                            onDismissRequest = { uiEvent(NewMemeViewModel.Event.OnEditTextDialogDismissed) },
                            onAcceptRequest = { uiEvent(NewMemeViewModel.Event.OnEditTextDialogAccepted(it)) },
                            textMeme = state.doubleTappedText?.text ?: stringResource(R.string.tap_twice_to_edit),
                            onTextChange = { uiEvent(NewMemeViewModel.Event.OnEditTextTextFieldValueChanged(it))}
                        )
                    }
                }
            }

            if(state.bitmap != null) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(bitmap = state.bitmap, null)
                }
            }
        }

        if (state.showLeaveDialog) {
            LeaveEditorDialog(
                onDismissRequest = {
                    uiEvent(NewMemeViewModel.Event.OnLeaveDialogDismissed)
                },
                onConfirmation = {
                    navController.popBackStack()
                }
            )
        }

        if (showSaveMemeBottomSheetDialog) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = {
                    showSaveMemeBottomSheetDialog = false
                },
            ) {
                Column {
                    SaveMemeComponent(
                        onSaveMemeClicked = {
                            uiEvent(NewMemeViewModel.Event.OnSaveButtonClicked(graphicsLayer))
                            navController.popBackStack()
                        },
                        onShareMemeClicked = {
                            uiEvent(NewMemeViewModel.Event.OnShareMemeButtonClicked(graphicsLayer))
                        }
                    )
                }
            }
        }

        if(state.imageUri != null) {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "image/png"
                putExtra(Intent.EXTRA_STREAM, state.imageUri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            launcher.launch(Intent.createChooser(shareIntent, stringResource(R.string.sharing_meme)))
        }
    }
}

@Composable
@Preview(showBackground = true)
fun NewMemeScreenPreview() {
    NewMemeScreen(
        navController = rememberNavController(),
        uiEvent = {},
        memeTemplateId = R.drawable.arm_wrestle_agreement
    )
}