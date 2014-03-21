package com.example.android;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.xstestandroid.R;

public class ConfigurateionActivity extends Activity {
	EditText oriEditText;
	EditText navigationEditText;
	EditText touchEditText;
	EditText mncEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		oriEditText = new EditText(this);
		navigationEditText = new EditText(this);
		touchEditText = new EditText(this);
		mncEditText = new EditText(this);

		Button btn = new Button(this);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/**
				 * 获取设备配置信息
				 */
				Configuration cfg = getResources().getConfiguration();
				String screen = cfg.orientation == Configuration.ORIENTATION_LANDSCAPE ? "横屏"
						: "竖屏";
				String naviName = cfg.orientation == Configuration.NAVIGATION_NONAV ? "没有方向控制"
						: cfg.orientation == Configuration.NAVIGATION_WHEEL ? "滚轮控制方向"
								: cfg.orientation == Configuration.NAVIGATION_DPAD ? "方向键控制方向"
										: "轨迹球控制方向";
				String touchName = cfg.touchscreen == Configuration.TOUCHSCREEN_NOTOUCH ? "无触摸屏" : "支持触摸屏";
				oriEditText.setText(screen);
				navigationEditText.setText(naviName);
				touchEditText.setText(touchName);
				mncEditText.setText(cfg.mnc + "");
				
				/**
				 * 
				 */
			}
		});
		
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Configuration cfg = getResources().getConfiguration();
				//横屏设置竖屏 竖屏设置横屏
				if (cfg.orientation == Configuration.ORIENTATION_LANDSCAPE){
					ConfigurateionActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
				}else if (cfg.orientation == Configuration.ORIENTATION_PORTRAIT) {
					ConfigurateionActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
				}
			}
		});
	}
	
	//重写设备方向变更回调方法。
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		
		String screen = newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE ? "横向屏幕" : "竖向屏幕";
		Toast.makeText(this, "设备屏幕方向发生变化:修改后的方向为" + screen, Toast.LENGTH_LONG).show();
	}
}
