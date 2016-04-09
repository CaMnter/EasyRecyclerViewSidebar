package com.camnter.easyrecyclerviewsidebar.sections;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Description：EasyImageSection
 * Created by：CaMnter
 * Time：2016-04-09 13:03
 */
public class EasyImageSection extends EasySection {

    public static final int ROUND = 2601;
    public static final int CIRCLE = 2602;

    // @formatter:off
    @IntDef({ ROUND, CIRCLE })
    @Retention(RetentionPolicy.SOURCE)
    public @interface ImageType {}
    // @formatter:on

    // @formatter:off
    @EasyImageSection.ImageType
    @NonNull
    public int imageType;
    // @formatter:on

    public int resId;
    public Drawable drawable;


    public EasyImageSection(@DrawableRes int resId, @ImageType int imageType) {
        this(resId, imageType, "");
    }


    public EasyImageSection(@DrawableRes int resId, @ImageType int imageType, String description) {
        super(description);
        this.resId = resId;
        this.imageType = imageType;
    }


    public EasyImageSection(@NonNull Drawable drawable, @ImageType int imageType) {
        this(drawable, imageType, "");
    }


    public EasyImageSection(
            @NonNull Drawable drawable, @ImageType int imageType, String description) {
        super(description);
        this.drawable = drawable;
        this.imageType = imageType;
    }
}
