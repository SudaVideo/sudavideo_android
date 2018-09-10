package com.suda.sudavideo.di.moudle

import com.jess.arms.di.scope.ActivityScope
import com.suda.sudavideo.mvp.contract.VideoContract
import com.suda.sudavideo.mvp.contract.VideoPlayerContract
import com.suda.sudavideo.mvp.model.VideoPlayerModel
import dagger.Module
import dagger.Provides

/**
 * @author guhaibo
 * @date 2018/9/8
 */
@Module
class VideoPlayerModule(private val view: VideoPlayerContract.View) {

    @Provides
    @ActivityScope
    fun provideVideoPlayerModel(videoPlayerModel: VideoPlayerModel): VideoPlayerContract.Model {
        return videoPlayerModel
    }

    @Provides
    @ActivityScope
    fun provideView(): VideoPlayerContract.View {
        return view
    }
}