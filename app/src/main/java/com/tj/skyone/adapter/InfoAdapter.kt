package com.tj.skyone.adapter

import android.graphics.drawable.GradientDrawable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.tj.skyone.R
import com.tj.skyone.bean.InofBean

/**
 * @describe infoFragment底部recyclerview适配器
 * @anthor wdq
 * @time 2020/6/11 15:28
 * @email wudq@infore.com
 */
class InfoAdapter : MyBaseQuickAdapter<InofBean, BaseViewHolder>(R.layout.item_info) {

    override fun convert(holder: BaseViewHolder, item: InofBean) {

        val name = holder.getView<TextView>(R.id.name)
//        val one = holder.getView<LinearLayout>(R.id.one)
//        val two = holder.getView<LinearLayout>(R.id.two)
//        val three = holder.getView<LinearLayout>(R.id.three)
//        val four = holder.getView<LinearLayout>(R.id.four)
//
//        val twoA = holder.getView<ImageView>(R.id.two_a)
//        val twoB = holder.getView<ImageView>(R.id.two_b)
//
//
//        val threeA = holder.getView<ImageView>(R.id.three_a)
//        val threeB = holder.getView<ImageView>(R.id.three_b)
//        val threeC = holder.getView<ImageView>(R.id.three_c)

        val fourA = holder.getView<ImageView>(R.id.four_a)
        val fourB = holder.getView<ImageView>(R.id.four_b)
        val fourC = holder.getView<ImageView>(R.id.four_c)
        val fourD = holder.getView<ImageView>(R.id.four_d)

        val grad1: GradientDrawable = fourA.background as GradientDrawable
        val grad2: GradientDrawable = fourB.background as GradientDrawable
        val grad3: GradientDrawable = fourC.background as GradientDrawable
        val grad4: GradientDrawable = fourD.background as GradientDrawable

        name.setText(item.name)


        if (!item.isTag1){

            grad1.setColor(ContextCompat.getColor(context,R.color.color_2DB700))

        }else{

            grad1.setColor(ContextCompat.getColor(context,R.color.color_C92600))

        }

        if (!item.isTag2){

            grad2.setColor(ContextCompat.getColor(context,R.color.color_2DB700))

        }else{

            grad2.setColor(ContextCompat.getColor(context,R.color.color_0054AF))


        }

        if (!item.isTag3){

            grad3.setColor(ContextCompat.getColor(context,R.color.color_2DB700))


        }else{

            grad3.setColor(ContextCompat.getColor(context,R.color.color_00706A))


        }

        if (!item.isTag4){

            grad4.setColor(ContextCompat.getColor(context,R.color.color_2DB700))


        }else{

            grad4.setColor(ContextCompat.getColor(context,R.color.color_939393))

        }
//
//        one.isVisible = false
//        two.isVisible = false
//        three.isVisible = false
//        four.isVisible = false
//
//
//        if (list1.size == 0){
//
//        one.isVisible = true
//
//        val grad: GradientDrawable = one.background as GradientDrawable
//        grad.setColor(ContextCompat.getColor(context,R.color.color_2DB700))
//
//        }else if (list1.size == 1){
//
//            one.isVisible = true
//
//            val grad: GradientDrawable = one.background as GradientDrawable
//            val  bean =   list1[0]
//
//            show(bean.tag,grad)
//
//
//        }else if (list1.size == 2){
//            two.isVisible = true
//
//            val grad1: GradientDrawable = twoA.background as GradientDrawable
//            val grad2: GradientDrawable = twoB.background as GradientDrawable
//
//            show(list1[0].tag,grad1)
//            show(list1[1].tag,grad2)
//
//
//        }else if (list1.size == 3){
//
//            three.isVisible = true
//
//            val grad1: GradientDrawable = threeA.background as GradientDrawable
//            val grad2: GradientDrawable = threeB.background as GradientDrawable
//            val grad3: GradientDrawable = threeC.background as GradientDrawable
//
//            show(list1[0].tag,grad1)
//            show(list1[1].tag,grad2)
//            show(list1[2].tag,grad3)
//
//
//        }else if (list1.size == 4){
//
//            four.isVisible = true
//
//            val grad1: GradientDrawable = fourA.background as GradientDrawable
//            val grad2: GradientDrawable = fourB.background as GradientDrawable
//            val grad3: GradientDrawable = fourC.background as GradientDrawable
//            val grad4: GradientDrawable = fourD.background as GradientDrawable
//
//            show(list1[0].tag,grad1)
//            show(list1[1].tag,grad2)
//            show(list1[2].tag,grad3)
//            show(list1[3].tag,grad4)
//        }
//
//
//




    }




    fun show(tag:Int,grad:GradientDrawable){

        if (tag == 1){//风管异常

           grad.setColor(ContextCompat.getColor(context,R.color.color_C92600))
        }else if (tag == 2){//摇臂异常

            grad.setColor(ContextCompat.getColor(context,R.color.color_0054AF))

        }else if (tag == 3){//低压过载

            grad.setColor(ContextCompat.getColor(context,R.color.color_00706A))

        }else if (tag == 4){//排水异常

            grad.setColor(ContextCompat.getColor(context,R.color.color_939393))

        }


    }



}