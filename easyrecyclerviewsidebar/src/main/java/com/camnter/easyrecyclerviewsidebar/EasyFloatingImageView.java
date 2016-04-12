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
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.ImageView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Description：EasyFloatingImageView
 * Created by：CaMnter
 * Time：2016-04-11 22:43
 */
public class EasyFloatingImageView extends ImageView {

    public static final int ROUND = 2601;
    public static final int CIRCLE = 2602;

    // @formatter:off
    @IntDef({ ROUND, CIRCLE })
    @Retention(RetentionPolicy.SOURCE)
    public @interface ImageType {}
    // @formatter:on

    @ImageType private int imageType;

    private static final int DEFAULT_BORDER_RADIUS = 6;

    protected int mRadius;
    protected float mBorderRadius;
    private Paint mBitmapPaint;
    private Matrix mMatrix;
    private int mSide;
    protected RectF mRoundRect;

    private DisplayMetrics mMetrics;


    public EasyFloatingImageView(Context context) {
        super(context);
        this.init(context, null);
    }


    public EasyFloatingImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context, attrs);
    }


    public EasyFloatingImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context, attrs);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public EasyFloatingImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        this.mMatrix = new Matrix();
        this.mBitmapPaint = new Paint();
        this.mBitmapPaint.setAntiAlias(true);
        this.mMetrics = this.getResources().getDisplayMetrics();

        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.EasyFloatingImageView);
        this.imageType = typedArray.getInt(R.styleable.EasyFloatingImageView_easyFloatingImageType,
                CIRCLE) == CIRCLE ? CIRCLE : ROUND;
        this.mBorderRadius = typedArray.getDimension(
                R.styleable.EasyFloatingImageView_easyFloatingBorderRadius,
                this.dp2px(DEFAULT_BORDER_RADIUS));
        typedArray.recycle();
    }


    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.imageType == CIRCLE) {
            this.mSide = Math.min(this.getMeasuredWidth(), this.getMeasuredHeight());
            this.mRadius = this.mSide / 2;
            this.setMeasuredDimension(this.mSide, this.mSide);
        }
    }


    @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (this.imageType == ROUND) {
            this.mRoundRect = new RectF(0, 0, this.getWidth(), this.getHeight());
        }
    }


    @Override protected void onDraw(Canvas canvas) {
        if (this.getDrawable() == null) return;
        this.setPaintShader();
        switch (this.imageType) {
            case ROUND: {
                canvas.drawRoundRect(this.mRoundRect, this.mBorderRadius, this.mBorderRadius,
                        this.mBitmapPaint);
                break;
            }
            case CIRCLE: {
                canvas.drawCircle(this.mRadius, this.mRadius, this.mRadius, this.mBitmapPaint);
                break;
            }
        }
    }


    private void setPaintShader() {
        Drawable drawable = this.getDrawable();
        if (drawable == null) return;

        Bitmap bitmap = this.drawableToBitmap(drawable);
        BitmapShader mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP);
        float scale = 1.0f;
        switch (this.imageType) {
            case ROUND: {
                scale = Math.max(this.getWidth() * 1.0f / bitmap.getWidth(),
                        this.getHeight() * 1.0f / bitmap.getHeight());
                break;
            }
            case CIRCLE: {
                scale = this.mSide * 1.0f / Math.min(bitmap.getWidth(), bitmap.getHeight());
                break;
            }
        }
        this.mMatrix.setScale(scale, scale);
        mBitmapShader.setLocalMatrix(this.mMatrix);
        this.mBitmapPaint.setShader(mBitmapShader);
    }


    private static final String STATE_INSTANCE = "state_instance";
    private static final String STATE_TYPE = "state_type";
    private static final String STATE_BORDER_RADIUS = "state_border_radius";


    @Override protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(STATE_INSTANCE, super.onSaveInstanceState());
        bundle.putInt(STATE_TYPE, this.imageType);
        bundle.putFloat(STATE_BORDER_RADIUS, this.mBorderRadius);
        return bundle;
    }


    @Override protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            super.onRestoreInstanceState(((Bundle) state).getParcelable(STATE_INSTANCE));
            this.imageType = bundle.getInt(STATE_TYPE, CIRCLE) == CIRCLE ? CIRCLE : ROUND;
            this.mBorderRadius = bundle.getFloat(STATE_BORDER_RADIUS);
        } else {
            super.onRestoreInstanceState(state);
        }
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


    public void setBorderRadius(int borderRadius) {
        float px = this.dp2px(borderRadius);
        if (this.mBorderRadius != px) {
            this.mBorderRadius = px;
            this.invalidate();
        }
    }


    public void setImageType(@ImageType int imageType) {
        if (this.imageType != imageType) {
            this.imageType = imageType;
            this.requestLayout();
        }
    }


    public float dp2px(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, this.mMetrics);
    }
}
