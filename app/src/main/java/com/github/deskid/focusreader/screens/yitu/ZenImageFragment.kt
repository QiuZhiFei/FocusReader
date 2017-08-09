package com.github.deskid.focusreader.screens.yitu

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.transition.ChangeBounds
import android.transition.ChangeImageTransform
import android.transition.Fade
import android.transition.TransitionSet
import android.view.View
import android.view.ViewTreeObserver
import com.github.deskid.focusreader.R
import com.github.deskid.focusreader.app.App
import com.github.deskid.focusreader.screens.ContentListFragment
import com.github.deskid.focusreader.widget.refreshing
import javax.inject.Inject

class ZenImageFragment : ContentListFragment() {
    override fun getLayoutId(): Int = R.layout.fragment_zenimage_list

    var currentPage: Int = 1

    lateinit var adapter: ZenItemRecyclerViewAdapter

    @Inject
    lateinit var factory: ZenImageViewModel.ZenImageFactory

    val viewModel: ZenImageViewModel by lazy {
        ViewModelProviders.of(this, factory).get(ZenImageViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        activity.window.allowEnterTransitionOverlap = false
        activity.window.allowReturnTransitionOverlap = false

        val reentertransitionSet = TransitionSet().apply {
            addTransition(ChangeImageTransform())
            addTransition(ChangeBounds())
            addTransition(Fade(Fade.IN))
        }

        val exittransitionSet = TransitionSet().apply {
            addTransition(ChangeImageTransform())
            addTransition(ChangeBounds())
            addTransition(Fade(Fade.OUT))
        }

//        activity.window.sharedElementExitTransition = exittransitionSet
//        activity.window.sharedElementReenterTransition = reentertransitionSet

        super.onCreate(savedInstanceState)
        (context.applicationContext as App).appComponent.inject(this)
    }

    override fun onViewCreated(root: View?, savedInstanceState: Bundle?) {
        adapter = ZenItemRecyclerViewAdapter(ArrayList())
        adapter.setOnClickListener { position, imageView, images ->
            ZenImageDetailAct.start(activity, position, imageView, images)
        }
        view.adapter = adapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data == null) {
            return
        }

        val selectedItem = data.getIntExtra("SELECTED_POSITION", 0)
        view.scrollToPosition(selectedItem)
        activity.postponeEnterTransition()
        view.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                view.viewTreeObserver.removeOnPreDrawListener(this)
                view.requestLayout()
                activity.startPostponedEnterTransition()
                return true
            }
        })

    }

    companion object {
        fun newInstance(): ZenImageFragment {
            return ZenImageFragment()
        }
    }

    override fun load(page: Int) {
        swiper.refreshing = true
        viewModel.load(page).observe(this, Observer {
            swiper.refreshing = false
            currentPage = 1
            it?.let {
                adapter.swipeData(it)
            }
        })
    }

    override fun loadMore() {
        viewModel.load(currentPage + 1).observe(this, Observer {
            if (it != null && !(it.isEmpty())) {
                currentPage++
                adapter.addData(it)
            } else {
                Snackbar.make(swiper, "No more data", Snackbar.LENGTH_SHORT).show()
            }
        })
    }

    override fun getItemOffset(): Int = 0
}
