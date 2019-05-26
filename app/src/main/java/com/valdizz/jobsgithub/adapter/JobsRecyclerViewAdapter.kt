package com.valdizz.jobsgithub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.valdizz.jobsgithub.R
import com.valdizz.jobsgithub.model.Job
import kotlinx.android.synthetic.main.item_jobs.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Adapter class for displaying data in [RecyclerView].
 *
 * @author Vlad Kornev
 */
class JobsRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var jobsList: MutableList<Job> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_jobs, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bindData(jobsList[position])
    }

    override fun getItemCount(): Int {
        return jobsList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        companion object {
            const val pattern = "dd.MM.yyyy HH:mm:ss"
        }

        private var simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())

        fun bindData(item: Job) {
            itemView.tv_company.text = item.company
            itemView.tv_location.text = item.location
            itemView.tv_vacancy.text = item.vacancy
            itemView.tv_created.text = simpleDateFormat.format(item.created)
        }
    }
}