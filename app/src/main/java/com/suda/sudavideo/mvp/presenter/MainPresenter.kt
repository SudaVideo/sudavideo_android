package com.suda.sudavideo.mvp.presenter

import com.jess.arms.mvp.BasePresenter
import com.jess.arms.utils.RxLifecycleUtils
import com.suda.sudavideo.app.utils.RxUtils
import com.suda.sudavideo.mvp.contract.MainContract
import com.suda.sudavideo.mvp.model.entity.Source
import com.suda.sudavideo.mvp.model.entity.Video
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author guhaibo
 * @date 2018/9/3
 */
class MainPresenter @Inject constructor(model: MainContract.Model, view: MainContract.View)
    : BasePresenter<MainContract.Model, MainContract.View>(model, view) {

    fun getSources() {
        mModel.getSources()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe({
                    mRootView.showShowSources(it)
                },{

                })
    }

    fun searchVideoByName(videoName: String) {
        mModel.searchVideoByName(videoName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe({
                    mRootView.showSearchVideos(it)
                },{

                })
    }
}