package com.example.githubusersubmission

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersViewModel: ViewModel() {
    private val _githubUsers = MutableLiveData<List<GithubUser>>()
    val githubUser: LiveData<List<GithubUser>> = _githubUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun searchUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().searchUser(username)
        client.enqueue(object : Callback<ResponseGithubUsers>{
            override fun onResponse(
                call: Call<ResponseGithubUsers>,
                response: Response<ResponseGithubUsers>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val listUsers = ArrayList<GithubUser>()
                    for(item in responseBody?.items!!){
                        val githubUser = GithubUser(item?.login,null,item?.avatarUrl,item?.organizationsUrl,null,null,null,null)
                        listUsers.add(githubUser)
                    }
                    _githubUsers.value = listUsers
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseGithubUsers>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    companion object{
        private const val TAG = "UserViewModel"
    }
}