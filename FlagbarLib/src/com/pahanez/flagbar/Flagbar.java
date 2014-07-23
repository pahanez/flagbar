package com.pahanez.flagbar;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

public class Flagbar extends View {
	
	private static int DELAY_35_FPS = 1000/45;
	private static int DELAY_60_FPS = 1000/60;
	
	private int mLayoutWidth,mLayoutHeigth,mCenterX,mCenterY;
	
	//->xml properties
	private int mStripesCount = 3;
	private int [] colors = new int[mStripesCount];
	private int []	speeds = new int[mStripesCount];
	private Direction [] directions = new Direction[mStripesCount]; 
	private int mStrokeWidth = 30;
	private boolean mIndeterminate = true;
	private int start = -90;
	//
	
	//tmp
	RectF rf;
	//
	
	private int mProgress;
	
	public enum Direction {
		CLOCKWIZE,COUNTERCLOCKWIZE;
	}
	
    private Handler mIndeterminateHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
        	removeMessages(0);
        	invalidate();
        	if(mIndeterminate)
        		sendEmptyMessageDelayed(0, DELAY_60_FPS);
            //Deutschland champion 2014!!!!
    		}
    };
	
	private List<Stripe> stripes = new ArrayList<Stripe>();

	public Flagbar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public Flagbar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public Flagbar(Context context) {
		super(context);
		init();
	}
	
	private void init() {
		colors[0] = Color.BLACK;
		colors[1] = Color.RED;
		colors[2] = 0XFFFFCC00; 
		
		directions[0] = Direction.CLOCKWIZE;
		directions[1] = Direction.COUNTERCLOCKWIZE;
		directions[2] = Direction.CLOCKWIZE;
		
		speeds[0] = 2;
		speeds[1] = 5;
		speeds[2] = 7;
		

		for(int i = 0; i < mStripesCount; i++){
			Paint  p = new Paint();
			p.setColor(colors[i]);
			p.setStrokeWidth(mStrokeWidth);
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
		mLayoutHeigth =  h;
		
		android.util.Log.e("p37td8", "mLayoutWidth : " + mLayoutWidth + " , mLayoutWidth2 : "  + mLayoutWidth);
		mCenterX = mLayoutWidth/2;
		mCenterY = mLayoutHeigth/2;
		for(int i = 0; i < mStripesCount; i++){
			rf = new RectF(mCenterX - mLayoutWidth/2 + mStrokeWidth/2+(i*mStrokeWidth), mCenterY-mLayoutHeigth/2+mStrokeWidth/2+(i*mStrokeWidth), mCenterX+mLayoutWidth/2-mStrokeWidth/2-(i*mStrokeWidth), mCenterY+mLayoutHeigth/2-mStrokeWidth/2-(i*mStrokeWidth));
			stripes.get(i).bounds = rf;
			
			//tmp
			stripes.get(i).startDeg = -90;
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
		
	}
	
	private class Stripe{
		RectF bounds;
		int startDeg;
		int endDeg; 
		Paint paint;
		Direction dir;
		int speed = 5;
		
		private void draw(Canvas c){
			if(mIndeterminate){
				//TODO
				switch (dir) {
				case CLOCKWIZE:
						if(startDeg >= 360) startDeg = 0;
						startDeg+=speed;
					break;
				case COUNTERCLOCKWIZE:
					if(startDeg <= -360) startDeg = 0;
						startDeg-=speed;
						
					break;
				}
				
				c.drawArc(bounds, startDeg, endDeg, false, paint);
//				postInvalidateDelayed(DELAY_60_FPS);
				
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
