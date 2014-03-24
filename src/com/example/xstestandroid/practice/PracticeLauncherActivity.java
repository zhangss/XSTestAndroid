package com.example.xstestandroid.practice;

import android.annotation.SuppressLint;
import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

/**
 * 练习UI
 * 
 * @author zhangss
 * 
 */
public class PracticeLauncherActivity extends LauncherActivity {
	// 初始化数据源
	String[] list = { "ExpandableListActivityTest", "PreferenceActivityTest",
			"ListActivityTest", "FragmentActivityTest" };
	Class<?>[] classList = { ExpandableListActivityTest.class,
			ExpandableListActivityTest.class, ExpandableListActivityTest.class,
			ExpandableListActivityTest.class };

	@SuppressLint("InlinedApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 生成Adapter添加内容 设置为显示需要的adapter
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);
		setListAdapter(adapter);
	}

	/**
	 * 根据用户点击的列表项 对应生成相应的Activity
	 */
	@Override
	public Intent intentForPosition(int position) {
		// 传递数据回MainActivity
		Intent intent = getIntent();
		Bundle bundle = new Bundle();
		bundle.putString("key", list[position]);
		intent.putExtras(bundle);
		this.setResult(0, intent);
		Log.v("Selected", list[position]);

		return new Intent(PracticeLauncherActivity.this, classList[position]);
	}

}
