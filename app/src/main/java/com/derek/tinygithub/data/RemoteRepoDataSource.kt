package com.derek.tinygithub.data

import com.derek.tinygithub.network.RetrofitTgNetwork
import com.derek.tinygithub.network.model.RepoDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object RemoteRepoDataSource {

    private val api = RetrofitTgNetwork()

    suspend fun fetchPopularRepos(): List<RepoDetail> {
        return withContext(Dispatchers.IO) {
            api.searchRepositories().items
        }
    }

}