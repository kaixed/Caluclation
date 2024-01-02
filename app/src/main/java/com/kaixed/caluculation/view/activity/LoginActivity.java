package com.kaixed.caluculation.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.immersionbar.ImmersionBar;
import com.kaixed.caluculation.R;
import com.kaixed.caluculation.common.AppDatabase;
import com.kaixed.caluculation.entity.User;

/**
 * @author hui
 */
public class LoginActivity extends AppCompatActivity {

    private TextView mTvRegister;
    private Button mBtnLogin;
    private EditText mEtUsername;
    private EditText mEtPasswd;
    private GradientDrawable drawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ImmersionBar.with(this).statusBarDarkFont(true).init();

        initView();

        setRegisterText();


        drawable = new GradientDrawable();
        drawable.setColor(Color.rgb(214, 220, 227));
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(52);


        mBtnLogin.setBackground(drawable);

        mEtUsername.addTextChangedListener(textWatcher);

        mEtPasswd.addTextChangedListener(textWatcher);

        mBtnLogin.setOnClickListener(view -> {
            String username = mEtUsername.getText().toString();
            String passwd = mEtPasswd.getText().toString();
            loginUser(username, passwd);
        });

    }

    private void loginUser(String username, String passwd) {
        // 在后台线程中执行数据库操作
        AsyncTask.execute(() -> {
            AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
            // 检查用户是否存在
            User user = db.userDao().getUserByUsername(username);
            if (user == null) {
                runOnUiThread(() -> Toast.makeText(LoginActivity.this, "用户不存在", Toast.LENGTH_SHORT).show());
            } else {
                // 检查密码是否匹配
                if (user.password.equals(passwd)) {
                    runOnUiThread(() -> {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }


    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            updateBtnState();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    public void setRegisterText() {
        String str = "还没有用户？点击注册";
        int startIndex = str.indexOf("点击注册");
        int endIndex = startIndex + "点击注册".length();

        SpannableString spannableString = new SpannableString(str);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                // 处理点击事件的逻辑
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                // 设置突出文字的颜色
                int color = Color.parseColor("#2196F3");
                ds.setColor(color);
                // 设置突出文字的下划线
                ds.setUnderlineText(false);
            }
        };

        spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 设置 TextView 可点击和链接可点击
        mTvRegister.setMovementMethod(LinkMovementMethod.getInstance());
        mTvRegister.setHighlightColor(Color.parseColor("#00000000"));
        mTvRegister.setText(spannableString);
    }

    public void updateBtnState() {
        if (!mEtUsername.getText().toString().isEmpty() && !mEtPasswd.getText().toString().isEmpty()) {
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable = new GradientDrawable();
            gradientDrawable.setColor(Color.parseColor("#2196F3"));
            gradientDrawable.setShape(GradientDrawable.RECTANGLE);
            gradientDrawable.setCornerRadius(32);
            mBtnLogin.setBackground(gradientDrawable);

            mBtnLogin.setTextColor(Color.parseColor("#FFFFFFFF"));
        } else {

            mBtnLogin.setBackground(drawable);
            mBtnLogin.setTextColor(Color.parseColor("#000000"));

        }
    }



    public void initView() {
        mTvRegister = findViewById(R.id.tv_register);
        mBtnLogin = findViewById(R.id.btn_login);
        mEtUsername = findViewById(R.id.et_username);
        mEtPasswd = findViewById(R.id.et_passwd);
    }
}