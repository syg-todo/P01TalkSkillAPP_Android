package com.tuodanhuashu.app.base;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.company.common.utils.DisplayUtil;

public class SimpleItemDecoration extends RecyclerView.ItemDecoration {
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount = parent.getChildCount();
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#EEEEEE"));
        paint.setStrokeWidth(DisplayUtil.dp2px( 1));
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            c.drawLine(0, child.getBottom(), child.getRight(), child.getBottom(), paint);
        }

    }
}
