package com.suda.sudavideo.mvp.presenter

import com.jess.arms.mvp.BasePresenter
import com.suda.sudavideo.app.utils.RxUtils
import com.suda.sudavideo.mvp.contract.VideoPlayerContract
import javax.inject.Inject

/**
 * @author guhaibo
 * @date 2018/9/8
 */
class VideoPlayerPresenter @Inject constructor(model: VideoPlayerContract.Model, view: VideoPlayerContract.View)
    : BasePresenter<VideoPlayerContract.Model, VideoPlayerContract.View>(model, view) {


    fun getVideoDetail(source: Int, videoId: String) {
        mModel.getVideoDetail(source, videoId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe({
                    mRootView.showVideoDetail(it)
                }, {

                })
    }


    fun getPlayUrl(source: Int, videoId: String, seriesId: String) {
        mModel.getPlayData(source, videoId, seriesId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe({
                    mRootView.play(it)
                }, {
                    mRootView.showMessage("获取失败")
                })
    }

}