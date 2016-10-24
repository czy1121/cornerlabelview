package com.github.czy1121.cornerlabelview.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.github.czy1121.view.CornerLabelView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    static final String[] TEXTS = {"C", "JS", "PHP", "Java", "Shell", "Groovy", "Clojure", "Markdown", "SmallTalk", "TypeScript"};

    int mText1Index = 3;
    float mText1Height = 12;

    int mText2Index = 3;
    float mText2Height = 8;

    boolean mIsLeft = true;
    boolean mIsTop = true;
    boolean mIsTriangle = true;

    CornerLabelView mClv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mClv = (CornerLabelView) findViewById(R.id.label);

        findViewById(R.id.btn_color).setOnClickListener(this);
        findViewById(R.id.btn_left).setOnClickListener(this);
        findViewById(R.id.btn_top).setOnClickListener(this);
        findViewById(R.id.btn_triangle).setOnClickListener(this);

        findViewById(R.id.btn_text1_minus).setOnClickListener(this);
        findViewById(R.id.btn_text1_plus).setOnClickListener(this);
        findViewById(R.id.btn_height1_minus).setOnClickListener(this);
        findViewById(R.id.btn_height1_plus).setOnClickListener(this);

        findViewById(R.id.btn_text2_minus).setOnClickListener(this);
        findViewById(R.id.btn_text2_plus).setOnClickListener(this);
        findViewById(R.id.btn_height2_minus).setOnClickListener(this);
        findViewById(R.id.btn_height2_plus).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.btn_color:
            mClv.setFillColor(0xff000000 | random(0, 0xffffff));
            break;
        case R.id.btn_left:
            if (mIsLeft) {
                mClv.right();
            } else {
                mClv.left();
            }
            mIsLeft = !mIsLeft;
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mClv.getLayoutParams();
            lp.gravity = (mIsLeft ? Gravity.LEFT : Gravity.RIGHT) | (mIsTop ? Gravity.TOP : Gravity.BOTTOM);
            mClv.setLayoutParams(lp);
            break;
        case R.id.btn_top:
            if (mIsTop) {
                mClv.bottom();
            } else {
                mClv.top();
            }
            mIsTop = !mIsTop;
            FrameLayout.LayoutParams lp2 = (FrameLayout.LayoutParams) mClv.getLayoutParams();
            lp2.gravity = (mIsLeft ? Gravity.LEFT : Gravity.RIGHT) | (mIsTop ? Gravity.TOP : Gravity.BOTTOM);
            mClv.setLayoutParams(lp2);
            break;
        case R.id.btn_triangle:
            mIsTriangle = !mIsTriangle;
            mClv.triangle(mIsTriangle);
            break;
        case R.id.btn_text1_minus:
            mText1Index = (mText1Index - 1 + TEXTS.length) % TEXTS.length;
            mClv.setText1(TEXTS[mText1Index]);
            break;
        case R.id.btn_text1_plus:
            mText1Index = (mText1Index + 1) % TEXTS.length;
            mClv.setText1(TEXTS[mText1Index]);
            break;
        case R.id.btn_height1_minus:
            if (mText1Height < 8) {
                break;
            }
            mText1Height -= 2;
            mClv.setText1Height(mText1Height);
            mClv.setPaddingTop(mText1Height);
            mClv.setPaddingCenter(mText1Height / 3);
            mClv.setPaddingBottom(mText1Height / 3);
            break;
        case R.id.btn_height1_plus:
            if (mText1Height > 30) {
                break;
            }
            mText1Height += 2;
            mClv.setText1Height(mText1Height);
            mClv.setPaddingTop(mText1Height);
            mClv.setPaddingCenter(mText1Height / 3);
            mClv.setPaddingBottom(mText1Height / 3);
            break;
        case R.id.btn_text2_minus:
            mText2Index = (mText2Index + 5 - 1) % 5;
            mClv.setText2("1234567890".substring(0, mText2Index));
            break;
        case R.id.btn_text2_plus:
            mText2Index = (mText2Index + 5 + 1) % 5;
            mClv.setText2("1234567890".substring(0, mText2Index));
            break;
        case R.id.btn_height2_minus:
            if (mText2Height < 4) {
                break;
            }
            mText2Height -= 2;
            mClv.setText2Height(mText2Height);
            break;
        case R.id.btn_height2_plus:
            if (mText2Height > 20) {
                break;
            }
            mText2Height += 2;
            mClv.setText2Height(mText2Height);
            break;
        }
    }
    public static int random(int min, int max) {
        if (min > max) {
            return 0;
        }
        if (min == max) {
            return min;
        }
        return min + new Random().nextInt(max - min);
    }
}
