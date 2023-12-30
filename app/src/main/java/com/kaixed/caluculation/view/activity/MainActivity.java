package com.kaixed.caluculation.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.kaixed.caluculation.R;

/**
 * @author hui
 */
public class MainActivity extends AppCompatActivity {

    private RelativeLayout mRlNormal;
    private LinearLayout mLlLimited;
    private LinearLayout mLlMistakes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImmersionBar.with(this).statusBarDarkFont(true).init();

        initView();

        mRlNormal.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, TrainActivity.class);
            startActivity(intent);
        });

        mLlLimited.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, LimitedActivity.class);
            startActivity(intent);
        });

        mLlMistakes.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MistakesActivity.class);
            startActivity(intent);
        });

    }

    public void initView() {
        mRlNormal = findViewById(R.id.rl_normal);
        mLlLimited = findViewById(R.id.ll_list_1);
        mLlMistakes = findViewById(R.id.ll_list_2);
    }
}