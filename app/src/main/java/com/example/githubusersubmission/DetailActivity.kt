package com.example.githubusersubmission

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.githubusersubmission.databinding.ActivityDetailBinding
import com.example.githubusersubmission.databinding.ActivityMainBinding

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "extra_user"
    }
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar!!.title = "Detail User"

        val user = intent.getParcelableExtra<GithubUser>(EXTRA_USER) as GithubUser
        binding.txtName.text = user.name
        binding.txtUsername.text = user.username
        binding.imgPhoto.setImageResource(user.avatar)
        binding.txtFollower.text = user.follower.toString()
        binding.txtFollowing.text = user.follower.toString()
        binding.txtRepository.text = user.follower.toString()
        binding.txtLocation.text = user.location
        binding.txtCompany.text = user.company
    }
}