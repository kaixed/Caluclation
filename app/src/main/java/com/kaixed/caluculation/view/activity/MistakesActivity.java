package com.kaixed.caluculation.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.gyf.immersionbar.ImmersionBar;
import com.kaixed.caluculation.R;
import com.kaixed.caluculation.entity.Mistakes;
import com.kaixed.caluculation.view.adapter.MistakesAdapter;

import java.util.ArrayList;
import java.util.List;

public class MistakesActivity extends AppCompatActivity {

    private RecyclerView mRecycleMistakes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mistakes);
        ImmersionBar.with(this).statusBarDarkFont(true).init();


        initView();

        mRecycleMistakes.setAdapter(new MistakesAdapter(getData()));
        mRecycleMistakes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void initView() {
        mRecycleMistakes = findViewById(R.id.recycle_mistakes);
    }

    private static List<Mistakes> getData() {
        List<Mistakes> mistakes = new ArrayList<>();

        mistakes.add(new Mistakes("1+1", "2", "23", "30", 1));
        mistakes.add(new Mistakes("1+1", "2", "23", "30", 1));
        mistakes.add(new Mistakes("1+1", "2", "23", "30", 1));

        return mistakes;
    }
}