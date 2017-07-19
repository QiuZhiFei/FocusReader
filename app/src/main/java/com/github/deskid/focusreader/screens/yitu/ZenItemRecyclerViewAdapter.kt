package com.github.deskid.focusreader.screens.yitu

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.deskid.focusreader.R
import com.github.deskid.focusreader.activity.WebViewActivity
import com.github.deskid.focusreader.api.data.ZenImage
import com.github.deskid.focusreader.utils.ResUtils
import com.github.deskid.focusreader.utils.setWidth
import com.github.deskid.focusreader.widget.WebImageView

class ZenItemRecyclerViewAdapter(private val mValues: MutableList<ZenImage>) : RecyclerView.Adapter<ZenItemRecyclerViewAdapter.ViewHolder>() {

    fun addData(data: List<ZenImage>) {
        val index = mValues.size
        mValues.addAll(data)
        notifyItemRangeChanged(index, mValues.size)
    }

    fun swipeData(data: List<ZenImage>) {

        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return mValues[oldItemPosition].description == data[newItemPosition].description
            }

            override fun getOldListSize(): Int {
                return mValues.size
            }

            override fun getNewListSize(): Int {
                return data.size
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return mValues[oldItemPosition] == data[newItemPosition]
            }

        })
        diffResult.dispatchUpdatesTo(this)
        mValues.clear()
        mValues.addAll(data)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_zenitem, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.mTitleView.text = mValues[position].title
        holder.mWebImageView.setImageUrl(mValues[position].imgurl)
        holder.mWebImageView.setWidth(ResUtils.screenWidth())
        holder.itemView.setOnClickListener {
            WebViewActivity.start(holder.itemView.context, mValues[position].description, mValues[position].imgurl)
        }

    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val mTitleView: TextView = mView.findViewById(R.id.title) as TextView
        val mWebImageView: WebImageView = mView.findViewById(R.id.web_imageview) as WebImageView
    }
}
