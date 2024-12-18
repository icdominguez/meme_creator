package com.icdominguez.icdominguez.memecreator.presentation.screens.newmeme

import android.net.Uri
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.lifecycle.viewModelScope
import com.icdominguez.icdominguez.memecreator.FileManager
import com.icdominguez.icdominguez.memecreator.MemeCreatorApp
import com.icdominguez.icdominguez.memecreator.domain.model.MemeEntity
import com.icdominguez.icdominguez.memecreator.domain.usecases.InsertNewMemeUseCase
import com.icdominguez.icdominguez.memecreator.presentation.MviViewModel
import com.icdominguez.icdominguez.memecreator.presentation.Utils.generateRandomNumber
import com.icdominguez.icdominguez.memecreator.presentation.model.TextMeme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewMemeViewModel @Inject constructor(
    private val fileManager: FileManager,
    private val insertNewMemeUseCase: InsertNewMemeUseCase,
): MviViewModel<NewMemeViewModel.State, NewMemeViewModel.Event>() {

    data class State (
        val texts: List<TextMeme> = emptyList(),
        val showOptionsComponent: Boolean = true,
        val showEditTextComponent: Boolean = false,
        val selectedMeme: TextMeme? = null,
        val doubleTappedText: TextMeme? = null,
        val showLeaveDialog: Boolean = false,
        val showEditTextDialog: Boolean = false,
        val bitmap: ImageBitmap? = null,
        val imageUri: Uri? = null,
    )

    override var currentState: State = State()

    sealed class Event {
        data object AddTextButtonClicked : Event()
        data class OnRemoveTextButtonClicked(val textMeme: TextMeme): Event()
        data object OnEditTextComponentCloseButtonClicked: Event()
        data object OnEditTextComponentCheckButtonClicked: Event()
        data class OnTextDoubleTapped(val textMeme: TextMeme): Event()
        data class OnTextTapped(val textMeme: TextMeme): Event()
        data class OnSliderValueChanged(val sliderValue: Float): Event()
        data class OnTextMemeMoved(val textMeme: TextMeme): Event()
        data object OnBackClicked: Event()
        data object OnLeaveDialogDismissed: Event()
        data object OnLeaveDialogAccepted: Event()
        data object OnEditTextDialogDismissed: Event()
        data class OnEditTextDialogAccepted(val text: String): Event()
        data class OnEditTextTextFieldValueChanged(val newText: String): Event()
        data class OnSaveButtonClicked(val picture: GraphicsLayer): Event()
        data class OnShareMemeButtonClicked(val picture: GraphicsLayer): Event()
        data object OnShareMemeResult: Event()
    }

    override fun uiEvent(event: Event) {
        when(event) {
            is Event.AddTextButtonClicked -> onAddTextButtonClicked()
            is Event.OnRemoveTextButtonClicked -> onRemoveTextButtonClicked(event.textMeme)
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
            is Event.OnEditTextDialogDismissed -> onEditTextDialogDismissed()
            is Event.OnEditTextDialogAccepted -> onEditTextDialogAccepted(event.text)
            is Event.OnSaveButtonClicked -> onSaveButtonClicked(event.picture)
            is Event.OnShareMemeButtonClicked -> onShareMemeButtonClicked(event.picture)
            is Event.OnShareMemeResult -> {
                updateState { copy(imageUri = null) }
            }
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

    private fun onEditTextDialogDismissed() {
        updateState { copy(showEditTextDialog = false) }
    }

    private fun onSaveButtonClicked(graphicsLayer: GraphicsLayer) {
        viewModelScope.launch {
            insertNewMemeUseCase(graphicsLayer)
        }
    }

    private fun onEditTextDialogAccepted(text: String) {
        state.value.doubleTappedText?.let { doubleTappedText ->
            val texts = state.value.texts.toMutableList().onEach {
                if(it.id == doubleTappedText.id) {
                    it.text = text
                }
            }

            updateState { copy(
                texts = texts,
                doubleTappedText = null,
                showEditTextDialog = false,
            ) }
        }

    }

    private fun onLeaveDialogDismissed() {
        updateState { copy(showLeaveDialog = false) }
    }

    private fun onLeaveDialogAccepted() {
        updateState { copy(showLeaveDialog = false) }
    }

    private fun onBackClicked() {
        if (state.value.texts.isNotEmpty()) {
            updateState { copy(showLeaveDialog = true) }
        }
    }

    private fun onRemoveTextButtonClicked(textMeme: TextMeme) {
        val texts = state.value.texts.toMutableList().apply {
            val index = this.indexOfFirst { it.id == textMeme.id }
            this.removeAt(index)
        }

        updateState {
            copy(
                texts = texts,
                showEditTextComponent = false,
                showOptionsComponent = texts.isEmpty(),
                selectedMeme = null,
            )
        }
    }

    private fun onAddTextButtonClicked() {
        val textMeme = TextMeme(id = generateRandomNumber(state.value.texts.map { it.id  }))
        updateState {
            copy(
                showOptionsComponent = false,
                showEditTextComponent = true,
                texts = state.value.texts.plus(textMeme),
                selectedMeme = textMeme
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
            val texts = state.value.texts.toMutableList().onEach {
                if(it.id == selectedMeme.id) {
                    it.fontSize = selectedMeme.fontSize
                }
            }

            updateState {
                copy(
                    texts = texts,
                    showEditTextComponent = false,
                    showOptionsComponent = true,
                    selectedMeme = null
                )
            }
        }
    }

    private fun onTextTapped(textMeme: TextMeme) {
        if(!state.value.showEditTextComponent) {
            updateState {
                copy(
                    showEditTextComponent = true,
                    showOptionsComponent = false,
                    selectedMeme = textMeme
                )
            }
        }
    }

    private fun onTextMemeMoved(textMeme: TextMeme) {
        val texts = state.value.texts.toMutableList().onEach {
            if(it.id == textMeme.id) {
                it.offset = textMeme.offset
            }
        }

        updateState { copy(texts = texts) }
    }

    private fun onSliderValueChanged(sliderValue: Float) {
        val selectedMeme = state.value.selectedMeme?.copy(fontSize = sliderValue)
        updateState { copy(selectedMeme = selectedMeme) }
    }

    private fun onTextDoubleTapped(textMeme: TextMeme) {
        updateState { copy(showEditTextDialog = true, doubleTappedText = textMeme) }
    }
}