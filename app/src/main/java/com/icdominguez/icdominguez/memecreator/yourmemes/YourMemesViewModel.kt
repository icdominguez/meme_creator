package com.icdominguez.icdominguez.memecreator.yourmemes

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.icdominguez.icdominguez.memecreator.FileManager
import com.icdominguez.icdominguez.memecreator.Meme
import com.icdominguez.icdominguez.memecreator.MviViewModel
import com.icdominguez.icdominguez.memecreator.Utils.generateRandomNumber
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class YourMemesViewModel @Inject constructor(
    private val fileManager: FileManager,
): MviViewModel<YourMemesViewModel.State, YourMemesViewModel.Event>() {

    data class State(
        val memes: List<Meme> = emptyList(),
        val isSelectionMode: Boolean = false,
        val showDeleteDialog: Boolean = false,
        val imageUri: List<Uri>? = null,
    )

    override var currentState: State = State()

    sealed class Event {
        data class OnMemeLongPressed(val selectedMeme: Meme): Event()
        data object OnCloseSelectionDialog: Event()
        data object OnDeleteMemeClicked: Event()
        data class OnSelectMemeButton(val selectedMeme: Meme): Event()
        data object OnDeleteDialogDismissed: Event()
        data object OnDeleteDialogConfirmed: Event()
        data object OnScreenLoaded: Event()
        data object OnShareMemeButtonClicked: Event()
        data object OnShareMemeResult: Event()
        data object OnShareMemeCreated: Event()
    }

    override fun uiEvent(event: Event) {
        when(event) {
            is Event.OnMemeLongPressed -> onMemeLongPressed(event.selectedMeme)
            is Event.OnCloseSelectionDialog -> onCloseSelectionDialog()
            is Event.OnDeleteMemeClicked -> onDeleteMemeClicked()
            is Event.OnSelectMemeButton -> onMemeClicked(event.selectedMeme)
            is Event.OnDeleteDialogDismissed -> onDeleteDialogDismissed()
            is Event.OnDeleteDialogConfirmed -> onDeleteDialogConfirmed()
            is Event.OnScreenLoaded -> getMyMemes()
            is Event.OnShareMemeResult -> onShareMemeResult()
            is Event.OnShareMemeButtonClicked -> onShareMemeButtonClicked()
            is Event.OnShareMemeCreated -> { updateState { copy(imageUri = null) }}
        }
    }

    private fun onShareMemeResult() {
        val memes = state.value.memes.map { meme ->
            meme.copy(selected = false)
        }
        updateState { copy(memes = memes, imageUri = null, isSelectionMode = false) }
    }

    private fun onDeleteDialogConfirmed() {
        updateState { copy(
            showDeleteDialog = false,
            isSelectionMode = false
        ) }
        state.value.memes.onEach { meme ->
            if(meme.selected) { fileManager.removeFile(meme.path) }
        }
        getMyMemes()
    }

    private fun onDeleteDialogDismissed() {
        updateState { copy(showDeleteDialog = false) }
    }

    private fun onDeleteMemeClicked() {
        updateState { copy(showDeleteDialog = true) }
    }

    private fun onCloseSelectionDialog() {
        val memes = state.value.memes.map { meme ->
            meme.copy(selected = false)
        }
        updateState { copy(isSelectionMode = false, memes = memes) }
    }

    private fun onMemeLongPressed(selectedMeme: Meme) {
        val memes = state.value.memes.map { meme ->
            if(meme.id == selectedMeme.id) {
                meme.copy(selected = !meme.selected)
            } else {
                meme
            }
        }

        updateState {
            copy(
                memes = memes,
                isSelectionMode = true
            )
        }
    }

    private fun onMemeClicked(selectedMeme: Meme) {
        val memes = state.value.memes.map { meme ->
            if(meme.id == selectedMeme.id) {
                meme.copy(selected = !meme.selected)
            } else {
                meme
            }
        }

        updateState {
            copy(
                memes = memes
            )
        }
    }

    private fun getMyMemes() {
        viewModelScope.launch {
            val bitmaps = fileManager.getAllFiles()
            val memes = bitmaps.map { bitmap ->
                Meme(
                    id = generateRandomNumber(state.value.memes.map { it.id }),
                    path = bitmap.path,
                    imageBitmap = bitmap.imageBitmap,
                    selected = false
                )
            }
            updateState { copy(memes = memes) }
        }
    }

    private fun onShareMemeButtonClicked() {
        val memesToShare = state.value.memes.filter { it.selected }
        val bitmaps = mutableListOf<Uri>()

        memesToShare.map { meme ->
            meme.imageBitmap?.let { bitmap ->
                fileManager.saveImageToCache(bitmap)?.let { uri ->
                    bitmaps.add(uri)
                }
            }
        }

        updateState { copy(imageUri = bitmaps) }
    }
}