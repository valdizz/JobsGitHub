package com.valdizz.jobsgithub.model

import retrofit2.Call

/**
 * GitHub search repository.
 *
 * @author Vlad Kornev
 */
class SearchRepository(private val apiService: GithubApiService) {
    fun searchJobs(description: String, location: String): Call<MutableList<Job>> {
        return apiService.searchJobs(description, location)
    }
}