package com.example.githubusersubmission.data.network

import com.example.githubusersubmission.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    @Headers("Authorization:token ${BuildConfig.PERSONAL_ACCESS_TOKEN}")
    fun searchUser(
        @Query("q") username: String
    ): Call<ResponseGithubUsers>

    @GET("users/{username}")
    fun detailUser(
        @Path("username") username: String
    ): Call<ResponseDetailUser>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ):Call<ResponseListFollowers>

    @GET("users/{username}/following")
    fun getFollowing(   
        @Path("username") username: String
    ):Call<ResponseListFollowing>

}