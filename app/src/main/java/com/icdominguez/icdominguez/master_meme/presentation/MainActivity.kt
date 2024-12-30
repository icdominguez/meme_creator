package com.icdominguez.icdominguez.master_meme.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.icdominguez.icdominguez.master_meme.presentation.navigation.Navigation
import com.icdominguez.icdominguez.master_meme.ui.theme.MasterMemeTheme
import com.icdominguez.icdominguez.master_meme.ui.theme.SurfaceContainer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            enableEdgeToEdge()
            actionBar?.hide()
        } else {
            installSplashScreen()
            window.statusBarColor = SurfaceContainer.toArgb()
        }


        setContent {
            MasterMemeTheme {
                Navigation()
            }
        }
    }
}