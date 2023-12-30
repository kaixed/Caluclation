package com.kaixed.caluculation.common;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @Author: kaixed
 * @Date: 2023/12/30 10:34
 * @Description: TODO
 */
public class AutoScrollLayoutManager extends LinearLayoutManager {

    public AutoScrollLayoutManager(Context context) {
        super(context);
    }

    public AutoScrollLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public AutoScrollLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    // 新增方法，滑动到指定position并将其置顶
    public void scrollToPositionWithTop(int position, RecyclerView recyclerView) {
        scrollToPosition(position);
        int firstVisibleItemPosition = findFirstVisibleItemPosition();
        int offset = position - firstVisibleItemPosition;
        if (offset > 0 && offset < getChildCount()) {
            recyclerView.scrollBy(0, getChildAt(offset).getTop());
        }
    }


}
