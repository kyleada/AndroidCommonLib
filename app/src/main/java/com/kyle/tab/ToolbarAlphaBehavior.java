package com.kyle.tab;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.kyle.myapp.R;


/**
 * Created by Administrator on 2016/3/11.
 */
public class ToolbarAlphaBehavior extends CoordinatorLayout.Behavior<Toolbar> {

    private static final String TAG = "ToolbarAlphaBehavior";
    private int offset = 0;
    private int startOffset = 0;
    private int endOffset = 0;
    private Context context;

    public ToolbarAlphaBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, Toolbar child, View directTargetChild, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, Toolbar child, View target, float velocityX, float velocityY) {
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
    }

    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, Toolbar child, View target, float velocityX, float velocityY, boolean consumed) {

        Log.d(TAG, "onNestedFling: " + target.getClass().getSimpleName() + velocityY + consumed);
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, Toolbar toolbar, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {

        Log.e(TAG, "onNestedScroll: " + dyConsumed);
        ImageView iv = (ImageView) ((Activity) target.getContext()).getWindow().getDecorView().findViewById(R.id.tb_iv2);


        startOffset = 0;
        endOffset = context.getResources().getDimensionPixelOffset(R.dimen.header_height) - toolbar.getHeight();
        offset += dyConsumed;
        if (offset <= startOffset) {  //alpha为0
            toolbar.getBackground().setAlpha(0);
            iv.getBackground().setAlpha(255);
        } else if (offset > startOffset && offset < endOffset) { //alpha为0到255
            float precent = (float) (offset - startOffset) / endOffset;
            int alpha = Math.round(precent * 255);
            toolbar.getBackground().setAlpha(alpha);
            iv.getBackground().setAlpha(255 - alpha);
        } else if (offset >= endOffset) {  //alpha为255, 工具栏变成全不透明
            toolbar.getBackground().setAlpha(255);
            iv.getBackground().setAlpha(0);
        }
    }

}
