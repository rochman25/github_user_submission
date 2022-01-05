package com.example.githubusersubmission.ui.view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.githubusersubmission.R

class EmptyDataObserver(rv: RecyclerView, ev: View) : RecyclerView.AdapterDataObserver() {
    private var emptyView: View = ev
    private var recyclerView: RecyclerView = rv

    init {
        checkIfEmpty()
    }


    private fun checkIfEmpty() {
        if (recyclerView.adapter != null) {
            val emptyViewVisible = recyclerView.adapter!!.itemCount == 0
            emptyView.visibility = if (emptyViewVisible) View.VISIBLE else View.GONE
            recyclerView.visibility = if (emptyViewVisible) View.GONE else View.VISIBLE
            if(emptyViewVisible) {
                emptyView.findViewById<TextView>(R.id.title).setText(R.string.title_empty)
                emptyView.findViewById<TextView>(R.id.sub_title).setText(R.string.subtitle_empty)
            }
        }
    }

    override fun onChanged() {
        super.onChanged()
        checkIfEmpty()
    }

}