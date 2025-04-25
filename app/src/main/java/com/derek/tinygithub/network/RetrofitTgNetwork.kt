package com.derek.tinygithub.network

import com.derek.tinygithub.network.model.SearchResult
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

private interface RetrofitNiaNetworkApi {

    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") query: String = "stars:>10000",
        @Query("sort") sort: String = "stars",
        @Query("order") order: String = "desc"
    ): SearchResult
}

private const val GITHUB_BASE_URL = "https://api.github.com"

class RetrofitTgNetwork {

    private val TIMEOUT = 30L

    private val networkJson = Json { ignoreUnknownKeys = true }

    private val httpClient = OkHttpClient.Builder().apply {
        addInterceptor {
            val original = it.request()

            val request = original.newBuilder()
//                .header("token", GlobalData.token)
//                .header("uid", GlobalData.uid)
                .method(original.method, original.body)
                .build()

            it.proceed(request)
        }
        callTimeout(TIMEOUT, TimeUnit.SECONDS)
        connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        readTimeout(TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(TIMEOUT, TimeUnit.SECONDS)
    }

    private val networkApi =
        Retrofit.Builder()
            .baseUrl(GITHUB_BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(
                networkJson.asConverterFactory("application/json".toMediaType()),
            )
            .build()
            .create(RetrofitNiaNetworkApi::class.java)

    suspend fun searchRepositories(): SearchResult {
        return networkApi.searchRepositories()
    }

}