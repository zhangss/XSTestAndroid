package com.example.xstestandroid.practice;

import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

/**
 * 练习UI
 * @author zhangss
 *
 */
public class PracticeActivity extends LauncherActivity {
	//初始化数据源
	String[] list = {"ExpandableListActivityTest","PreferenceActivityTest",
			"ListActivityTest","FragmentActivityTest"};
	Class<?>[] classList = {ExpandableListActivityTest.class,PreferenceActivityTest.class,
			ListActivityTest.class,FragmentActivityTest.class};
	
	@Override 
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		//生成Adapter添加内容 设置为显示需要的adapter
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.list_content, list);
		setListAdapter(adapter);
	}
	
	/**
	 * 根据用户点击的列表项 对应生成相应的Activity
	 */
	@Override 
	public Intent intentForPosition(int position){
		return new Intent(PracticeActivity.this, classList[position]);
	} 
}
