package com.example.githubusersubmission.ui.detail_profil

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusersubmission.R
import com.example.githubusersubmission.data.GithubUser
import com.example.githubusersubmission.databinding.FragmentFollowerBinding
import com.example.githubusersubmission.ui.DetailActivity
import com.example.githubusersubmission.ui.adapter.ListUserAdapter
import com.example.githubusersubmission.ui.view.EmptyDataObserver
import com.example.githubusersubmission.ui.viewmodel.FollowViewModel

class FollowerFragment : Fragment() {

    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!
    private lateinit var followViewModel: FollowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvFollower.setHasFixedSize(true)

        binding.rvFollower.layoutManager = LinearLayoutManager(context)

        followViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowViewModel::class.java)
        followViewModel.githubUser.observe(viewLifecycleOwner, { githubUser ->
            setFollowerList(githubUser)
        })

        followViewModel.isLoading.observe(viewLifecycleOwner, {
            showLoading(it)
        })
        val user = activity?.intent?.getParcelableExtra<GithubUser>(DetailActivity.EXTRA_USER) as GithubUser
        user.username?.let { followViewModel.getFollowers(it) }
    }

    private fun setFollowerList(listGithubUsers: List<GithubUser>){
        val listUserAdapter = ListUserAdapter(listGithubUsers)
        binding.rvFollower.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: GithubUser) {

            }
        })

        val emptyDataObserver = view?.let { EmptyDataObserver(binding.rvFollower, it.findViewById(R.id.empty_data_parent)) }
        emptyDataObserver?.let { listUserAdapter.registerAdapterDataObserver(it) }
    }

    private fun showLoading(isLoading: Boolean) {
        if(isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}