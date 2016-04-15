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
package com.camnter.easyrecyclerviewsidebar;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import com.camnter.easyrecyclerviewsidebar.sections.EasyImageSection;
import com.camnter.easyrecyclerviewsidebar.sections.EasySection;
import java.util.ArrayList;
import java.util.List;

/**
 * Description：EasyRecyclerViewSidebar
 * Created by：CaMnter
 * Time：2016-04-05 21:18
 */
public class EasyRecyclerViewSidebar extends View {

    private static final String DEFAULT_ONE_LETTER_MEASURE_WIDTH_CASE = "M";

    private static final int DEFAULT_VIEW_BACKGROUND = 0x40000000;
    private static final int DEFAULT_FONT_COLOR = 0xff555555;
    private static final int DEFAULT_FONT_SIZE = 14;
    private static final int MAX_SECTION_COUNT = 30;

    private static final float DEFAULT_IMAGE_SECTION_PAINT_WIDTH = 0.01f;
    private static final int DEFAULT_IMAGE_SECTION_BORDER_RADIUS = 2;
    private static final int DEFAULT_IMAGE_SECTION_CIRCLE_BORDER_RADIUS = 66;
    private static final boolean DEFAULT_ON_TOUCH_WRAP_DRAW_AREA = true;

    private boolean touchWrapArea;
    private int fontColor;
    private int viewBackground;

    private Paint letterPaint;
    private SparseArray<Paint> imagePaints = new SparseArray<>();

    private View floatView;
    private float sectionHeight;
    private float sectionFontSize;
    private float letterSize = 0;
    private float letterHeight = 0;

    private Matrix imageSectionMatrix;
    private RectF imageSectionRect;
    protected float imageSectionBorderRadius;
    protected float imageSectionCircleBorderRadius;

    private float drawBeginY;
    private float drawEndY;

    private DisplayMetrics metrics;

    private ArrayList<EasySection> sections;

    private OnTouchSectionListener onTouchSectionListener;


    public EasyRecyclerViewSidebar(Context context) {
        super(context);
        this.init(context, null);
    }


