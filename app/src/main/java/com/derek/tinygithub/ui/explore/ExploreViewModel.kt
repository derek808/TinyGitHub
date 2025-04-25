package com.derek.tinygithub.ui.explore

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.derek.tinygithub.data.RepoDataSource
import com.derek.tinygithub.network.model.RepoDetail
import kotlinx.coroutines.launch

class ExploreViewModel : ViewModel() {

    private val dataSource = RepoDataSource

    private val _popularRepos = mutableStateOf<List<RepoDetail>>(emptyList())
    val popularRepos: List<RepoDetail> = _popularRepos.value

    fun fetchPopularRepos() {
        viewModelScope.launch {
            _popularRepos.value = dataSource.fetchPopularRepos()
        }
    }
}