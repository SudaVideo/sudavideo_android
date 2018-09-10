package com.suda.sudavideo.mvp.model.api.service

import com.suda.sudavideo.mvp.model.entity.PlayData
import com.suda.sudavideo.mvp.model.entity.Result
import com.suda.sudavideo.mvp.model.entity.VideoDetail
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author guhaibo
 * @date 2018/9/8
 */
interface VideoPalyerService {

    @GET("/video/detail")
    fun getVideoDetail(@Query("source") sourceId: Int, @Query("videoId") videoId: String)
            : Observable<Result<VideoDetail>>

    @GET("/video/playUrl")
    fun getPlayUrl(@Query("source") sourceId: Int, @Query("videoId") videoId: String, @Query("seriesId") seriesId: String)
            : Observable<Result<PlayData>>
}