package com.camnter.easyrecyclerviewsidebar.demo.adapter;

import com.camnter.easyrecyclerview.adapter.EasyRecyclerViewAdapter;
import com.camnter.easyrecyclerview.holder.EasyRecyclerViewHolder;

/**
 * Description：ImageSectionAdapter
 * Created by：CaMnter
 * Time：2016-04-10 20:41
 */
public class ImageSectionAdapter extends EasyRecyclerViewAdapter {
    /**
     * Please return RecyclerView loading layout Id array
     * 请返回RecyclerView加载的布局Id数组
     *
     * @return 布局Id数组
     */
    @Override public int[] getItemLayouts() {
        return new int[0];
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
}
