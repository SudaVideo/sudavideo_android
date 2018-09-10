package com.suda.sudavideo.mvp.model.api.service

import com.suda.sudavideo.mvp.model.entity.Result
import com.suda.sudavideo.mvp.model.entity.Source
import com.suda.sudavideo.mvp.model.entity.Video
import com.suda.sudavideo.mvp.model.entity.VideoPage
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author guhaibo
 * @date 2018/9/2
 */
interface VideoService {

    @GET("/video/source")
    fun getSource(): Observable<Result<List<Source>>>

    @GET("/video/page")
    fun getVideosByPage(@Query("source") sourceId: Int, @Query("type") type: String, @Query("pageIndex") page: Int)
            : Observable<Result<VideoPage>>

    @GET("/video/search")
    fun searchVideoByName(@Query("videoName") videoName: String)
            : Observable<Result<List<Video>>>
}