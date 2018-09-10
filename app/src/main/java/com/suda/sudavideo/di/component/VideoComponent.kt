package com.suda.sudavideo.di.component

import com.jess.arms.di.component.AppComponent
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.di.scope.FragmentScope
import com.suda.sudavideo.di.moudle.VideoModule
import com.suda.sudavideo.mvp.ui.fragment.VideoPageFragment
import dagger.Component

/**
 * @author guhaibo
 * @date 2018/9/4
 */
@FragmentScope
@Component(modules = [VideoModule::class], dependencies = [AppComponent::class])
interface VideoComponent {
    fun inject(videoPageFragment: VideoPageFragment)
}


