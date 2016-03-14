package me.kkwang.commonlib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;

import me.kkwang.commonlib.R;

/**
 * Created by kw on 2016/3/10.
 * 目的是为了支持显示empty时也支持下拉刷新
 *
 *  <com.kyle.commonlib.widget.KSwipeRefreshLayout
 android:id="@+id/swipe"
 android:layout_width="match_parent"
 android:layout_height="match_parent"
 app:scrollableChildId="@+id/rv_movie_list"
 app:emptyChildId = "@+id/tv_empty">

 <FrameLayout
 android:layout_width="match_parent"
 android:layout_height="match_parent">

 <android.support.v7.widget.RecyclerView
 android:id="@+id/rv_movie_list"
 android:layout_width="match_parent"
 android:layout_height="match_parent"/>

 <TextView
 android:id="@+id/tv_empty"
 android:layout_width="match_parent"
 android:layout_height="match_parent"
 android:text="没有数据，下拉刷新"
 android:gravity="center"
 android:visibility="gone"/>

 </FrameLayout>

 </com.kyle.commonlib.widget.widget.KSwipeRefreshLayout>
 */
public class KSwipeRefreshLayout extends SwipeRefreshLayout{

    private static final String TAG = "KSwipeRefreshLayout";

    private int mScrollableChildId;//控件ID
    private int mEmptyViewId;//控件ID
    private View mScrollableChild;//子控件
    private View mEmptyView;

    public KSwipeRefreshLayout(Context context){
        this(context, null);
    }

    public KSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取监听子控件的ID
        TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.KSwipeRefreshLayoutAttrs);
        mScrollableChildId = a.getResourceId(R.styleable.KSwipeRefreshLayoutAttrs_scrollableChildId, 0);
        mScrollableChild = findViewById(mScrollableChildId);
        mEmptyViewId = a.getResourceId(R.styleable
                .KSwipeRefreshLayoutAttrs_emptyChildId,0);
        mEmptyView = findViewById(mEmptyViewId);
        a.recycle();
    }
    @Override
    public boolean canChildScrollUp() {
        //判断有没有传入子控件
        if(ensureScrollableChild()){
            if (android.os.Build.VERSION.SDK_INT < 14) {
                if (mScrollableChild instanceof AbsListView) {
                    final AbsListView absListView = (AbsListView) mScrollableChild;
                    return absListView.getChildCount() > 0
                            && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                            .getTop() < absListView.getPaddingTop());
                } else {
                    return mScrollableChild.getScrollY() > 0;
                }
            } else {
                return ViewCompat.canScrollVertically(mScrollableChild, -1);
            }
        }else{
            return true;
        }
    }
    //如果当前显示的是不是空View，那么返回真
    private boolean ensureScrollableChild() {
        if (mScrollableChild == null) {
            mScrollableChild = findViewById(mScrollableChildId);
        }
        if (mEmptyView == null) {
            mEmptyView = findViewById(mEmptyViewId);
        }
        if(mEmptyView.getVisibility() != View.VISIBLE){
            return true;
        }
        return false;
    }

}
