package com.kaixed.caluculation.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.gyf.immersionbar.ImmersionBar;
import com.kaixed.caluculation.R;
import com.kaixed.caluculation.common.AutoScrollLayoutManager;
import com.kaixed.caluculation.entity.Equation;
import com.kaixed.caluculation.model.NewFourNum;
import com.kaixed.caluculation.view.adapter.Adapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hui
 */
public class TrainActivity extends AppCompatActivity {

    private RecyclerView mRvEquation;
    private Adapter adapter;

    private AutoScrollLayoutManager autoScrollLayoutManager;

    private List<Equation> equations;

    int initValue = 0;

    private final StringBuilder stringBuilder = new StringBuilder();
    private final int[] buttonIds = {R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9, R.id.btn_determine, R.id.btn_clear};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);
        ImmersionBar.with(this).statusBarDarkFont(true).init();

        initView();

        equations = new ArrayList<>();
        generateEquation();

        adapter = new Adapter(equations);

        mRvEquation.setAdapter(adapter);

        autoScrollLayoutManager = new AutoScrollLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRvEquation.setLayoutManager(autoScrollLayoutManager);

        adapter.setSelectedPosition(initValue);

        for (int buttonId : buttonIds) {
            Button button = findViewById(buttonId);
            button.setOnClickListener(v -> handleButtonClick(button.getText().toString()));
        }

    }

    private void generateEquation() {

        SharedPreferences sp;
        sp = getSharedPreferences("group", MODE_PRIVATE);
        int group = sp.getInt("group", 0);
        int counts = sp.getInt("counts", 15);


        NewFourNum questionGenerator = new NewFourNum(group + 1, counts);
        equations.addAll(questionGenerator.getEquations());

    }


    private void handleButtonClick(String haha) {

        if ("确认".equals(haha)) {
            if (stringBuilder.length() != 0) {

                Equation currentEquation = adapter.getData().get(initValue);

                if (currentEquation.getInPutValue().equals(currentEquation.getResult())) {
                    currentEquation.setTrue(true);
                    adapter.notifyItemChanged(initValue);
                    autoScrollLayoutManager.scrollToPositionWithTop(initValue + 1, mRvEquation);
                    adapter.setSelectedPosition(initValue);

                    if (initValue < adapter.getItemCount()) {
                        initValue++;
                        adapter.setSelectedPosition(initValue);
                    }
                    if (initValue == equations.size()) {
                        Toast.makeText(this, "请开始你的下一轮训练", Toast.LENGTH_SHORT).show();
                    }
                    stringBuilder.setLength(0);
                } else {
                    Toast.makeText(this, "请再次尝试", Toast.LENGTH_SHORT).show();
                    currentEquation.setInPutValue("");
                    adapter.notifyItemChanged(initValue);
                    stringBuilder.setLength(0);
                }


            } else {
                Toast.makeText(this, "请输入您的答案", Toast.LENGTH_SHORT).show();
            }


        } else if ("清除".equals(haha)) {

            stringBuilder.setLength(0);

            Equation currentEquation = adapter.getData().get(initValue);
            currentEquation.setInPutValue("");
            adapter.setSelectedPosition(initValue);

            adapter.notifyItemChanged(initValue);
        } else {
            stringBuilder.append(haha);

            String inputValue = stringBuilder.toString();

            Equation currentEquation = adapter.getData().get(initValue);

            currentEquation.setInPutValue(inputValue);

            adapter.setSelectedPosition(initValue);
            adapter.notifyItemChanged(initValue);

        }
    }

    @SuppressLint("ClickableViewAccessibility")
    public void initView() {
        mRvEquation = findViewById(R.id.rv_calculation);
        mRvEquation.setOnTouchListener((view, event) -> true);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}