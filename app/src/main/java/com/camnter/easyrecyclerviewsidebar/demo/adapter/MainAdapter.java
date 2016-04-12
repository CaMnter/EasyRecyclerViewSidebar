/*
 * Copyright (C) 2016 CaMnter yuanyu.camnter@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.camnter.easyrecyclerviewsidebar.demo.adapter;

import android.widget.TextView;
import com.camnter.easyrecyclerview.adapter.EasyRecyclerViewAdapter;
import com.camnter.easyrecyclerview.holder.EasyRecyclerViewHolder;
import com.camnter.easyrecyclerviewsidebar.demo.R;

/**
 * Description：MainAdapter
 * Created by：CaMnter
 * Time：2016-03-18 13:21
 */
public class MainAdapter extends EasyRecyclerViewAdapter {

    @Override public int[] getItemLayouts() {
        return new int[] { R.layout.item_main };
    }


    @Override public void onBindRecycleViewHolder(EasyRecyclerViewHolder viewHolder, int position) {
        Class c = (Class) this.getList().get(position);
        if (c == null) return;
        TextView textView = viewHolder.findViewById(R.id.main_item_tv);
        textView.setText(c.getSimpleName());
    }


    @Override public int getRecycleViewItemType(int position) {
        return 0;
    }
}
