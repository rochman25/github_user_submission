package com.example.githubusersubmission.ui.adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubusersubmission.R
import com.example.githubusersubmission.data.GithubUser
import com.example.githubusersubmission.database.FavoriteUser
import com.example.githubusersubmission.databinding.ItemRowUserBinding

class ListUserAdapter(private val listUser: List<GithubUser>) : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (dataUsername, dataName, dataAvatar) = listUser[position]
        holder.binding.tvItemName.text = dataName
        holder.binding.tvItemFollower.text = dataUsername
        Glide.with(holder.itemView.context)
                .load(dataAvatar)
                .circleCrop()
                .placeholder(R.drawable.ic_baseline_account_circle_24)
                .error(ColorDrawable(Color.RED))
                .into(holder.binding.imgItemPhoto)
        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(listUser[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = listUser.size

    class ListViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: GithubUser)
    }
}