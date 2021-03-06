package com.tj.skyone.widget.dialog

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.animation.LinearInterpolator
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialog
import com.blankj.utilcode.util.StringUtils
import com.tj.skyone.R

class LoadingDialog : AppCompatDialog {

//    private var disposable: Disposable? = null

    private var title = ""

    //    private var ivLoding: ImageView? = null
    private var titleTextView: TextView? = null
//    private var operatingAnim: Animation? = null

    constructor(context: Context?) : super(context, R.style.dialogstyle) {
        title = context?.getString(R.string.dialog_loading).toString()
    }

    constructor(context: Context?, title: String) : super(context, R.style.dialogstyle) {
        this.title = context?.getString(R.string.dialog_loading).toString()
        this.title = title
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_loading_to)
//        ivLoding = findViewById(R.id.iv_loding)
        titleTextView = findViewById(R.id.loading_text)
        titleTextView?.text = title

        //动画
//        operatingAnim = AnimationUtils.loadAnimation(context, R.anim.anim_rotate_dialog)
        val interpolator = LinearInterpolator()
//        operatingAnim?.interpolator = interpolator

        setCanceledOnTouchOutside(false)
    }

    fun setTitle(title: String?) {
        this.title = title!!
    }

    override fun show() {

//        if (disposable != null && !disposable!!.isDisposed()) {
//            disposable!!.dispose()
//
//        }


        Handler().postDelayed({

//            if (this.isShowing) {
//
//                ToastUtils.showLong("发送消息超时，请重试当前功能。")
//
//            }

            dismiss()

        }, 20000)


        //取消上次请求

//        disposable = Observable.interval(0, 20, TimeUnit.SECONDS)
//            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//            .subscribe {
//
////                if (this.isShowing){
////                    ToastUtils.showLong("发送消息超时，请重试当前功能。")
////                }
//
//                if (disposable != null && !disposable!!.isDisposed()) {
//                    disposable!!.dispose()
//
//                }
//
//                dismiss()
//
//            }

//        val add = disposables.add(this.disposable)

        super.show()
        if (titleTextView != null && !StringUtils.isEmpty(title)) {
            titleTextView!!.text = title
        }
    }

}