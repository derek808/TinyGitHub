package com.derek.tinygithub.ui.explore

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.derek.tinygithub.navigation.RepoListItem
import kotlinx.coroutines.launch

/**
 *
 * @author: derekzchen@2025/4/25
 */
@Composable
internal fun ExploreScreen(
    onRepoClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val exploreViewModel = ExploreViewModel()
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
            0 -> PopularTabPage(modifier, page1State, exploreViewModel)
            1 -> TrendingTabPage()
        }
    }
}

@Composable
fun PopularTabPage(
    modifier: Modifier = Modifier,
    scrollableState: LazyListState,
    exploreViewModel: ExploreViewModel
) {
    Column(
//        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PullRefreshComponent(modifier, scrollableState, exploreViewModel)
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
    modifier: Modifier = Modifier,
    scrollableState: LazyListState,
    exploreViewModel: ExploreViewModel,
) {
    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }
    var itemCount by remember { mutableStateOf(15) }

    fun refresh() =
        refreshScope.launch {
            refreshing = true
            /*delay(1500)
            itemCount += 5*/
            exploreViewModel.fetchPopularRepos()
            refreshing = false
        }

    val state = rememberPullRefreshState(refreshing, ::refresh)
//    val scrollableState = rememberLazyListState()

    Box(Modifier.pullRefresh(state)) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = scrollableState,
        ) {
            if (!refreshing) {
                /*exploreViewModel.popularRepos.forEach {
                    item {
                        RepoListItem(
                            name = it.full_name,
                            description = it.description ?: "",
                            avatarUrl = "",
                            forks = 10,
                        )
                    }
                }*/
                items(itemCount) {
                    RepoListItem(
                        name = "Item $it",
                        description = "Description",
                        avatarUrl = "",
                        forks = 10,
                    )
                }
            }
        }

        PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter))
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TabHost(exploreViewModel = ExploreViewModel())
}
