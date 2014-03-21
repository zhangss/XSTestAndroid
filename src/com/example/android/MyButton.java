package com.example.android;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;

/**
 * 自定义Btton 回调机制的事件传递 如果不阻止事件传播，那么事件穿过的顺序是listener--button--Activity
 * 监听机制优先于回调机制触发
 * 
 * @author saic
 * 
 */
public class MyButton extends Button {

	public MyButton(Context context) {
		super(context);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		super.onKeyDown(keyCode, event);

		/**
		 * True表示 事件到此截止,事件不会继续传递 False表示 事件继续向下传递
		 */
		Log.v("--CallBack--", "2:MyButton OnKeyDown");
		return false;
	}
}
