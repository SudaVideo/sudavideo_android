package com.suda.sudavideo.di.moudle

import com.jess.arms.di.scope.ActivityScope
import com.suda.sudavideo.mvp.contract.MainContract
import com.suda.sudavideo.mvp.model.MainModel
import dagger.Module
import dagger.Provides

/**
 * @author guhaibo
 * @date 2018/9/4
 */

@Module
class MainModule(private val view: MainContract.View) {

    @Provides
    @ActivityScope
    fun provideView(): MainContract.View {
        return view
    }

    @Provides
    @ActivityScope
    fun provideMainModel(mainModel: MainModel):MainContract.Model{
        return  mainModel
    }

}
