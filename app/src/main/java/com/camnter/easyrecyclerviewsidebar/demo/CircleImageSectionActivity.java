package com.camnter.easyrecyclerviewsidebar.demo;

import com.camnter.easyrecyclerviewsidebar.demo.adapter.CircleImageSectionAdapter;
import com.camnter.easyrecyclerviewsidebar.demo.base.SectionActivity;
import com.camnter.easyrecyclerviewsidebar.demo.bean.Contacts;
import com.camnter.easyrecyclerviewsidebar.demo.constant.Constant;
import java.util.List;

/**
 * Description：CircleImageSectionActivity
 * Created by：CaMnter
 * Time：2016-04-12 00:01
 */
public class CircleImageSectionActivity extends SectionActivity {
    @Override public void setActivityTitle() {
        this.setTitle("CircleImageSectionActivity");
    }


    @Override public void initAdapter() {
        this.adapter = new CircleImageSectionAdapter();
    }


    @Override public List<Contacts> getData() {
        return Constant.circleImageSectionList;
    }
}
