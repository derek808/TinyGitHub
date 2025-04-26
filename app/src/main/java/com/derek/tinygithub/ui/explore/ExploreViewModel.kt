package com.derek.tinygithub.ui.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.derek.tinygithub.data.RepoRepository
import com.derek.tinygithub.data.RepoRepositoryImpl
import com.derek.tinygithub.ui.RepoUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ExploreViewModel @Inject constructor() : ViewModel() {

    private val repository: RepoRepository = RepoRepositoryImpl()

    private val _repoStateFlow: MutableStateFlow<RepoUiState> = MutableStateFlow(RepoUiState.Loading)
    val repoStateFlow: StateFlow<RepoUiState> = _repoStateFlow

    init {
        fetchPopularRepos()
    }

    fun fetchPopularRepos() {
        viewModelScope.launch {
            val flow = repository.fetchPopularReposFlow()
            flow.collect {
                _repoStateFlow.emit(it)
            }
        }
    }
}