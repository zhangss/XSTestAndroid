package com.example.android;

import android.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CodeViewActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		LinearLayout lineLayout = new LinearLayout(this);
		lineLayout.setOrientation(LinearLayout.VERTICAL);
		super.setContentView(lineLayout);
		
		final TextView textView = new TextView(this);
		lineLayout.addView(textView);
		
		Button btn = new Button(this);
		btn.setText(R.string.ok);
		btn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				textView.setText("Hello Android!" + "\n" + new java.util.Date());
			}
		});
		lineLayout.addView(btn);	
	}
}
