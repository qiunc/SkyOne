package com.tj.skyone.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder


/**
 * 自定义适配器
 * 1.实现屏幕适配
 * 2.防止连续点击操作（未使用)
 */
abstract class MyBaseQuickAdapter<T, VH : BaseViewHolder> : BaseQuickAdapter<T, VH> {

    constructor(layoutResId: Int) : this(layoutResId, null)

    constructor(layoutResId: Int, data: MutableList<T>?) : super(layoutResId, data)

    override fun onItemViewHolderCreated(viewHolder: VH, viewType: Int) {
        //ScreenAdapterTools.getInstance().loadView(viewHolder.itemView)  //屏幕适配
        super.onItemViewHolderCreated(viewHolder, viewType)
    }

    override fun setOnItemClick(v: View, position: Int) {
        super.setOnItemClick(v, position)
    }


}