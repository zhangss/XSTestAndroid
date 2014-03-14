package com.example.xstestandroid.practice;

import com.example.xstestandroid.R;

import android.app.ExpandableListActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ExpandableListActivityTest extends ExpandableListActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		//生成内容
		ExpandableListAdapter adapter = new BaseExpandableListAdapter() {
			//数据结构
			//图片
			int[] logos = new int[]{
//					R.drawable.picture_frame,
//					R.drawable.zoom_plate,
//					R.drawable.title_bar
					};
			//组数据
			private String[] armType = new String[]{
					"神族",
					"魔族",
					"人族"
					};
			//子数据
			private String[][] arms = new String[][]{
					{"羽灵","精灵"},
					{"食人魔","伏地魔"},
					{"步兵","骑兵"}
					};
			
			@Override
			public boolean isChildSelectable(int groupPosition, int childPosition) {
				// TODO Auto-generated method stub
				return true;
			}
			
			@Override
			public boolean hasStableIds() {
				// TODO Auto-generated method stub
				return true;
			}
											
			/**
			 * 获取指定位置的数据项
			 */
			@Override
			public Object getGroup(int groupPosition) {
				// TODO Auto-generated method stub
				return armType[groupPosition];
			}
			
			@Override
			public int getGroupCount() {
				// TODO Auto-generated method stub
				return armType.length;
			}
			
			@Override
			public long getGroupId(int groupPosition) {
				// TODO Auto-generated method stub
				return groupPosition;
			}
			
			@Override
			public View getGroupView(int groupPosition, boolean isExpanded,
					View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				LinearLayout lineLayout = new LinearLayout(ExpandableListActivityTest.this);
				lineLayout.setOrientation(0);
				ImageView logo = new ImageView(ExpandableListActivityTest.this);
				logo.setImageResource(logos[groupPosition]);
				lineLayout.addView(logo);
				TextView textView = getTextView();
				textView.setText(getGroup(groupPosition).toString());
				lineLayout.addView(textView);
				return lineLayout;
			}
									
			/**
			 * 获取指定位置 指定子列表项处的子列表数据项
			 * 1.获取内容
			 * 2.获取位置
			 * 3.获取个数
			 * 4.生成视图
			 */
			@Override
			public Object getChild(int groupPosition, int childPosition) {
				// TODO Auto-generated method stub
				return arms[groupPosition][childPosition];
			}
			
			@Override
			public long getChildId(int groupPosition, int childPosition) {
				// TODO Auto-generated method stub
				return childPosition;
			}
			
			@Override
			public int getChildrenCount(int groupPosition) {
				// TODO Auto-generated method stub
				return arms[groupPosition].length;
			}
			
			@Override
			public View getChildView(int groupPosition, int childPosition,
					boolean isLastChild, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				TextView textview = getTextView();
				textview.setText(getChild(groupPosition, childPosition).toString());
				return textview;
			}

			//初始化TextView
			private TextView getTextView(){
				AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 64);
				TextView textView = new TextView(ExpandableListActivityTest.this);
				textView.setLayoutParams(lp);
				textView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.LEFT);
				textView.setPadding(36, 0, 0, 0);
				textView.setTextSize(20);
				return textView;
			}
		};
		
		//设置显示内容
		setListAdapter(adapter);
	}
}
