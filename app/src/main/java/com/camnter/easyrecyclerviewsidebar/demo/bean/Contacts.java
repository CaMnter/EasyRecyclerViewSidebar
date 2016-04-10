package com.camnter.easyrecyclerviewsidebar.demo.bean;

import android.text.TextUtils;

/**
 * Description：ContactsList
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
