package com.valdizz.jobsgithub.model

import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit interface for getting vacancies from GitHub.
 *
 * @author Vlad Kornev
 */
interface GithubApiService {

    @GET("positions.json")
    fun searchJobs(@Query("description") description: String, @Query("location") location: String): Call<MutableList<Job>>

    companion object Factory {
        fun create(): GithubApiService {
            val gson = GsonBuilder().setDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").create()
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("https://jobs.github.com/")
                .build()
            return retrofit.create(GithubApiService::class.java)
        }
    }
}