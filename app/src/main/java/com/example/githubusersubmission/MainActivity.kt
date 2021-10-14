package com.example.githubusersubmission

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubusersubmission.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val listGithubUser = ArrayList<GithubUser>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar!!.title = "Github User's"

        binding.rvUsers.setHasFixedSize(true)

        listGithubUser.addAll(listGithubUsers)
        showRecyclerList()

    }

    private val listGithubUsers: ArrayList<GithubUser>
        get() {
            val dataUsername = resources.getStringArray(R.array.username)
            val dataName = resources.getStringArray(R.array.name)
            val dataLocation = resources.getStringArray(R.array.location)
            val dataRepository = resources.getIntArray(R.array.repository)
            val dataCompany = resources.getStringArray(R.array.company)
            val dataFollower = resources.getIntArray(R.array.followers)
            val dataFollowing = resources.getIntArray(R.array.following)
            val dataPhoto = resources.obtainTypedArray(R.array.avatar)
            val listUser = ArrayList<GithubUser>()
            for(i in dataName.indices) {
                val user = GithubUser(dataUsername[i], dataName[i], dataPhoto.getResourceId(i, -1), dataCompany[i],dataLocation[i], dataRepository[i], dataFollower[i], dataFollowing[i])
                listUser.add(user)
            }
            dataPhoto.recycle()
            return listUser
        }

    private fun showRecyclerList() {
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        val listUserAdapter = ListUserAdapter(listGithubUser)
        binding.rvUsers.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: GithubUser) {
                showSelectedUser(data)
            }

        })
    }

    private fun showSelectedUser(user: GithubUser){
        val githubUser = GithubUser(
            user.username,
            user.name,
            user.avatar,
            user.company,
            user.location,
            user.repository,
            user.follower,
            user.following
        )

        val detailActivityIntent = Intent(this@MainActivity, DetailActivity::class.java)
        detailActivityIntent.putExtra(DetailActivity.EXTRA_USER,githubUser)
        startActivity(detailActivityIntent)
    }
}