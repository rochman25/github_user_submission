package com.example.githubusersubmission

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class EmptyDataObserver(rv: RecyclerView?, ev: View): RecyclerView.AdapterDataObserver() {
    private var emptyView: View? = null
    private var recyclerView: RecyclerView? = null

    init {
        recyclerView = rv
        emptyView = ev
        checkIfEmpty()
    }


    private fun checkIfEmpty() {
        if (emptyView != null && recyclerView!!.adapter != null) {
            val emptyViewVisible = recyclerView!!.adapter!!.itemCount == 0
            emptyView!!.visibility = if (emptyViewVisible) View.VISIBLE else View.GONE
            recyclerView!!.visibility = if (emptyViewVisible) View.GONE else View.VISIBLE
        }
    }

    override fun onChanged() {
        super.onChanged()
        checkIfEmpty()
    }

}