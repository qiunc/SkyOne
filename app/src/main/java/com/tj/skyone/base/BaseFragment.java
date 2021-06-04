package com.tj.skyone.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.tj.skyone.ui.GlobalApp;
import com.tj.skyone.widget.dialog.LoadingDialog;

import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.jessyan.autosize.AutoSize;
import me.jessyan.autosize.internal.CustomAdapt;

/**
 * @describe fragment基类
 * @anthor wdq
 * @time 2018/8/8 10:14
 * @email wudq@infore.com
 */
public abstract class BaseFragment extends Fragment implements IBaseFragmentView, IBaseView, CustomAdapt {

    protected View mContentView;
    protected Activity mActivity;
    protected Fragment fragment;

    private long lastClick = 0;
    private boolean isButterKnife = true;
    private boolean isStart = true;

    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";

    private LoadingDialog dialog;

    /**
     * ButterKnife
     */
    private Unbinder butterKnife;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commitAllowingStateLoss();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(GlobalApp.Companion.isPad()) {
            AutoSize.autoConvertDensity(Objects.requireNonNull(getActivity()), 900, true);
        } else {
            AutoSize.autoConvertDensity(Objects.requireNonNull(getActivity()), 392, true);
        }
        setBaseView(inflater, bindLayout());
        return mContentView;
    }

    @SuppressLint("ResourceType")
    protected void setBaseView(@NonNull LayoutInflater inflater, @LayoutRes int layoutId) {
        if (layoutId <= 0) return;
        mContentView = inflater.inflate(layoutId, null);
        //适配Fragment
        //ScreenAdapterTools.getInstance().loadView(mContentView);
        butterKnife = ButterKnife.bind(this, mContentView);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        initData(bundle);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = getActivity();
        fragment = this;
        initView(savedInstanceState, mContentView);
        doBusiness();
    }

    @Override
    public void onDestroyView() {
        if (mContentView != null && (ViewGroup) mContentView.getParent() != null) {
            ((ViewGroup) mContentView.getParent()).removeView(mContentView);
        }
        super.onDestroyView();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (dialog != null) dialog.dismiss();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isButterKnife) if (butterKnife != null) butterKnife.unbind();
        if (dialog != null) dialog.cancel();
        mContentView = null;
        fragment = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isStart) {
            isStart = false;
            isStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    private boolean isFastClick() {
        long now = System.currentTimeMillis();
        if (now - lastClick >= 200) {
            lastClick = now;
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        if (!isFastClick()) onWidgetClick(view);
    }

    public <T extends View> T findViewById(@IdRes int id) {
        if (mContentView == null) throw new NullPointerException("ContentView is null.");
        return mContentView.findViewById(id);
    }

    //初始化Dialog
    @Override
    public LoadingDialog getDialog() {
        if (dialog == null) {
            dialog = new LoadingDialog(mActivity);
        }
        return dialog;
    }

    //初始化Dialog
    @Override
    public LoadingDialog getDialog(boolean cancel) {
        if (dialog == null) {
            dialog = new LoadingDialog(mActivity);
        }
        dialog.setCanceledOnTouchOutside(cancel);
        return dialog;
    }

    //设置Dialog标题
    @Override
    public LoadingDialog getDialog(String title) {
        if (dialog == null) {
            dialog = new LoadingDialog(getActivity(), title);
        } else {
            dialog.setTitle(title);
        }
        dialog.setTitle(title);
        return dialog;
    }

    //跳转至另一个activity
    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(mActivity, clazz);
        startActivity(intent);
    }

    //带参数的跳转至另一个activity
    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(mActivity, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    public boolean isBaseOnWidth() {
        return true;
    }

    @Override
    public float getSizeInDp() {
        if (GlobalApp.Companion.isPad()) {
            return (float) 900.0;
        } else {
            return (float) 392.0;
        }
    }
}
