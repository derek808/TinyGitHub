package com.derek.tinygithub.ui.explore

import android.content.Context
import android.net.Uri
import androidx.annotation.ColorInt
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.derek.tinygithub.navigation.RepoListItem
import com.derek.tinygithub.ui.RepoUiState
import kotlinx.coroutines.launch
import androidx.core.net.toUri

/**
 *
 * @author: derekzchen@2025/4/25
 */
@Composable
internal fun ExploreScreen(
    onRepoClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    exploreViewModel: ExploreViewModel = hiltViewModel(),
) {
    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        TabHost(modifier = modifier.padding(innerPadding), exploreViewModel)
    }
}

@Composable
fun TabHost(
    modifier: Modifier = Modifier,
    exploreViewModel: ExploreViewModel
) {
    val tabData = listOf("Popular", "Trending")
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }

    val page1State = rememberLazyListState()
    val page2State = rememberLazyListState()

    val uiState by exploreViewModel.repoStateFlow.collectAsStateWithLifecycle()

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
        ) {
            tabData.forEachIndexed { index, title ->
                Tab(
                    text = {
                        Text(
                            text = title,
                            fontWeight = if (index == selectedTabIndex) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 14.sp
                        )
                    },
                    selected = index == selectedTabIndex,
                    onClick = { selectedTabIndex = index }
                )
            }
        }

        // 根据选中的 Tab 显示对应的页面
        when (selectedTabIndex) {
            0 -> PopularTabPage(page1State, uiState) {
                exploreViewModel.fetchPopularRepos()
            }

            1 -> TrendingTabPage()
        }
    }
}

@Composable
fun PopularTabPage(
    scrollableState: LazyListState,
    repoUiState: RepoUiState,
    onRefresh: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PullRefreshComponent(scrollableState, repoUiState, onRefresh)
    }
}

@Composable
fun TrendingTabPage(modifier: Modifier = Modifier) {
    Column(
//        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "This is the content of Tab 2", fontSize = 24.sp)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PullRefreshComponent(
    scrollableState: LazyListState,
    repoUiState: RepoUiState,
    onRefresh: () -> Unit,
) {
    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }

    fun refresh() =
        refreshScope.launch {
            if (repoUiState is RepoUiState.Success) {
                return@launch
            }
            refreshing = true
            onRefresh()
        }

    val state = rememberPullRefreshState(refreshing, ::refresh)
    val context = LocalContext.current
    val toolbarColor = MaterialTheme.colorScheme.background.toArgb()

    Box(Modifier.pullRefresh(state)) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = scrollableState,
        ) {
            when (repoUiState) {
                is RepoUiState.Loading -> {
                    refreshing = true
                }

                is RepoUiState.Error -> {
                    refreshing = false
                    item {
                        Text(
                            text = "Error: ${repoUiState.message}",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                is RepoUiState.Success -> {
                    refreshing = false
                    repoUiState.repos.forEach {
                        item {
                            RepoListItem(
                                name = it.fullName,
                                description = it.description ?: "",
                                avatarUrl = it.owner?.avatarUrl ?: "",
                                stars = it.starsCount,
                                forks = it.forksCount,
                            ) {
                                launchCustomChromeTab(
                                    context = context,
                                    uri = it.htmlUrl.toUri(),
                                    toolbarColor = toolbarColor
                                )
                            }
                        }
                    }
                }
            }
        }

        PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter))
    }
}

fun launchCustomChromeTab(context: Context, uri: Uri, @ColorInt toolbarColor: Int) {
    val customTabBarColor = CustomTabColorSchemeParams.Builder()
        .setToolbarColor(toolbarColor).build()
    val customTabsIntent = CustomTabsIntent.Builder()
        .setDefaultColorSchemeParams(customTabBarColor)
        .build()

    customTabsIntent.launchUrl(context, uri)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TabHost(exploreViewModel = ExploreViewModel())
}
