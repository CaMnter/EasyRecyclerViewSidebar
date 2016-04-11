package com.camnter.easyrecyclerviewsidebar.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.camnter.easyrecyclerview.widget.EasyRecyclerView;
import com.camnter.easyrecyclerviewsidebar.EasyRecyclerViewSidebar;
import com.camnter.easyrecyclerviewsidebar.demo.adapter.ImageSectionAdapter;
import com.camnter.easyrecyclerviewsidebar.sections.EasyImageSection;
import com.camnter.easyrecyclerviewsidebar.sections.EasySection;

/**
 * Description：ImageSectionActivity
 * Created by：CaMnter
 * Time：2016-04-10 20:23
 */
public class ImageSectionActivity extends AppCompatActivity
        implements EasyRecyclerViewSidebar.OnTouchSectionListener {
    private EasyRecyclerViewSidebar imageSidebar;
    private TextView imageFloatingTv;

    private EasyRecyclerView imageSectionRv;
    private ImageSectionAdapter adapter;


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_section);
        this.initViews();
        this.initData();
    }


    private void initViews() {
        this.imageSectionRv = (EasyRecyclerView) this.findViewById(R.id.image_section_rv);
        this.adapter = new ImageSectionAdapter();
        this.imageSectionRv.setAdapter(this.adapter);
        this.imageSidebar = (EasyRecyclerViewSidebar) this.findViewById(R.id.image_section_sidebar);
        this.imageFloatingTv = (TextView) this.findViewById(R.id.image_section_floating_tv);
        this.imageSidebar.setFloatView(this.imageFloatingTv);
        this.imageSidebar.setOnTouchSectionListener(this);
    }


    private void initData() {
        this.adapter.setList(Constant.imageSectionList);
        this.adapter.notifyDataSetChanged();
        this.imageSidebar.setSections(this.adapter.getSections());
    }


    /**
     * On touch image section
     *
     * @param sectionIndex sectionIndex
     * @param imageSection imageSection
     */
    @Override public void onTouchImageSection(int sectionIndex, EasyImageSection imageSection) {
    }


    /**
     * On touch letter section
     *
     * @param sectionIndex sectionIndex
     * @param letterSection letterSection
     */
    @Override public void onTouchLetterSection(int sectionIndex, EasySection letterSection) {
    }
}
