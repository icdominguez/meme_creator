package com.icdominguez.icdominguez.master_meme.presentation.screens.newmeme

import android.net.Uri
import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import com.icdominguez.icdominguez.master_meme.FileManager
import com.icdominguez.icdominguez.master_meme.domain.usecases.InsertNewMemeUseCase
import com.icdominguez.icdominguez.master_meme.presentation.MviViewModel
import com.icdominguez.icdominguez.master_meme.presentation.Utils.generateRandomNumber
import com.icdominguez.icdominguez.master_meme.presentation.model.TextMeme
import com.icdominguez.icdominguez.master_meme.presentation.screens.newmeme.composables.CustomFont
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewMemeViewModel @Inject constructor(
    private val fileManager: FileManager,
    private val insertNewMemeUseCase: InsertNewMemeUseCase,
): MviViewModel<NewMemeViewModel.State, NewMemeViewModel.Event>() {

    data class State (
        val textMemeList: List<TextMeme> = emptyList(),
        val showOptionsComponent: Boolean = true,
        val showEditTextComponent: Boolean = false,
        val selectedMeme: TextMeme? = null,
        val showLeaveDialog: Boolean = false,
        val bitmap: ImageBitmap? = null,
        val imageUri: Uri? = null,
        val undoItems: List<TextMeme> = emptyList(),
        val redoItems: List<TextMeme> = emptyList(),
    )

    override var currentState: State = State()

    sealed class Event {
        data object AddTextButtonClicked : Event()
        data object OnRemoveTextButtonClicked: Event()
        data object OnEditTextComponentCloseButtonClicked: Event()
        data object OnEditTextComponentCheckButtonClicked: Event()
        data class OnTextDoubleTapped(val textMeme: TextMeme): Event()
        data class OnTextTapped(val textMeme: TextMeme): Event()
        data class OnSliderValueChanged(val sliderValue: Float): Event()
        data class OnTextMemeMoved(val textMeme: TextMeme): Event()
        data object OnBackClicked: Event()
        data object OnLeaveDialogDismissed: Event()
        data object OnLeaveDialogAccepted: Event()
        data class OnEditTextTextFieldValueChanged(val newText: String): Event()
        data class OnSaveButtonClicked(val picture: GraphicsLayer, val templateName: String): Event()
        data class OnShareMemeButtonClicked(val picture: GraphicsLayer): Event()
        data object OnShareMemeResult: Event()
        data class OnColorClicked(val color: String): Event()
        data class OnCustomFontClicked(val customFont: CustomFont): Event()
        data object OnDraggableComponentClicked : Event()
        data object OnUndoButtonClicked: Event()
        data object OnRedoButtonClicked: Event()
    }

    override fun uiEvent(event: Event) {
        when(event) {
            is Event.AddTextButtonClicked -> onAddTextButtonClicked()
            is Event.OnRemoveTextButtonClicked -> onRemoveTextButtonClicked()
            is Event.OnEditTextComponentCloseButtonClicked -> onEditTextComponentCloseButtonClicked()
            is Event.OnEditTextComponentCheckButtonClicked -> onEditTextComponentCheckButtonClicked()
            is Event.OnTextTapped -> onTextTapped(event.textMeme)
            is Event.OnSliderValueChanged -> onSliderValueChanged(event.sliderValue)
            is Event.OnTextMemeMoved -> onTextMemeMoved(event.textMeme)
            is Event.OnTextDoubleTapped -> onTextDoubleTapped(event.textMeme)
            is Event.OnBackClicked -> onBackClicked()
            is Event.OnEditTextTextFieldValueChanged -> onEditTextTextFieldValueChanged(event.newText)
            is Event.OnLeaveDialogDismissed -> onLeaveDialogDismissed()
            is Event.OnLeaveDialogAccepted -> onLeaveDialogAccepted()
            is Event.OnSaveButtonClicked -> onSaveButtonClicked(event.picture, event.templateName)
            is Event.OnShareMemeButtonClicked -> onShareMemeButtonClicked(event.picture)
            is Event.OnShareMemeResult -> { updateState { copy(imageUri = null) } }
            is Event.OnColorClicked -> {
                val selectedMeme = state.value.selectedMeme?.copy(color = event.color)
                updateState { copy(selectedMeme = selectedMeme) }
            }
            is Event.OnCustomFontClicked -> {
                val selectedMeme = state.value.selectedMeme?.copy(typography = event.customFont)
                updateState { copy(selectedMeme = selectedMeme) }
            }
            is Event.OnDraggableComponentClicked -> onDraggableComponentClicked()
            is Event.OnUndoButtonClicked -> onUndoButtonClicked()
            is Event.OnRedoButtonClicked -> onRedoButtonClicked()
        }
    }

    private fun onRedoButtonClicked() {
        val lastText = state.value.redoItems.last()
        updateState {
            copy(
                textMemeList = state.value.textMemeList.toMutableList().apply { add(lastText) }.toList(),
                undoItems = state.value.undoItems.toMutableList().apply { add(lastText) }.toList(),
                redoItems = state.value.redoItems.toMutableList().apply { remove(last()) }.toList()
            )
        }
    }

    private fun onUndoButtonClicked() {
        val lastText = state.value.textMemeList.last()
        updateState {
            copy(
                textMemeList = state.value.textMemeList.toMutableList().apply { remove(lastText) }.toList(),
                redoItems = state.value.redoItems.toMutableList().apply { add(lastText) }.toList(),
                undoItems = state.value.undoItems.toMutableList().apply { remove(lastText) }.toList()
            )
        }
    }

    private fun onShareMemeButtonClicked(graphicsLayer: GraphicsLayer) {
        viewModelScope.launch {
            val bitmap = graphicsLayer.toImageBitmap()
            val uri = fileManager.saveImageToCache(bitmap)
            updateState { copy(imageUri = uri) }
        }
    }

    private fun onEditTextTextFieldValueChanged(newText: String) {
        val selectedMeme = state.value.selectedMeme?.copy(text = newText)
        updateState { copy(selectedMeme = selectedMeme) }
    }

    private fun onSaveButtonClicked(graphicsLayer: GraphicsLayer, templateName: String) {
        viewModelScope.launch {
            val fileName = "${templateName.split("/").last()}-${state.value.textMemeList.joinToString("_") { it.text }}"
            insertNewMemeUseCase(graphicsLayer, fileName)
        }
    }

    private fun onLeaveDialogDismissed() {
        updateState { copy(showLeaveDialog = false) }
    }

    private fun onLeaveDialogAccepted() {
        updateState { copy(showLeaveDialog = false) }
    }

    private fun onBackClicked() {
        if (state.value.textMemeList.isNotEmpty()) {
            updateState { copy(showLeaveDialog = true) }
        }
    }

    private fun onRemoveTextButtonClicked() {
        Log.i("icd", "Text to remove: ${state.value.selectedMeme}")
        val texts = state.value.textMemeList.toMutableList().apply {
            val index = this.indexOfFirst { it.id == state.value.selectedMeme?.id }
            this.removeAt(index)
        }.toList()

        Log.i("icd", "Text list updated: $texts")

        updateState {
            copy(
                textMemeList = texts,
                showEditTextComponent = false,
                showOptionsComponent = texts.isEmpty(),
                selectedMeme = null,
            )
        }
    }

    private fun onAddTextButtonClicked() {
        val textMeme = TextMeme(id = generateRandomNumber(state.value.textMemeList.map { it.id  }))
        updateState {
            copy(
                showOptionsComponent = false,
                showEditTextComponent = true,
                textMemeList = state.value.textMemeList.plus(textMeme),
                selectedMeme = textMeme
            )
        }
    }

    private fun onDraggableComponentClicked() {
        val updatedTexts = state.value.textMemeList.toMutableList().map { text ->
            if(text.id == state.value.selectedMeme?.id) {
                text.copy(text = state.value.selectedMeme!!.text)
            } else {
                text
            }
        }.toList()

        updateState {
            copy(
                textMemeList = updatedTexts,
                selectedMeme = null
            )
        }
    }

    private fun onEditTextComponentCloseButtonClicked() {
        updateState {
            copy(
                showEditTextComponent = false,
                showOptionsComponent = true,
                selectedMeme = null,
            )
        }
    }

    private fun onEditTextComponentCheckButtonClicked() {
        state.value.selectedMeme?.let { selectedMeme ->
            val texts = state.value.textMemeList.toMutableList().map {
                if(it.id == selectedMeme.id) {
                    it.copy(
                        text = selectedMeme.text,
                        fontSize = selectedMeme.fontSize,
                        typography = selectedMeme.typography,
                        color = selectedMeme.color,
                        offset = selectedMeme.offset
                    )
                } else {
                    it
                }
            }.toList()

            updateState {
                copy(
                    textMemeList = texts,
                    showEditTextComponent = false,
                    showOptionsComponent = true,
                    selectedMeme = null,
                    undoItems = state.value.undoItems.toMutableList().apply { add(selectedMeme) }.toList(),
                    redoItems = emptyList(),
                )
            }
        }
    }

    private fun onTextTapped(textMeme: TextMeme) {
        Log.i("icd", "Text clicked: $textMeme")
        updateState {
            copy(
                showEditTextComponent = true,
                showOptionsComponent = false,
                selectedMeme = state.value.textMemeList.firstOrNull { it.id == textMeme.id }
            )
        }
    }

    private fun onTextMemeMoved(textMeme: TextMeme) {
        val texts = state.value.textMemeList.toMutableList().onEach {
            if(it.id == textMeme.id) {
                it.offset = textMeme.offset
            }
        }

        updateState { copy(textMemeList = texts) }
    }

    private fun onSliderValueChanged(sliderValue: Float) {
        val selectedMeme = state.value.selectedMeme?.copy(fontSize = sliderValue)
        updateState { copy(selectedMeme = selectedMeme) }
    }

    private fun onTextDoubleTapped(textMeme: TextMeme) {
        Log.i("icd", "Text double tapped: $textMeme")
        val selectedMemeUpdated = state.value.selectedMeme ?: state.value.textMemeList.firstOrNull { it.id == textMeme.id }
        updateState { copy(selectedMeme = selectedMemeUpdated?.copy(enabledToEdit = true)) }
    }
}