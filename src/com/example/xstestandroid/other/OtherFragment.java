package com.example.xstestandroid.other;

import com.example.xstestandroid.practice.PracticeLauncherActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * ListFragment
 * 
 * @author saic
 * 
 */
public class OtherFragment extends ListFragment {

	private ListFragmentCallBacks callBacks;

	public interface ListFragmentCallBacks {
		public void onItemSelected(Integer id, Class<?> classString);
	}

	private String[] items = new String[] { "PractivceList", "2", "3" };
	private Class<?>[] classes = new Class<?>[] {
			PracticeLauncherActivity.class, PracticeLauncherActivity.class,
			PracticeLauncherActivity.class };

	/**
	 * 当fragment被添加被显示时回调 该方法只能被调用一次
	 */
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		Log.v("FragmentLifeCycel", "OnAttach");
		if (!(activity instanceof ListFragmentCallBacks)) {
			throw new IllegalStateException(
					"Acitivity必须实现ListFragmentCallBacks接口!");
		}
		callBacks = (ListFragmentCallBacks) activity;
	}

	/**
	 * 创建Fragment时调用 只能被调用一次
	 */
	@SuppressLint("InlinedApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.v("FragmentLifeCycel", "onCreate");

		// 设置数据源
		// Field requires API level 11 (current min is 8):
		// android.R.layout#simple_list_item_activated_1
		setListAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_activated_1,
				android.R.id.text1, items));
	}

	/**
	 * 绘制Fragment需要显示的内容 会被调用多次
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.v("FragmentLifeCycel", "onCreateView");
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Log.v("FragmentLifeCycel", "onActivityCreated");
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.v("FragmentLifeCycel", "onStart");
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.v("FragmentLifeCycel", "onResume");
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.v("FragmentLifeCycel", "onPause");
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.v("FragmentLifeCycel", "onStop");
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		Log.v("FragmentLifeCycel", "onDestroyView");
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.v("FragmentLifeCycel", "onDestroy");
	}

	/**
	 * 当fragment从所属的activity中删除时回调 该方法只会被调用一次
	 */
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		Log.v("FragmentLifeCycel", "onDetach");
		callBacks = null;
	}

	/**
	 * 当用户点击某表项时触发回调
	 */
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);

		callBacks.onItemSelected(position, classes[position]);
	}

	// 设置选中的显示模式
	public void setActivateOnItemClick(boolean activateOnItemClick) {
		getListView().setChoiceMode(
				activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
						: ListView.CHOICE_MODE_NONE);
	}
}
