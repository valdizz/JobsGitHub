package com.valdizz.jobsgithub.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.valdizz.jobsgithub.R
import com.valdizz.jobsgithub.common.NetConnectionManager
import com.valdizz.jobsgithub.model.Job
import com.valdizz.jobsgithub.model.SearchRepositoryProvider


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * ViewModel class implements the logic of loading jobs from GitHub.
 *
 * @author Vlad Kornev
 */
class JobsViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application
    private val jobs: MutableLiveData<MutableList<Job>> = MutableLiveData()
    val error: MutableLiveData<String> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    private val connectionManager = NetConnectionManager(context)

    fun getJobs(description: String, location: String): LiveData<MutableList<Job>> {
        if (connectionManager.isConnected) {
            loadJobs(description, location)
        }
        else {
            error.postValue(context.getString(R.string.msg_connection_unavailable))
        }
        return jobs
    }

    private fun loadJobs(description: String, location: String) {
        isLoading.postValue(true)
        val repository = SearchRepositoryProvider.provideSearchRepository()
        repository.searchJobs(description, location).enqueue(object: Callback<MutableList<Job>?> {
            override fun onFailure(call: Call<MutableList<Job>?>, t: Throwable) {
                error.postValue(t.localizedMessage)
                isLoading.postValue(false)
            }
            override fun onResponse(call: Call<MutableList<Job>?>, response: Response<MutableList<Job>?>) {
                if (response.isSuccessful) {
                    val jobsList = response.body()
                    jobs.postValue(jobsList)
                }
                else {
                    error.postValue("Response code: ${response.code()}")
                }
                isLoading.postValue(false)
            }
        })
    }
}