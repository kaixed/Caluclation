package com.kaixed.caluculation.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.immersionbar.ImmersionBar;
import com.kaixed.caluculation.R;
import com.kaixed.caluculation.common.AppDatabase;
import com.kaixed.caluculation.common.AutoScrollLayoutManager;
import com.kaixed.caluculation.entity.Equation;
import com.kaixed.caluculation.entity.Mistakes;
import com.kaixed.caluculation.entity.UniqueEquation;
import com.kaixed.caluculation.entity.User;
import com.kaixed.caluculation.model.NewFourNum;
import com.kaixed.caluculation.view.adapter.LimitedAdapter;
import com.kaixed.caluculation.widget.SmoothCheckBox;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hui
 */
public class LimitedActivity extends AppCompatActivity {

    private RecyclerView mRvEquation;
    private LimitedAdapter adapter;
    private SmoothCheckBox mScbPass;
    private List<UniqueEquation> equations;
    private CountDownTimer countDownTimer;
    private AutoScrollLayoutManager autoScrollLayoutManager;

    private long secondsRemaining;

    int initValue = 0;

    private TextView countdownTextView;
    private String time;

    private final StringBuilder stringBuilder = new StringBuilder();
    private final int[] buttonIds = {R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9, R.id.btn_determine, R.id.btn_clear};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_limited);
        ImmersionBar.with(this).statusBarDarkFont(true).init();

        initView();
        adapter = new LimitedAdapter(getEquation());

        Intent intent = getIntent();

        time = intent.getStringExtra("time");

        mRvEquation.setAdapter(adapter);

        adapter.setSelectedPosition(initValue);

        autoScrollLayoutManager = new AutoScrollLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRvEquation.setLayoutManager(autoScrollLayoutManager);

        for (int buttonId : buttonIds) {
            Button button = findViewById(buttonId);
            button.setOnClickListener(v -> handleButtonClick(button.getText().toString()));
        }

        start();

    }

    private void start() {
        countDownTimer = new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                secondsRemaining = millisUntilFinished / 1000;
                countdownTextView.setText("剩余时间：" + secondsRemaining + "秒");
            }

            public void onFinish() {

                countDownTimer.start();
                countdownTextView.setText("倒计时完成");
            }
        };
        countDownTimer.start();
    }

    private List<UniqueEquation> getEquation() {
        equations = new ArrayList<>();
        SharedPreferences sp;
        sp = getSharedPreferences("group", MODE_PRIVATE);
        int group = sp.getInt("group", 0);
        int counts = sp.getInt("counts", 15);

        NewFourNum questionGenerator = new NewFourNum(group + 1, counts);

        for (Equation equation : questionGenerator.getEquations()) {
            equations.add(new UniqueEquation(equation.getEquation(), equation.getResult(), "", ""));
        }
        return equations;
    }

    private void handleButtonClick(String haha) {

        if ("确认".equals(haha)) {
            if (stringBuilder.length() != 0) {

                UniqueEquation currentEquation = adapter.getData().get(initValue);
                currentEquation.setTime(String.valueOf(10 - secondsRemaining));
                currentEquation.setInPutValue(stringBuilder.toString());
                adapter.notifyItemChanged(initValue);
                autoScrollLayoutManager.scrollToPositionWithTop(initValue + 1, mRvEquation);
                adapter.setSelectedPosition(initValue);

                countDownTimer.cancel();

                start();

                if (initValue < adapter.getItemCount()) {
                    initValue++;
                    adapter.setSelectedPosition(initValue);
                }
                if (initValue == equations.size()) {

                    List<UniqueEquation> uniqueEquation = adapter.getData();

                    int i = 0;

                    for (UniqueEquation uniqueEquation1 : uniqueEquation) {

                        if (!uniqueEquation1.getResult().equals(uniqueEquation1.getInPutValue())) {
                            Mistakes mistakes = new Mistakes();
                            mistakes.setCounts(1);
                            mistakes.setEquation(uniqueEquation1.getEquation());
                            mistakes.setTime(String.valueOf(10 - secondsRemaining));
                            mistakes.setResult(uniqueEquation1.getResult());
                            mistakes.setInPutValue(uniqueEquation1.getInPutValue());
                            mistakes.setTrue(false);

                            saveMisTakes(mistakes);
                        }

                        uniqueEquation1.setEquationId(time + "-" + i);
                        saveLimited(uniqueEquation1);
                        i++;
                    }

                    Intent intent = new Intent(LimitedActivity.this, ReportDetailActivity.class);
                    intent.putExtra("time", time);
                    startActivity(intent);
                    finish();

                }
                stringBuilder.setLength(0);
            } else {
                Toast.makeText(this, "请输入您的答案", Toast.LENGTH_SHORT).show();
            }


        } else if ("清除".equals(haha)) {

            stringBuilder.setLength(0);

            UniqueEquation currentEquation = adapter.getData().get(initValue);
            currentEquation.setInPutValue("");
            adapter.setSelectedPosition(initValue);

            adapter.notifyItemChanged(initValue);
        } else {
            stringBuilder.append(haha);

            String inputValue = stringBuilder.toString();
            UniqueEquation currentEquation = adapter.getData().get(initValue);

            currentEquation.setInPutValue(inputValue);

            adapter.setSelectedPosition(initValue);
            adapter.notifyItemChanged(initValue);

        }
    }

    private void saveLimited(UniqueEquation uniqueEquation) {

        // 在后台线程中执行数据库操作
        AsyncTask.execute(() -> {
            AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
            db.equationDao().insert(uniqueEquation);

        });
    }

    private void saveMisTakes(Mistakes mistakes) {

        // 在后台线程中执行数据库操作
        AsyncTask.execute(() -> {
            AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
            db.mistakesDao().insert(mistakes);

        });
    }

    @SuppressLint("ClickableViewAccessibility")
    public void initView() {
        mRvEquation = findViewById(R.id.rv_calculation);

        mRvEquation.setOnTouchListener((view, event) -> true);

        mScbPass = findViewById(R.id.scb_pass);

        mScbPass.setClickable(false);
        mScbPass.setChecked(true);
        countdownTextView = findViewById(R.id.tv_time);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 在Activity销毁时取消倒计时，以防止内存泄漏
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}