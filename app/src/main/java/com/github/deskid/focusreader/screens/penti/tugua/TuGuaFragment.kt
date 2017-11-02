package com.github.deskid.focusreader.screens.penti.tugua

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import com.github.deskid.focusreader.R
import com.github.deskid.focusreader.api.data.TuGua
import com.github.deskid.focusreader.app.App
import com.github.deskid.focusreader.screens.ContentListFragment
import com.github.deskid.focusreader.utils.lazyFast
import com.github.deskid.focusreader.widget.refreshing
import javax.inject.Inject

class TuGuaFragment : ContentListFragment() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_tuguaitem_list
    }

    var currentPage: Int = 1

    @Inject
    lateinit var factory: TuGuaViewModel.TuGuaFactory

    private val viewModel: TuGuaViewModel by lazyFast {
        ViewModelProviders.of(this, factory).get(TuGuaViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (context.applicationContext as App).appComponent.inject(this)
    }

    lateinit var adapter: TuGuaItemRecyclerViewAdapter

    override fun onViewCreated(root: View?, savedInstanceState: Bundle?) {
        adapter = TuGuaItemRecyclerViewAdapter(emptyList<TuGua>().toMutableList())
        view.adapter = adapter
    }

    companion object {
        fun newInstance(): TuGuaFragment {
            return TuGuaFragment()
        }
    }

    override fun load(onLoaded: () -> Unit) {
        swiper.refreshing = true
        viewModel.load().observe(this, Observer {
            swiper.refreshing = false
            onLoaded()
            adapter.swipeData(it?.data ?: emptyList())
        })
    }

    override fun loadMore(onLoaded: () -> Unit) {
        swiper.refreshing = true
        viewModel.load(currentPage + 1).observe(this, Observer {
            swiper.refreshing = false
            onLoaded()
            if (it?.data != null) {
                currentPage++
                adapter.addData(it.data ?: emptyList())
            }
        })
    }
}
