package com.example.githubusersubmission.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubusersubmission.ItemsItem
import com.example.githubusersubmission.data.GithubUser
import com.example.githubusersubmission.data.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel: ViewModel() {
    private val _githubUsers = MutableLiveData<List<GithubUser>>()
    val githubUser: LiveData<List<GithubUser>> = _githubUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getFollowing(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val listUsers = ArrayList<GithubUser>()
                    for(item in responseBody!!){
                        val githubUser = GithubUser(item.login,null,
                            item.avatarUrl, item.organizationsUrl,null,null,null,null)
                        listUsers.add(githubUser)
                    }
                    _githubUsers.value = listUsers
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getFollowers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val listUsers = ArrayList<GithubUser>()
                    for(item in responseBody!!){
                        val githubUser = GithubUser(item.login,null,
                            item.avatarUrl, item.organizationsUrl,null,null,null,null)
                        listUsers.add(githubUser)
                    }
                    _githubUsers.value = listUsers
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    companion object{
        private const val TAG = "FollowingViewModel"
    }
}