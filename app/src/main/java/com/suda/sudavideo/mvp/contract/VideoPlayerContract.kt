package com.suda.sudavideo.mvp.contract

import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import com.suda.sudavideo.mvp.model.entity.PlayData
import com.suda.sudavideo.mvp.model.entity.VideoDetail
import com.suda.sudavideo.mvp.model.entity.VideoSeries
import io.reactivex.Observable

/**
 * @author guhaibo
 * @date 2018/9/8
 */
interface VideoPlayerContract {
    interface View : IView {
        /**
         * 展示影片详情
         */
        fun showVideoDetail(videoDetail: VideoDetail)


        /**
         * 播放影片
         */
        fun play(playData: PlayData);


        /**
         * 更换剧集
         */
        fun changePlay(videoSeries: VideoSeries)
    }

    interface Model : IModel {
        /**
         * 获取影片详情
         */
        fun getVideoDetail(sourceId: Int, videoId: String): Observable<VideoDetail>

        /**
         * 获取播放数据
         */
        fun getPlayData(source: Int, videoId: String, seriesId: String): Observable<PlayData>
    }
}