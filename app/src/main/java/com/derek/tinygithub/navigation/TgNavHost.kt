package com.derek.tinygithub.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.derek.tinygithub.ui.TgAppState

/**
 * Top-level navigation graph. Navigation is organized as explained at
 * https://d.android.com/jetpack/compose/nav-adaptive
 *
 * The navigation graph defined in this file defines the different top level routes. Navigation
 * within each route is handled using state and Back Handlers.
 */
@Composable
fun TgNavHost(
    appState: TgAppState,
    modifier: Modifier = Modifier,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = ExploreBaseRoute,
        modifier = modifier,
    ) {
        exploreSection(
            onRepoClick = navController::navigateToRepo,
        ) {
            repoScreen()
        }
        searchScreen(
            onRepoClick = navController::navigateToRepo,
        )
        profileScreen(
            onRepoClick = navController::navigateToRepo,
        )
    }
}
