package com.tj.skyone.ui.home.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.google.gson.Gson;
import com.tj.skyone.R;
import com.tj.skyone.base.BaseFragment;
import com.tj.skyone.bean.HomeBean;
import com.tj.skyone.utils.HttpParam;
import com.tj.skyone.utils.TcpClient;
import com.tj.skyone.utils.TimeUtil;
import com.tj.skyone.utils.eventbus.AnyEventType;
import com.tj.skyone.utils.eventbus.AnyEventTypes;
import com.tj.skyone.utils.eventbus.EventBusConsts;
import com.tj.skyone.utils.eventbus.EventBusUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @describe 首页
 * @anthor wdq
 * @time 2021/1/11 16:09
 * @email wudq@infore.com
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.time)
    TextView tvTime;
    @BindView(R.id.date)
    TextView tvDate;
    @BindView(R.id.dates)
    TextView tvDates;
    @BindView(R.id.tv_a)
    TextView tvA;
    @BindView(R.id.tv_b)
    TextView tvB;


    private Disposable disposable;//活跃度

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {

    }

    @Override
    public void doBusiness() {
        EventBusUtils.register(this);


        String time = TimeUtil.timeToString();
        tvTime.setText(time);

        String date = TimeUtil.dateToStrings();

        tvDate.setText(date);
        tvDates.setText(TimeUtils.getChineseWeek(TimeUtils.getNowDate()));


        disposable = Observable.interval(0,5,  TimeUnit.SECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) {

                        String time = TimeUtil.timeToString();
                        tvTime.setText(time);

                        String date = TimeUtil.dateToStrings();

                        tvDate.setText(date);
                        tvDates.setText(TimeUtils.getChineseWeek(TimeUtils.getNowDate()));
                    }
                });



    }

    @Override
    public void onWidgetClick(View view) {

    }

    @Override
    public void isStart() {

        HttpParam httpParam = new HttpParam();
        httpParam.getMap().put("methodName","temphumi");

        TcpClient.getInstance().sendChsPrtCmds(new Gson().toJson(httpParam.getMap()), 1001);


    }

    @Override
    public void showError(String errorMessage) {

    }

    @Override
    public void showResult(Object object) {

    }

    @Override
    public void showResult(Object object, String code) {

    }

    @Subscribe
    public void onEvent(AnyEventTypes event) {

            if (StringUtils.equals("temphumi",event.getEventCode())) {
            HomeBean bean = GsonUtils.fromJson(event.getAnyData().toString(),HomeBean.class);

            tvA.setText(StringUtils.isEmpty(bean.getTemperature())?"0℃":bean.getTemperature() +"℃");
            tvB.setText(StringUtils.isEmpty(bean.getHumidity())?"0%":bean.getHumidity()+"%");


        }

    }

    @Override
    public void onDestroy() {
        EventBusUtils.unregister(this);
        if (disposable!=null) disposable.dispose();
        disposable = null;
        super.onDestroy();
    }
}
