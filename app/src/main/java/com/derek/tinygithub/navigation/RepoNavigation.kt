/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.derek.tinygithub.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.derek.tinygithub.ui.RepoScreen
import kotlinx.serialization.Serializable

@Serializable data class RepoRoute(val id: String)

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
