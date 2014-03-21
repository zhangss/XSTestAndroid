package com.example.android;

import com.example.xstestandroid.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class PlaneView extends View {
	public float xPoint;
	public float yPoint;
	Bitmap plane;

	public PlaneView(Context context) {
		super(context);
		plane = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.plane);
		setFocusable(true);
	}
	
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		Paint paint = new Paint();
		canvas.drawBitmap(plane, xPoint, yPoint, paint);
	}
}
