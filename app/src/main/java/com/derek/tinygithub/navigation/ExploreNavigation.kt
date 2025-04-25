package com.derek.tinygithub.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.derek.tinygithub.ui.explore.ExploreScreen
import kotlinx.serialization.Serializable

@Serializable
data object ExploreRoute // route to Explore screen

@Serializable
data object ExploreBaseRoute // route to base navigation graph

fun NavController.navigateToExplore(navOptions: NavOptions) = navigate(route = ExploreRoute, navOptions)

/**
 *  The Explore section of the app.
 *
 *  @param onRepoClick - Called when a repo is clicked, contains the name of the repo
 *  @param repoDestination - Destination for repo content
 */
fun NavGraphBuilder.exploreSection(
    onRepoClick: (String) -> Unit,
    repoDestination: NavGraphBuilder.() -> Unit,
) {
    navigation<ExploreBaseRoute>(startDestination = ExploreRoute) {
        composable<ExploreRoute> {
            ExploreScreen(onRepoClick)
        }
        repoDestination()
    }
}
