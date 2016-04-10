package com.camnter.easyrecyclerviewsidebar.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.camnter.easyrecyclerview.widget.EasyRecyclerView;
import com.camnter.easyrecyclerviewsidebar.EasyRecyclerViewSidebar;
import com.camnter.easyrecyclerviewsidebar.demo.adapter.ImageSectionAdapter;

/**
 * Description：ImageSectionActivity
 * Created by：CaMnter
 * Time：2016-04-10 20:23
 */
public class ImageSectionActivity extends AppCompatActivity {
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
    }


    private void initData() {
        this.adapter.setList(Constant.imageSectionList);
        this.adapter.notifyDataSetChanged();
        this.imageSidebar.setSections(this.adapter.getSections());

        //String[] letters = { "A", "B", "C", "D", "E", "F", "G", "H" };
        //List<EasySection> sections = new ArrayList<>();
        //EasyImageSection imageSection1 = new EasyImageSection(R.drawable.ic_camnter,
        //        EasyImageSection.ROUND, "CaMnter", 0);
        //EasyImageSection imageSection2 = new EasyImageSection(R.drawable.ic_drakeet,
        //        EasyImageSection.ROUND, "drakeet", 1);
        //sections.add(imageSection1);
        //sections.add(imageSection2);
        //for (String letter : letters) {
        //    EasySection section = new EasySection(letter);
        //    sections.add(section);
        //}
        //this.imageSidebar.setSections(sections);
    }
}
