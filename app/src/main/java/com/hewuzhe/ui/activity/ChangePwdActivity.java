package com.hewuzhe.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hewuzhe.R;
import com.hewuzhe.model.ChangePwd;
import com.hewuzhe.presenter.ChangePwdPresenter;
import com.hewuzhe.ui.base.ToolBarActivity;
import com.hewuzhe.view.ChangePwdView;

import butterknife.Bind;

public class ChangePwdActivity extends ToolBarActivity<ChangePwdPresenter> implements ChangePwdView {

    @Bind(R.id.edt_old_pwd)
    EditText _EdtOldPwd;
    @Bind(R.id.edt_pwd)
    EditText _EdtPwd;
    @Bind(R.id.edt_pwd_again)
    EditText _EdtPwdAgain;
    @Bind(R.id.btn_ensure)
    Button _BtnEnsure;

    /**
     * @return 提供标题
     */
    @Override
    protected CharSequence provideTitle() {
        return "修改密码";
    }

    /**
     * @return 提供LayoutId
     */
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_change_pwd;
    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {
        _BtnEnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.changePwd();
            }
        });

    }

    /**
     * 绑定Presenter
     */
    @Override
    public ChangePwdPresenter createPresenter() {
        return new ChangePwdPresenter();
    }

    @Override
    public ChangePwd getData() {
        ChangePwd changePwd = new ChangePwd();
        changePwd.oldPwd = _EdtOldPwd.getText().toString().trim();
        changePwd.newPwd = _EdtPwd.getText().toString().trim();
        changePwd.newPwdAgain = _EdtPwdAgain.getText().toString().trim();

        return changePwd;
    }
}
