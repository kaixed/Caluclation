package com.kaixed.caluculation.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;

import com.gyf.immersionbar.ImmersionBar;
import com.kaixed.caluculation.R;
import com.kaixed.caluculation.common.AppDatabase;
import com.kaixed.caluculation.entity.Mistakes;
import com.kaixed.caluculation.view.adapter.MistakesAdapter;

import java.util.ArrayList;
import java.util.List;

public class MistakesActivity extends AppCompatActivity {

    private RecyclerView mRecycleMistakes;
    private List<Mistakes> mistakes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mistakes);
        ImmersionBar.with(this).statusBarDarkFont(true).init();

        mistakes = new ArrayList<>();
        initView();
        getAllItems();


    }

    private void getAllItems() {
        AsyncTask.execute(() -> {
            AppDatabase db = AppDatabase.getDatabase(getApplicationContext());

            mistakes = db.mistakesDao().getAllItems();

            mRecycleMistakes.setAdapter(new MistakesAdapter(mistakes));
            mRecycleMistakes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        });
    }

    private void initView() {
        mRecycleMistakes = findViewById(R.id.recycle_mistakes);
    }


}