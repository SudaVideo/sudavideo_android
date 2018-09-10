package com.suda.sudavideo.mvp.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jess.arms.utils.ArmsUtils
import com.suda.sudavideo.R
import com.suda.sudavideo.di.component.DaggerVideoComponent
import com.suda.sudavideo.mvp.model.MainModel
import com.suda.sudavideo.mvp.model.entity.Source
import com.suda.sudavideo.mvp.model.entity.Video
import com.suda.sudavideo.mvp.model.entity.VideoPage
import com.suda.sudavideo.mvp.ui.adapter.VideoAdapter
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_video_page.*
import javax.inject.Inject

/**
 * @author guhaibo
 * @date 2018/9/4
 */

class VideoPageFragment : Fragment() {


    @Inject
    lateinit var mainModel: MainModel

    @Inject
    lateinit var mVideos:MutableList<Video>;

    @Inject
    lateinit var videoAdapter:VideoAdapter

    private var page = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(container!!.context).inflate(R.layout.fragment_video_page, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        DaggerVideoComponent.builder()
                .appComponent(ArmsUtils.obtainAppComponentFromContext(context))
                .build().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val source = arguments?.getSerializable("source") as Source
        video_list_rv.layoutManager = GridLayoutManager(context, 3)

        video_list_rv.adapter = videoAdapter
        videoAdapter.setEnableLoadMore(true)
        videoAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)

        videoAdapter.setOnLoadMoreListener({
            loadData(source.source, source.type, page)
        }, video_list_rv)

        loadData(source.source, source.type, page)
    }

    private fun loadData(sourceId: Int, sourceType: String, page: Int) {
        mainModel.getVideosByPage(sourceId, sourceType, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mVideos.addAll(it.data)
                    videoAdapter.notifyDataSetChanged()

                    if (this@VideoPageFragment.page >= it.pageSize) {
                        videoAdapter.loadMoreEnd()
                    } else {
                        this@VideoPageFragment.page++
                        videoAdapter.loadMoreComplete()
                    }
                }, {
                    videoAdapter.loadMoreFail()})
    }


}