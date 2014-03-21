package com.example.android;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;

import com.example.xstestandroid.R;

/**
 * 测试事件的响应顺序及是否阻断事件传递
 * 
 * @author saic
 * 
 */
public class MyButtonTestActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		MyButton btn = new MyButton(this);
		btn.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					Log.v("--CallBack--", "1:Activity OnKey Listener");
				}
				// 扩散事件
				return false;
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		super.onKeyDown(keyCode, event);
		Log.v("--CallBack--", "3:Activity OnKeyDown");
		return false;
	}
}
