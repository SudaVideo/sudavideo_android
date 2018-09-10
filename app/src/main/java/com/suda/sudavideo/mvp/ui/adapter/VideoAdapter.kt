package com.suda.sudavideo.mvp.ui.adapter

import android.content.Intent
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jess.arms.http.imageloader.glide.ImageConfigImpl
import com.jess.arms.utils.ArmsUtils
import com.suda.sudavideo.R
import com.suda.sudavideo.mvp.model.entity.Video
import com.suda.sudavideo.mvp.ui.activity.VideoPlayerActivity
import javax.inject.Inject

/**
 * @author guhaibo
 * @date 2018/9/4
 */
class VideoAdapter @Inject constructor(videos: MutableList<Video>) : BaseQuickAdapter<Video, BaseViewHolder>(R.layout.item_video, videos) {

    override fun convert(helper: BaseViewHolder, item: Video) {
        val appComponent = ArmsUtils.obtainAppComponentFromContext(helper.itemView.context)
        val imageLoader = appComponent.imageLoader()
        helper.setText(R.id.title_tv, item.title)
        imageLoader.loadImage(helper.itemView.context, ImageConfigImpl.builder().url(item.thumb)
                .imageView(helper.getView(R.id.thumb_iv)).build())
        helper.itemView.setOnClickListener {
            val intent = Intent(it.context, VideoPlayerActivity::class.java)
            intent.putExtra("video", item)
            it.context.startActivity(intent)
        }
    }

}