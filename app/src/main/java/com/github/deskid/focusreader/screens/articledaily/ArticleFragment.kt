package com.github.deskid.focusreader.screens.articledaily

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.deskid.focusreader.R
import com.github.deskid.focusreader.screens.penti.ArticlePagerAdapter
import kotlinx.android.synthetic.main.fragment_penti.*

class ArticleFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_article, container, false)
    }

    override fun onViewCreated(root: View?, savedInstanceState: Bundle?) {
        setUpViewPager()
    }

    private fun setUpViewPager() {
        pager.adapter = ArticlePagerAdapter(childFragmentManager)
        pager.currentItem = 0
        tabLayout.setupWithViewPager(pager)
    }

    companion object {
        fun newInstance(): ArticleFragment {
            return ArticleFragment()
        }
    }

}