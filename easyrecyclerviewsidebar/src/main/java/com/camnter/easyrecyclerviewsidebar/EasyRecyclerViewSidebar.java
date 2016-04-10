package com.camnter.easyrecyclerviewsidebar;

import android.annotation.TargetApi;
import android.content.Context;
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
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
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
    private static final int DEFAULT_FONT_COLOR = 0xff444444;
    private static final int DEFAULT_FONT_SIZE = 14;

    private static final int MAX_SECTION_COUNT = 30;

    private static final int DEFAULT_IMAGE_SECTION_BORDER_RADIUS = 6;

    private Paint letterPaint;
    private Paint imagePaint;

    private TextView floatView;
    private float sectionHeight;
    private float allSectionHeight;
    private float sectionFontSize;
    private float letterWidth = 0;
    private float letterHeight = 0;

    private Matrix imageSectionMatrix;
    private float imageSectionRadius;
    private RectF imageSectionRect;
    protected float imageSectionBorderRadius;

    private int viewWidth;
    private int viewHeight;
    private int viewHalfWidth;

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
        this.metrics = this.getResources().getDisplayMetrics();
        this.sectionFontSize = this.sp2px(DEFAULT_FONT_SIZE);
        this.sections = new ArrayList<>();
        this.imageSectionMatrix = new Matrix();
        this.initPaints();
        float[] letterProperty = this.measureText(this.letterPaint,
                DEFAULT_ONE_LETTER_MEASURE_WIDTH_CASE);
        this.letterWidth = letterProperty[0];
        this.letterHeight = letterProperty[1];
        this.imageSectionRadius = this.letterWidth / 2;
        this.imageSectionRect = new RectF(0, 0, this.letterWidth, this.letterWidth);
        this.imageSectionBorderRadius = this.dp2px(DEFAULT_IMAGE_SECTION_BORDER_RADIUS);
    }


    private void initPaints() {
        this.letterPaint = new Paint();
        this.letterPaint.setAntiAlias(true);
        this.letterPaint.setTextAlign(Paint.Align.CENTER);
        this.letterPaint.setColor(DEFAULT_FONT_COLOR);
        this.letterPaint.setTextSize(this.sectionFontSize);

        this.imagePaint = new Paint();
        this.imagePaint.setAntiAlias(true);
    }


    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.viewWidth = this.getWidth();
        this.viewHeight = this.getHeight();
        this.viewHalfWidth = this.viewWidth / 2;
        this.sectionHeight = this.viewHeight / MAX_SECTION_COUNT;
        boolean isPreviousImage = false;
        boolean isPreviousLetter = false;

        if (this.sections.size() > 0) {
            this.allSectionHeight = this.sectionHeight * this.sections.size();
            float top = this.viewHeight / 2 - allSectionHeight / 2 + this.sectionHeight / 2 -
                    this.sectionFontSize / 2;
            for (int i = 0; i < this.sections.size(); i++) {
                EasySection section = this.sections.get(i);
                if (section instanceof EasyImageSection) {
                    EasyImageSection imageSection = (EasyImageSection) section;
                    this.setPaintShader(imageSection);
                    canvas.save();
                    if (isPreviousLetter) {
                        top -= this.letterWidth - (Math.max(this.letterHeight, this.sectionHeight) -
                                Math.min(this.letterHeight, this.sectionHeight));
                    }
                    canvas.translate(this.viewHalfWidth - this.letterWidth / 2,
                            top + this.sectionHeight * i);
                    switch (imageSection.imageType) {
                        case EasyImageSection.ROUND: {
                            canvas.drawRoundRect(this.imageSectionRect,
                                    this.imageSectionBorderRadius, this.imageSectionBorderRadius,
                                    this.imagePaint);
                            break;
                        }
                        case EasyImageSection.CIRCLE: {
                            canvas.drawCircle(this.viewHalfWidth, top + this.sectionHeight * i,
                                    this.imageSectionRadius, this.imagePaint);
                            break;
                        }
                    }
                    canvas.restore();
                    isPreviousImage = true;
                    isPreviousLetter = false;
                } else {
                    if (isPreviousImage) {
                        top += this.letterWidth;
                    }
                    canvas.drawText(section.description, this.viewHalfWidth,
                            top + this.sectionHeight * i, this.letterPaint);
                    isPreviousImage = false;
                    isPreviousLetter = true;
                }
            }
        } else {
            this.sectionHeight = 0;
            this.allSectionHeight = 0;
        }
    }


    @Override public boolean onTouchEvent(MotionEvent event) {
        if (this.sections == null || this.sections.size() < 1) return super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.setBackgroundColor(DEFAULT_VIEW_BACKGROUND);
                this.floatView.setVisibility(VISIBLE);
                this.showFloatView(event);
                return true;
            case MotionEvent.ACTION_MOVE:
                this.showFloatView(event);
                return true;
            case MotionEvent.ACTION_UP:
                this.setBackgroundColor(Color.TRANSPARENT);
                this.floatView.setVisibility(INVISIBLE);
                return true;
            case MotionEvent.ACTION_CANCEL:
                this.setBackgroundColor(Color.TRANSPARENT);
                this.floatView.setVisibility(INVISIBLE);
                return true;
        }
        return super.onTouchEvent(event);
    }


    private int getSectionIndex(float y) {
        float currentY = y - (this.getHeight() - this.allSectionHeight) / 2;
        int index = (int) (currentY / this.sectionHeight);
        if (index <= 0) {
            return 0;
        }
        if (index >= this.sections.size()) {
            index = this.sections.size() - 1;
        }
        return index;
    }


    private void showFloatView(MotionEvent event) {
        int sectionIndex = this.getSectionIndex(event.getY());
        if (this.sections == null || this.sections.size() < 1 ||
                sectionIndex >= this.sections.size()) {
            return;
        }
        EasySection section = this.sections.get(sectionIndex);
        String floatText = section.description;
        if (section instanceof EasyImageSection) {

        }
        this.floatView.setText(floatText);
        if (this.onTouchSectionListener != null) {
            this.onTouchSectionListener.onTouchSection(sectionIndex);
        }
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
        float scale = 1.0f;
        switch (imageSection.imageType) {
            case EasyImageSection.ROUND: {
                scale = Math.max(this.letterWidth * 1.0f / bitmap.getWidth(),
                        this.letterWidth * 1.0f / bitmap.getHeight());
                break;
            }
            case EasyImageSection.CIRCLE: {
                scale = this.letterWidth * 1.0f / Math.min(bitmap.getWidth(), bitmap.getHeight());
                break;
            }
        }
        this.imageSectionMatrix.setScale(scale, scale);
        sectionBitmapShader.setLocalMatrix(this.imageSectionMatrix);
        this.imagePaint.setShader(sectionBitmapShader);
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


    public float dp2px(int dp) {
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
        this.invalidate();
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


    public TextView getFloatView() {
        return floatView;
    }


    public void setFloatView(TextView floatView) {
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
         * On touch section
         *
         * @param sectionIndex sectionIndex
         */
        void onTouchSection(int sectionIndex);
    }
}
