package com.kaixed.caluculation.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;

import com.gyf.immersionbar.ImmersionBar;
import com.kaixed.caluculation.R;
import com.kaixed.caluculation.common.AppDatabase;
import com.kaixed.caluculation.entity.Records;
import com.kaixed.caluculation.entity.Reports;
import com.kaixed.caluculation.view.adapter.ReportsAdapter;

import java.util.ArrayList;
import java.util.List;

public class ReportActivity extends AppCompatActivity {

    private RecyclerView mRecycleReports;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ImmersionBar.with(this).statusBarDarkFont(true).init();

        list = new ArrayList<>();

        initView();

        findRecords();


    }

    private void findRecords() {
        // 在后台线程中执行数据库操作
        AsyncTask.execute(() -> {
            AppDatabase db = AppDatabase.getDatabase(getApplicationContext());

            List<Records> records = db.recordsDao().getAllItemsInOrder();

            for (Records records1 : records) {
                list.add(records1.getTime());
            }

            ReportsAdapter reportsAdapter = new ReportsAdapter(this, list);

            mRecycleReports.setAdapter(reportsAdapter);
            mRecycleReports.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        });
    }


    public void initView() {
        mRecycleReports = findViewById(R.id.recycle_reports);
    }

}