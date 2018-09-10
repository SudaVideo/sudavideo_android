package com.suda.sudavideo.mvp.contract

import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import com.suda.sudavideo.mvp.model.entity.Source
import com.suda.sudavideo.mvp.model.entity.Video
import com.suda.sudavideo.mvp.model.entity.VideoPage
import io.reactivex.Observable

/**
 * @author guhaibo
 * @date 2018/9/3
 */
interface MainContract {
    interface View : IView {

        /**
         * 显示视频源
         */
        fun showShowSources(sources: List<Source>)

        /**
         * 显示查询到的视频
         */
        fun showSearchVideos(videos: List<Video>)
    }

    interface Model : IModel {

        /**
         * 获取视频源
         */
        fun getSources(): Observable<List<Source>>

        /**
         * 根据类型查询视频
         */
        fun getVideosByPage(sourceId: Int, type: String, page: Int): Observable<VideoPage>

        /**
         * 根据名称查询视频
         */
        fun searchVideoByName(videoNmae: String): Observable<List<Video>>
    }

}