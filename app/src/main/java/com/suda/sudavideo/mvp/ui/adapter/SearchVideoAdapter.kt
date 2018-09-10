package com.suda.sudavideo.mvp.ui.adapter

import android.content.Intent
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.suda.sudavideo.R
import com.suda.sudavideo.mvp.model.entity.Video
import com.suda.sudavideo.mvp.ui.activity.VideoPlayerActivity

/**
 * @author guhaibo
 * @date 2018/9/9
 */
class SearchVideoAdapter(lists: List<Video>) : BaseQuickAdapter<Video, BaseViewHolder>(R.layout.item_search_video, lists) {

    override fun convert(helper: BaseViewHolder, item: Video) {
        helper.setText(R.id.tv_title, item.title + "(源" + item.source + ")")
        helper.itemView.setOnClickListener {
            val intent = Intent(it.context, VideoPlayerActivity::class.java)
            intent.putExtra("video", item)
            it.context.startActivity(intent)
        }
    }
}