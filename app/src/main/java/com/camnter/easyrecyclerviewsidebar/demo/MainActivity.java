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

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.camnter.easyrecyclerview.holder.EasyRecyclerViewHolder;
import com.camnter.easyrecyclerview.widget.EasyRecyclerView;
import com.camnter.easyrecyclerview.widget.decorator.EasyDividerItemDecoration;
import com.camnter.easyrecyclerviewsidebar.demo.adapter.MainAdapter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EasyRecyclerView mainRv;
    private MainAdapter mainAdapter;
    private ArrayList<Class> classes;


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.initViews();
        this.initData();
        this.initListeners();
    }


    private void initViews() {
        this.mainRv = (EasyRecyclerView) this.findViewById(R.id.main_rv);
        EasyDividerItemDecoration decoration = new EasyDividerItemDecoration(this,
                EasyDividerItemDecoration.VERTICAL_LIST);
        decoration.bottomDivider = true;
        this.mainRv.addItemDecoration(decoration);
    }


    private void initData() {
        this.classes = new ArrayList<>();
        this.classes.add(LetterSectionActivity.class);
        this.classes.add(CircleImageSectionActivity.class);
        this.classes.add(RoundImageSectionActivity.class);

        this.mainAdapter = new MainAdapter();
        this.mainAdapter.setList(this.classes);
        this.mainRv.setAdapter(this.mainAdapter);
    }


    private void initListeners() {
        this.mainAdapter.setOnItemClickListener(new EasyRecyclerViewHolder.OnItemClickListener() {
            @Override public void onItemClick(View view, int i) {
                Class c = MainActivity.this.classes.get(i);
                MainActivity.this.startActivity(new Intent(MainActivity.this, c));
            }
        });
    }
}
