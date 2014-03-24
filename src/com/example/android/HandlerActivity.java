package com.example.android;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.xstestandroid.R;

/**
 * 子线程周期性发送通知主线程更新UI
 * Handler:
 * 1.Handler发送的消息存储在MessageQueue中,如果想要使用Handler处理则必须保证存在MessageQueue
 * 2.MessageQueue在Looper创建之初创建
 * 3.每个线程都只能用于一个looper,负责处理处理MessageQueue中的信息
 * 4.主线程的Looper有系统负责创建
 * 5.子线程的Looper需要自己手动创建,然后调用prepare()方法与线程绑定,该方法确保每个线程只有一个Looper.
 * 6.Looper通过loop()方法启动，该方法使用一个死循环不断等待queue中的信息。
 * @author saic
 *
 */
@SuppressLint("HandlerLeak")
public class HandlerActivity extends Activity {
	int[] images = new int[]{
			R.drawable.tab_bargain_icon_n,
			R.drawable.tab_home_icon_n,
			R.drawable.tab_kan_bg4,
			R.drawable.tab_more_icon_n
	};
	int curImageId = 0;
	
	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final ImageView showImageView = new ImageView(this);
		final Handler myhHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 0x1223) {
					//更新UI
					showImageView.setImageResource(images[curImageId++%images.length]);
				}
			}
		};
		
		//定时器 周期性的给主线程发送UI更新通知
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//发送空消息
				myhHandler.sendEmptyMessage(0x1123);
			}
		}, 0, 1200);
		
		//启动新线程
		calThred = new CalThred();
		calThred.start();		
	}

	private CalThred calThred;
	class CalThred extends Thread{
		public Handler handler;
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			Looper.prepare();
			handler = new Handler(){
				@Override
				public void handleMessage(Message msg) {
					if (msg.what == 0x123){
						//处理内容
						Toast.makeText(HandlerActivity.this, msg.obj.toString(), Toast.LENGTH_LONG).show();
					}
				}
			};
			Looper.loop();
		}
	}
	
	//UI触发 向线程内发送信息
	public void cal(View view){
		Message msg = new Message();
		msg.what = 0x123;
		msg.obj = "Test";
		calThred.handler.sendMessage(msg);
	}

}
