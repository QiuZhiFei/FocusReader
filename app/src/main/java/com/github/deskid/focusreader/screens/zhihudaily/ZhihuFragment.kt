package com.github.deskid.focusreader.screens.zhihudaily

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.github.deskid.focusreader.R
import com.github.deskid.focusreader.api.data.NetworkState
import com.github.deskid.focusreader.screens.ContentListFragment
import com.github.deskid.focusreader.utils.lazyFast
import com.github.deskid.focusreader.widget.refreshing
import javax.inject.Inject

class ZhihuFragment : ContentListFragment() {

    private lateinit var adapter: ZhihuAdapter

    private lateinit var date: String

    @Inject
    lateinit var factory: ZhihuViewModel.ZhihuFactory

    private val viewModel: ZhihuViewModel by lazyFast {
        ViewModelProviders.of(this, factory).get(ZhihuViewModel::class.java)
    }

    override fun getLayoutId(): Int = R.layout.fragment_zhihu_list

    override fun onViewCreated(root: View?, savedInstanceState: Bundle?) {
        adapter = ZhihuAdapter(activity, ArrayList())
        view.adapter = adapter

        viewModel.refreshState.observe(this, Observer {
            when (it) {
                NetworkState.LOADING -> swiper.refreshing = true
                NetworkState.LOADED -> swiper.refreshing = false
                else -> Toast.makeText(context, it?.msg, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.zhihuList.observe(this, Observer {
            it?.let {
                adapter.addData(it.stories)
                date = it.date
            }
        })
    }

    override fun load() {
        viewModel.load()
    }

    companion object {
        fun newInstance(): ZhihuFragment {
            return ZhihuFragment()
        }
    }

    override fun loadMore() {
        viewModel.loadMore(date)
    }
}