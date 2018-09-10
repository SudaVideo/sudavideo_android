package com.suda.sudavideo.di.component

import com.jess.arms.di.component.AppComponent
import com.jess.arms.di.scope.ActivityScope
import com.suda.sudavideo.di.moudle.VideoPlayerModule
import com.suda.sudavideo.mvp.ui.activity.VideoPlayerActivity
import dagger.Component

/**
 * @author guhaibo
 * @date 2018/9/8
 */

@ActivityScope
@Component(modules = [VideoPlayerModule::class], dependencies = [AppComponent::class])
interface VideoPlayerCommpent {
    fun inject(videoPlayerActivity: VideoPlayerActivity)
}