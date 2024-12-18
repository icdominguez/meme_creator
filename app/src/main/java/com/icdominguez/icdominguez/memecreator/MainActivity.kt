package com.icdominguez.icdominguez.memecreator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.icdominguez.icdominguez.memecreator.presentation.navigation.NavArg
import com.icdominguez.icdominguez.memecreator.presentation.navigation.NavItem
import com.icdominguez.icdominguez.memecreator.presentation.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Navigation()
        }
    }
}