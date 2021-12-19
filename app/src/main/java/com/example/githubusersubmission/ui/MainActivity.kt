package com.example.githubusersubmission.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusersubmission.*
import com.example.githubusersubmission.data.GithubUser
import com.example.githubusersubmission.databinding.ActivityMainBinding
import com.example.githubusersubmission.ui.adapter.ListUserAdapter
import com.example.githubusersubmission.ui.view.EmptyDataObserver
import com.example.githubusersubmission.ui.viewmodel.UsersViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userViewModel: UsersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar?.title = "Github User's"

        binding.rvUsers.setHasFixedSize(true)

        binding.rvUsers.layoutManager = LinearLayoutManager(this)

        userViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            UsersViewModel::class.java)
        userViewModel.githubUser.observe(this, { githubUser ->
            setUsersList(githubUser)
        })

        userViewModel.isLoading.observe(this, {
            showLoading(it)
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                userViewModel.searchUser(query)
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    private fun setUsersList(listGithubUsers: List<GithubUser>){
        val listUserAdapter = ListUserAdapter(listGithubUsers)
        binding.rvUsers.adapter = listUserAdapter
        listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: GithubUser) {
                showSelectedUser(data)
            }
        })

        val emptyDataObserver = EmptyDataObserver(binding.rvUsers, findViewById(R.id.empty_data_parent))
        listUserAdapter.registerAdapterDataObserver(emptyDataObserver)
    }

    private fun showSelectedUser(user: GithubUser){
        val detailActivityIntent = Intent(this@MainActivity, DetailActivity::class.java)
        detailActivityIntent.putExtra(DetailActivity.EXTRA_USER,user)
        startActivity(detailActivityIntent)
    }

    private fun showLoading(isLoading: Boolean) {
        if(isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}