package com.example.githubusersubmission.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubusersubmission.database.FavoriteUser
import com.example.githubusersubmission.database.FavoriteUserRoomDatabase
import com.example.githubusersubmission.database.UserFavoriteDao
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
    private val mFavoriteDao: UserFavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserRoomDatabase.getDatabase(application)
        mFavoriteDao = db.userFavoriteDao()
    }

    fun getAllFavorites(): LiveData<List<FavoriteUser>> = mFavoriteDao.getAllFavoriteUsers()

    fun insert(favoriteUser: FavoriteUser) {
        executorService.execute {
            mFavoriteDao.insert(favoriteUser)
        }
    }

    fun delete(favoriteUser: FavoriteUser) {
        executorService.execute {
            mFavoriteDao.delete(favoriteUser)
        }
    }

    fun isExist() {
        executorService.execute {
            mFavoriteDao.isExists()
        }
    }

}