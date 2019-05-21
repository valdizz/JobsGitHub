package com.valdizz.jobsgithub.model

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Job data class.
 *
 * @author Vlad Kornev
 */
data class Job (
    val company: String,
    @SerializedName("title")
    val vacancy: String,
    val location: String,
    @SerializedName("created_at")
    val created: Date
)