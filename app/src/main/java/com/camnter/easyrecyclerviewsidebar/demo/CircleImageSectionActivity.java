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
package com.camnter.easyrecyclerviewsidebar.demo;

import com.camnter.easyrecyclerviewsidebar.demo.adapter.CircleImageSectionAdapter;
import com.camnter.easyrecyclerviewsidebar.demo.base.SectionActivity;
import com.camnter.easyrecyclerviewsidebar.demo.bean.Contacts;
import com.camnter.easyrecyclerviewsidebar.demo.constant.Constant;
import java.util.List;

/**
 * Description：CircleImageSectionActivity
 * Created by：CaMnter
 * Time：2016-04-12 00:01
 */
public class CircleImageSectionActivity extends SectionActivity {
    @Override public void setActivityTitle() {
        this.setTitle("CircleImageSectionActivity");
    }


    @Override public void initAdapter() {
        this.adapter = new CircleImageSectionAdapter();
    }


    @Override public List<Contacts> getData() {
        return Constant.circleImageSectionList;
    }
}
