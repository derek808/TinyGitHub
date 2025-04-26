package com.derek.tinygithub.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.derek.tinygithub.ui.screen.RepoScreen
import kotlinx.serialization.Serializable

@Serializable
data class RepoRoute(val id: String)

fun NavController.navigateToRepo(repo: String, navOptions: NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = RepoRoute(repo)) {
        navOptions()
    }
}

fun NavGraphBuilder.repoScreen() {
    composable<RepoRoute> { entry ->
        RepoScreen()
    }
}
