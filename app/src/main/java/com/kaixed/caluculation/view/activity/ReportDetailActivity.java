package com.kaixed.caluculation.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.immersionbar.ImmersionBar;
import com.kaixed.caluculation.R;
import com.kaixed.caluculation.common.AppDatabase;
import com.kaixed.caluculation.entity.UniqueEquation;
import com.kaixed.caluculation.view.adapter.ReportDetailAdapter;

import java.util.ArrayList;
import java.util.List;

public class ReportDetailActivity extends AppCompatActivity {

    private ReportDetailAdapter reportDetailAdapter;
    private List<UniqueEquation> uniqueEquationList;
    private RecyclerView mRecycleReportDetail;
    private TextView mTvDesc;

    private int total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_detail);
        ImmersionBar.with(this).statusBarDarkFont(true).init();

        mRecycleReportDetail = findViewById(R.id.recycle_report_detail);
        mTvDesc = findViewById(R.id.tv_desc);

        uniqueEquationList = new ArrayList<>();

        Intent intent = getIntent();

        String equationId = intent.getStringExtra("time");

        findItemsWithId(equationId);


    }

    private void findItemsWithId(String equationId) {

        // 在后台线程中执行数据库操作
        AsyncTask.execute(() -> {
            int a = 0;
            int b = 0;
            AppDatabase db = AppDatabase.getDatabase(getApplicationContext());

            uniqueEquationList = db.equationDao().findItemsWithId(equationId);

            mRecycleReportDetail.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            mRecycleReportDetail.setAdapter(new ReportDetailAdapter(uniqueEquationList));

            total = uniqueEquationList.size();
            for (UniqueEquation un : uniqueEquationList) {

                if (un.getResult().equals(un.getInPutValue())) {
                    a++;
                } else {
                    b++;
                }
            }
            mTvDesc.setText("共完成" + total + "道题目，答对" + a + "道，答错" + b + "道");

        });
    }
}