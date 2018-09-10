package com.suda.sudavideo.mvp.model.entity

import java.io.Serializable

/**
 * @author guhaibo
 * @date 2018/9/2
 */
data class Video(var title: String, var thumb: String, var videoId: String, var source: Int) : Serializable