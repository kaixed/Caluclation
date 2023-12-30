package com.kaixed.caluculation.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kaixed.caluculation.R;
import com.kaixed.caluculation.entity.Equation;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: kaixed
 * @Date: 2023/12/29 23:03
 * @Description: TODO
 */
public class LimitedAdapter extends RecyclerView.Adapter<LimitedAdapter.myViewHolder> {

    private List<Equation> equations = new ArrayList<>();

    private Context mContext;

    public LimitedAdapter(List<Equation> equations) {
        this.equations = equations;
//        this.mContext = mContext;
    }

    public List<Equation> getData() {
        return equations;
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_equation_limited, parent, false);

        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        Equation equation = equations.get(position);
        holder.bindData(equation);
    }

    @Override
    public int getItemCount() {
        return equations.size();
    }

    static class myViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvProblem;
        private TextView mTvResult;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            initView();
        }

        public void bindData(Equation equation) {

            mTvProblem.setText(equation.getEquation());
            mTvResult.setText(equation.getInPutValue());

        }

        public void initView() {
            mTvProblem = itemView.findViewById(R.id.tv_problem);
            mTvResult = itemView.findViewById(R.id.tv_result);

        }

    }
}
