package com.valdizz.jobsgithub.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.valdizz.jobsgithub.R
import com.valdizz.jobsgithub.adapter.JobsRecyclerViewAdapter
import com.valdizz.jobsgithub.viewmodel.JobsViewModel
import kotlinx.android.synthetic.main.fragment_jobs.*

/**
 * [JobsFragment] loads a list with jobs from GitHub.
 *
 * @author Vlad Kornev
 */
class JobsFragment : Fragment() {

    companion object {
        fun newInstance() = JobsFragment()
    }

    private var jobsViewModel: JobsViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        jobsViewModel = activity?.run {
            ViewModelProviders.of(this).get(JobsViewModel::class.java)
        } ?: throw Exception("Invalid Activity!")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_jobs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = JobsRecyclerViewAdapter()
        rv_jobs.layoutManager = LinearLayoutManager(activity)
        rv_jobs.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        rv_jobs.adapter = adapter

        jobsViewModel?.getJobs("Java", "London")?.observe(this, Observer { list -> adapter.jobsList = list })
        jobsViewModel?.error?.observe(this, Observer { message -> showMessage(message) })
        jobsViewModel?.isLoading?.observe(this, Observer { isLoading -> progress_bar.isVisible = isLoading })
    }

    private fun showMessage(message: String) {
        Snackbar.make(rv_jobs, message, Snackbar.LENGTH_LONG).show()
    }
}