package com.camnter.easyrecyclerviewsidebar.demo;

import com.camnter.easyrecyclerviewsidebar.demo.adapter.LetterSectionAdapter;
import com.camnter.easyrecyclerviewsidebar.demo.base.SectionActivity;
import com.camnter.easyrecyclerviewsidebar.demo.bean.Contacts;
import com.camnter.easyrecyclerviewsidebar.demo.constant.Constant;
import java.util.List;

/**
 * Description：LetterSectionActivity
 * Created by：CaMnter
 * Time：2016-04-12 22:42
 */
public class LetterSectionActivity extends SectionActivity {
    @Override public void setActivityTitle() {
        this.setTitle("LetterSectionActivity");
    }


    @Override public void initAdapter() {
        this.adapter = new LetterSectionAdapter();
    }


    @Override public List<Contacts> getData() {
        return Constant.letterSectionList;
    }
}
