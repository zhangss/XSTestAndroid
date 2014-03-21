package com.example.android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class DrawView extends View {
	
	/**
	 * 必须定义构造器方法
	 */
	public DrawView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	private float curX = 40;
	private float curY = 50;
	Paint paint = new Paint();
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		//设置颜色
		paint.setColor(Color.RED);
		//绘制一个红色球点
		canvas.drawCircle(curX, curY, 15, paint);
	}
	
	/**
	 * 此时使用回调机制处理事件，有助于程序功能的内聚
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		//重置坐标
		curX = event.getX();
		curY = event.getY();
		//重绘对象
		invalidate();
		
		//返回true表示已经处理完 事件不会被继续分发
		return true;
	}
	

}
