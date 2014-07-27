package com.pahanez.flagbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * @author pindziukou
 * */
public class Flagbar extends View {
	
	private static int DELAY_30_FPS = 1000/30;
	private static int DELAY_60_FPS = 1000/60;

    private static final int DEFAULT_STRIPE_COLOR = Color.WHITE;
    private static final int DEFAULT_STRIPE_SPEED = 9;
    private static final int DEFAULT_STRIPE_DIRECTION = 0;
    private static final int DEFAULT_PROGRESS_START = -90;
    private static final int DEFAULT_STROKE_WIDTH = 30;
    private static final int DEFAULT_STRIPE_COUNT = 3;

    private static final int MAX_STRIPES_COUNT = 4;

	private int mLayoutWidth,mLayoutHeigth,mCenterX,mCenterY;

	private int mStripesCount;
	private boolean mIndeterminate;
	private int mStart;
	private int mStrokeWidth;
	private int mProgress;
    private int mFPS;
	
	public enum Direction {
		CLOCKWIZE,COUNTERCLOCKWIZE;
	}
	
    private Handler mIndeterminateHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
        	removeMessages(0);
        	invalidate();
        	if(mIndeterminate)
        		sendEmptyMessageDelayed(0, mFPS);
            //Deutschland champion 2014!!!!
    		}
    };
	
	private List<Stripe> stripes = new ArrayList<Stripe>();

	public Flagbar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public Flagbar(Context context, AttributeSet attrs) {
		super(context, attrs);
        xmlConfig(context.obtainStyledAttributes(attrs,
                R.styleable.flagbar));

	}


    public Flagbar(Context context) {
		super(context);
	}

    private void xmlConfig(TypedArray typedArray) {
        mStripesCount = typedArray.getInteger(R.styleable.flagbar_stripesCount,DEFAULT_STRIPE_COUNT);

        if(mStripesCount<1 || mStripesCount>4)
            throw new IllegalArgumentException("Stripes count could be between 1 ... 4 !");


        //colors
        int colors []  = new int[MAX_STRIPES_COUNT];
        colors[0] = (int) typedArray.getInteger(R.styleable.flagbar_firstLineColor,DEFAULT_STRIPE_COLOR);
        colors[1] = (int) typedArray.getInteger(R.styleable.flagbar_secondLineColor,DEFAULT_STRIPE_COLOR);
        colors[2] = (int) typedArray.getInteger(R.styleable.flagbar_thirdLineColor,DEFAULT_STRIPE_COLOR);
        colors[3] = (int) typedArray.getInteger(R.styleable.flagbar_fourthLineColor,DEFAULT_STRIPE_COLOR);

        //speeds
        int speeds [] = new int[MAX_STRIPES_COUNT];
        speeds[0] = typedArray.getInteger(R.styleable.flagbar_firstLineSpeed,DEFAULT_STRIPE_SPEED);
        speeds[1] = typedArray.getInteger(R.styleable.flagbar_secondLineSpeed,DEFAULT_STRIPE_SPEED);
        speeds[2] = typedArray.getInteger(R.styleable.flagbar_thirdLineSpeed,DEFAULT_STRIPE_SPEED);
        speeds[3] = typedArray.getInteger(R.styleable.flagbar_thirdLineSpeed,DEFAULT_STRIPE_SPEED);

        //directions
        Direction directions [] = new Direction[MAX_STRIPES_COUNT];
        directions[0] = Direction.values()[typedArray.getInteger(R.styleable.flagbar_firstLineDirection,DEFAULT_STRIPE_DIRECTION)];
        directions[1] = Direction.values()[typedArray.getInteger(R.styleable.flagbar_secondLineDirection,DEFAULT_STRIPE_DIRECTION)];
        directions[2] = Direction.values()[typedArray.getInteger(R.styleable.flagbar_thirdLineDirection,DEFAULT_STRIPE_DIRECTION)];
        directions[3] = Direction.values()[typedArray.getInteger(R.styleable.flagbar_fourthLineDirection,DEFAULT_STRIPE_DIRECTION)];

        // is indeterminate
        mIndeterminate = typedArray.getBoolean(R.styleable.flagbar_indeterminate, mIndeterminate);

        // stroke width
        mStrokeWidth = (int)typedArray.getDimension(R.styleable.flagbar_lineWidth,DEFAULT_STROKE_WIDTH);

        // progressbar start position
        mStart = typedArray.getInteger(R.styleable.flagbar_progressStart, DEFAULT_PROGRESS_START);

        // fps
        mFPS = typedArray.getInteger(R.styleable.flagbar_fps,DELAY_60_FPS);

        for (int i = 0; i < mStripesCount; i++) {
            Paint p = new Paint();
            p.setColor(colors[i]);
            p.setStyle(Style.STROKE);
            p.setAntiAlias(true);

            Stripe stripe = new Stripe();
            stripe.paint = p;
            stripe.dir = directions[i];
            stripe.speed = speeds[i];
            stripes.add(stripe);
        }

    }


	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mLayoutWidth = w;
		mLayoutHeigth =  w;
		
		mCenterX = mLayoutWidth/2;
		mCenterY = mLayoutHeigth/2;
        if(mLayoutHeigth / 2 < mStrokeWidth*mStripesCount){
            mStrokeWidth = mLayoutHeigth/(mStripesCount * 2);

        }
		for(int i = 0; i < mStripesCount; i++){
			RectF rf = new RectF(mCenterX - mLayoutWidth/2 + mStrokeWidth/2+(i*mStrokeWidth), mCenterY-mLayoutHeigth/2+mStrokeWidth/2+(i*mStrokeWidth), mCenterX+mLayoutWidth/2-mStrokeWidth/2-(i*mStrokeWidth), mCenterY+mLayoutHeigth/2-mStrokeWidth/2-(i*mStrokeWidth));
			stripes.get(i).bounds = rf;
            stripes.get(i).paint.setStrokeWidth(mStrokeWidth);
			stripes.get(i).startDeg = mStart;
            if(mIndeterminate)
			    stripes.get(i).endDeg = 60;
		}
		if(mIndeterminate)
			mIndeterminateHandler.sendEmptyMessage(0);
	}
	
	
	/**
	 * Set progress value
	 * 
	 * @param value 0...360
	 * */
	private void setProgress(int value){
		if(mIndeterminate == false){
			mProgress = value;
			for(Stripe stripe : stripes){
				stripe.endDeg = mProgress;
			}
			invalidate();
		}
	}
	
	public void setIndeterminate(boolean indeterminate){
		mIndeterminate = indeterminate;
        if(!indeterminate)
            mIndeterminateHandler.removeMessages(0);
        invalidate();
	}

    /**
     * Flag stripe
     * */
	private class Stripe{
		private RectF bounds;
		private int startDeg;
		private int endDeg;
		private Paint paint;
		private Direction dir;
		private int speed;
		
		private void draw(Canvas c){
			if(mIndeterminate){
				switch (dir) {
				case CLOCKWIZE:
						if(startDeg >= 360) startDeg = 0;
						startDeg+=mFPS == DELAY_30_FPS ? speed:speed/*/2*/;
					break;
				case COUNTERCLOCKWIZE:
					if(startDeg <= -360) startDeg = 0;
						startDeg-=mFPS == DELAY_30_FPS ? speed:speed/*/2*/;
						
					break;
				}
				
				c.drawArc(bounds, startDeg, endDeg, false, paint);
			}else{
				c.drawArc(bounds, startDeg, endDeg, false, paint);
			}
		} 
		
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		for(Stripe stripe : stripes)
			stripe.draw(canvas);
	}
	
	

	public int getProgress() {
		return mProgress;
	}

}
