package com.github.deskid.focusreader.screens.penti.yitu

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.github.deskid.focusreader.R
import com.github.deskid.focusreader.api.data.NetworkState
import com.github.deskid.focusreader.screens.ContentListFragment
import com.github.deskid.focusreader.utils.lazyFast
import com.github.deskid.focusreader.widget.refreshing
import javax.inject.Inject

class ZenImageFragment : ContentListFragment() {
    override fun getLayoutId(): Int = R.layout.fragment_zenimage_list

    var currentPage: Int = 1

    lateinit var adapter: ZenItemRecyclerViewAdapter

    @Inject
    lateinit var factory: ZenImageViewModel.ZenImageFactory

    private val viewModel: ZenImageViewModel by lazyFast {
        ViewModelProviders.of(this, factory).get(ZenImageViewModel::class.java)
    }

    override fun onViewCreated(root: View?, savedInstanceState: Bundle?) {
        adapter = ZenItemRecyclerViewAdapter(ArrayList())
        adapter.setOnClickListener { position, titleView,imageView, images ->
            ZenImageDetailAct.start(activity, position,titleView, imageView, images)
        }
        view.adapter = adapter

        viewModel.refreshState.observe(this, Observer {
            when (it) {
                NetworkState.LOADING -> swiper.refreshing = true
                NetworkState.LOADED -> swiper.refreshing = false
                else -> Toast.makeText(context, it?.msg, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.zenImages.observe(this, Observer {
            it?.let {
                adapter.addData(it)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data == null) {
            return
        }

        val selectedItem = data.getIntExtra("SELECTED_POSITION", 0)
        view.scrollToPosition(selectedItem)
    }

    companion object {
        fun newInstance(): ZenImageFragment {
            return ZenImageFragment()
        }
    }

    override fun load() {
        viewModel.load()
    }

    override fun loadMore() {
        viewModel.load(++currentPage)
    }

    override fun getItemOffset(): Int = 0
}
