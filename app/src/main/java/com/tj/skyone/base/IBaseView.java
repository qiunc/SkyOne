package com.tj.skyone.base;


import com.tj.skyone.widget.dialog.LoadingDialog;

/**
 * Created on 16/11/13.
 *
 * @author zlj
 * @version 1.0
 */
public interface IBaseView {
    //    /**
//     * 显示加载中状态
//     *
//     * @param message 提示文本
//     */
//    void showLoading(String message);
//
//    /**
//     * 隐藏加载中状态
//     */
//    void hideLoading();
//
//    /**
//     * 显示错误信息
//     *
//     * @param errorMessage 错误信息文本
//     */
    void showError(String errorMessage);

    /**
     * 响应请求数据到View层,目前没有想到更好的方法来转发数据
     *
     * @param object 返回的数据
     */
    void showResult(Object object);

    void showResult(Object object, String code);


    /**
     * 获取Dialog
     */
    LoadingDialog getDialog();

    /**
     * 是否能够手动退出
     */
    LoadingDialog getDialog(boolean cancel);

    /**
     * 设置标题
     *
     * @param title
     * @return
     */
    LoadingDialog getDialog(String title);
}
