package com.icdominguez.icdominguez.memecreator.presentation.screens.yourmemes

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.icdominguez.icdominguez.memecreator.FileManager
import com.icdominguez.icdominguez.memecreator.domain.usecases.DeleteMemeUseCase
import com.icdominguez.icdominguez.memecreator.domain.usecases.GetAllUseCase
import com.icdominguez.icdominguez.memecreator.domain.usecases.UpdateIsFavoriteMemeUseCase
import com.icdominguez.icdominguez.memecreator.presentation.MviViewModel
import com.icdominguez.icdominguez.memecreator.presentation.SortOptions
import com.icdominguez.icdominguez.memecreator.presentation.Utils
import com.icdominguez.icdominguez.memecreator.presentation.model.Meme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class YourMemesViewModel @Inject constructor(
    private val fileManager: FileManager,
    private val getAllUseCase: GetAllUseCase,
    private val updateIsFavoriteMemeUseCase: UpdateIsFavoriteMemeUseCase,
    private val deleteMemeUseCase: DeleteMemeUseCase,
): MviViewModel<YourMemesViewModel.State, YourMemesViewModel.Event>() {

    data class State(
        val memes: List<Meme> = emptyList(),
        val isSelectionMode: Boolean = false,
        val showDeleteDialog: Boolean = false,
        val imageUri: List<Uri>? = null,
        val sortOptions: SortOptions = SortOptions.FAVORITES_FIRST
    )

    override var currentState: State = State()

    sealed class Event {
        data class OnMemeLongPressed(val selectedMeme: Meme): Event()
        data object OnCloseSelectionDialog: Event()
        data object OnDeleteMemeClicked: Event()
        data class OnSelectMemeButton(val selectedMeme: Meme): Event()
        data object OnDeleteDialogDismissed: Event()
        data object OnDeleteDialogConfirmed: Event()
        data object OnShareMemeButtonClicked: Event()
        data object OnShareMemeResult: Event()
        data object OnShareMemeCreated: Event()
        data class OnFavoriteIconButtonClicked(val selectedMeme: Meme): Event()
        data object OnNewestFirstButtonClicked: Event()
        data object OnFavoritesFirstButtonCLicked: Event()
    }

    override fun uiEvent(event: Event) {
        when(event) {
            is Event.OnMemeLongPressed -> onMemeLongPressed(event.selectedMeme)
            is Event.OnCloseSelectionDialog -> onCloseSelectionDialog()
            is Event.OnDeleteMemeClicked -> onDeleteMemeClicked()
            is Event.OnSelectMemeButton -> onMemeClicked(event.selectedMeme)
            is Event.OnDeleteDialogDismissed -> onDeleteDialogDismissed()
            is Event.OnDeleteDialogConfirmed -> onDeleteDialogConfirmed()
            is Event.OnShareMemeResult -> onShareMemeResult()
            is Event.OnShareMemeButtonClicked -> onShareMemeButtonClicked()
            is Event.OnShareMemeCreated -> onShareMemeCreated()
            is Event.OnFavoriteIconButtonClicked -> onFavoriteIconButtonClicked(event.selectedMeme)
            is Event.OnNewestFirstButtonClicked -> onNewestFirstButtonClicked()
            is Event.OnFavoritesFirstButtonCLicked -> onFavoritesFirstButtonClicked()
        }
    }

    private fun onFavoritesFirstButtonClicked() {
        updateState {
            copy(
                sortOptions = SortOptions.FAVORITES_FIRST,
                memes = sortList(memeList = state.value.memes, sortOption = SortOptions.FAVORITES_FIRST)
            )
        }
    }

    private fun onNewestFirstButtonClicked() {
        updateState {
            copy(
                sortOptions = SortOptions.NEWEST_FIRST,
                memes = sortList(state.value.memes, sortOption = SortOptions.NEWEST_FIRST),
            )
        }
    }

    private fun onFavoriteIconButtonClicked(selectedMeme: Meme) {
        viewModelScope.launch {
            updateIsFavoriteMemeUseCase(selectedMeme.id, !selectedMeme.isFavorite)
        }
        val memes = state.value.memes.map { meme ->
            if(meme.id == selectedMeme.id) {
                meme.copy(isFavorite = !meme.isFavorite)
            } else {
               meme
            }
        }
        updateState { copy(memes = memes) }
    }

    private fun onShareMemeCreated() {
        updateState { copy(imageUri = null) }
    }

    private fun onShareMemeResult() {
        val memes = state.value.memes.map { meme ->
            meme.copy(selected = false)
        }
        updateState { copy(memes = memes, imageUri = null, isSelectionMode = false) }
    }

    private fun onDeleteDialogConfirmed() {
        updateState {
            copy(
                showDeleteDialog = false,
                isSelectionMode = false
            )
        }
        state.value.memes.onEach { meme ->
            if(meme.selected) {
                viewModelScope.launch {
                    deleteMemeUseCase(memeId = meme.id)
                }
                fileManager.removeFile(meme.path)
            }
        }
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

    init {
        viewModelScope.launch {
            getAllUseCase().collect { memes ->
                val memeList = mutableListOf<Meme>()
                memes.map { meme ->
                    meme.path?.let {
                        memeList.add(
                            Meme(
                                id = meme.uid,
                                path = it,
                                imageBitmap = fileManager.createImageBitmap(File(it)),
                                selected = false,
                                isFavorite = meme.favorite,
                                dateCreated = Utils.localDateTimeToCalendar(localDateTime = meme.date)
                            )
                        )
                    }
                }

                val sortedList = sortList(memeList, state.value.sortOptions)

                updateState { copy(memes = sortedList) }
            }
        }
    }

    private fun sortList(memeList: List<Meme>, sortOption: SortOptions) =
        when(sortOption) {
            SortOptions.FAVORITES_FIRST -> {
                memeList.sortedWith(compareByDescending<Meme> { it.isFavorite }.thenBy { it.dateCreated })
            }

            SortOptions.NEWEST_FIRST -> {
                memeList.sortedWith(compareByDescending { it.dateCreated })
            }
        }
}