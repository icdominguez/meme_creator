package com.icdominguez.icdominguez.master_meme.presentation.screens.yourmemes.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.icdominguez.icdominguez.master_meme.R

@Composable
fun SortMemesDropdownMenu(
    onNewestFirstButtonClicked: () -> Unit = {},
    onFavoritesFirstButtonClicked: () -> Unit = {},
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { expanded = !expanded } ) {
            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text(text = stringResource(R.string.menu_newest_first)) },
                onClick = {
                    expanded = !expanded
                    onNewestFirstButtonClicked()
                }
            )
            DropdownMenuItem(
                text = { Text(text = stringResource(R.string.menu_favorites_first_option)) },
                onClick = {
                    expanded = !expanded
                    onFavoritesFirstButtonClicked()
                }
            )
        }
    }
}