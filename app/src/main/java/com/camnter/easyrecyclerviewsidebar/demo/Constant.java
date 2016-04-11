package com.camnter.easyrecyclerviewsidebar.demo;

import com.camnter.easyrecyclerviewsidebar.demo.bean.Contacts;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description：Constant
 * Created by：CaMnter
 * Time：2016-04-10 21:28
 */
public class Constant {
    public static final Map<String, Integer> letter2ResId = new HashMap<>();
    public static final Map<String, String> letter2Name = new HashMap<>();
    public static final Map<String, String> letter2Pinyin = new HashMap<>();
    public static final String[] letterArray = { "C", "D", "F", "H", "K", "P", "Q", "R", "U", "X" };

    public static final List<Contacts> circleImageSectionList = new ArrayList<>();
    public static final List<Contacts> roundImageSectionList = new ArrayList<>();
    public static final List<Contacts> letterSectionList = new ArrayList<>();


    static {
        letter2ResId.put("C", R.drawable.ic_camnter);
        letter2ResId.put("D", R.drawable.ic_drakeet);
        letter2ResId.put("F", R.drawable.ic_fython);
        letter2ResId.put("H", R.drawable.ic_harry_chen);
        letter2ResId.put("K", R.drawable.ic_kaede_akatsuki);
        letter2ResId.put("P", R.drawable.ic_peter_cai);
        letter2ResId.put("Q", R.drawable.ic_qixingchen);
        letter2ResId.put("R", R.drawable.ic_randy_lu);
        letter2ResId.put("U", R.drawable.ic_undownding);
        letter2ResId.put("X", R.drawable.ic_xingrz);

        letter2Name.put("C", "CaMnter");
        letter2Name.put("D", "drakeet");
        letter2Name.put("F", "Fython");
        letter2Name.put("H", "Harry Chen");
        letter2Name.put("K", "Kaede Akatsuki");
        letter2Name.put("P", "Peter Cai");
        letter2Name.put("Q", "Qixingchen");
        letter2Name.put("R", "Randy");
        letter2Name.put("U", "undownding");
        letter2Name.put("X", "xingrz");

        letter2Pinyin.put("C", "camnter");
        letter2Pinyin.put("D", "drakeet");
        letter2Pinyin.put("F", "fython");
        letter2Pinyin.put("H", "harrychen");
        letter2Pinyin.put("K", "kaedeakatsuki");
        letter2Pinyin.put("P", "petercai");
        letter2Pinyin.put("Q", "qixingchen");
        letter2Pinyin.put("R", "randy");
        letter2Pinyin.put("U", "undownding");
        letter2Pinyin.put("X", "xingrz");

        letter2ResId.put("C", R.drawable.ic_camnter);
        letter2ResId.put("D", R.drawable.ic_drakeet);
        letter2ResId.put("F", R.drawable.ic_fython);
        letter2ResId.put("H", R.drawable.ic_harry_chen);
        letter2ResId.put("K", R.drawable.ic_kaede_akatsuki);
        letter2ResId.put("P", R.drawable.ic_peter_cai);
        letter2ResId.put("Q", R.drawable.ic_qixingchen);
        letter2ResId.put("R", R.drawable.ic_randy_lu);
        letter2ResId.put("U", R.drawable.ic_undownding);
        letter2ResId.put("X", R.drawable.ic_xingrz);

        // Special
        Contacts camnter = new Contacts();
        camnter.name = letter2Name.get("C");
        camnter.pinyin = letter2Pinyin.get("C");
        camnter.resId = letter2ResId.get("C");
        camnter.top = true;
        Contacts drakeet = new Contacts();
        drakeet.name = letter2Name.get("D");
        drakeet.pinyin = letter2Pinyin.get("D");
        drakeet.resId = letter2ResId.get("D");
        drakeet.top = true;
        Contacts ka = new Contacts();
        ka.name = letter2Name.get("K");
        ka.pinyin = letter2Pinyin.get("K");
        ka.resId = letter2ResId.get("K");
        ka.top = true;
        circleImageSectionList.add(camnter);
        circleImageSectionList.add(drakeet);
        circleImageSectionList.add(ka);
        roundImageSectionList.add(camnter);
        roundImageSectionList.add(drakeet);
        roundImageSectionList.add(ka);
        for (String letter : letterArray) {
            Contacts contacts = new Contacts();
            contacts.name = letter2Name.get(letter);
            contacts.pinyin = letter2Pinyin.get(letter);
            contacts.resId = letter2ResId.get(letter);
            Contacts contacts1 = new Contacts();
            contacts1.name = letter + "lingyi";
            contacts1.pinyin = letter.toLowerCase() + "lingyi";
            contacts1.resId = 0;
            Contacts contacts2 = new Contacts();
            contacts2.name = letter + "linger";
            contacts2.pinyin = letter.toLowerCase() + "linger";
            contacts2.resId = 0;
            circleImageSectionList.add(contacts);
            circleImageSectionList.add(contacts1);
            circleImageSectionList.add(contacts2);
            roundImageSectionList.add(contacts);
            roundImageSectionList.add(contacts1);
            roundImageSectionList.add(contacts2);
            letterSectionList.add(contacts);
            letterSectionList.add(contacts1);
            letterSectionList.add(contacts2);
        }
    }
}
