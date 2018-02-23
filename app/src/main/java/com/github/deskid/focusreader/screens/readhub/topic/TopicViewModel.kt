package com.github.deskid.focusreader.screens.readhub.topic

import android.app.Application
import com.github.deskid.focusreader.api.data.Topics
import com.github.deskid.focusreader.api.data.UIState
import com.github.deskid.focusreader.app.App
import com.github.deskid.focusreader.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TopicViewModel(application: Application) : BaseViewModel<Topics>(application) {
    override fun inject(app: App) {
        app.appComponent().inject(this)
    }

    fun load(lastCursor: Long? = null) {
        disposable.add(appService.getReadhubTopics(lastCursor)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe({ refreshState.value = UIState.LoadingState() })
                .subscribe({
                    data.value = it
                    refreshState.value = UIState.LoadedState()
                }, { refreshState.value = UIState.ErrorState(it.message) }))
    }

}