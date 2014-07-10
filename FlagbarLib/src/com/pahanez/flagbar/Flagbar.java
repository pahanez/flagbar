package com.pahanez.flagbar;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class Flagbar extends View {
	
	private int mLayoutWidth,mLayoutHeigth,mCenterX,mCenterY;
	
	//->xml properties
	private int mStripesCount = 3;
	private int [] colors = new int[mStripesCount];
	private int mStrokeWidth = 30;
	private boolean mIndeterminate = false;
	//
	
	//tmp
	RectF rf;
	//
	
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
		colors[0] = Color.YELLOW;
		colors[1] = Color.RED;
		colors[2] = Color.BLACK;
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
			stripes.get(i).startDeg = 0;
			stripes.get(i).endDeg = 270;
			 
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

}
