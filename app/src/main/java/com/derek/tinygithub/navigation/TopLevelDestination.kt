package com.derek.tinygithub.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.derek.tinygithub.R
import com.derek.tinygithub.ui.icon.TgIcons
import kotlin.reflect.KClass

/**
 * Type for the top level destinations in the application. Contains metadata about the destination
 * that is used in the top app bar and common navigation UI.
 *
 * @param selectedIcon The icon to be displayed in the navigation UI when this destination is
 * selected.
 * @param unselectedIcon The icon to be displayed in the navigation UI when this destination is
 * not selected.
 * @param iconTextId Text that to be displayed in the navigation UI.
 * @param titleTextId Text that is displayed on the top app bar.
 * @param route The route to use when navigating to this destination.
 * @param baseRoute The highest ancestor of this destination. Defaults to [route], meaning that
 * there is a single destination in that section of the app (no nested destinations).
 */
enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    @StringRes val iconTextId: Int,
    @StringRes val titleTextId: Int,
    val route: KClass<*>,
    val baseRoute: KClass<*> = route,
) {
    EXPLORE(
        selectedIcon = TgIcons.Explore,
        unselectedIcon = TgIcons.ExploreBorder,
        iconTextId = R.string.tab_explore_title,
        titleTextId = R.string.app_name,
        route = ExploreRoute::class,
        baseRoute = ExploreBaseRoute::class,
    ),
    SEARCH(
        selectedIcon = TgIcons.Search,
        unselectedIcon = TgIcons.SearchBorder,
        iconTextId = R.string.tab_search_title,
        titleTextId = R.string.tab_search_title,
        route = SearchRoute::class,
    ),
    PROFILE(
        selectedIcon = TgIcons.Profile,
        unselectedIcon = TgIcons.ProfileBorder,
        iconTextId = R.string.tab_profile_title,
        titleTextId = R.string.tab_profile_title,
        route = ProfileRoute::class,
    ),
}
