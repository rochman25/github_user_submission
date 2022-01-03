package com.example.githubusersubmission.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubUser(
    var username: String?,
    var name: String?,
    var avatar: String?,
    val company: String?,
    val location: String?,
    val repository: Int?,
    val follower: Int?,
    val following: Int?
) : Parcelable
