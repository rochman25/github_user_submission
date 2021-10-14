package com.example.githubusersubmission

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubusersubmission.databinding.ItemRowUserBinding

class ListUserAdapter(private val listUser: ArrayList<GithubUser>) : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (dataName, dataFollower, dataAvatar) = listUser[position]
        holder.binding.tvItemName.text = dataName
        holder.binding.tvItemFollower.text = dataFollower
        if (dataAvatar != null) {
            holder.binding.imgItemPhoto.setImageResource(dataAvatar)
        }
    }

    override fun getItemCount(): Int = listUser.size

    class ListViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {}
}