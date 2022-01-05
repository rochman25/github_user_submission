package com.example.githubusersubmission.ui

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubusersubmission.R
import com.example.githubusersubmission.data.GithubUser
import com.example.githubusersubmission.database.FavoriteUser
import com.example.githubusersubmission.databinding.ActivityDetailBinding
import com.example.githubusersubmission.helper.ViewModelFactory
import com.example.githubusersubmission.ui.adapter.SectionPagerAdapter
import com.example.githubusersubmission.ui.viewmodel.DetailViewModel
import com.example.githubusersubmission.ui.viewmodel.FavoriteUserAddDeleteViewModel
import com.example.githubusersubmission.utils.MainViewModel
import com.example.githubusersubmission.utils.SettingPreference
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    private lateinit var favoriteUserAddDeleteViewModel: FavoriteUserAddDeleteViewModel
    private var favoriteUser: FavoriteUser? = null
    private var isDelete = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar?.title = getString(R.string.detail_user)

        val user = intent.getParcelableExtra<GithubUser>(EXTRA_USER) as GithubUser

        val sectionsPagerAdapter = SectionPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[DetailViewModel::class.java]

        detailViewModel.githubUser.observe(this, { githubUser ->
            setDetail(githubUser)
            favoriteUserAddDeleteViewModel.isExist(githubUser.username.toString())
                .observe(this, { favoriteUserList ->
                    isDelete = favoriteUserList != null
                    if (favoriteUserList != null) {
                        favoriteUser = FavoriteUser(favoriteUserList.id, favoriteUserList.username, favoriteUserList.name, favoriteUserList.avatar)
                        binding.favBtn.imageTintList = ColorStateList.valueOf(Color.GREEN)
                    }else{
                        favoriteUser =
                            FavoriteUser(0, githubUser.username, githubUser.name, githubUser.avatar)
                    }
                })
        })

        detailViewModel.isLoading.observe(this, {
            showLoading(it)
        })

        user.username?.let { detailViewModel.getUser(it) }

        val pref = SettingPreference.getInstance(dataStore)
        val mainViewModel =
            ViewModelProvider(this, ViewModelFactory(application, pref))[MainViewModel::class.java]

        mainViewModel.getThemeSettings().observe(this,
            { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        )

        favoriteUserAddDeleteViewModel = obtainViewModel(this)

        binding.favBtn.setOnClickListener {
            if(isDelete){
                favoriteUserAddDeleteViewModel.delete(favoriteUser as FavoriteUser)
                showToast(getString(R.string.remove_from_fav))
                binding.favBtn.imageTintList = ColorStateList.valueOf(Color.WHITE)
            }else{
                favoriteUserAddDeleteViewModel.insert(favoriteUser as FavoriteUser)
                showToast(getString(R.string.added_to_fav))
                binding.favBtn.imageTintList = ColorStateList.valueOf(Color.GREEN)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        menu?.findItem(R.id.search)?.isVisible = false
        menu?.findItem(R.id.favorites)?.isVisible = false
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val pref = SettingPreference.getInstance(dataStore)
        val mainViewModel =
            ViewModelProvider(this, ViewModelFactory(application, pref))[MainViewModel::class.java]
        when (item.itemId) {
            R.id.light_mode -> mainViewModel.saveThemeSetting(false)
            R.id.dark_mode -> mainViewModel.saveThemeSetting(true)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setDetail(githubUser: GithubUser) {
        binding.apply {
            txtUsername.text = githubUser.username
            txtName.text = githubUser.name
            txtFollower.text = githubUser.follower.toString()
            txtFollowing.text = githubUser.following.toString()
            txtRepository.text = githubUser.repository.toString()
            txtLocation.text = githubUser.location.toString()
            txtCompany.text = githubUser.company.toString()
            Glide.with(applicationContext).load(githubUser.avatar).circleCrop().into(imgPhoto)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteUserAddDeleteViewModel {
        val pref = SettingPreference.getInstance(dataStore)
        val factory = ViewModelFactory.getInstance(activity.application, pref)
        return ViewModelProvider(activity, factory)[FavoriteUserAddDeleteViewModel::class.java]
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
    }
}