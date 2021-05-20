package com.tj.skyone.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;


public class SaveStatePagerAdapter extends PagerAdapter {

    private List<Fragment> fragments;
    private FragmentManager fragmentManager;
    private ViewPager viewPager;
    private int currentPageIndex = 0;

    private OnExtraPageChangeListener onExtraPageChangeListener;

    public SaveStatePagerAdapter(FragmentManager fragmentManager, ViewPager viewPager, List<Fragment> fragments) {
        this.fragments = fragments;
        this.fragmentManager = fragmentManager;
        this.viewPager = viewPager;
        this.viewPager.setAdapter(this);
        this.viewPager.addOnPageChangeListener(mOnPageChangeListener);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(fragments.get(position).getView()); // 移出viewpager两边之外的page布局
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = fragments.get(position);
        if (!fragment.isAdded()) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.add(fragment, fragment.getClass().getSimpleName());
            ft.commit();
            /**
             * 在用FragmentTransaction.commit()方法提交FragmentTransaction对象后
             * 会在进程的主线程中，用异步的方式来执行。 如果想要立即执行这个等待中的操作，就要调用这个方法（只能在主线程中调用）。
             * 要注意的是，所有的回调和相关的行为都会在这个调用中被执行完成，因此要仔细确认这个方法的调用位置。
             */
            fragmentManager.executePendingTransactions();
        }

        if (fragment.getView().getParent() == null) {
            container.addView(fragment.getView());
        }

        return fragment.getView();
    }

    public int getCurrentPageIndex() {
        return currentPageIndex;
    }

    public OnExtraPageChangeListener getOnExtraPageChangeListener() {
        return onExtraPageChangeListener;
    }

    public void setOnExtraPageChangeListener(
            OnExtraPageChangeListener onExtraPageChangeListener) {
        this.onExtraPageChangeListener = onExtraPageChangeListener;
    }

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int arg0) {
            // TODO Auto-generated method stub
            fragments.get(currentPageIndex).onPause();
            if (fragments.get(arg0).isAdded()) {
                fragments.get(arg0).onResume();
            }
            currentPageIndex = arg0;

            if (null != onExtraPageChangeListener) {
                onExtraPageChangeListener.onExtraPageSelected(arg0);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub
            if (null != onExtraPageChangeListener) {
                onExtraPageChangeListener.onExtraPageScrolled(arg0, arg1, arg2);
            }

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub
            if (null != onExtraPageChangeListener) {
                onExtraPageChangeListener.onExtraPageScrollStateChanged(arg0);
            }
        }
    };

    static class OnExtraPageChangeListener {
        public void onExtraPageScrolled(int i, float v, int i2) {
        }

        public void onExtraPageSelected(int i) {
        }

        public void onExtraPageScrollStateChanged(int i) {
        }
    }

}
