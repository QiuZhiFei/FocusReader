package com.github.deskid.focusreader.screens.zhihudaily

import android.app.Application
import com.github.deskid.focusreader.api.data.ErrorState
import com.github.deskid.focusreader.api.data.LoadedState
import com.github.deskid.focusreader.api.data.LoadingState
import com.github.deskid.focusreader.api.data.Zhihu
import com.github.deskid.focusreader.app.App
import com.github.deskid.focusreader.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ZhihuViewModel(application: Application) : BaseViewModel<Zhihu>(application) {
    override fun inject(app: App) {
        app.appComponent().inject(this)
    }

    fun load() {
        disposable.add(appService.getZhihuLatest()
                .map {
                    it.stories = ArrayList(it.stories.filter { !it.title.contentEquals("这里是广告") })
                    it
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe({ refreshState.value = LoadingState() })
                .subscribe({
                    data.value = it
                    refreshState.value = LoadedState()
                }, { refreshState.value = ErrorState(it.message) }))
    }

    fun loadMore(date: String) {
        disposable.add(appService.getZhihuHistory(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe({ refreshState.value = LoadingState() })
                .subscribe({
                    data.value = it
                    refreshState.value = LoadedState()
                }, { refreshState.value = ErrorState(it.message) }))
    }
}