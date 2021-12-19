package com.example.githubusersubmission.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.githubusersubmission.data.GithubUser
import com.example.githubusersubmission.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar?.title = "Detail User"

        val user = intent.getParcelableExtra<GithubUser>(EXTRA_USER) as GithubUser
        binding.apply {
            txtName.text = user.name
            txtUsername.text = user.username
            Glide.with(applicationContext).load(user.avatar).circleCrop().into(imgPhoto)
            txtFollower.text = user.follower.toString()
            txtFollowing.text = user.follower.toString()
            txtRepository.text = user.follower.toString()
            txtLocation.text = user.location
            txtCompany.text = user.company
        }

    }


    companion object {
        const val EXTRA_USER = "extra_user"
    }
}