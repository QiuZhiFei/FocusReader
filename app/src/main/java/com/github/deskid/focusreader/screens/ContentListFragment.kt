package com.github.deskid.focusreader.screens

import android.arch.lifecycle.LifecycleFragment
import android.graphics.Rect
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.deskid.focusreader.R
import com.github.deskid.focusreader.utils.dp2Px
import com.github.deskid.focusreader.widget.ScrollableRecyclerView

abstract class ContentListFragment : LifecycleFragment() {

    abstract fun getLayoutId(): Int

    protected lateinit var view: ScrollableRecyclerView
    protected lateinit var swiper: SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val content = inflater.inflate(getLayoutId(), container, false)
        swiper = content.findViewById(R.id.swiper) as SwipeRefreshLayout
        view = swiper.findViewById(R.id.list) as ScrollableRecyclerView
        view.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect?.set(0, 0, 0, getItemOffset())
            }
        })

        view.layoutManager = LinearLayoutManager(context)

        view.loadMoreListener = { loadMore() }
        swiper.setOnRefreshListener { load() }

        return content
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        load()
    }

    abstract fun load(page: Int = 1)

    abstract fun loadMore()

    protected open fun getItemOffset(): Int = context.dp2Px(10)
}