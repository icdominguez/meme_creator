package com.icdominguez.icdominguez.master_meme.presentation.screens.yourmemes

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.icdominguez.icdominguez.master_meme.R
import com.icdominguez.icdominguez.master_meme.presentation.screens.yourmemes.composables.EmptyView
import com.icdominguez.icdominguez.master_meme.presentation.screens.yourmemes.composables.GradientFloatingActionButton
import com.icdominguez.icdominguez.master_meme.presentation.screens.yourmemes.composables.SortMemesDropdownMenu
import com.icdominguez.icdominguez.master_meme.presentation.screens.yourmemes.composables.TemplateItem
import com.icdominguez.icdominguez.master_meme.presentation.screens.yourmemes.composables.TemplateSearchBar
import com.icdominguez.icdominguez.master_meme.presentation.screens.yourmemes.dialogs.DeleteMemesDialog
import com.icdominguez.icdominguez.master_meme.ui.theme.MasterMemeTheme
import com.icdominguez.icdominguez.master_meme.ui.theme.OnSurface
import com.icdominguez.icdominguez.master_meme.ui.theme.SecondaryFixedDim
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YourMemeScreen(
    onClick: (Int) -> Unit = {},
    state: YourMemesViewModel.State = YourMemesViewModel.State(),
    uiEvent: (YourMemesViewModel.Event) -> Unit = {}
) {
    val isSearchActive = remember { mutableStateOf(false) }
    val modalExpandedBeforeSearch = remember { mutableStateOf(false) }
    var showBottomSheet by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = if(isSearchActive.value) true else if(!isSearchActive.value && modalExpandedBeforeSearch.value) true else false
    )
    val drawables = arrayOf(
        R.drawable.creepy_condescending_wonka,
        R.drawable.arm_wrestle_agreement,
        R.drawable.becoming_a_clown,
        R.drawable.blank_protest_sign,
        R.drawable.distracted_boyfriend,
        R.drawable.boardroom_suggestion,
        R.drawable.change_my_mind,
        R.drawable.cheers_to_that,
        R.drawable.chills_down_my_spine,
        R.drawable.confronting_the_mirror_self,
        R.drawable.crying_man_close_up,
        R.drawable.disaster_girl,
        R.drawable.drake_hotline_bling,
        R.drawable.empty_todo_list,
        R.drawable.false_hope,
        R.drawable.flex_tape,
        R.drawable.giant_pill_bottle_blocking_face,
        R.drawable.grus_plan,
        R.drawable.guy_running_from_the_tornado,
        R.drawable.handshake_agreement,
        R.drawable.hard_turn,
        R.drawable.hide_the_pain_harold,
        R.drawable.incoming_call,
        R.drawable.is_this_a_pidgeon,
        R.drawable.late_night_overthinking,
        R.drawable.live_reaction,
        R.drawable.miltons_red_stapler_office,
        R.drawable.mr_bean_waiting,
        R.drawable.mr_incredible_becoming_uncanny,
        R.drawable.ol_reliable,
        R.drawable.orangutan_morning_show,
        R.drawable.persistent_plans,
        R.drawable.running_away_from_responsabilities,
        R.drawable.sad_megamind_close_up,
        R.drawable.sad_pablo_escobar_waiting,
        R.drawable.shocked_cat,
        R.drawable.skeptical_kid,
        R.drawable.skinner_the_simpsons,
        R.drawable.spider_man_pointing,
        R.drawable.spongebob_comparison,
        R.drawable.the_rock_driving,
        R.drawable.thick_vs_thin_bokk,
        R.drawable.thinking_about_other_girls,
        R.drawable.tired_ben_affleck,
        R.drawable.troya_horse,
        R.drawable.types_of_headaches,
        R.drawable.two_buttons,
        R.drawable.waiting_skeleton,
        R.drawable.x_all_the_y,
        R.drawable.two_guys_on_a_bus,
        R.drawable.squidward_window,
    )
    val searchList = drawables.map {
        val name = LocalContext.current.resources.getResourceName(it).split("/").last().lowercase().replace("_", " ")
        Pair(it, name)
    }
    val scope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }

    MasterMemeTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                        ),
                        title = {
                            if(state.isSelectionMode && state.memes.any { it.selected }) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    IconButton(onClick = { uiEvent(YourMemesViewModel.Event.OnCloseSelectionDialog) }) {
                                        Icon(
                                            imageVector = Icons.Outlined.Close,
                                            contentDescription = null
                                        )
                                    }
                                    Text(
                                        text = state.memes.count { it.selected }.toString()
                                    )
                                    Spacer(modifier = Modifier.weight(1f))

                                    IconButton(onClick = { uiEvent(YourMemesViewModel.Event.OnShareMemeButtonClicked) }) {
                                        Icon(
                                            imageVector = Icons.Default.Share,
                                            contentDescription = null
                                        )
                                    }

                                    IconButton(onClick = { uiEvent(YourMemesViewModel.Event.OnDeleteMemeClicked) }) {
                                        Icon(
                                            imageVector = Icons.Outlined.Delete,
                                            contentDescription = null
                                        )
                                    }
                                }
                            } else {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(
                                        fontFamily = LocalTextStyle.current.fontFamily,
                                        text = stringResource(R.string.your_memes_screen),
                                        fontSize = 24.sp,
                                        color = OnSurface,
                                    )

                                    Spacer(modifier = Modifier.weight(1f))

                                    Text(
                                        text = state.sortOptions.description,
                                        style = TextStyle(
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight(700),
                                            fontFamily = FontFamily.Default,
                                            color = OnSurface
                                        )
                                    )

                                    SortMemesDropdownMenu(
                                        onNewestFirstButtonClicked = { uiEvent(YourMemesViewModel.Event.OnNewestFirstButtonClicked) },
                                        onFavoritesFirstButtonClicked = { uiEvent(YourMemesViewModel.Event.OnFavoritesFirstButtonCLicked) }
                                    )
                                }
                            }
                        }
                    )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surfaceContainerLowest)
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                if(state.memes.isEmpty()) {
                    EmptyView()
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        LazyVerticalGrid(
                            modifier = Modifier
                                .padding(
                                    top = 42.dp,
                                    bottom = 12.dp,
                                    start = 22.dp,
                                    end = 22.dp,
                                ),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            columns = GridCells.Fixed(2),
                        ) {
                            items(state.memes.size) { index ->
                                val currentMeme = state.memes[index]
                                currentMeme.imageBitmap?.let {
                                    Box {
                                        Image(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(176.dp)
                                                .clip(RoundedCornerShape(size = 8.dp))
                                                .pointerInput(Unit) {
                                                    detectTapGestures(
                                                        onLongPress = { uiEvent(YourMemesViewModel.Event.OnMemeLongPressed(selectedMeme = currentMeme)) }
                                                    )
                                                }
                                                .drawWithCache {
                                                    val gradient = Brush.radialGradient(
                                                        colors = listOf(
                                                            Color.Transparent,
                                                            Color.Black.copy(alpha = 0.9f)
                                                        ),
                                                        center = Offset(size.width, if(state.isSelectionMode) 0f else size.height),
                                                        radius = size.maxDimension / 2
                                                    )
                                                    onDrawWithContent {
                                                        drawContent()
                                                        drawRect(
                                                            brush = gradient,
                                                            blendMode = BlendMode.DstIn
                                                        )
                                                    }
                                                },
                                            bitmap = it,
                                            contentDescription = null,
                                            contentScale = ContentScale.Crop,
                                        )

                                        if (state.isSelectionMode && state.memes.any { it.selected }) {
                                            IconButton(
                                                modifier = Modifier
                                                    .align(Alignment.TopEnd),
                                                onClick = { uiEvent(YourMemesViewModel.Event.OnSelectMemeButton(selectedMeme = currentMeme)) }) {
                                                Icon(
                                                    imageVector = if (currentMeme.selected) Icons.Default.CheckCircle else Icons.Outlined.Circle,
                                                    contentDescription = null,
                                                    tint = Color(android.graphics.Color.parseColor("#EADDFF"))
                                                )
                                            }
                                        }

                                        if (!state.isSelectionMode) {
                                            IconButton(
                                                modifier = Modifier
                                                    .blur(radiusX = 16.dp, radiusY = 0.dp)
                                                    .align(Alignment.BottomEnd),
                                                onClick = {
                                                    uiEvent(YourMemesViewModel.Event.OnFavoriteIconButtonClicked(currentMeme))
                                                }
                                            ) {
                                                Icon(
                                                    imageVector = if (currentMeme.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                                    contentDescription = null,
                                                    tint = Color(android.graphics.Color.parseColor("#EADDFF"))
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .align(Alignment.BottomEnd)
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            Color.Black.copy(alpha = 0.9f),
                                        )
                                    )
                                ),
                        )
                    }
                }

                GradientFloatingActionButton(
                    modifier = Modifier
                        .align(Alignment.BottomEnd),
                    onClick = {
                        showBottomSheet = true
                    }
                )
            }

            if (showBottomSheet) {
                ModalBottomSheet(
                    sheetState = bottomSheetState,
                    onDismissRequest = {
                        showBottomSheet = false
                        isSearchActive.value = false
                        modalExpandedBeforeSearch.value = false
                    }
                ) {
                    Box {
                        if(isSearchActive.value) {
                            TemplateSearchBar(
                                searchList = searchList,
                                sheetState = bottomSheetState,
                                scope = scope,
                                active = isSearchActive,
                                onTemplateClicked = { onClick(it) },
                                focusRequester = focusRequester
                            )
                        } else {
                            Column(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(
                                        text = stringResource(R.string.choose_template_title),
                                        style = TextStyle(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp
                                        )
                                    )
                                    Spacer(modifier = Modifier.weight(1f))

                                    IconButton(
                                        onClick = {
                                            scope.launch {
                                                if(bottomSheetState.currentValue == SheetValue.Expanded) {
                                                    modalExpandedBeforeSearch.value = true
                                                }
                                            }.invokeOnCompletion {
                                                isSearchActive.value = true
                                            }
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Search,
                                            contentDescription = null,
                                            tint = SecondaryFixedDim,
                                        )
                                    }
                                }

                                Text(
                                    modifier = Modifier
                                        .padding(top = 2.dp),
                                    text = stringResource(R.string.choose_template_description),
                                    style = TextStyle(
                                        fontSize = 12.sp
                                    )
                                )

                                LazyVerticalGrid(
                                    modifier = Modifier
                                        .padding(top = 42.dp),
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(16.dp),
                                    columns = GridCells.Fixed(2)
                                ) {
                                    items(drawables.size) { index ->
                                        TemplateItem(
                                            drawableId = drawables[index],
                                            sheetState = bottomSheetState,
                                            scope = scope,
                                            onTemplateClicked = { onClick(drawables[index]) }
                                        )
                                    }
                                }
                            }
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .align(Alignment.BottomCenter)
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            Color.Black.copy(alpha = 0.9f),
                                        )
                                    )
                                ),
                        )
                    }
                }
            }

            if(state.showDeleteDialog) {
                DeleteMemesDialog(
                    onDismissRequest = { uiEvent(YourMemesViewModel.Event.OnDeleteDialogDismissed) },
                    onConfirmButtonClicked = { uiEvent(YourMemesViewModel.Event.OnDeleteDialogConfirmed) },
                    memesToDelete = state.memes.filter { it.selected }.size
                )
            }

            if(state.imageUri != null) {
                val shareIntent = Intent(Intent.ACTION_SEND_MULTIPLE).apply {
                    val uris = ArrayList<Uri>()
                    state.imageUri.forEach {
                        uris.add(it)
                    }
                    putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris)
                    putExtra(Intent.EXTRA_SUBJECT, stringResource(R.string.sharing_meme))
                    type = "image/png"
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
                LocalContext.current.startActivity(Intent.createChooser(shareIntent, stringResource(R.string.sharing_meme)))
            }

            LaunchedEffect(state.imageUri) { uiEvent(YourMemesViewModel.Event.OnShareMemeCreated) }

            LaunchedEffect(isSearchActive.value) {
                if(isSearchActive.value) {
                    focusRequester.requestFocus()
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun YourMemeScreenPreview() {
    YourMemeScreen()
}