package com.example.xstestandroid.base;
/**
 * Starting with HONEYCOMB(蜂巢，蜂窝状的.), 
 * Activity implementations(实现) can make use of the Fragment(片段碎片) class to better modularize([mɒdjʊlərɑɪ'zeɪʃən]模块化组件化) their code, 
 * build more sophisticated([səˈfɪstɪˌkeɪtəd]老练的复杂的精密的高级的) user interfaces for larger screens, 
 * and help scale their application between small and large screens.
 */

import android.support.v4.app.FragmentActivity;

public class BaseFragmentActivity extends FragmentActivity {

	private ViewModel baseModel;
	public void setBaseModel(ViewModel model){
		this.baseModel = model;
	}
	public ViewModel getBaseModel(){
		return this.baseModel;
	}
}
