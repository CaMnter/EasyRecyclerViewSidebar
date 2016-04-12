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
package com.camnter.easyrecyclerviewsidebar.demo.bean;

import android.text.TextUtils;

/**
 * Description：Contacts
 * Created by：CaMnter
 * Time：2016-04-10 21:05
 */
public class Contacts {
    public int resId;
    public String name;
    public String pinyin;
    public String header;
    public boolean top = false;


    public String getHeader() {
        if (this.header != null) return this.header;
        if (TextUtils.isEmpty(this.pinyin) || Character.isDigit(this.pinyin.charAt(0))) {
            this.header = null;
        } else {
            this.header = this.pinyin.substring(0, 1).toUpperCase();
            char temp = this.header.charAt(0);
            if (temp < 'A' || temp > 'Z') {
                this.header = "#";
            }
        }
        return this.header;
    }
}
