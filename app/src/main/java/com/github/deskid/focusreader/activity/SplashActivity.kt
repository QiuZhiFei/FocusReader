package com.github.deskid.focusreader.activity

import android.os.Bundle
import android.view.View
import com.github.deskid.focusreader.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import javax.inject.Inject

class SplashActivity : BaseActivity() {
    @Inject
    lateinit var splashViewModel: SplashViewModel

    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
        fullscreen_content.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LOW_PROFILE or
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

        val disposable = splashViewModel.splashImage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ imgUrl ->
                    web_imageview.setImageUrl(imgUrl.data)
                }, {
                    toast(it.message ?: "something wrong")
                })

        mCompositeDisposable.add(disposable)
        startActivity<MainActivity>()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        mCompositeDisposable.clear()
    }
}
