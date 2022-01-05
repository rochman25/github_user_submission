package com.example.githubusersubmission.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubusersubmission.ui.viewmodel.FavoriteUserAddDeleteViewModel
import com.example.githubusersubmission.ui.viewmodel.FavoriteUserViewModel
import com.example.githubusersubmission.utils.MainViewModel
import com.example.githubusersubmission.utils.SettingPreference
import java.lang.IllegalArgumentException

class ViewModelFactory(private val mApplication: Application, private val pref: SettingPreference) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application, pref: SettingPreference): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application, pref)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(pref) as T
            }
            modelClass.isAssignableFrom(FavoriteUserViewModel::class.java) -> {
                FavoriteUserViewModel(mApplication) as T
            }
            modelClass.isAssignableFrom(FavoriteUserAddDeleteViewModel::class.java) -> {
                FavoriteUserAddDeleteViewModel(mApplication) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel Class: "+modelClass.name)
        }
    }

}