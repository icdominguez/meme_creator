package com.icdominguez.icdominguez.memecreator.yourmemes

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.icdominguez.icdominguez.memecreator.R
import com.icdominguez.icdominguez.memecreator.newmeme.NewMemeViewModel
import com.icdominguez.icdominguez.memecreator.ui.theme.MemeCreatorTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YourMemeScreen(
    onClick: (Int) -> Unit = {},
    state: YourMemesViewModel.State = YourMemesViewModel.State(),
    uiEvent: (YourMemesViewModel.Event) -> Unit = {}
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    var showBottomSheet by remember { mutableStateOf(false) }
    val drawables = arrayOf(
        R.drawable.meme_template_1,
        R.drawable.meme_template_2,
        R.drawable.meme_template_3,
        R.drawable.meme_template_4,
        R.drawable.meme_template_5,
        R.drawable.meme_template_6,
        R.drawable.meme_template_7,
        R.drawable.meme_template_8,
        R.drawable.meme_template_9,
        R.drawable.meme_template_10,
        R.drawable.meme_template_11,
        R.drawable.meme_template_12,
        R.drawable.meme_template_13,
        R.drawable.meme_template_14,
        R.drawable.meme_template_15,
        R.drawable.meme_template_16,
        R.drawable.meme_template_17,
        R.drawable.meme_template_18,
        R.drawable.meme_template_19,
        R.drawable.meme_template_20,
        R.drawable.meme_template_21,
        R.drawable.meme_template_22,
        R.drawable.meme_template_23,
        R.drawable.meme_template_24,
        R.drawable.meme_template_25,
        R.drawable.meme_template_26,
        R.drawable.meme_template_27,
        R.drawable.meme_template_28,
        R.drawable.meme_template_29,
        R.drawable.meme_template_30,
        R.drawable.meme_template_31,
        R.drawable.meme_template_32,
        R.drawable.meme_template_33,
        R.drawable.meme_template_34,
        R.drawable.meme_template_35,
        R.drawable.meme_template_36,
        R.drawable.meme_template_37,
        R.drawable.meme_template_38,
        R.drawable.meme_template_39,
        R.drawable.meme_template_40,
        R.drawable.meme_template_41,
        R.drawable.meme_template_42,
        R.drawable.meme_template_43,
        R.drawable.meme_template_44,
        R.drawable.meme_template_45,
        R.drawable.meme_template_46,
        R.drawable.meme_template_47,
        R.drawable.meme_template_48,
        R.drawable.meme_template_49,
    )
    val scope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if(result.resultCode == Activity.RESULT_OK || result.resultCode == Activity.RESULT_CANCELED) {
            uiEvent(YourMemesViewModel.Event.OnShareMemeResult)
        }
    }

    MemeCreatorTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            floatingActionButton = {
                FloatingActionButton(
                    containerColor = MaterialTheme.colorScheme.primary,
                    onClick = { showBottomSheet = true }
                ) {
                    Icon(Icons.Filled.Add, null)
                }
            },
            topBar = {
                if(state.isSelectionMode && state.memes.any { it.selected }) {
                    TopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                        ),
                        title = {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                IconButton(onClick = {
                                    uiEvent(YourMemesViewModel.Event.OnCloseSelectionDialog)
                                }) {
                                    Icon(imageVector = Icons.Outlined.Close, contentDescription = null)
                                }
                                Text(
                                    text = state.memes.count { it.selected }.toString()
                                )
                                Spacer(modifier = Modifier.weight(1f))

                                IconButton(onClick = {
                                    uiEvent(YourMemesViewModel.Event.OnShareMemeButtonClicked)
                                }) {
                                    Icon(imageVector = Icons.Default.Share, contentDescription = null)
                                }

                                IconButton(onClick = {
                                    uiEvent(YourMemesViewModel.Event.OnDeleteMemeClicked)
                                }) {
                                    Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                                }
                            }
                        }
                    )
                } else {
                    TopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                        ),
                        title = {
                            Text(
                                fontFamily = LocalTextStyle.current.fontFamily,
                                text = stringResource(R.string.your_memes_screen)
                            )
                        }
                    )
                }
            }
        ) { innerPadding ->
            if(state.showDeleteDialog) {
                DeleteMemesDialog(
                    onDismissRequest = { uiEvent(YourMemesViewModel.Event.OnDeleteDialogDismissed) },
                    onConfirmButtonClicked = { uiEvent(YourMemesViewModel.Event.OnDeleteDialogConfirmed) },
                    memesToDelete = state.memes.filter { it.selected }.size
                )
            }

            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if(state.memes.isEmpty()) {
                    Image(
                        painter = painterResource(id = R.drawable.no_memes_found),
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier
                            .padding(top = 32.dp),
                        text = stringResource(R.string.no_memes_found),
                        style = TextStyle(fontSize = 12.sp)
                    )
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                top = 0.dp,
                                bottom = 12.dp,
                                start = 22.dp,
                                end = 22.dp,
                            )
                    ) {
                        LazyVerticalGrid(
                            modifier = Modifier
                                .padding(top = 42.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            columns = GridCells.Fixed(2),
                        ) {
                            items(state.memes.size) { index ->
                                state.memes[index]?.let { meme ->
                                    meme.imageBitmap?.let {
                                        Box {
                                            Image(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(176.dp)
                                                    .clip(RoundedCornerShape(size = 8.dp))
                                                    .pointerInput(Unit) {
                                                        detectTapGestures (
                                                            onLongPress = {
                                                                uiEvent(YourMemesViewModel.Event.OnMemeLongPressed(meme))
                                                            }
                                                        )
                                                    },
                                                bitmap = it,
                                                contentDescription = null,
                                                contentScale = ContentScale.Crop,
                                            )

                                            if(state.isSelectionMode && state.memes.any { it.selected }) {
                                                IconButton(
                                                    modifier = Modifier
                                                        .align(Alignment.TopEnd),
                                                    onClick = {
                                                        uiEvent(YourMemesViewModel.Event.OnSelectMemeButton(meme))
                                                    }) {
                                                    Icon(
                                                        imageVector = if(meme.selected) Icons.Default.CheckCircle else Icons.Outlined.Circle,
                                                        contentDescription = null,
                                                        tint = Color(android.graphics.Color.parseColor("#EADDFF"))
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }


                if (showBottomSheet) {
                    ModalBottomSheet(
                        modifier = Modifier
                            .fillMaxHeight(),
                        sheetState = sheetState,
                        onDismissRequest = {
                            showBottomSheet = false
                        },
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 16.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.choose_template_title),
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            )
                            Text(
                                modifier = Modifier
                                    .padding(top = 18.dp),
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
                                    Box(modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            scope
                                                .launch {
                                                    sheetState.hide()
                                                }
                                                .invokeOnCompletion {
                                                    if (!sheetState.isVisible) {
                                                        onClick(drawables[index])
                                                    }
                                                }
                                        }
                                    ) {
                                        Image(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(176.dp)
                                                .clip(RoundedCornerShape(size = 8.dp)),
                                            painter = painterResource(id = drawables[index]),
                                            contentDescription = null,
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
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
                launcher.launch(Intent.createChooser(shareIntent, stringResource(R.string.sharing_meme)))
            }

            LaunchedEffect(state.imageUri) { uiEvent(YourMemesViewModel.Event.OnShareMemeCreated) }

            DisposableEffect(lifecycleOwner) {
                val observer = LifecycleEventObserver { _, event ->
                    if (event == Lifecycle.Event.ON_CREATE) {
                        uiEvent(YourMemesViewModel.Event.OnScreenLoaded)
                    }
                }
                lifecycleOwner.lifecycle.addObserver(observer)
                onDispose {
                    lifecycleOwner.lifecycle.removeObserver(observer)
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