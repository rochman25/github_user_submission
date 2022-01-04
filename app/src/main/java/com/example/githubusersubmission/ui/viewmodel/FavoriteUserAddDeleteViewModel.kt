package com.example.githubusersubmission.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubusersubmission.database.FavoriteUser
import com.example.githubusersubmission.repository.FavoriteUserRepository

class FavoriteUserAddDeleteViewModel(application: Application): ViewModel() {
    private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)


    fun insert(favoriteUser: FavoriteUser) {
        mFavoriteUserRepository.insert(favoriteUser)
    }

    fun isExist(username: String): LiveData<FavoriteUser> = mFavoriteUserRepository.isExist(username)

    fun delete(favoriteUser: FavoriteUser) {
        mFavoriteUserRepository.delete(favoriteUser)
    }

}