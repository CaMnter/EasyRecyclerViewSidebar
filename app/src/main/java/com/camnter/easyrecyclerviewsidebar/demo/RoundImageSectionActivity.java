package com.camnter.easyrecyclerviewsidebar.demo;

import com.camnter.easyrecyclerviewsidebar.demo.adapter.RoundImageSectionAdapter;

/**
 * Description：RoundImageSectionActivity
 * Created by：CaMnter
 * Time：2016-04-12 00:02
 */
public class RoundImageSectionActivity extends ImageSectionActivity {
    @Override public void initAdapter() {
        this.adapter = new RoundImageSectionAdapter();
    }
}
