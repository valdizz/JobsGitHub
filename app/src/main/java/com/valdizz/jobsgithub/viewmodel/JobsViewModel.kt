package com.valdizz.jobsgithub.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.valdizz.jobsgithub.R
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
    private val error: MutableLiveData<String> = MutableLiveData()
    private val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    private val connManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val request = NetworkRequest.Builder()
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()
    private var isConnected = false

    init {
        connManager.registerNetworkCallback(request, object : ConnectivityManager.NetworkCallback(){
            override fun onAvailable(network: Network?) {
                isConnected = true
            }

            override fun onUnavailable() {
                isConnected = false
            }
        })
    }

    fun getJobs(description: String, location: String): LiveData<MutableList<Job>> {
        if (isConnected) {
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

    fun getError(): LiveData<String> {
        return error
    }

    fun isLoading(): LiveData<Boolean> {
        return isLoading
    }
}