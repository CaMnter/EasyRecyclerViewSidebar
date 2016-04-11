package com.camnter.easyrecyclerviewsidebar.demo;

import com.camnter.easyrecyclerviewsidebar.demo.adapter.CircleImageSectionAdapter;

/**
 * Description：CircleImageSectionActivity
 * Created by：CaMnter
 * Time：2016-04-12 00:01
 */
public class CircleImageSectionActivity extends ImageSectionActivity {
    @Override public void initAdapter() {
        this.adapter = new CircleImageSectionAdapter();
    }
}
