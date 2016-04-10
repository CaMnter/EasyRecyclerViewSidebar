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

    public static final int NULL_POSITION = -2601;

    // @formatter:off
    @IntDef({ ROUND, CIRCLE })
    @Retention(RetentionPolicy.SOURCE)
    public @interface ImageType {}
    // @formatter:on

    // @formatter:off
    @EasyImageSection.ImageType
    public int imageType;
    // @formatter:on

    public int resId;
    public Drawable drawable;
    public int defaultPosition;


    public EasyImageSection(@DrawableRes int resId, @ImageType int imageType, int defaultPosition) {
        this(resId, imageType, null, defaultPosition);
    }


    public EasyImageSection(
            @DrawableRes int resId,
            @ImageType int imageType, String description, int defaultPosition) {
        super(description);
        this.resId = resId;
        this.imageType = imageType;
        this.defaultPosition = defaultPosition;
    }


    public EasyImageSection(
            @NonNull Drawable drawable, @ImageType int imageType, int defaultPosition) {
        this(drawable, imageType, null, defaultPosition);
    }


    public EasyImageSection(
            @NonNull Drawable drawable,
            @ImageType int imageType, String description, int defaultPosition) {
        super(description);
        this.drawable = drawable;
        this.imageType = imageType;
        this.defaultPosition = defaultPosition;
    }
}
