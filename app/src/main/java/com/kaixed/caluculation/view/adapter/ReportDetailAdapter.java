package com.kaixed.caluculation.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kaixed.caluculation.R;
import com.kaixed.caluculation.entity.Equation;
import com.kaixed.caluculation.entity.UniqueEquation;

import java.util.List;

/**
 * @Author: kaixed
 * @Date: 2024/1/1 13:12
 * @Description: TODO
 */
public class ReportDetailAdapter extends RecyclerView.Adapter<ReportDetailAdapter.ViewHolder> {

    private List<UniqueEquation> equations;

    public ReportDetailAdapter(List<UniqueEquation> equations) {
        this.equations = equations;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_report_detail, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UniqueEquation uniqueEquation = equations.get(position);
        holder.bindData(uniqueEquation);
    }

    @Override
    public int getItemCount() {
        return equations.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvEquation;
        private TextView mTvResult;
        private TextView mTvInPut;
        private ImageView mIvTrue;
        private ImageView mIvFalse;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView();
        }

        private void initView() {
            mTvEquation = itemView.findViewById(R.id.tv_equation);
            mTvInPut = itemView.findViewById(R.id.tv_input);
            mTvResult = itemView.findViewById(R.id.tv_result);
            mIvFalse = itemView.findViewById(R.id.iv_false);
            mIvTrue = itemView.findViewById(R.id.iv_true);
        }

        public void bindData(UniqueEquation uniqueEquation) {

            mTvEquation.setText(uniqueEquation.getEquation());
            mTvInPut.setText(uniqueEquation.getInPutValue());
            if (uniqueEquation.getResult().equals(uniqueEquation.getInPutValue())) {

                mIvTrue.setVisibility(View.VISIBLE);
                mIvFalse.setVisibility(View.INVISIBLE);

                mTvResult.setVisibility(View.INVISIBLE);
            } else {
                mTvResult.setText("正确答案是: " + uniqueEquation.getResult());
                mIvFalse.setVisibility(View.VISIBLE);
                mIvTrue.setVisibility(View.INVISIBLE);
            }

        }
    }
}
