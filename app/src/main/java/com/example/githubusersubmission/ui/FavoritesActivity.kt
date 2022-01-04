package com.example.githubusersubmission.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusersubmission.R
import com.example.githubusersubmission.databinding.ActivityFavoritesBinding
import com.example.githubusersubmission.helper.ViewModelFactory
import com.example.githubusersubmission.ui.adapter.FavoriteUserAdapter
import com.example.githubusersubmission.ui.view.EmptyDataObserver
import com.example.githubusersubmission.ui.viewmodel.FavoriteUserViewModel
import com.example.githubusersubmission.utils.SettingPreference

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class FavoritesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritesBinding
    private lateinit var adapter: FavoriteUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar?.title = getString(R.string.favorite_users)

        val favoriteUserViewModel = obtainViewModel(this)
        favoriteUserViewModel.getAllFavoriteUsers().observe(this, {
            favoriteList ->
                if(favoriteList != null) {
                    adapter.setListFavoriteUsers(favoriteList)
                }
        })

        adapter = FavoriteUserAdapter()

        binding.rvFavorites.layoutManager = LinearLayoutManager(this)
        binding.rvFavorites.setHasFixedSize(true)
        binding.rvFavorites.adapter = adapter

        val emptyDataObserver =
            EmptyDataObserver(binding.rvFavorites, findViewById(R.id.empty_data_parent))
        adapter.registerAdapterDataObserver(emptyDataObserver)
    }

    private fun obtainViewModel(activity: AppCompatActivity) : FavoriteUserViewModel {
        val pref = SettingPreference.getInstance(dataStore)
        val factory = ViewModelFactory.getInstance(activity.application, pref)
        return ViewModelProvider(activity, factory)[FavoriteUserViewModel::class.java]
    }
}