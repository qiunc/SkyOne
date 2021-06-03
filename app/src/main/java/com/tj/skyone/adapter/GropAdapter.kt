package com.tj.skyone.adapter

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.tj.skyone.R
import com.tj.skyone.bean.GroupBean


class GropAdapter : MyBaseQuickAdapter<GroupBean, BaseViewHolder>(R.layout.item_grop) {

    override fun convert(holder: BaseViewHolder, item: GroupBean) {

        val name = holder.getView<TextView>(R.id.name)
        val img = holder.getView<ImageView>(R.id.img)


        Log.e("==", "tag=${item.isTag}")
        Log.e("==", "select=${item.isSelect}")


        name.text = item.name


        if (item.isTag && item.isSelect){

            img.setBackgroundResource(R.mipmap.gg_icon)

        }else if (item.isTag && !item.isSelect){

            img.setBackgroundResource(R.mipmap.b_g_icon)

        }else if (!item.isTag && !item.isSelect){

            img.setBackgroundResource(R.mipmap.b_icon)

        }else if (!item.isTag && item.isSelect){

            img.setBackgroundResource(R.mipmap.g_icon)

        }

    }
}