package com.icdominguez.icdominguez.master_meme.presentation.navigation

import android.net.Uri
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class NavItem(
    val baseRoute: String,
    val navArgs: List<NavArg> = emptyList()
) {
    val route = run {
        val argKeys = navArgs.map { "{${it.key}}" }
        listOf(baseRoute)
            .plus(argKeys)
            .joinToString("/")
    }

    val args = navArgs.map {
        navArgument(it.key) { type = it.navType}
    }

    data object YourMemes: NavItem("your_memes")
    data object NewMeme: NavItem("new_meme", listOf(NavArg.MemeTemplate)) {
        fun createNavRoute(memeTemplate: String) = "$baseRoute/${Uri.encode(memeTemplate)}"
    }
}

enum class NavArg(
    val key: String,
    val navType: NavType<*>
) {
    MemeTemplate(
        key = "memeTemplate",
        navType = NavType.StringType
    )
}