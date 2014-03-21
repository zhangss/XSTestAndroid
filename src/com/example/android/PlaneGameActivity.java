package com.example.android;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.view.WindowManager;

import com.example.xstestandroid.R;

/**
 * 键盘控制飞机移动的程序
 * 增加监听器事件的5种方式
 * @author saic
 *
 */
public class PlaneGameActivity extends Activity {
	// 定义飞机的移动速度
	private int speed = 10;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		//设置去掉标题 和 全屏幕显示
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		//创建飞机控件，加入到view中
		final PlaneView planeView = new PlaneView(this);
		setContentView(planeView);
		planeView.setBackgroundResource(R.drawable.back);

		//获取设备的窗口管理器，设置显示大小
		WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);

		//飞机的初始位置
		planeView.xPoint = metrics.widthPixels / 2;
		planeView.yPoint = metrics.heightPixels / 2 - 40;

		/**
		 * 1.内部类创建事件监听器，可以在类内部复用该监听类.
		 * 2.外部类创建事件监听类
		 * 3.Activity直接实现监听器接口
		 * 4.匿名内部类作为监听器类.
		 * 大部分情况下，事件监听类都没有复用价值，可复用的代码应该被抽离抽象成业务逻辑方法
		 * 估监听器基本上只会被使用一次，故推荐使用匿名内部类的形式创建事件监听器
		 * 5.XML标签增加onClick="click"属性，然后Activity中实现方法(public void click(View view){})即可
		 */
		//注册键盘监听事件
		planeView.setOnKeyListener(new OnKeyListener() {
			/**
			 * 根据按键控制飞机移动
			 */
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				switch (event.getKeyCode()) {
				case KeyEvent.KEYCODE_S:
					planeView.yPoint += speed;
					break;
				case KeyEvent.KEYCODE_W:
					planeView.yPoint -= speed;
					break;
				case KeyEvent.KEYCODE_A:
					planeView.xPoint -= speed;
					break;
				case KeyEvent.KEYCODE_D:
					planeView.xPoint += speed;
					break;
				default:
					break;
				}
				//重绘组件
				planeView.invalidate();
				return true;
			}
		});

	}

}