    public EasyRecyclerViewSidebar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context, attrs);
    }


    public EasyRecyclerViewSidebar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context, attrs);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public EasyRecyclerViewSidebar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.EasyRecyclerViewSidebar);
        this.fontColor = typedArray.getColor(
                R.styleable.EasyRecyclerViewSidebar_easySidebarFontColor, DEFAULT_FONT_COLOR);
        this.viewBackground = typedArray.getColor(
                R.styleable.EasyRecyclerViewSidebar_easySidebarBackground, DEFAULT_VIEW_BACKGROUND);
        this.touchWrapArea = typedArray.getBoolean(
                R.styleable.EasyRecyclerViewSidebar_easySidebarTouchWrapArea,
                DEFAULT_ON_TOUCH_WRAP_DRAW_AREA);
        this.initOtherAttributes();
        this.initPaints();
        this.initLetterAndImageAttributes();
        typedArray.recycle();
    }


    private void initOtherAttributes() {
        this.metrics = this.getResources().getDisplayMetrics();
        this.sections = new ArrayList<>();
        this.imageSectionMatrix = new Matrix();
    }


    private void initPaints() {
        this.sectionFontSize = this.sp2px(DEFAULT_FONT_SIZE);

        this.letterPaint = new Paint();
        this.letterPaint.setAntiAlias(true);
        this.letterPaint.setTextAlign(Paint.Align.CENTER);
        this.letterPaint.setColor(this.fontColor);
        this.letterPaint.setTextSize(this.sectionFontSize);
    }


    private void initLetterAndImageAttributes() {
        float[] letterProperty = this.measureText(this.letterPaint,
                DEFAULT_ONE_LETTER_MEASURE_WIDTH_CASE);
        this.letterSize = letterProperty[0];
        this.letterHeight = letterProperty[1];
        this.imageSectionRect = new RectF(0, 0, this.letterSize, this.letterSize);
        this.imageSectionBorderRadius = this.dp2px(DEFAULT_IMAGE_SECTION_BORDER_RADIUS);
        this.imageSectionCircleBorderRadius = this.dp2px(
                DEFAULT_IMAGE_SECTION_CIRCLE_BORDER_RADIUS);
    }


    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int viewWidth = this.getWidth();
        int viewHeight = this.getHeight();
        int viewHalfWidth = viewWidth / 2;
        this.sectionHeight = viewHeight / MAX_SECTION_COUNT;
        boolean isPreviousImage = false;
        boolean isPreviousLetter = false;
        float allSectionHeight;
        if (this.sections.size() > 0) {
            allSectionHeight = this.sectionHeight * this.sections.size();
            this.drawBeginY = (viewHeight - allSectionHeight) / 2;
            this.drawEndY = this.drawBeginY + allSectionHeight;
            float top = viewHeight / 2 - allSectionHeight / 2 + this.sectionHeight / 2 -
                    this.sectionFontSize / 2;
            for (int i = 0; i < this.sections.size(); i++) {
                EasySection section = this.sections.get(i);
                if (section instanceof EasyImageSection) {
                    EasyImageSection imageSection = (EasyImageSection) section;
                    this.setPaintShader(imageSection);
                    if (isPreviousLetter) {
                        top -= this.letterSize - (Math.max(this.letterHeight, this.sectionHeight) -
                                Math.min(this.letterHeight, this.sectionHeight));
                    }
                    canvas.save();
                    canvas.translate(viewHalfWidth - this.letterSize / 2,
                            top + this.sectionHeight * i);
                    Paint imagePaint = this.imagePaints.get(imageSection.hashCode());
                    switch (imageSection.imageType) {
                        case EasyImageSection.ROUND: {
                            canvas.drawRoundRect(this.imageSectionRect,
                                    this.imageSectionBorderRadius, this.imageSectionBorderRadius,
                                    imagePaint);
                            break;
                        }
                        case EasyImageSection.CIRCLE: {
                            canvas.drawRoundRect(this.imageSectionRect,
                                    this.imageSectionCircleBorderRadius,
                                    this.imageSectionCircleBorderRadius, imagePaint);
                            break;
                        }
                    }
                    canvas.restore();
                    isPreviousImage = true;
                    isPreviousLetter = false;
                } else {
                    if (isPreviousImage) {
                        top = top + this.letterSize;
                    }
                    canvas.drawText(section.letter, viewHalfWidth, top + this.sectionHeight * i,
                            this.letterPaint);
                    isPreviousImage = false;
                    isPreviousLetter = true;
                }
            }
        } else {
            this.sectionHeight = 0;
        }
    }


    @Override public boolean onTouchEvent(MotionEvent event) {
        if (this.sections == null || this.sections.size() < 1) return super.onTouchEvent(event);
        float eventY = event.getY();
        int action = event.getAction();
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
            this.setBackgroundColor(Color.TRANSPARENT);
            this.floatView.setVisibility(INVISIBLE);
            return true;
        }
        if (this.touchWrapArea && eventY < this.drawBeginY || eventY > this.drawEndY) {
            return super.onTouchEvent(event);
        }
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                this.setBackgroundColor(this.viewBackground);
                this.floatView.setVisibility(VISIBLE);
                this.showFloatView(eventY);
                return true;
            case MotionEvent.ACTION_MOVE:
                this.showFloatView(eventY);
                return true;
        }
        return super.onTouchEvent(event);
    }


    private void showFloatView(float eventY) {
        int sectionIndex = this.getSectionIndex(eventY);
        if (this.sections == null || this.sections.size() < 1 ||
                sectionIndex >= this.sections.size()) {
            return;
        }
        EasySection section = this.sections.get(sectionIndex);
        if (this.onTouchSectionListener == null) {
            return;
        }
        if (section instanceof EasyImageSection) {
            this.onTouchSectionListener.onTouchImageSection(sectionIndex,
                    (EasyImageSection) section);
        } else {
            this.onTouchSectionListener.onTouchLetterSection(sectionIndex, section);
        }
    }


    private int getSectionIndex(float eventY) {
        float currentY = eventY - this.drawBeginY;
        int index = (int) (currentY / this.sectionHeight);
        if (index <= 0) {
            return 0;
        }
        if (index >= this.sections.size()) {
            index = this.sections.size() - 1;
        }
        return index;
    }


    private void setPaintShader(@NonNull EasyImageSection imageSection) {
        Drawable drawable;
        if (imageSection.drawable == null && imageSection.resId >= 0) {
            drawable = ResourcesCompat.getDrawable(this.getResources(), imageSection.resId, null);
        } else {
            drawable = imageSection.drawable;
        }
        if (drawable == null) return;

        Bitmap bitmap = this.drawableToBitmap(drawable);
        BitmapShader sectionBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP);
        float scale;
        scale = Math.max(this.letterSize * 1.0f / bitmap.getWidth(),
                this.letterSize * 1.0f / bitmap.getHeight());
        this.imageSectionMatrix.setScale(scale, scale);
        sectionBitmapShader.setLocalMatrix(this.imageSectionMatrix);
        Paint imagePaint = this.imagePaints.get(imageSection.hashCode());
        imagePaint.setShader(sectionBitmapShader);
    }


    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) ((BitmapDrawable) drawable).getBitmap();
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = this.createBitmapSafely(width, height, Bitmap.Config.ARGB_8888, 1);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }


    public Bitmap createBitmapSafely(int width, int height, Bitmap.Config config, int retryCount) {
        try {
            return Bitmap.createBitmap(width, height, config);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            if (retryCount > 0) {
                System.gc();
                return createBitmapSafely(width, height, config, retryCount - 1);
            }
            return null;
        }
    }


    private float dp2px(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, this.metrics);
    }


    private float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, this.metrics);
    }


    private float sp2px(float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, this.metrics);
    }


    public void setSections(String[] sections) {
        this.sections.clear();
        for (String section : sections) {
            this.sections.add(new EasySection(section));
        }
        this.invalidate();
    }


    public void setSections(List<EasySection> sections) {
        this.sections.clear();
        this.sections.addAll(sections);
        this.imagePaints.clear();
        for (EasySection section : this.sections) {
            if (section instanceof EasyImageSection) {
                Paint paint = this.createImagePaint();
                this.imagePaints.put(section.hashCode(), paint);
            }
        }
        this.invalidate();
    }


    private Paint createImagePaint() {
        Paint imagePaint = new Paint();
        imagePaint.setAntiAlias(true);
        imagePaint.setStrokeWidth(this.dp2px(DEFAULT_IMAGE_SECTION_PAINT_WIDTH));
        return imagePaint;
    }


    /**
     * @param paint paint
     * @param text text
     * @return float[] [0]=width [1]=height
     */
    private float[] measureText(Paint paint, String text) {
        float[] property = new float[2];
        property[0] = paint.measureText(text);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        property[1] = fontMetrics.descent - fontMetrics.ascent + fontMetrics.leading;
        return property;
    }


    public View getFloatView() {
        return this.floatView;
    }


    public void setFloatView(View floatView) {
        this.floatView = floatView;
    }


    public OnTouchSectionListener getOnTouchSectionListener() {
        return onTouchSectionListener;
    }


    public void setOnTouchSectionListener(OnTouchSectionListener onTouchSectionListener) {
        this.onTouchSectionListener = onTouchSectionListener;
    }


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
}
