package com.derek.tinygithub.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.derek.tinygithub.ui.screen.SearchScreen
import kotlinx.serialization.Serializable

@Serializable
object SearchRoute

fun NavController.navigateToSearch(navOptions: NavOptions) =
    navigate(route = SearchRoute, navOptions)

fun NavGraphBuilder.searchScreen(
    onRepoClick: (String) -> Unit,
) {
    composable<SearchRoute> {
        SearchScreen(onRepoClick)
    }
}
