package com.example.githubusersubmission.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.githubusersubmission.database.FavoriteUser

class FavoriteUserDiffCallback(private val mOldFavoriteUser: List<FavoriteUser>, private val mNewFavoriteUser: List<FavoriteUser>): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldFavoriteUser.size
    }

    override fun getNewListSize(): Int {
        return mNewFavoriteUser.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldFavoriteUser[oldItemPosition].id == mNewFavoriteUser[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFavoriteUser = mOldFavoriteUser[oldItemPosition]
        val newFavoriteUser = mNewFavoriteUser[newItemPosition]
        return oldFavoriteUser.username == newFavoriteUser.username && oldFavoriteUser.name == newFavoriteUser.name
    }
}