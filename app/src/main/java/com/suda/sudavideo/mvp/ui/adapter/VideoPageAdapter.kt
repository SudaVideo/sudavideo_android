package com.suda.sudavideo.mvp.ui.adapter

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.suda.sudavideo.mvp.model.entity.Source
import com.suda.sudavideo.mvp.ui.fragment.VideoPageFragment

/**
 * @author guhaibo
 * @date 2018/9/4
 */
class VideoPageAdapter(fm: FragmentManager, private val ctx: Context, private val list: List<Source>) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        val bundle = Bundle()
        bundle.putSerializable("source", list[position])
        return Fragment.instantiate(ctx, VideoPageFragment::class.java.name, bundle)
    }

    override fun getCount(): Int {
        return this.list.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return this.list[position].name
    }
}