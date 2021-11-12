package com.example.githubusersubmission

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubUser(
    val username: String?,
    val name: String?,
    val avatar: String?,
    val company: String?,
    val location: String?,
    val repository: Int?,
    val follower: Int?,
    val following: Int?
) : Parcelable
