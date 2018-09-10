package com.suda.sudavideo.di.moudle

import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.di.scope.FragmentScope
import com.suda.sudavideo.mvp.contract.MainContract
import com.suda.sudavideo.mvp.model.MainModel
import com.suda.sudavideo.mvp.model.entity.Video
import com.suda.sudavideo.mvp.ui.adapter.VideoAdapter
import dagger.Module
import dagger.Provides

/**
 * @author guhaibo
 * @date 2018/9/4
 */

@Module
class VideoModule {

    @Provides
    @FragmentScope
    fun provideMainModel(mainModel: MainModel): MainContract.Model {
        return mainModel
    }

    @Provides
    @FragmentScope
    fun provideVideoList(): MutableList<Video> {
        return  arrayListOf<Video>()
    }
}
