package com.kaixed.caluculation.view.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.kaixed.caluculation.R;
import com.kaixed.caluculation.common.AppDatabase;
import com.kaixed.caluculation.entity.Records;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author hui
 */
public class MainActivity extends AppCompatActivity {

    private RelativeLayout mRlNormal;
    private RelativeLayout mRlReport;
    private LinearLayout mLlLimited;
    private LinearLayout mLlMistakes;
    private ImageView mIvSwitch;
    private TextView mTvGroup;
    int m = 0;
    int n = 0;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImmersionBar.with(this).statusBarDarkFont(true).init();

        initView();


        sp = getSharedPreferences("group", MODE_PRIVATE);
        int a = sp.getInt("group", 0);

        TextView textView = findViewById(R.id.tv_group);
        textView.setText((a + 1) + "年级");


        mRlNormal.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, TrainActivity.class);
            startActivity(intent);
        });

        mLlLimited.setOnClickListener(view -> {

            // 获取当前时间
            Date currentDate = new Date();

            // 指定日期格式
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
            // 格式化日期
            String formattedDate = dateFormat.format(currentDate);

            insert(formattedDate);

            Intent intent = new Intent(MainActivity.this, LimitedActivity.class);
            intent.putExtra("time", formattedDate);
            startActivity(intent);
        });

        mLlMistakes.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MistakesActivity.class);
            startActivity(intent);
        });

        mRlReport.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ReportActivity.class);
            startActivity(intent);
        });

        mTvGroup.setOnClickListener(view -> {
            showSwitchDialog(this);
        });

        mIvSwitch.setOnClickListener(view -> {
            showSwitchDialog(this);
        });
    }


    private void insert(String time) {

        // 在后台线程中执行数据库操作
        AsyncTask.execute(() -> {
            AppDatabase db = AppDatabase.getDatabase(getApplicationContext());

            Records records = new Records();

            records.time = time;


            db.recordsDao().insert(records);

        });
    }


    public void showSwitchDialog(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View customView = inflater.inflate(R.layout.custom_dialog_layout, null);

        m = sp.getInt("group", 0);
        n = sp.getInt("counts", 15);

        // 创建AlertDialog.Builder对象
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomDialogStyle);
        builder.setView(customView);
        // 创建对话框并显示
        AlertDialog dialog = builder.create();

        GridLayout gridLayout = customView.findViewById(R.id.gridLayout);

        final int[] checkedRadioButtonId = {-1};

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            View view = gridLayout.getChildAt(i);

            if (view instanceof RadioButton) {
                RadioButton radioButton = (RadioButton) view;

                // 设置默认选中的RadioButton
                if (i == m) {
                    radioButton.setChecked(true);
                    checkedRadioButtonId[0] = radioButton.getId();
                }

                int finalI = i;
                radioButton.setOnClickListener(v -> {
                    int currentId = radioButton.getId();


                    if (currentId == checkedRadioButtonId[0]) {
                        return;
                    }

                    m = finalI;

                    if (checkedRadioButtonId[0] != -1) {
                        RadioButton previousRadioButton = customView.findViewById(checkedRadioButtonId[0]);
                        previousRadioButton.setChecked(false);
                    }

                    checkedRadioButtonId[0] = currentId;
                });
            }
        }


        RadioGroup radioGroup = customView.findViewById(R.id.radioGroup);


        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            View view = radioGroup.getChildAt(i);
            if (view instanceof RadioButton) {
                RadioButton radioButton = (RadioButton) view;
                if (radioButton.getText().toString().equals(String.valueOf(n))) {
                    radioButton.setChecked(true);
                    break;
                }
            }
        }

// 设置 RadioGroup 的选择监听器
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedRadioButton = customView.findViewById(checkedId);
            if (selectedRadioButton != null) {
                n = Integer.parseInt(selectedRadioButton.getText().toString());
            }
        });

        Button button = customView.findViewById(R.id.btn_save);
        button.setOnClickListener(view -> {
            saveGroup(context, m, n);
            dialog.dismiss();
        });


        dialog.show();
        float dpValue = 250.0f;
        float dpValue1 = 260.0f;
        // 获取当前屏幕的密度
        float density = context.getResources().getDisplayMetrics().density;

        int pxValue = (int) (dpValue * density + 0.5f);
        int pxValue1 = (int) (dpValue1 * density + 0.5f);

        Window window = dialog.getWindow();

        assert window != null;
        window.setLayout(pxValue, pxValue1);

    }

    public void saveGroup(Context context, int group, int counts) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("group", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("group", group);
        editor.putInt("counts", counts);
        editor.apply();

    }

    public void initView() {
        mRlNormal = findViewById(R.id.rl_normal);
        mLlLimited = findViewById(R.id.ll_list_1);
        mLlMistakes = findViewById(R.id.ll_list_2);
        mRlReport = findViewById(R.id.rl_report);
        mIvSwitch = findViewById(R.id.iv_switch);
        mTvGroup = findViewById(R.id.tv_group);
    }
}