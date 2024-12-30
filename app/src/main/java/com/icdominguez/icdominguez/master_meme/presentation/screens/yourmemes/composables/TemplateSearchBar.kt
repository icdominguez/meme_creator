package com.icdominguez.icdominguez.master_meme.presentation.screens.yourmemes.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.icdominguez.icdominguez.master_meme.R
import com.icdominguez.icdominguez.master_meme.ui.theme.SecondaryFixedDim
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemplateSearchBar(
    searchList: List<String>,
    sheetState: SheetState,
    scope: CoroutineScope,
    active: MutableState<Boolean>,
    onTemplateClicked: (String) -> Unit = {},
    focusRequester: FocusRequester,
) {
    var searchQuery by remember { mutableStateOf("") }
    var searchListState by remember { mutableStateOf(searchList) }

    SearchBar(
        query = searchQuery,
        onQueryChange = { newQuery ->
            searchQuery = newQuery
            searchListState = searchList.filter { it.split("/").last().contains(searchQuery.lowercase()) }
        },
        modifier = Modifier
            .focusRequester(focusRequester = focusRequester)
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        onSearch = {
            active.value = false
        },
        active = active.value,
        onActiveChange = { active.value = it },
        placeholder = { Text(stringResource(R.string.search_input)) },
        leadingIcon = {
            IconButton(
                onClick = {
                    active.value = false
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = SecondaryFixedDim,
                )
            }
        },
        trailingIcon = {
            IconButton(onClick = {
                searchQuery = ""
                searchListState = searchList
            }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = SecondaryFixedDim,
                )
            }
        },
        colors = SearchBarDefaults.colors(
            containerColor = Color.Transparent
        ),
        content = {
            Box {
                Text(
                    modifier = Modifier
                        .padding(top = 12.dp),
                    text = if(searchListState.isNotEmpty()) "${searchListState.size} templates" else "No memes found :(",
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.outline
                    )
                )
                LazyVerticalGrid(
                    modifier = Modifier
                        .padding(top = 40.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    columns = GridCells.Fixed(2)
                ) {
                    items(searchListState.size) { index ->
                        TemplateItem(
                            template = searchListState[index],
                            sheetState = sheetState,
                            scope = scope,
                            onTemplateClicked = { onTemplateClicked(searchListState[index]) }
                        )
                    }
                }

                BottomBlurredBox(modifier = Modifier.align(Alignment.BottomCenter))
            }
        }
    )
}