package com.camnter.easyrecyclerviewsidebar;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Description：EasyRecyclerViewSidebar
 * Created by：CaMnter
 * Time：2016-04-05 21:18
 */
public class EasyRecyclerViewSidebar extends View {

    private static final int DEFAULT_VIEW_BACKGROUND = 0x40000000;
    private static final int DEFAULT_FONT_COLOR = 0xff444444;
    private static final int DEFAULT_FONT_SIZE = 10;

    private static final int MAX_SECTION_COUNT = 30;

    private Paint paint;
    private TextView floatView;
    private float sectionHeight;
    private float allSectionHeight;
    private float sectionFontSize;

    private int viewWidth;
    private int viewHeight;
    private int viewHalfWidth;

    private String[] sections;

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
        this.sectionFontSize = this.sp2px(context, DEFAULT_FONT_SIZE);
        this.paint = new Paint();
        this.paint.setAntiAlias(true);
        this.paint.setTextAlign(Paint.Align.CENTER);
        this.paint.setColor(DEFAULT_FONT_COLOR);
        this.paint.setTextSize(this.sectionFontSize);
    }


    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.viewWidth = this.getWidth();
        this.viewHeight = this.getHeight();
        this.viewHalfWidth = this.viewWidth / 2;
        this.sectionHeight = this.viewHeight / MAX_SECTION_COUNT;
        if (this.sections != null && this.sections.length > 0) {
            this.allSectionHeight = this.sectionHeight * this.sections.length;
            float top = this.viewHeight / 2 - allSectionHeight / 2 + this.sectionHeight / 2 -
                    this.sectionFontSize / 2;
            for (int i = 1; i <= this.sections.length; i++) {
                canvas.drawText(sections[i], this.viewHalfWidth, top + this.sectionHeight * i,
                        paint);
            }
        } else {
            this.sectionHeight = 0;
            this.allSectionHeight = 0;
        }
    }


    @Override public boolean onTouchEvent(MotionEvent event) {
        if (this.sections == null || this.sections.length < 1) return super.onTouchEvent(event);
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
        if (index >= sections.length) {
            index = this.sections.length - 1;
        }
        return index;
    }


    private void showFloatView(MotionEvent event) {
        int sectionIndex = this.getSectionIndex(event.getY());
        if (this.sections == null || this.sections.length < 1 ||
                sectionIndex >= this.sections.length) {
            return;
        }
        String floatText = this.sections[sectionIndex];
        this.floatView.setText(floatText);
        if (this.onTouchSectionListener != null) {
            this.onTouchSectionListener.onTouchSection(sectionIndex);
        }
    }


    private int sp2px(Context context, float sp) {
        float density = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * density + 0.5F);
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
