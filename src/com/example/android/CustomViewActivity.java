package com.example.android;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.xstestandroid.R;

public class CustomViewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.mix_layout);
		/**
		 * 到mix_layout中参看 xml中初始化控件。
		 */
		LinearLayout lineLayout = (LinearLayout) findViewById(R.id.root);
		DrawView drawView = new DrawView(this);
		drawView.setMinimumHeight(500);
		drawView.setMinimumWidth(300);
		lineLayout.addView(drawView);
	}
}
