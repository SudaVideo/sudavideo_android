package com.suda.sudavideo.mvp.model

import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.suda.sudavideo.mvp.contract.VideoPlayerContract
import com.suda.sudavideo.mvp.model.api.service.VideoPalyerService
import com.suda.sudavideo.mvp.model.entity.PlayData
import com.suda.sudavideo.mvp.model.entity.VideoDetail
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @author guhaibo
 * @date 2018/9/8
 */
class VideoPlayerModel @Inject constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), VideoPlayerContract.Model {

    override fun getVideoDetail(sourceId: Int, videoId: String): Observable<VideoDetail> {
        return mRepositoryManager.obtainRetrofitService(VideoPalyerService::class.java)
                .getVideoDetail(sourceId, videoId).concatMap { source ->
                    Observable.create<VideoDetail> {
                        if (source.data != null) {
                            it.onNext(source.data)
                        }
                        it.onComplete()

                    }
                }
    }

    override fun getPlayData( source: Int,videoId: String, seriesId: String):Observable<PlayData> {
        return mRepositoryManager.obtainRetrofitService(VideoPalyerService::class.java)
                .getPlayUrl(source, videoId,seriesId).concatMap { result ->
                    Observable.create<PlayData> {
                        if (result.success){
                            if (result.data != null) {
                                it.onNext(result.data)
                            }
                            it.onComplete()
                        }else{
                            it.onError(RuntimeException("RuntimeException"))
                        }
                    }
                }
    }
}