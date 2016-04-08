package com.camnter.easyrecyclerviewsidebar.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.camnter.easyrecyclerviewsidebar.EasyRecyclerViewSidebar;

public class MainActivity extends AppCompatActivity {

    private EasyRecyclerViewSidebar mSidebar;
    private TextView floatingTv;


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.initViews();
        this.initData();
    }


    private void initViews() {
        this.mSidebar = (EasyRecyclerViewSidebar) this.findViewById(R.id.sidebar);
        this.floatingTv = (TextView) this.findViewById(R.id.floating_tv);
        this.mSidebar.setFloatView(this.floatingTv);
    }


    private void initData() {
        String[] sections = { "★", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L" };
        this.mSidebar.setSections(sections);
    }
}
