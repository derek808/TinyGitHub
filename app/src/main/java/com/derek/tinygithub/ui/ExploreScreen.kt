package com.derek.tinygithub.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.derek.tinygithub.Greeting

/**
 *
 * @author: derekzchen@2025/4/25
 */
@Composable
internal fun ExploreScreen(
    onRepoClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        Greeting(
            name = "Explore",
            modifier = modifier.padding(innerPadding)
        )
    }
}
