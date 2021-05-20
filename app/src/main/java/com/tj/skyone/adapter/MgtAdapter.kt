package com.tj.skyone.adapter

import android.widget.TextView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.tj.skyone.R
import com.tj.skyone.bean.MgtBean
import com.tj.skyone.utils.eventbus.EventBusConsts
import com.tj.skyone.utils.eventbus.EventBusUtils
import com.tj.skyone.widget.dialog.PubDialog


class MgtAdapter : MyBaseQuickAdapter<MgtBean, BaseViewHolder>(R.layout.item_mgt) {

    override fun convert(holder: BaseViewHolder, item: MgtBean) {

        val id = holder.getView<TextView>(R.id.id)
        val name = holder.getView<TextView>(R.id.name)
        val type = holder.getView<TextView>(R.id.type)
        val btn = holder.getView<TextView>(R.id.btn)


        id.text = item.id
        name.text = item.name
        type.text = item.type

        btn.setOnClickListener {



            val dialog = PubDialog(context,false,"删除用户","是否删除当前选择用户！",true,true)
            dialog.show()

            dialog.btn.setOnClickListener {


                EventBusUtils.post(EventBusConsts.D_USER,"")

                dialog.dismiss()
            }

            dialog.ok.setOnClickListener {




                dialog.dismiss()
            }


        }



    }
}