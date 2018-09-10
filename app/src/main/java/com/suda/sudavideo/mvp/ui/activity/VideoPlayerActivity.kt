package com.suda.sudavideo.mvp.ui.activity

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import androidx.core.widget.toast
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.suda.sudavideo.R
import com.suda.sudavideo.di.component.DaggerVideoPlayerCommpent
import com.suda.sudavideo.di.moudle.VideoPlayerModule
import com.suda.sudavideo.mvp.contract.VideoPlayerContract
import com.suda.sudavideo.mvp.model.entity.PlayData
import com.suda.sudavideo.mvp.model.entity.Video
import com.suda.sudavideo.mvp.model.entity.VideoDetail
import com.suda.sudavideo.mvp.model.entity.VideoSeries
import com.suda.sudavideo.mvp.presenter.VideoPlayerPresenter
import com.suda.sudavideo.mvp.ui.adapter.*
import kotlinx.android.synthetic.main.content_video_player.*
import kotlinx.android.synthetic.main.include_title.*


class VideoPlayerActivity : BaseActivity<VideoPlayerPresenter>(), VideoPlayerContract.View {


    private val video: Video by lazy {
        intent.getSerializableExtra("video") as Video
    }

    private val progressDialog: ProgressDialog by lazy {
        ProgressDialog(this@VideoPlayerActivity)
    }

    private lateinit var videoDetailAdapter: VideoDetailAdapter

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerVideoPlayerCommpent.builder()
                .appComponent(appComponent)
                .videoPlayerModule(VideoPlayerModule(this as VideoPlayerContract.View))
                .build()
                .inject(this)
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_video_player
    }

    override fun initData(savedInstanceState: Bundle?) {
        window.setFormat(PixelFormat.TRANSLUCENT);
        toolbar.title = video.title
        title = video.title
        mPresenter?.getVideoDetail(video.source, video.videoId)
    }

    override fun showVideoDetail(videoDetail: VideoDetail) {
        ry_player_content.layoutManager = LinearLayoutManager(this)
        val videoDetails = listOf(
                com.suda.sudavideo.mvp.model.vo.VideoDetailMultiItem(TYPE_PLAYER, videoDetail),
                com.suda.sudavideo.mvp.model.vo.VideoDetailMultiItem(TYPE_SERIES, videoDetail),
                com.suda.sudavideo.mvp.model.vo.VideoDetailMultiItem(TYPE_DESC, videoDetail),
                com.suda.sudavideo.mvp.model.vo.VideoDetailMultiItem(TYPE_IMG, videoDetail)
        )
        videoDetailAdapter = VideoDetailAdapter(videoDetails, this,this)
        ry_player_content.adapter = videoDetailAdapter

        mPresenter?.getPlayUrl(video.source, video.videoId, videoDetail.videoSeries[0].seriesId)

    }

    override fun play(playData: PlayData) {
        videoDetailAdapter.changePlayData(playData)
    }

    override fun changePlay(videoSeries: VideoSeries) {
        mPresenter?.getPlayUrl(video.source, video.videoId, videoSeries.seriesId)
    }

    override fun showLoading() {
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.setMessage(getString(R.string.loading))
//        progressDialog.setCancelable(false)
        progressDialog.show()
    }

    override fun hideLoading() {
        progressDialog.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        videoDetailAdapter.release()
    }

    override fun showMessage(message: String) {
        toast(message)
    }

    override fun launchActivity(intent: Intent) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun killMyself() {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
