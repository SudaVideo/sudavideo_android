package com.suda.sudavideo.di.component

import com.jess.arms.di.component.AppComponent
import com.jess.arms.di.scope.ActivityScope
import com.suda.sudavideo.di.moudle.MainModule
import com.suda.sudavideo.mvp.ui.activity.MainActivity
import dagger.Component

/**
 * @author guhaibo
 * @date 2018/9/4
 */
@ActivityScope
@Component(modules = [MainModule::class], dependencies = [AppComponent::class])
interface MainComponent {
    fun inject(mainActivity: MainActivity)
}


