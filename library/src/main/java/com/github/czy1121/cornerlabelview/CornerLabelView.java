/*
 * Copyright 2016 czy1121
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.czy1121.cornerlabelview;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;


public class CornerLabelView extends View {

    private static final float SQRT2 = (float) Math.sqrt(2);

    private Paint mPaint;
    private final Painter mText1 = new Painter();
    private final Painter mText2 = new Painter();

    private float mPaddingTop = 0;
    private float mPaddingCenter = 0;
    private float mPaddingBottom = 0;

    private boolean mIsLeft = true;
    private boolean mIsTop = true;
    private boolean mIsTriangle = true;

    private int mHeight;

    public CornerLabelView(Context context) {
        this(context, null);
    }

    public CornerLabelView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CornerLabelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CornerLabelView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CornerLabelView);

        mPaddingTop = ta.getDimension(R.styleable.CornerLabelView_clvPaddingTop, dp2px(10));
        mPaddingCenter = ta.getDimension(R.styleable.CornerLabelView_clvPaddingCenter, dp2px(0));
        mPaddingBottom = ta.getDimension(R.styleable.CornerLabelView_clvPaddingBottom, dp2px(3));

        mText1.text = ta.getString(R.styleable.CornerLabelView_clvText1);
        mText1.height = ta.getDimension(R.styleable.CornerLabelView_clvText1Height, dp2px(12));
        mText1.color = ta.getColor(R.styleable.CornerLabelView_clvText1Color, 0xffffffff);
        mText1.init();
        mText1.reset();

        mText2.text = ta.getString(R.styleable.CornerLabelView_clvText2);
        mText2.height = ta.getDimension(R.styleable.CornerLabelView_clvText2Height, dp2px(8));
        mText2.color = ta.getColor(R.styleable.CornerLabelView_clvText2Color, 0x66ffffff);
        mText2.init();
        mText2.reset();

        int fillColor = ta.getColor(R.styleable.CornerLabelView_clvFillColor, 0x66000000);
        int flags = ta.getInteger(R.styleable.CornerLabelView_clvFlags, 0);

        mIsLeft = (flags & 1) == 0;
        mIsTop = (flags & 2) == 0;
        mIsTriangle = (flags & 4) > 0;

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        mPaint.setAntiAlias(true);
        mPaint.setColor(fillColor);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mHeight = (int) (mPaddingTop + mPaddingCenter + mPaddingBottom + mText1.height + mText2.height);


        int size = (int) (mHeight * SQRT2);
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float xy = mHeight / SQRT2;
        canvas.save();
        canvas.translate(xy, xy);
        canvas.rotate((mIsTop ? 1 : -1) * (mIsLeft ? -45 : 45));
        canvas.drawPath(calcPath(), mPaint);

        mText1.draw(canvas, mPaddingBottom , mIsTop);
        if (mIsTriangle) {
            mText2.draw(canvas, mPaddingBottom + mPaddingCenter + mText1.height, mIsTop);
        }
        canvas.restore();
    }

    private int dp2px(float dp) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    private Path calcPath() {
        Path path = new Path();
        path.moveTo(-mHeight, 0);
        path.lineTo(mHeight, 0);
        int factor = mIsTop ? -1 : 1;
        if (mIsTriangle) {
            path.lineTo(0, factor * mHeight);
        } else {
            int lineHeight = factor * (int) (mPaddingCenter + mPaddingBottom + mText1.height);
            path.lineTo(mHeight + lineHeight, lineHeight);
            path.lineTo(-mHeight + lineHeight, lineHeight);
        }
        path.close();
        return path;
    }

    public CornerLabelView setPaddingTop(float dp) {
        mPaddingTop = dp2px(dp);
        requestLayout();
        return this;
    }
    public CornerLabelView setPaddingCenter(float dp) {
        mPaddingCenter = dp2px(dp);
        requestLayout();
        return this;
    }
    public CornerLabelView setPaddingBottom(float dp) {
        mPaddingBottom = dp2px(dp);
        requestLayout();
        return this;
    }

    public CornerLabelView setText1(String text) {
        mText1.text = text;
        mText1.init();
        requestLayout();
        return this;
    }
    public CornerLabelView setText1Height(float dp) {
        mText1.height = dp2px(dp);
        mText1.init();
        requestLayout();
        return this;
    }
    public CornerLabelView setText1Color(@ColorInt int color) {
        mText1.color = color;
        mText1.init();
        invalidate();
        return this;
    }
    public CornerLabelView setText1ColorResource(@ColorRes int resource) {
        mText1.color = ContextCompat.getColor(getContext(), resource);
        mText1.init();
        invalidate();
        return this;
    }

    public CornerLabelView setText2(String text) {
        mText2.text = text;
        mText2.init();
        requestLayout();
        return this;
    }
    public CornerLabelView setText2Height(float dp) {
        mText2.height = dp2px(dp);
        mText2.init();
        requestLayout();
        return this;
    }
    public CornerLabelView setText2Color(@ColorInt int color) {
        mText2.color = color;
        mText2.init();
        invalidate();
        return this;
    }
    public CornerLabelView setText2ColorResource(@ColorRes int resource) {
        mText2.color = ContextCompat.getColor(getContext(), resource);
        mText2.init();
        invalidate();
        return this;
    }

    public CornerLabelView setFillColor(@ColorInt int color) {
        mPaint.setColor(color);
        invalidate();
        return this;
    }
    public CornerLabelView setFillColorResource(@ColorRes int resource) {
        return setFillColor(ContextCompat.getColor(getContext(), resource));
    }
    public CornerLabelView left() {
        mIsLeft = true;
        invalidate();
        return this;
    }
    public CornerLabelView right() {
        mIsLeft = false;
        invalidate();
        return this;
    }
    public CornerLabelView top() {
        mIsTop = true;
        invalidate();
        return this;
    }
    public CornerLabelView bottom() {
        mIsTop = false;
        invalidate();
        return this;
    }
    public CornerLabelView triangle(boolean value) {
        mIsTriangle = value;
        invalidate();
        return this;
    }

    static class Painter {
        private final TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        String text = "";
        int color = Color.WHITE;
        float height = 0;

        private float offset = 0;


        void init() {
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(height);
            paint.setColor(color);
            reset();
        }

        void draw(Canvas canvas, float y, boolean isTop) {
            canvas.drawText(text, 0, (isTop ? -1 : 1) * (y + height / 2) + offset, paint);
        }

        void reset() {
            if (text == null) {
                text = "";
            }
            Rect rect = new Rect();
            paint.getTextBounds(text, 0, text.length(), rect);
            offset = rect.height() / 2;
        }
    }
}