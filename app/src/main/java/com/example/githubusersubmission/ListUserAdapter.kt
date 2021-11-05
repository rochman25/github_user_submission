package com.example.githubusersubmission

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubusersubmission.databinding.ItemRowUserBinding

class ListUserAdapter(private val listUser: ArrayList<GithubUser>) : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

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
        Glide.with(holder.itemView.context).load(dataAvatar).circleCrop().into(holder.binding.imgItemPhoto)
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