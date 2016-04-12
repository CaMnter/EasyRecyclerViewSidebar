package com.camnter.easyrecyclerviewsidebar.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.camnter.easyrecyclerview.widget.EasyRecyclerView;
import com.camnter.easyrecyclerviewsidebar.EasyFloatingImageView;
import com.camnter.easyrecyclerviewsidebar.EasyRecyclerViewSidebar;
import com.camnter.easyrecyclerviewsidebar.demo.adapter.CircleImageSectionAdapter;
import com.camnter.easyrecyclerviewsidebar.demo.adapter.ImageSectionAdapter;
import com.camnter.easyrecyclerviewsidebar.demo.utils.GlideUtils;
import com.camnter.easyrecyclerviewsidebar.sections.EasyImageSection;
import com.camnter.easyrecyclerviewsidebar.sections.EasySection;

/**
 * Description：CircleImageSectionActivity
 * Created by：CaMnter
 * Time：2016-04-10 20:23
 */
public class ImageSectionActivity extends AppCompatActivity
        implements EasyRecyclerViewSidebar.OnTouchSectionListener {
    private EasyRecyclerViewSidebar imageSidebar;
    private TextView imageFloatingTv;
    private EasyFloatingImageView imageFloatingIv;

    public ImageSectionAdapter adapter;
    private EasyRecyclerView imageSectionRv;


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_section);
        this.initViews();
        this.initData();
    }


    private void initViews() {
        this.imageSectionRv = (EasyRecyclerView) this.findViewById(R.id.image_section_rv);
        this.imageSidebar = (EasyRecyclerViewSidebar) this.findViewById(R.id.image_section_sidebar);
        this.imageFloatingTv = (TextView) this.findViewById(R.id.image_section_floating_tv);
        this.imageFloatingIv = (EasyFloatingImageView) this.findViewById(
                R.id.image_section_floating_iv);
        RelativeLayout imageFloatingRl = (RelativeLayout) this.findViewById(
                R.id.image_section_floating_rl);

        this.initAdapter();
        if (this.imageSectionRv != null) {
            this.imageSectionRv.setAdapter(this.adapter);
        }

        this.imageSidebar.setFloatView(imageFloatingRl);
        this.imageSidebar.setOnTouchSectionListener(this);
    }


    private void initData() {
        this.adapter.setList(Constant.circleImageSectionList);
        this.adapter.notifyDataSetChanged();
        this.imageSidebar.setSections(this.adapter.getSections());
    }


    public void initAdapter() {
        this.adapter = new CircleImageSectionAdapter();
    }


    /**
     * On touch image section
     *
     * @param sectionIndex sectionIndex
     * @param imageSection imageSection
     */
    @Override public void onTouchImageSection(int sectionIndex, EasyImageSection imageSection) {
        this.imageFloatingTv.setVisibility(View.INVISIBLE);
        this.imageFloatingIv.setVisibility(View.VISIBLE);
        switch (imageSection.imageType) {
            case EasyImageSection.CIRCLE:
                this.imageFloatingIv.setImageType(EasyFloatingImageView.CIRCLE);
                break;
            case EasyImageSection.ROUND:
                this.imageFloatingIv.setImageType(EasyFloatingImageView.ROUND);
                break;
        }
        GlideUtils.displayNative(this.imageFloatingIv, imageSection.resId);
        this.scrollToPosition(this.adapter.getPositionForSection(sectionIndex));
    }


    /**
     * On touch letter section
     *
     * @param sectionIndex sectionIndex
     * @param letterSection letterSection
     */
    @Override public void onTouchLetterSection(int sectionIndex, EasySection letterSection) {
        this.imageFloatingTv.setVisibility(View.VISIBLE);
        this.imageFloatingIv.setVisibility(View.INVISIBLE);
        this.imageFloatingTv.setText(letterSection.letter);
        this.scrollToPosition(this.adapter.getPositionForSection(sectionIndex));
    }


    private void scrollToPosition(int position) {
        this.imageSectionRv.getLinearLayoutManager().scrollToPositionWithOffset(position, 0);
    }
}
