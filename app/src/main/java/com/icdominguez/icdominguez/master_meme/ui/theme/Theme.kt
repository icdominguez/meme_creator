package com.icdominguez.icdominguez.master_meme.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = OnPrimary,
    secondary = SecondaryFixedDim,
    onSurface = OnSurface,
    surfaceContainerLowest = SurfaceContainerLowest,
    surfaceContainerLow = SurfaceContainerLow,
    surfaceContainer = SurfaceContainer,
    outline = Outline,
    primaryContainer = PrimaryContainer,
    surfaceContainerHigh = SurfaceContainerHigh,
)

@Composable
fun MasterMemeTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}