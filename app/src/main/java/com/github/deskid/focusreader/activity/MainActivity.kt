package com.github.deskid.focusreader.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.SparseArray
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter
import com.github.deskid.focusreader.R
import com.github.deskid.focusreader.utils.getColorCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val screens: SparseArray<Fragment> = SparseArray(3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        view_pager.offscreenPageLimit = 3
        val adapter = MainPagerAdapter(supportFragmentManager)
        view_pager.adapter = adapter

        bottom_navigation.setOnTabSelectedListener { position, wasSelected ->
            if (!wasSelected) {
                view_pager.currentItem = position
            }
            return@setOnTabSelectedListener true
        }

        val navigationAdapter = AHBottomNavigationAdapter(this, R.menu.navigation)
        navigationAdapter.setupWithBottomNavigation(bottom_navigation)
        bottom_navigation.isBehaviorTranslationEnabled = true
        bottom_navigation.currentItem = 1
        bottom_navigation.titleState = AHBottomNavigation.TitleState.ALWAYS_HIDE
        bottom_navigation.accentColor = getColorCompat(R.color.colorPrimary)
    }
}
