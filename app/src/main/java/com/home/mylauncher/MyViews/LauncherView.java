package com.home.mylauncher.MyViews;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.home.mylauncher.MyUtils.Utils;
import com.home.mylauncher.MyUtils.ViewPath;
import com.home.mylauncher.MyUtils.ViewPathEvaluator;
import com.home.mylauncher.MyUtils.ViewPoint;
import com.home.mylauncher.R;

/**
 * Created by home on 2017/12/10.
 */

public class LauncherView extends RelativeLayout {
    private int mHeight;
    private int mWidth;
    private int dp80 = Utils.dp2px(getContext(), 80);
    private boolean mHasStart;

    private ImageView blue, red, purple, yellow;
    private ViewPath bluePath, redPath, purplePath, yellowPath;
    private AnimatorSet blueAll, redAll, purpleAll, yellowAll;

    public LauncherView(Context context) {
        super(context);
    }

    public LauncherView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LauncherView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.e("TEST", "onMeasure()");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();
        initPath();
    }

    private void initPath()
    {
        Log.e("TEST", "initPath() " + mWidth + " , " + mHeight);
        bluePath = new ViewPath();
        bluePath.moveTo(0,0);
        bluePath.lineTo(mWidth / 5 * 4 - mWidth / 2, 0);
        //bluePath.lineTo(200, 200);
        bluePath.cubicTo(700, 2 / 3 * mHeight, -mWidth / 2, mHeight / 2, 0, -dp80);

        redPath = new ViewPath();
        redPath.moveTo(0,0);
        redPath.lineTo(mWidth / 5 * 1 - mWidth / 2, 0);
        redPath.cubicTo(-700, -mHeight / 2, mWidth / 3 * 2, -mHeight / 3 * 2, 0, -dp80);

        purplePath = new ViewPath();
        purplePath.moveTo(0,0);
        purplePath.lineTo(mWidth / 5 * 2 - mWidth / 2, 0);
        purplePath.cubicTo(-300, -mHeight / 2, mWidth, -mHeight / 9 * 5, 0, -dp80);

        yellowPath = new ViewPath();
        yellowPath.moveTo(0,0);
        yellowPath.lineTo(mWidth / 5 * 3 - mWidth / 2, 0);
        yellowPath.cubicTo(700, mHeight / 3 * 2, -mWidth / 2, mHeight / 2, 0, -dp80);
    }
    public void start()
    {
        Log.e("TEST", "start()");
        //reset the status
        removeAllViews();
        //init again()
        //paths has been created in on Measure
        init();
        //start the animations
        blueAll.start();
        purpleAll.start();
        redAll.start();
        yellowAll.start();

        //delay to shown logo and slogan
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showLogo();
            }
        }, 2400);
    }
    private void init()
    {
        Log.e("TEST", "init()");
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.addRule(CENTER_HORIZONTAL, TRUE);
        lp.addRule(CENTER_VERTICAL, TRUE);
        lp.setMargins(0,0,0,dp80);

        blue = new ImageView(getContext());
        blue.setLayoutParams(lp);
        blue.setImageDrawable(getResources().getDrawable(R.drawable.shape_circle_blue));
        addView(blue);

        red = new ImageView(getContext());
        red.setLayoutParams(lp);
        red.setImageDrawable(getResources().getDrawable(R.drawable.shape_circle_red));
        addView(red);

        purple = new ImageView(getContext());
        purple.setLayoutParams(lp);
        purple.setImageDrawable(getResources().getDrawable(R.drawable.shape_circle_purple));
        addView(purple);

        yellow = new ImageView(getContext());
        yellow.setLayoutParams(lp);
        yellow.setImageDrawable(getResources().getDrawable(R.drawable.shape_circle_yellow));
        addView(yellow);

        setAnimation(blue, bluePath);
        setAnimation(red, redPath);
        setAnimation(purple, purplePath);
        setAnimation(yellow, yellowPath);
    }

    /**
     * Given path, add animation to the target.
     * --convert the path into animation for the target.
     * --add some more animations like fading/scaling
     * @param target
     * @param path
     */
    private void setAnimation(final ImageView target, ViewPath path)
    {
        //build path-pathAnimator
        ObjectAnimator pathAnimator = ObjectAnimator.ofObject(new ViewObj(target),
                "fabLoc", new ViewPathEvaluator(), path.getPoints().toArray());
        pathAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        pathAnimator.setDuration(2600);

        //build value-pathAnimator
        ValueAnimator valueAnimator = buildValueAnimator(target);

        //bind the animation end listener
        valueAnimator.addListener(new AnimationEndListener(target));

        //generate animation set for each ImageView
        if(target == blue)
        {
            blueAll = new AnimatorSet();
            blueAll.playTogether(pathAnimator, valueAnimator);
        }else if(target == red)
        {
            redAll = new AnimatorSet();
            redAll.playTogether(pathAnimator, valueAnimator);
        }else if(target == purple)
        {
            purpleAll = new AnimatorSet();
            purpleAll.playTogether(pathAnimator, valueAnimator);
        }else if(target == yellow)
        {
            yellowAll = new AnimatorSet();
            yellowAll.playTogether(pathAnimator, valueAnimator);
        }
    }

    /**
     * Give target, build value animator to do scaling/alpha
     * @param target
     * @return value-animator with scaling/alpha effects
     */
    private ValueAnimator buildValueAnimator(final ImageView target)
    {
        //build value animator
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1000);
        valueAnimator.setDuration(1800);
        valueAnimator.setStartDelay(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                //increasing the transparency
                float alpha = 1 - value / 2000;
                float scale = getScale(target) - 1;
                if(value <= 500)
                {
                    //growing quickly at first
                    scale = 1 + (value / 500) * scale;
                }
                else
                {
                    //then growing slower and slower
                    scale = 1 + (1000 - value) / 500 * scale;
                }
                //where changes happens
                target.setScaleX(scale);
                target.setScaleY(scale);
                target.setAlpha(alpha);
            }
        });

        return valueAnimator;
    }

    private class AnimationEndListener extends AnimatorListenerAdapter {
        private ImageView item;
        public AnimationEndListener(ImageView target)
        {
            this.item = target;
        }
        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            removeView(this.item);
        }
    }
    public class ViewObj
    {
        /**
         * Seems there is some contract to use  ObjectAnimator.ofObject()
         * And this class feeds that contract.
         * It will find the 'setFabLoc' method of this object automatically?
         */
        private final ImageView item;

        public ViewObj(ImageView target)
        {
            Log.e("TEST", "ViewObj()");
            //must have setTranslationX/Y
            this.item = target;
        }

        public void setFabLoc(ViewPoint newLoc)
        {
            Log.e("TEST", "setFabLoc()");
            this.item.setTranslationX(newLoc.getX());
            this.item.setTranslationY(newLoc.getY());
        }
    }

    /**
     * @param target
     * @return the scale configured for this object
     */
    private float getScale(ImageView target) {
        if (target == red)
            return 3.0f;
        if (target == purple)
            return 2.0f;
        if (target == yellow)
            return 4.5f;
        if (target == blue)
            return 3.5f;
        return 2f;
    }

    private void showLogo()
    {
        //generate a view from the layout file
        View view = View.inflate(getContext(), R.layout.widget_load_view, this);

        //find log and slogan
        final View logo = view.findViewById(R.id.iv_logo);
        final View slogan = view.findViewById(R.id.iv_slogan);

        //create alpha-animator for logo
        ObjectAnimator animator = ObjectAnimator.ofFloat(logo, View.ALPHA, 0f, 1f);
        animator.setDuration(800);
        animator.start();

        //delay to animate for slogan
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //create animator for slogan
                ObjectAnimator sloganAnimator = ObjectAnimator.ofFloat(slogan, View.ALPHA, 0f, 1f);
                sloganAnimator.setDuration(800);
                sloganAnimator.start();
            }
        }, 400);
    }
}
