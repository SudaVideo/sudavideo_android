package com.suda.sudavideo.mvp.ui.adapter

import android.app.Activity
import android.view.Gravity
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.*
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jess.arms.http.imageloader.glide.ImageConfigImpl
import com.jess.arms.utils.ArmsUtils
import com.suda.sudavideo.R
import com.suda.sudavideo.mvp.contract.VideoPlayerContract
import com.suda.sudavideo.mvp.model.entity.PlayData
import com.suda.sudavideo.mvp.model.vo.VideoDetailMultiItem
import com.suda.sudavideo.view.FlowLayout
import com.tencent.smtt.export.external.interfaces.WebResourceResponse
import java.util.*
//import android.webkit.*
import com.tencent.smtt.sdk.*


/**
 * @author guhaibo
 * @date 2018/9/8
 */
const val TYPE_PLAYER: Int = 1
const val TYPE_SERIES: Int = 2
const val TYPE_DESC: Int = 3
const val TYPE_IMG: Int = 4


class VideoDetailAdapter(list: List<VideoDetailMultiItem>, val view: VideoPlayerContract.View, act: Activity)
    : BaseMultiItemQuickAdapter<VideoDetailMultiItem, BaseViewHolder>(list) {

    private var mPlayData: PlayData? = null
    private var seriesName: String? = null
    private val webView = WebView(act)
    private val progressBar = ProgressBar(act);

    init {
        addItemType(TYPE_PLAYER, R.layout.item_player)
        addItemType(TYPE_DESC, R.layout.item_desc)
        addItemType(TYPE_SERIES, R.layout.item_series)
        addItemType(TYPE_IMG, R.layout.item_img)
        webView.settings.useWideViewPort = (true)
        webView.settings.javaScriptEnabled = (true)
        webView.settings.builtInZoomControls = (false)
        webView.settings.loadsImagesAutomatically = (true)
        webView.settings.layoutAlgorithm = (WebSettings.LayoutAlgorithm.SINGLE_COLUMN)
        webView.settings.setSupportZoom(false)
        webView.settings.domStorageEnabled = (true)
        progressBar.visibility = GONE
        webView.webViewClient = object : WebViewClient(){
            override fun shouldOverrideUrlLoading(webView: WebView?, url: String?): Boolean {
                val map = HashMap<String, String>()
                map["Referer"] = "https://www.sudavideo.site/#/"
                webView!!.loadUrl(url, map)
                return false
            }

            override fun shouldInterceptRequest(view: WebView?, url: String?): WebResourceResponse? {
                view!!.loadUrl("javascript:function hideAd(){document.querySelector('.ftxIcon').style.display=\"none\";document.querySelector('.pauseAdImg').style.display=\"none\";}hideAd();")
                return if (url!!.contains("456jjh.cn")) {
                    WebResourceResponse(null, null, null)
                } else super.shouldInterceptRequest(view, url)
            }


            override fun onPageFinished(webView: WebView?, s: String?) {
                super.onPageFinished(webView, s)
                progressBar.visibility = (View.GONE)
            }
        }
    }

    fun release() {
        (webView.parent as ViewGroup).removeAllViews()
        webView.destroy()
        webView.stopLoading()
        webView.settings.javaScriptEnabled = (false)
        webView.clearView()
        webView.removeAllViews()
        webView.destroy()
    }

    fun changePlayData(playData: PlayData) {
        if (playData != mPlayData) {
            progressBar.visibility = VISIBLE
            this.mPlayData = playData
            webView.stopLoading()
            webView.loadUrl("")
            mPlayData?.apply {
                var playUrl = this.playUrl
                if (!playUrl.startsWith("http")) {
                    playUrl = "http:$playUrl"
                }
                if (this.type == "iframe") {
                    webView.loadUrl(playUrl)
                } else {
                    webView.loadUrl("file:///android_asset/video.html?playUrl=$playUrl")
                }
            }
        }
    }

    override fun convert(helper: BaseViewHolder, item: VideoDetailMultiItem?) {

        when (item!!.itemType) {
            TYPE_PLAYER -> {
                seriesName = if (seriesName == null) {
                    item.videoDetail.videoSeries[0].name
                } else {
                    seriesName
                }

                val webContainer = helper.getView(R.id.web_container) as ViewGroup
                if (webContainer.childCount == 0) {
                    val layoutParamsWeb = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                    webContainer.addView(webView, layoutParamsWeb)

                    val layoutParams = FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                    layoutParams.gravity = Gravity.CENTER;
                    webContainer.addView(progressBar, layoutParams)
                }

            }
            TYPE_SERIES -> {
                val seriesContainer = helper.getView(R.id.series_container) as FlowLayout
                if (seriesContainer.childCount == 0) {
                    item.videoDetail.videoSeries.forEach {
                        val button = Button(seriesContainer.context)
                        button.text = it.name
                        val layoutParams = FlowLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                        seriesContainer.addView(button, layoutParams)
                        val videoSeries = it
                        button.setOnClickListener {
                            seriesName = videoSeries.name
                            view.changePlay(videoSeries)
                        }
                    }

                }
            }
            TYPE_DESC -> helper.setText(R.id.tv_desc, item.videoDetail.desc)
            TYPE_IMG -> {
                val seriesContainer = helper.getView(R.id.img_container) as ViewGroup
                if (seriesContainer.childCount == 0) {
                    item.videoDetail.previewImgs.forEach {
                        val img = ImageView(seriesContainer.context)
                        val layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                        val appComponent = ArmsUtils.obtainAppComponentFromContext(helper.itemView.context)
                        val imageLoader = appComponent.imageLoader()
                        imageLoader.loadImage(img.context, ImageConfigImpl.builder().url(it)
                                .imageView(img).build())
                        seriesContainer.addView(img, layoutParams)
                    }
                }

            }
        }
    }


}