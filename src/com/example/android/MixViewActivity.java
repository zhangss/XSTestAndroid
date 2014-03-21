package com.example.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.xstestandroid.R;

public class MixViewActivity extends Activity {
	int[] images = new int[]{
			R.drawable.ic_launcher,
			R.drawable.ic_launcher,
	};
	int currentImage = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.mix_layout);
		
		
		LinearLayout lineLayout = (LinearLayout) findViewById(R.id.root);
		
		final ImageView imageV = new ImageView(this);
		imageV.setImageResource(images[0]);
		imageV.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				imageV.setImageResource(images[++currentImage % images.length]);
			}
		});
		lineLayout.addView(imageV);
	}

}
