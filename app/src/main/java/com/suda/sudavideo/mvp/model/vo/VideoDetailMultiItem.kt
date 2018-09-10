package com.suda.sudavideo.mvp.model.vo

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.suda.sudavideo.mvp.model.entity.VideoDetail

/**
 * @author guhaibo
 * @date 2018/9/8
 */
data class VideoDetailMultiItem(val type: Int, val videoDetail: VideoDetail) : MultiItemEntity {

    override fun getItemType(): Int {
        return type
    }
}