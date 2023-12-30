package com.kaixed.caluculation.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.gyf.immersionbar.ImmersionBar;
import com.kaixed.caluculation.R;
import com.kaixed.caluculation.common.AutoScrollLayoutManager;
import com.kaixed.caluculation.entity.Equation;
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

    int initValue = 0;
    int initValue1 = 1;
    private boolean isFinished = false;

    private final StringBuilder stringBuilder = new StringBuilder();
    private final int[] buttonIds = {R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9, R.id.btn_determine, R.id.btn_clear};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);
        ImmersionBar.with(this).statusBarDarkFont(true).init();

        initView();
        adapter = new Adapter(getEquation());
        mRvEquation.setAdapter(adapter);

        autoScrollLayoutManager = new AutoScrollLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRvEquation.setLayoutManager(autoScrollLayoutManager);

        for (int buttonId : buttonIds) {
            Button button = findViewById(buttonId);
            button.setOnClickListener(v -> handleButtonClick(button.getText().toString()));
        }

    }

    private List<Equation> getEquation() {
        List<Equation> equations = new ArrayList<>();
        equations.add(new Equation("1+1", "", ""));
        equations.add(new Equation("1+1", "", ""));
        equations.add(new Equation("1+1", "", ""));
        equations.add(new Equation("1+1", "", ""));
        equations.add(new Equation("1+1", "", ""));
        equations.add(new Equation("1+1", "", ""));
        equations.add(new Equation("1+1", "", ""));
        equations.add(new Equation("1+1", "", ""));
        equations.add(new Equation("1+1", "", ""));
        equations.add(new Equation("1+1", "", ""));
        equations.add(new Equation("1+1", "", ""));
        equations.add(new Equation("1+1", "", ""));
        equations.add(new Equation("1+1", "", ""));
        equations.add(new Equation("1+1", "", ""));
        equations.add(new Equation("1+1", "", ""));
        equations.add(new Equation("1+1", "", ""));
        equations.add(new Equation("1+1", "", ""));
        equations.add(new Equation("1+1", "", ""));
        equations.add(new Equation("1+1", "", ""));
        equations.add(new Equation("1+1", "", ""));
        equations.add(new Equation("1+1", "", ""));
        equations.add(new Equation("1+1", "", ""));
        equations.add(new Equation("1+1", "", ""));
        equations.add(new Equation("1+1", "", ""));
        equations.add(new Equation("1+1", "", ""));
        return equations;
    }

    private void handleButtonClick(String haha) {

        if (!isFinished) {
            if ("确认".equals(haha) ) {
                if (stringBuilder.length() != 0){
                    autoScrollLayoutManager.scrollToPositionWithTop(initValue1, mRvEquation);

                    if (initValue <= adapter.getItemCount()) {
                        initValue++;
                        initValue1++;
                    }

                    if (initValue == adapter.getItemCount()) {
                        isFinished = true;
                    }

                    stringBuilder.setLength(0);
                }else {
                    Toast.makeText(this, "请输入您的答案", Toast.LENGTH_SHORT).show();
                }



            } else if ("清除".equals(haha)) {

                stringBuilder.setLength(0);

                Equation currentEquation = adapter.getData().get(initValue);
                currentEquation.setResult("");

                adapter.notifyItemChanged(initValue);
            } else {
                stringBuilder.append(haha);

                String inputValue = stringBuilder.toString();

                Equation currentEquation = adapter.getData().get(initValue);

                // 修改Equation对象的数据
                currentEquation.setResult(inputValue);
                currentEquation.setInPutValue(inputValue);

                // 通知适配器更新
                adapter.notifyItemChanged(initValue);

            }
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