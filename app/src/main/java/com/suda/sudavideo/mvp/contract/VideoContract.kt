package com.suda.sudavideo.mvp.contract

import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import com.suda.sudavideo.mvp.model.entity.Source
import com.suda.sudavideo.mvp.model.entity.Video
import com.suda.sudavideo.mvp.model.entity.VideoSeries

/**
 * @author guhaibo
 * @date 2018/9/4
 */
interface VideoContract{
    interface View :IView{
        /**
         * 展示影片
         */
        fun showVideos(videos:Array<Video>)
    }

    interface Model:IModel{
        /**
         * 查询影片
         */
        fun getVideos(sorce:Source,pageIndex:Int)
    }
}
