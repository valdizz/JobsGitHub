package com.valdizz.jobsgithub.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.valdizz.jobsgithub.R
import com.valdizz.jobsgithub.viewmodel.JobsViewModel

/**
 * [JobsActivity] has a fragment that loads jobs from GitHub.
 *
 * @author Vlad Kornev
 */
class JobsActivity : AppCompatActivity() {

    private var jobsViewModel: JobsViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jobs)

        jobsViewModel = ViewModelProviders.of(this).get(JobsViewModel::class.java)
        if (savedInstanceState == null) {
            createJobsFragment()
        }
    }

    private fun createJobsFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragment_container,
                JobsFragment.newInstance()
            )
            .commit()
    }
}
