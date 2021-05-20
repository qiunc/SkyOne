package com.tj.skyone.adapter

import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.tj.skyone.R
import com.tj.skyone.bean.GroupBean


 class LowAdapter :MyBaseQuickAdapter<String, BaseViewHolder>(R.layout.item_low) {

    override fun convert(holder: BaseViewHolder, item: String) {

        val name = holder.getView<TextView>(R.id.name)
        val linLow = holder.getView<FrameLayout>(R.id.lin_low)

        name.text = item


        linLow.setOnClickListener {


        }

    }
}