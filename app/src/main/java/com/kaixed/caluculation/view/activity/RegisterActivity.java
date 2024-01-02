package com.kaixed.caluculation.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gyf.immersionbar.ImmersionBar;
import com.kaixed.caluculation.R;
import com.kaixed.caluculation.common.AppDatabase;
import com.kaixed.caluculation.entity.User;

/**
 * @author hui
 */
public class RegisterActivity extends AppCompatActivity {

    private EditText mEtUsername;
    private EditText mEtPasswd;
    private EditText mEtRePasswd;
    private Button mBtnRegister;
    private GradientDrawable drawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ImmersionBar.with(this).statusBarDarkFont(true).init();

        initView();

        drawable = new GradientDrawable();
        drawable.setColor(Color.rgb(214, 220, 227));
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(52);


        mBtnRegister.setBackground(drawable);

        mEtUsername.addTextChangedListener(textWatcher);
        mEtPasswd.addTextChangedListener(textWatcher);
        mEtRePasswd.addTextChangedListener(textWatcher);

        mBtnRegister.setOnClickListener(view -> {
            String username = mEtUsername.getText().toString();
            String passwd = mEtPasswd.getText().toString();
            String rePasswd = mEtRePasswd.getText().toString();

            if (!username.isEmpty() && !passwd.isEmpty() && !rePasswd.isEmpty()) {
                if (passwd.equals(rePasswd)) {
                    registerUser(username, passwd);
                } else {
                    Toast.makeText(this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                }

            }

        });


    }

    public void initView() {
        mEtUsername = findViewById(R.id.et_username);
        mEtPasswd = findViewById(R.id.et_passwd);
        mEtRePasswd = findViewById(R.id.et_rePasswd);
        mBtnRegister = findViewById(R.id.btn_register);
    }

    public void updateBtnState() {
        if (!mEtUsername.getText().toString().isEmpty() && !mEtPasswd.getText().toString().isEmpty() && !mEtRePasswd.getText().toString().isEmpty()) {
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable = new GradientDrawable();
            gradientDrawable.setColor(Color.parseColor("#2196F3"));
            gradientDrawable.setShape(GradientDrawable.RECTANGLE);
            gradientDrawable.setCornerRadius(32);
            mBtnRegister.setBackground(gradientDrawable);

            mBtnRegister.setTextColor(Color.parseColor("#FFFFFFFF"));
        } else {

            mBtnRegister.setBackground(drawable);
            mBtnRegister.setTextColor(Color.parseColor("#000000"));
        }
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

    private void registerUser(String username, String passwd) {
        // 在后台线程中执行数据库操作
        AsyncTask.execute(() -> {
            AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
            // 检查用户是否存在
            User existingUser = db.userDao().getUserByUsername(username);
            if (existingUser != null) {
                runOnUiThread(() -> Toast.makeText(RegisterActivity.this, "用户名已存在", Toast.LENGTH_SHORT).show());
                return;
            }
            // 创建新用户并插入数据库
            User newUser = new User();
            newUser.username = username;
            newUser.password = passwd;
            db.userDao().insert(newUser);
            runOnUiThread(() -> {
                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                finish(); // 注册成功后关闭当前Activity
            });
        });
    }
}