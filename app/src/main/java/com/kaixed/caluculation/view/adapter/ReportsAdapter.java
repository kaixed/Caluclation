package com.kaixed.caluculation.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.kaixed.caluculation.R;
import com.kaixed.caluculation.view.activity.ReportDetailActivity;

import java.util.List;

/**
 * @Author: kaixed
 * @Date: 2023/12/30 16:30
 * @Description: TODO
 */
public class ReportsAdapter extends RecyclerView.Adapter<ReportsAdapter.myViewHolder> {

    private final Context mContext;
    private final List<String> list;

    public ReportsAdapter(Context mContext, List<String> list) {
        this.list = list;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reports, parent, false);
        return new myViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.mTvNo.setText("限时训练" + (position + 1) + ":");
        holder.mTvTotalTime.setText("总用时: " + 1);
        holder.mClItem.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, ReportDetailActivity.class);
            Log.d("hahaha",list.get(position));
            intent.putExtra("time", list.get(position));
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class myViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvNo;
        private TextView mTvTotalTime;
        private ConstraintLayout mClItem;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            bindView();
        }

        private void bindView() {
            mTvNo = itemView.findViewById(R.id.tv_no);
            mTvTotalTime = itemView.findViewById(R.id.tv_total_time);
            mClItem = itemView.findViewById(R.id.cl_item);
        }
    }
}
