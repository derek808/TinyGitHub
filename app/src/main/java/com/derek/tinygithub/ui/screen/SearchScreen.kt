package com.derek.tinygithub.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.derek.tinygithub.navigation.RepoListItem
import com.derek.tinygithub.ui.component.SearchTextField

@Composable
internal fun SearchScreen(
    onRepoClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding)
        ) {
            SearchTextField(
                searchQuery = "",
                onSearchQueryChanged = {},
                onSearchTriggered = { }
            )

            SearchResult()
        }
    }
}

@Composable
private fun SearchResult(modifier: Modifier = Modifier) {
    val itemCount by remember { mutableIntStateOf(15) }
    val scrollableState = rememberLazyListState()

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        state = scrollableState,
    ) {
        items(itemCount) {
            RepoListItem(
                name = "Item $it",
                description = "Description",
                avatarUrl = "",
                stars = 100,
                forks = 10,
            )
        }
    }
}

@Preview
@Composable
fun SearchScreenPreview() {
    SearchScreen(
        onRepoClick = {}
    )
}
