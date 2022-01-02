package com.example.githubusersubmission.helper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubusersubmission.utils.MainViewModel
import com.example.githubusersubmission.utils.SettingPreference
import java.lang.IllegalArgumentException

class ViewModelFactory(private val pref: SettingPreference) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class: "+modelClass.name)
    }

}