package com.icdominguez.icdominguez.master_meme.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.icdominguez.icdominguez.master_meme.presentation.screens.newmeme.NewMemeScreen
import com.icdominguez.icdominguez.master_meme.presentation.screens.newmeme.NewMemeViewModel
import com.icdominguez.icdominguez.master_meme.presentation.screens.yourmemes.YourMemeScreen
import com.icdominguez.icdominguez.master_meme.presentation.screens.yourmemes.YourMemesViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavItem.YourMemes.route
    ) {
        composable(route = NavItem.YourMemes.route) { backStackEntry ->
            val viewModel = hiltViewModel<YourMemesViewModel>(backStackEntry)

            YourMemeScreen(
                state = viewModel.state.collectAsStateWithLifecycle().value,
                uiEvent = viewModel::uiEvent,
                onClick = { memeTemplate ->
                    navController.navigate(NavItem.NewMeme.createNavRoute(memeTemplate))
                },
            )
        }
        composable(
            route = NavItem.NewMeme.route,
            arguments = NavItem.NewMeme.args
        ) { backStackEntry ->
            val viewmodel = hiltViewModel<NewMemeViewModel>(backStackEntry)
            val memeTemplate = backStackEntry.arguments?.getString(NavArg.MemeTemplate.key)
            requireNotNull(memeTemplate) { "Can't be null, new meme requires a template" }

            NewMemeScreen(
                state = viewmodel.state.collectAsStateWithLifecycle().value,
                uiEvent = viewmodel::uiEvent,
                navController = navController,
                memeTemplate = memeTemplate
            )
        }
    }
}