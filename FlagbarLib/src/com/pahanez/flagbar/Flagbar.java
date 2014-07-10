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
	
	private static int DELAY_30_FPS = 1000/30;
	
	private int mLayoutWidth,mLayoutHeigth,mCenterX,mCenterY;
	
	//->xml properties
	private int mStripesCount = 3;
	private int [] colors = new int[mStripesCount];
	private int mStrokeWidth = 30;
	private boolean mIndeterminate = false;
	private int start = -90;
	//
	
	//tmp
	RectF rf;
	//
	
	private int mProgress;
	
    private Handler mIndeterminateHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
        	mProgress += 4;
        	if(mProgress >= 360)
        		mProgress = 0;
        	setProgress(mProgress);
        	sendEmptyMessageDelayed(0, DELAY_30_FPS);
            
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
		for(int i = 0; i < mStripesCount; i++){
			Paint  p = new Paint();
			p.setColor(colors[i]);
			p.setStrokeWidth(mStrokeWidth);
			p.setStyle(Style.STROKE);
			p.setAntiAlias(true);
			
			Stripe stripe = new Stripe();
			stripe.paint = p;
			stripes.add(stripe);
		}
	}

	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mLayoutWidth = w;
		mLayoutHeigth =  h;
		
		android.util.Log.e("p37td8", "mLayoutWidth : " + mLayoutWidth + " , mLayoutWidth : "  + mLayoutWidth);
		mCenterX = mLayoutWidth/2;
		mCenterY = mLayoutHeigth/2;
		for(int i = 0; i < mStripesCount; i++){
			rf = new RectF(mCenterX - mLayoutWidth/2 + mStrokeWidth/2+(i*mStrokeWidth), mCenterY-mLayoutHeigth/2+mStrokeWidth/2+(i*mStrokeWidth), mCenterX+mLayoutWidth/2-mStrokeWidth/2-(i*mStrokeWidth), mCenterY+mLayoutHeigth/2-mStrokeWidth/2-(i*mStrokeWidth));
			stripes.get(i).bounds = rf;
			
			//tmp
			stripes.get(i).startDeg = -90;
			stripes.get(i).endDeg = 180;
			 
		}
		
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
	
	private class Stripe{
		RectF bounds;
		int startDeg;
		int endDeg; 
		Paint paint;
		
		private void draw(Canvas c){
			if(mIndeterminate){
				//TODO
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
