package com.derek.tinygithub.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.derek.tinygithub.ui.screen.ProfileRoute
import kotlinx.serialization.Serializable

@Serializable
object ProfileRoute

fun NavController.navigateToProfile(navOptions: NavOptions) =
    navigate(route = ProfileRoute, navOptions)

fun NavGraphBuilder.profileScreen(
    onRepoClick: (String) -> Unit,
) {
    composable<ProfileRoute> {
        ProfileRoute(onRepoClick)
    }
}
