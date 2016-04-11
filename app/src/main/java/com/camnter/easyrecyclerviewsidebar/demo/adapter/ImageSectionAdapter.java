package com.camnter.easyrecyclerviewsidebar.demo.adapter;

import android.text.TextUtils;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.camnter.easyrecyclerview.adapter.EasyRecyclerViewAdapter;
import com.camnter.easyrecyclerview.holder.EasyRecyclerViewHolder;
import com.camnter.easyrecyclerviewsidebar.EasyRecyclerSectionIndexer;
import com.camnter.easyrecyclerviewsidebar.demo.R;
import com.camnter.easyrecyclerviewsidebar.demo.bean.Contacts;
import com.camnter.easyrecyclerviewsidebar.demo.utils.GlideUtils;
import com.camnter.easyrecyclerviewsidebar.sections.EasyImageSection;
import com.camnter.easyrecyclerviewsidebar.sections.EasySection;
import java.util.ArrayList;
import java.util.List;

/**
 * Description：ImageSectionAdapter
 * Created by：CaMnter
 * Time：2016-04-10 20:41
 */
public class ImageSectionAdapter extends EasyRecyclerViewAdapter
        implements EasyRecyclerSectionIndexer<EasySection> {

    private SparseIntArray positionOfSection;
    private SparseIntArray sectionOfPosition;
    private List<EasySection> easySections;


    /**
     * Please return RecyclerView loading layout Id array
     * 请返回RecyclerView加载的布局Id数组
     *
     * @return 布局Id数组
     */
    @Override public int[] getItemLayouts() {
        return new int[] { R.layout.item_image_section };
    }


    /**
     * butt joint the onBindViewHolder and
     * If you want to write logic in onBindViewHolder, you can write here
     * 对接了onBindViewHolder
     * onBindViewHolder里的逻辑写在这
     *
     * @param viewHolder viewHolder
     * @param position position
     */
    @Override public void onBindRecycleViewHolder(EasyRecyclerViewHolder viewHolder, int position) {
        Contacts contacts = this.getItem(position);
        if (contacts == null) return;
        TextView headerTv = viewHolder.findViewById(R.id.section_header_tv);
        ImageView sectionIv = viewHolder.findViewById(R.id.image_section_iv);
        TextView nameTv = viewHolder.findViewById(R.id.image_section_name_tv);

        if (!TextUtils.isEmpty(contacts.name)) {
            nameTv.setText(contacts.name);
        } else {
            nameTv.setText("");
        }
        if (contacts.resId != 0) {
            GlideUtils.displayNative(sectionIv, contacts.resId);
        } else {
            GlideUtils.displayNative(sectionIv, R.drawable.img_default_head);
        }

        // Header
        if (position != 0 && !contacts.top) {
            Contacts pre = this.getItem(position - 1);
            if (pre.top || !contacts.getHeader().equals(pre.getHeader())) {
                this.setHeader(true, headerTv, contacts.getHeader());
            } else {
                this.setHeader(false, headerTv, null);
            }
        } else {
            this.setHeader(false, headerTv, null);
        }
    }


    /**
     * Please write judgment logic when more layout
     * and not write when single layout
     * 如果是多布局的话，请写判断逻辑
     * 单布局可以不写
     *
     * @param position Item position
     * @return 布局Id数组中的index
     */
    @Override public int getRecycleViewItemType(int position) {
        return 0;
    }


    private void setHeader(boolean visible, TextView headerTv, String header) {
        if (visible) {
            headerTv.setText(header);
            headerTv.setVisibility(View.VISIBLE);
        } else {
            headerTv.setVisibility(View.GONE);
        }
    }


    @Override public List<EasySection> getSections() {
        if (this.easySections == null) this.easySections = new ArrayList<>();
        if (this.easySections.size() > 0) this.easySections.clear();
        if (sectionOfPosition == null) this.sectionOfPosition = new SparseIntArray();
        if (this.sectionOfPosition.size() > 0) this.sectionOfPosition.clear();
        if (this.positionOfSection == null) this.positionOfSection = new SparseIntArray();
        if (this.positionOfSection.size() > 0) this.positionOfSection.clear();

        int itemCount = getItemCount();
        if (itemCount < 1) return this.easySections;

        String letter;

        for (int i = 0; i < itemCount; i++) {
            Contacts contacts = this.getItem(i);
            letter = contacts.getHeader();
            int section = this.easySections.size() == 0 ? 0 : this.easySections.size() - 1;
            if (contacts.top) {
                this.easySections.add(
                        new EasyImageSection(contacts.resId, EasyImageSection.CIRCLE, i));
                if (i != 0) section++;
            } else {
                // A B C D E F ...
                if (section == 0 || this.easySections.get(section) != null) {
                    EasySection easySection = this.easySections.get(section);
                    if (easySection instanceof EasyImageSection) {
                        // last section = image section
                        this.easySections.add(new EasySection(letter));
                        section++;
                        this.positionOfSection.put(section, i);
                    } else {
                        // last section = letter section
                        if (!this.easySections.get(section).letter.equals(letter)) {
                            this.easySections.add(new EasySection(letter));
                            section++;
                            this.positionOfSection.put(section, i);
                        }
                    }
                }
            }
            this.sectionOfPosition.put(i, section);
        }
        return this.easySections;
    }


    @Override public int getPositionForSection(int sectionIndex) {
        return positionOfSection.get(sectionIndex);
    }


    @Override public int getSectionForPosition(int position) {
        return sectionOfPosition.get(position);
    }
}
