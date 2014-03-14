package com.example.xstestandroid.base;

import android.util.Log;

public class Route {
	
	/**
	 * 静态变量
	 */
	public final static String VIEW_MODEL_KEY = "viewModel";
	
	/**
	 * 变量
	 */
	
	//单例类的生成
	private static Route instance;
	public static Route shareInstance(){
		synchronized (Route.class) {
			if (instance == null){
				instance = new Route();
			}
			return instance;
		}
	}

	/**
	 * Acitivity
	 */
	private Controller curActivity;
	public void setCurrentActivity(Controller curActivity){
		Log.v("Route", "SetActivity" + curActivity.toString());
		this.curActivity = curActivity;
	}
	
	public Controller getCurrentActivity(){
		Log.v("Route", "GetActivity" + this.curActivity);
		return this.curActivity;
	}
	
	/**
	 * 通过Class获取绑定的ViewModel
	 */
	public ViewModel getViewModelWithClass(String activityClass){
		ViewModel resultViewModel = null;
		String modelClass = ViewModelPlist.plist.get(activityClass);
		try {
			resultViewModel = (ViewModel) Class.forName(modelClass).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("Route", "Exception" + e.toString());
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("Route", "Exception" + e.toString());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("Route", "Exception" + e.toString());
		}	
		resultViewModel.setBindActivityClass(activityClass);
		return resultViewModel;
	}
	
}
