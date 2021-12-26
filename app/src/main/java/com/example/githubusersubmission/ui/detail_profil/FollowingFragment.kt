package com.example.githubusersubmission.ui.detail_profil

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusersubmission.R
import com.example.githubusersubmission.data.GithubUser
import com.example.githubusersubmission.databinding.FragmentFollowingBinding
import com.example.githubusersubmission.ui.DetailActivity
import com.example.githubusersubmission.ui.adapter.ListUserAdapter
import com.example.githubusersubmission.ui.view.EmptyDataObserver
import com.example.githubusersubmission.ui.viewmodel.FollowViewModel


class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var followViewModel: FollowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvFollowing.setHasFixedSize(true)

        binding.rvFollowing.layoutManager = LinearLayoutManager(context)

        followViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowViewModel::class.java)
        followViewModel.githubUser.observe(viewLifecycleOwner, { githubUser ->
            setFollowingList(githubUser)
        })

        followViewModel.isLoading.observe(viewLifecycleOwner, {
            showLoading(it)
        })
        val user = activity?.intent?.getParcelableExtra<GithubUser>(DetailActivity.EXTRA_USER) as GithubUser
        user.username?.let { followViewModel.getFollowing(it) }
    }

    private fun setFollowingList(listGithubUsers: List<GithubUser>){
        val listUserAdapter = ListUserAdapter(listGithubUsers)
        binding.rvFollowing.adapter = listUserAdapter


        val emptyDataObserver = view?.let { EmptyDataObserver(binding.rvFollowing, it.findViewById(R.id.empty_data_parent)) }
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