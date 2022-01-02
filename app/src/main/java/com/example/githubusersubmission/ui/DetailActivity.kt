package com.example.githubusersubmission.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toolbar
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubusersubmission.R
import com.example.githubusersubmission.data.GithubUser
import com.example.githubusersubmission.databinding.ActivityDetailBinding
import com.example.githubusersubmission.ui.adapter.ListUserAdapter
import com.example.githubusersubmission.ui.adapter.SectionPagerAdapter
import com.example.githubusersubmission.ui.view.EmptyDataObserver
import com.example.githubusersubmission.ui.viewmodel.DetailViewModel
import com.example.githubusersubmission.ui.viewmodel.UsersViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel

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

        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            DetailViewModel::class.java)
        detailViewModel.githubUser.observe(this, { githubUser ->
            setDetail(githubUser)
        })

        detailViewModel.isLoading.observe(this, {
            showLoading(it)
        })

        user.username?.let { detailViewModel.getUser(it) }

    }

    private fun setDetail(githubUser: GithubUser){
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
        if(isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
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