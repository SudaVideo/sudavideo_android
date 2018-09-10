package com.suda.sudavideo.mvp.model

import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.suda.sudavideo.mvp.contract.MainContract
import com.suda.sudavideo.mvp.model.api.service.VideoService
import com.suda.sudavideo.mvp.model.entity.Source
import com.suda.sudavideo.mvp.model.entity.Video
import com.suda.sudavideo.mvp.model.entity.VideoPage
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @author guhaibo
 * @date 2018/9/3
 */

class MainModel @Inject constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), MainContract.Model {

    override fun getSources(): Observable<List<Source>> {
        return mRepositoryManager.obtainRetrofitService(VideoService::class.java).getSource().concatMap { source ->
            Observable.create<List<Source>> {
                if (source.data != null) {
                    it.onNext(source.data)
                }
                it.onComplete()
            }
        }
    }

    override fun getVideosByPage(sourceId: Int, type: String, page: Int): Observable<VideoPage> {
        return mRepositoryManager.obtainRetrofitService(VideoService::class.java)
                .getVideosByPage(sourceId, type, page).concatMap { source ->
            Observable.create<VideoPage> {
                if (source.data != null) {
                    it.onNext(source.data)
                }
                it.onComplete()
            }
        }
    }

    override fun searchVideoByName(videoNmae: String): Observable<List<Video>> {
        return mRepositoryManager.obtainRetrofitService(VideoService::class.java)
                .searchVideoByName(videoNmae).concatMap { source ->
                    Observable.create<List<Video>> {
                        if (source.data != null) {
                            it.onNext(source.data)
                        }
                        it.onComplete()
                    }
                }
    }
}