package com.camnter.easyrecyclerviewsidebar.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.camnter.easyrecyclerviewsidebar.EasyRecyclerViewSidebar;
import com.camnter.easyrecyclerviewsidebar.sections.EasyImageSection;
import com.camnter.easyrecyclerviewsidebar.sections.EasySection;
import java.util.ArrayList;
import java.util.List;

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
        String[] sections = { "A", "B", "C" };
        List<EasySection> sections1 = new ArrayList<>();
        EasyImageSection imageSection1 = new EasyImageSection(R.drawable.ic_camnter,
                EasyImageSection.ROUND);
        EasyImageSection imageSection2 = new EasyImageSection(R.drawable.ic_drakeet,
                EasyImageSection.ROUND);
        sections1.add(imageSection2);
        sections1.add(imageSection1);

        EasySection section1 = new EasySection("A");
        EasySection section2 = new EasySection("B");
        EasySection section3 = new EasySection("C");
        EasySection section4 = new EasySection("D");
        EasySection section5 = new EasySection("E");
        sections1.add(section1);
        sections1.add(section2);
        sections1.add(section3);
        sections1.add(section4);
        sections1.add(section5);

        EasyImageSection imageSection4 = new EasyImageSection(R.drawable.ic_camnter,
                EasyImageSection.ROUND);
        EasyImageSection imageSection5 = new EasyImageSection(R.drawable.ic_drakeet,
                EasyImageSection.ROUND);
        sections1.add(imageSection5);
        sections1.add(imageSection4);
        this.mSidebar.setSections(sections1);
    }
}
