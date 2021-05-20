package com.tj.skyone.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @describe 接口
 * @anthor wdq
 * @time 2018/8/8 10:19
 * @email wudq@infore.com
 */
public interface IBaseFragmentView extends View.OnClickListener{

    /**
     * 初始化数据
     *
     * @param bundle 传递过来的 bundle
     */
    void initData(@Nullable final Bundle bundle);

    /**
     * 绑定布局
     *
     * @return 布局 Id
     */
    int bindLayout();

    /**
     * 初始化 view
     */
    void initView(final Bundle savedInstanceState, final View contentView);

    /**
     * 业务操作
     */
    void doBusiness();

    /**
     * 视图点击事件
     *
     * @param view 视图
     */
    void onWidgetClick(final View view);

    /**
     * 第一次加载数据
     *
     */

    void isStart();

}
