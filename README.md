#EasyRecyclerViewSidebar

**Easy sidebar for Android RecyclerView (｡>﹏<｡)**

![Language](https://img.shields.io/badge/language-Java-EE0000.svg) [![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](https://github.com/CaMnter/EasyRecyclerViewSidebar/blob/master/LICENSE) 
![Version](https://img.shields.io/badge/version-1.3-8470FF.svg)
![SDK](https://img.shields.io/badge/SDK-9%2B-orange.svg)
[ ![Download](https://api.bintray.com/packages/camnter/maven/EasyRecyclerViewSidebar/images/download.svg) ](https://bintray.com/camnter/maven/EasyRecyclerViewSidebar/_latestVersion)


##Introduction

EasyRecyclerViewSidebar is more convenient sidebar index column .  

**Not only can load letter, you can also load pictures.**

You can also customize the floating View.

## Gradle

```groovy
dependencies {
	compile 'com.camnter.easyrecyclerviewsidebar:easyrecyclerviewsidebar:1.3'
}
```

## Attributes

```xml
<declare-styleable name="EasyRecyclerViewSidebar">
    <attr name="easySidebarBackground" format="color" />
    <attr name="easySidebarFontColor" format="color" />
    <attr name="easySidebarTouchWrapArea" format="boolean" />
</declare-styleable>
```

##Listener

You must implement the **EasyRecyclerViewSidebar.OnTouchSectionListener** .

- In onTouchImageSection , using the EasyImageSection rendering images for **You floating view** .
- In onTouchLetterSection using the EasySection set letter for **You floating view** . 

**Also , you can see the SectionActivity of demo .**

```java
public interface OnTouchSectionListener {
    /**
     * On touch image section
     *
     * @param sectionIndex sectionIndex
     * @param imageSection imageSection
     */
    void onTouchImageSection(int sectionIndex, EasyImageSection imageSection);

    /**
     * On touch letter section
     *
     * @param sectionIndex sectionIndex
     * @param letterSection letterSection
     */
    void onTouchLetterSection(int sectionIndex, EasySection letterSection);
}
```

## Easy to use

More details, you can see the xml of demo 

```xml
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.camnter.easyrecyclerview.widget.EasyRecyclerView
        android:id="@+id/section_rv"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

    <com.camnter.easyrecyclerviewsidebar.EasyRecyclerViewSidebar
        android:id="@+id/section_sidebar"
        android:layout_width="30dp"
        android:layout_height="fill_parent"
        android:layout_alignParentEnd="true"
        android:layout_alignParentRight="true"
        android:layout_gravity="center|end"/>

    <RelativeLayout
        android:id="@+id/section_floating_rl"
        android:layout_width="70dp"
        android:layout_height="60dp"
        android:layout_centerInParent="true"
        android:background="@drawable/show_float_bg"
        android:visibility="invisible">

        <com.camnter.easyrecyclerviewsidebar.EasyFloatingImageView
            android:id="@+id/section_floating_iv"
            android:layout_width="32dp"
            android:layout_height="32dp"
            android:layout_centerInParent="true"
            android:visibility="invisible"/>

        <TextView
            android:id="@+id/section_floating_tv"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_centerInParent="true"
            android:textColor="#ff444444"
            android:textSize="23sp"
            android:visibility="invisible"/>

    </RelativeLayout>

</RelativeLayout>
```

**EasyFloatingImageView Attributes**

```xml
<declare-styleable name="EasyFloatingImageView">
    <attr name="easyFloatingImageType">
        <enum name="round" value="2601" />
        <enum name="circle" value="2602" />
    </attr>
    <attr name="easyFloatingBorderRadius" format="dimension" />
</declare-styleable>
```

**setSections**

```java
private void initData() {
    this.adapter.setList(this.getData());
    this.adapter.notifyDataSetChanged();
    this.imageSidebar.setSections(this.adapter.getSections());
}
```

**A sample implement of getSections**

```java
@Override public List<EasySection> getSections() {
    this.resetSectionCache();

    int itemCount = getItemCount();
    if (itemCount < 1) return this.easySections;

    String letter;

    for (int i = 0; i < itemCount; i++) {
        Contacts contacts = this.getItem(i);
        letter = contacts.getHeader();
        int section = this.easySections.size() == 0 ? 0 : this.easySections.size() - 1;
        if (contacts.top) {
            if (i != 0) section++;
            this.positionOfSection.put(section, i);
            this.easySections.add(
                    new EasyImageSection(contacts.resId, this.getEasyImageSection(), i));
        } else {
            // A B C D E F ...
            if (section < this.easySections.size()) {
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
            } else if (section == 0) {
                this.easySections.add(new EasySection(letter));
                this.positionOfSection.put(section, i);
            }
        }
        this.sectionOfPosition.put(i, section);
    }
    return this.easySections;
}
```


##ScreenShots

<img src="https://github.com/CaMnter/EasyRecyclerViewSidebar/raw/master/screenshots/EasyRecyclerViewSidebar.gif" width="320x">   

|    Style   |     Circle    |      Round     |
| :--------: | :-----------: | :------------: |
| | <img src="http://ww3.sinaimg.cn/large/006lPEc9jw1f2vh0iwt6vj31401z4ah4.jpg" width="320x">             |  <img src="http://ww2.sinaimg.cn/large/006lPEc9jw1f2vh0zq98tj31401z4ah6.jpg" width="320x">             |  
  
**No Images**  

<img src="http://ww1.sinaimg.cn/large/006lPEc9jw1f2vh1f2d6dj31401z4n2k.jpg" width="320x">


## License

      Copyright (C) 2016 CaMnter yuanyu.camnter@gmail.com

      Licensed under the Apache License, Version 2.0 (the "License");
      you may not use this file except in compliance with the License.
      You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

      Unless required by applicable law or agreed to in writing, software
      distributed under the License is distributed on an "AS IS" BASIS,
      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
      See the License for the specific language governing permissions and
      limitations under the License.




