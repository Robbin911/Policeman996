package com.example.policeman996.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.policeman996.R;
import com.example.policeman996.Utils.JumpUtils;
import com.example.policeman996.Utils.ResponseUtils;
import com.example.policeman996.presenter.Interface.IRegisterView;
import com.example.policeman996.presenter.RegisterPresenter;
import com.rengwuxian.materialedittext.MaterialEditText;
import es.dmoral.toasty.Toasty;

public class RegisterActivity extends AppCompatActivity implements IRegisterView {


  @BindView(R.id.root_layout)
  RelativeLayout root;
  @BindView(R.id.username)
  MaterialEditText mUsernameEdit;
  @BindView(R.id.password)
  MaterialEditText mPasswordEdit;
  @BindView(R.id.password_confirm)
  MaterialEditText mPasswordConfirmEdit;
  @BindView(R.id.btn_confirm)
  CardView mConfirm;

  OnClickListener mListener;
  RegisterPresenter mPresenter;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.register_layout);
    ButterKnife.bind(this);

    mPresenter = new RegisterPresenter(this);
    mListener = new OnClickListener() {
      @Override
      public void onClick(View v) {
        switch (v.getId()) {
          case R.id.root_layout:
            if (mUsernameEdit != null && mPasswordEdit != null && mPasswordConfirmEdit != null) {
              mUsernameEdit.clearFocus();
              mPasswordEdit.clearFocus();
              mPasswordConfirmEdit.clearFocus();
              InputMethodManager imm = (InputMethodManager) RegisterActivity.this
                  .getSystemService(Context.INPUT_METHOD_SERVICE);
              // 隐藏软键盘
              if (imm != null) {
                imm.hideSoftInputFromWindow(
                    RegisterActivity.this.getWindow().getDecorView().getWindowToken(), 0);
              }
            }
            break;
          case R.id.btn_confirm:
            String account = mUsernameEdit.getText().toString();
            String pw = mPasswordEdit.getText().toString();
            String pwConfirm = mPasswordConfirmEdit.getText().toString();

            if (!account.equals("") && !pw.equals("") && !pwConfirm.equals("")) {
              if(account.length()<4||account.length()>16){
                Toasty
                    .error(RegisterActivity.this, "User Name Length Error!", Toast.LENGTH_SHORT,
                        true)
                    .show();
                break;
              }

              if (pw.equals(pwConfirm)) {
                if (pw.length() < 4 || pw.length() > 16) {
                  Toasty
                      .error(RegisterActivity.this, "Password Length Error!", Toast.LENGTH_SHORT,
                          true)
                      .show();
                } else {
                  mPresenter.register(account, pw);
                }

              } else {
                Toasty
                    .error(RegisterActivity.this, "Password Inconsistent!", Toast.LENGTH_SHORT,
                        true)
                    .show();
              }
            } else {
              Toasty
                  .error(RegisterActivity.this, "Information Incomplete!", Toast.LENGTH_SHORT, true)
                  .show();
            }
            break;
        }
      }
    };
    mConfirm.setOnClickListener(mListener);
    root.setOnClickListener(mListener);
  }

  @Override
  public void onRegisterSuccess() {
    JumpUtils.startMainPagerActivity(this);
  }

  @Override
  public void onRegisterFail(String msg) {
    ResponseUtils.handleErrorText(this, msg);
  }
}
