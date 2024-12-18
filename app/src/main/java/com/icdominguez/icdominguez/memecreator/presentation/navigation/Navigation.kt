package com.icdominguez.icdominguez.memecreator.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.icdominguez.icdominguez.memecreator.presentation.screens.newmeme.NewMemeScreen
import com.icdominguez.icdominguez.memecreator.presentation.screens.newmeme.NewMemeViewModel
import com.icdominguez.icdominguez.memecreator.presentation.screens.yourmemes.YourMemeScreen
import com.icdominguez.icdominguez.memecreator.presentation.screens.yourmemes.YourMemesViewModel

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
                onClick = { memeTemplateId ->
                    navController.navigate(NavItem.NewMeme.createNavRoute(memeTemplateId))
                },
            )
        }
        composable(
            route = NavItem.NewMeme.route,
            arguments = NavItem.NewMeme.args
        ) { backStackEntry ->
            val viewmodel = hiltViewModel<NewMemeViewModel>(backStackEntry)
            val memeTemplateId = backStackEntry.arguments?.getInt(NavArg.MemeTemplateId.key)
            requireNotNull(memeTemplateId) { "Can't be null, new meme requires a template id" }

            NewMemeScreen(
                state = viewmodel.state.collectAsStateWithLifecycle().value,
                uiEvent = viewmodel::uiEvent,
                navController = navController,
                memeTemplateId = memeTemplateId
            )
        }
    }
}