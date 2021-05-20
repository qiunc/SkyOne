package com.tj.skyone.adapter

import android.widget.TextView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.tj.skyone.R
import com.tj.skyone.bean.MgtBean
import com.tj.skyone.bean.RecordBean
import com.tj.skyone.utils.eventbus.EventBusConsts
import com.tj.skyone.utils.eventbus.EventBusUtils
import com.tj.skyone.widget.dialog.PubDialog


class RecordAdapter : MyBaseQuickAdapter<RecordBean, BaseViewHolder>(R.layout.item_record) {

    override fun convert(holder: BaseViewHolder, item: RecordBean) {

        val id = holder.getView<TextView>(R.id.id)
        val name = holder.getView<TextView>(R.id.name)
        val content = holder.getView<TextView>(R.id.content)
        val time = holder.getView<TextView>(R.id.time)


        id.text = item.id
        name.text = item.name
        content.text = item.content
        time.text = item.time





    }
}