package com.example.android;
/**
 * 霓虹灯程序
 */
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.example.xstestandroid.R;

public class FragmentLayoutActivity extends Activity {
	private int currentColor = 0;
	final int[] colors = new int[]{
			
	};
	
	final int[] names = new int[]{
			
	};
	
	TextView[] views = new TextView[names.length];
	
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0x123) {
				for (int i = 0; i < names.length; i++) {
					views[i].setBackgroundResource(colors[(i+currentColor) % names.length]);
				}
				currentColor ++;
			}
			super.handleMessage(msg);
		};
	};
	
	@Override
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_fragmentlayout);
		for (int i = 0; i < names.length; i++) {
			views[i] = (TextView) findViewById(names[i]);
		}
		
		//定义一个线程 周期性改变currentColor的值 200ms执行一次
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//发送一个空消息
				handler.sendEmptyMessage(0x123);
			}
		}, 0, 200);
	};
	
}
