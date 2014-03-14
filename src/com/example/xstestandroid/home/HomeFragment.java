package com.example.xstestandroid.home;

/**
 * Fragment支持在不同的Activity中使用并且可以处理自己的输入事件以及生命周期方法等。可以看做是一个子Activity。
 * Fragment的生命周期方法依赖于Activity的生命周期，
 * 例如一个Activity的onPause()的生命周期方法被调用的时候这个Activity中的所有的Fragment的onPause()方法也将被调用。

 */

import android.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class HomeFragment extends Fragment {
	
	/**
	 * 重写Fragment的布局
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return super.onCreateView(inflater, container, savedInstanceState);
	}

}
