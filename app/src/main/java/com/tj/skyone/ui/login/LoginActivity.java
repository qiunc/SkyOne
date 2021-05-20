package com.tj.skyone.ui.login;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.JsonUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.tj.skyone.R;
import com.tj.skyone.base.BaseActivity;
import com.tj.skyone.ui.home.view.HomeActivity;
import com.tj.skyone.utils.NoDoubleClickUtils;
import com.tj.skyone.utils.TcpClient;
import com.tj.skyone.utils.eventbus.AnyEventType;
import com.tj.skyone.utils.eventbus.AnyEventTypes;
import com.tj.skyone.utils.eventbus.EventBusUtils;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {


    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_pwd_or_code)
    EditText etPwdOrCode;
    @BindView(R.id.btn_login)
    TextView btnLogin;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initEventAndData() {

        EventBusUtils.register(this);

        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {


                if (!StringUtils.isEmpty(etPhone.getText())&& !StringUtils.isEmpty(etPwdOrCode.getText())){


                    btnLogin.setBackground(getResources().getDrawable(R.mipmap.loginbs));

                }else{

                    btnLogin.setBackground(getResources().getDrawable(R.mipmap.loginb));

                }

            }
        });

        etPwdOrCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!StringUtils.isEmpty(etPhone.getText())&& !StringUtils.isEmpty(etPwdOrCode.getText())){


                    btnLogin.setBackground(getResources().getDrawable(R.mipmap.loginbs));

                }else{

                    btnLogin.setBackground(getResources().getDrawable(R.mipmap.loginb));

                }

            }

        });


    }

    @Override
    protected void isStart() {

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


    @OnClick(R.id.btn_login)
    public void onViewClicked() {

        if (!NoDoubleClickUtils.isDoubleClick()) {


            if (StringUtils.isEmpty(etPhone.getText())){
                ToastUtils.showLong("请输入用户名");
                return;
            }

            if (StringUtils.isEmpty(etPwdOrCode.getText())){
                ToastUtils.showLong("请输入密码");
                return;
            }


            HashMap map = new HashMap();

            map.put("methodName","appLogin");
            map.put("loginName",etPhone.getText().toString());
            map.put("loginPassword",etPwdOrCode.getText().toString());


            TcpClient.getInstance().sendChsPrtCmds(new Gson().toJson(map), 1001);

            getDialog().show();



        }

    }

    @Subscribe
    public void onEvent(AnyEventTypes event) {

        getDialog().dismiss();

        if (StringUtils.equals("appLogin",event.getEventCode())) {



            SPUtils.getInstance().put("user", etPhone.getText().toString());
            readyGo(HomeActivity.class);
            finish();

        }



    }

    @Override
    protected void onDestroy() {
        EventBusUtils.unregister(this);
        super.onDestroy();
    }
}