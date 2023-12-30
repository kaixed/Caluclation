package com.kaixed.caluculation.view.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kaixed.caluculation.R;
import com.kaixed.caluculation.entity.Mistakes;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: kaixed
 * @Date: 2023/12/30 16:30
 * @Description: TODO
 */
public class MistakesAdapter extends RecyclerView.Adapter<MistakesAdapter.myViewHolder> {

    List<Mistakes> mistakes = new ArrayList<>();

    public MistakesAdapter(List<Mistakes> mistakes) {
        this.mistakes = mistakes;
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mistakes, parent, false);
        return new myViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.mTvNo.setText("题目" + (position + 1) + ":");

        Mistakes mistakes1 = mistakes.get(position);

        holder.bindData(mistakes1);
    }

    @Override
    public int getItemCount() {
        return mistakes.size();
    }

    static class myViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvNo;
        private TextView mTvTotalNumbers;
        private TextView mTvEquation;
        private TextView mTvVisible;
        private TextView mTvResult;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            bindView();
        }

        private void bindView() {
            mTvNo = itemView.findViewById(R.id.tv_no);
            mTvTotalNumbers = itemView.findViewById(R.id.tv_total_numbers);
            mTvVisible = itemView.findViewById(R.id.tv_visible);
            mTvResult = itemView.findViewById(R.id.tv_result);
            mTvEquation = itemView.findViewById(R.id.tv_equation);
        }

        @SuppressLint("SetTextI18n")
        public void bindData(Mistakes mistakes) {
            mTvTotalNumbers.setText("错误次数: " + mistakes.getCounts());
            mTvResult.setText("正确答案: " + mistakes.getResult());
            mTvEquation.setText(mistakes.getEquation());
            mTvVisible.setOnClickListener(view -> {
                mTvResult.setVisibility(View.VISIBLE);
                mTvVisible.setVisibility(View.GONE);
            });
        }


    }
}
