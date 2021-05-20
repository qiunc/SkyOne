package com.tj.skyone.widget.dialog;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tj.skyone.R;
import com.tj.skyone.bean.UserBean;
import com.tj.skyone.utils.NoDoubleClickUtils;
import com.tj.skyone.utils.eventbus.EventBusConsts;
import com.tj.skyone.utils.eventbus.EventBusUtils;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 */
public class CreateUserDialog extends AppCompatDialog {


    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.et_pwd_to)
    EditText etPwdTo;
    @BindView(R.id.btn1)
    TextView btn1;
    @BindView(R.id.btn2)
    TextView btn2;

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_pwd)
    TextView tvPwd;

    private boolean tag = false;

    public CreateUserDialog(Context context,boolean tag) {
        super(context, R.style.dialogstyle);
        this.tag = tag;


        setContentView(R.layout.dialog_user_add);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        this.setCancelable(false);
        ButterKnife.bind(this);



        if (tag){

            title.setText("修改密码");
            tvPwd.setText("新  密  码：");

        }



    }


    @OnClick({R.id.btn1, R.id.btn2})
    public void onViewClicked(View view) {
        if (!NoDoubleClickUtils.isDoubleClick()) {
            switch (view.getId()) {
                case R.id.btn1:

                    if (StringUtils.isEmpty(etPhone.getText())){
                        ToastUtils.showLong("请输入用户名");
                        return;
                    }


                    if (StringUtils.isEmpty(etPwd.getText())){
                        ToastUtils.showLong("请输入密码");
                        return;
                    }

                    if (etPwd.getText().length() < 4){
                        ToastUtils.showLong("请输入4到8位字符");
                        return;
                    }

                    if (StringUtils.isEmpty(etPwdTo.getText())){
                        ToastUtils.showLong("请输再次入密码");
                        return;
                    }


                    if (!StringUtils.equals(etPwd.getText(),etPwdTo.getText())){
                        ToastUtils.showLong("两次密码请保持一致");
                        return;
                    }


                    if (tag){
                        UserBean bean = new UserBean();
                        bean.setUser(etPhone.getText().toString());
                        bean.setPwd(etPwdTo.getText().toString());

                        EventBusUtils.post(EventBusConsts.UP_USER,bean);

                    }else{

                        UserBean bean = new UserBean();
                        bean.setUser(etPhone.getText().toString());
                        bean.setPwd(etPwdTo.getText().toString());

                        EventBusUtils.post(EventBusConsts.CT_USER,bean);

                    }





                    break;
                case R.id.btn2:
                    dismiss();
                    break;
            }
        }
    }
}
