package com.kkwang.tab;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kkwang.myapp.R;


@SuppressLint("ValidFragment")
public class SimpleCardFragment2 extends Fragment {
    private String mTitle;

    public static SimpleCardFragment2 getInstance(String title) {
        SimpleCardFragment2 sf = new SimpleCardFragment2();
        sf.mTitle = title;
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_fr_simple_card2, null); //此处必须为null
        TextView card_title_tv = (TextView) v.findViewById(R.id.card_title_tv);


        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar_behavior);
        toolbar.getBackground().setAlpha(0);//toolbar透明度初始化为0
//        ImageView iv = (ImageView)v.findViewById(R.id.tb_iv2);
//        Drawable drawable = iv.getDrawable();
//        drawable.setColorFilter(Color.GRAY, PorterDuff.Mode.XOR);
//        iv.setImageDrawable(drawable);

        return v;
    }
}