package com.example.githubusersubmission.ui.adapter

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubusersubmission.R
import com.example.githubusersubmission.data.GithubUser
import com.example.githubusersubmission.database.FavoriteUser
import com.example.githubusersubmission.databinding.ItemRowUserBinding
import com.example.githubusersubmission.helper.FavoriteUserDiffCallback
import com.example.githubusersubmission.ui.DetailActivity

class FavoriteUserAdapter: RecyclerView.Adapter<FavoriteUserAdapter.FavoriteUserHolder>() {

    private val listFavoriteUser = ArrayList<FavoriteUser>()

    fun setListFavoriteUsers(listFavoriteUser: List<FavoriteUser>) {
        val diffCallback = FavoriteUserDiffCallback(this.listFavoriteUser, listFavoriteUser)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavoriteUser.clear()
        this.listFavoriteUser.addAll(listFavoriteUser)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteUserHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteUserHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteUserHolder, position: Int) {
        holder.bind(listFavoriteUser[position])
    }

    override fun getItemCount(): Int {
        return listFavoriteUser.size
    }

    inner class FavoriteUserHolder(private val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteUser: FavoriteUser) {
            with(binding) {
                tvItemName.text = favoriteUser.name
                tvItemFollower.text = favoriteUser.username
                Glide.with(itemView.context)
                    .load(favoriteUser.avatar)
                    .circleCrop()
                    .placeholder(R.drawable.ic_baseline_account_circle_24)
                    .error(ColorDrawable(Color.RED))
                    .into(imgItemPhoto)

                itemView.setOnClickListener {
                    val user = GithubUser(favoriteUser.username, favoriteUser.name, favoriteUser.avatar, null, null, null, null, null)
                    val detailActivityIntent = Intent(it.context, DetailActivity::class.java)
                    detailActivityIntent.putExtra(DetailActivity.EXTRA_USER, user)
                    it.context.startActivity(detailActivityIntent)
                }

            }
        }
    }

}