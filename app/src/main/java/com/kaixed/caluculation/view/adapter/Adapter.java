package com.kaixed.caluculation.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kaixed.caluculation.R;
import com.kaixed.caluculation.entity.Equation;
import com.kaixed.caluculation.widget.SmoothCheckBox;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: kaixed
 * @Date: 2023/12/29 23:03
 * @Description: TODO
 */
public class Adapter extends RecyclerView.Adapter<Adapter.myViewHolder> {

    private List<Equation> equations = new ArrayList<>();

    private Context mContext;
    int selectedPosition = -1;

    public Adapter(List<Equation> equations) {
        this.equations = equations;
//        this.mContext = mContext;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setSelectedPosition(int position) {
        selectedPosition = position;
        notifyDataSetChanged();
    }

    public List<Equation> getData() {
        return equations;
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_equation, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        Equation equation = equations.get(position);
        if (position == selectedPosition) {
            holder.mTvProblem.setTextColor(Color.RED);
        } else {
            holder.mTvProblem.setTextColor(Color.BLACK);
        }
        holder.bindData(equation);
    }

    @Override
    public int getItemCount() {
        return equations.size();
    }

    static class myViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvProblem;
        private SmoothCheckBox mScbTrue;
        private TextView mTvResult;
        private View mView;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            initView();
        }

        public void bindData(Equation equation) {
            mTvProblem.setText(equation.getEquation());
            mTvResult.setText(equation.getInPutValue());

            if (!equation.getInPutValue().isEmpty() && equation.isTrue()) {
                mScbTrue.setChecked(true);
                mScbTrue.setVisibility(View.VISIBLE);
            } else {
                mScbTrue.setChecked(false);
                mScbTrue.setVisibility(View.GONE);
            }
        }

        public void initView() {
            mTvProblem = itemView.findViewById(R.id.tv_problem);
            mTvResult = itemView.findViewById(R.id.tv_result);
            mScbTrue = itemView.findViewById(R.id.haha);
            mScbTrue.setClickable(false);
        }
    }
}
