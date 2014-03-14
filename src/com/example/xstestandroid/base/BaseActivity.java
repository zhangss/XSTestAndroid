package com.example.xstestandroid.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class BaseActivity extends Activity implements Controller {
	private ViewModel baseModel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//TODO ???:什么意思
		Intent intent = getIntent();
		/**
		 * Retrieves a map of extended data from the intent.
		 */
		if (intent.getExtras() != null){
			baseModel = (ViewModel)intent.getExtras().get(Route.VIEW_MODEL_KEY);
			this.setViewModel(baseModel);
		}
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		//当前显示的VC
		Route.shareInstance().setCurrentActivity(this);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (handler != null) {
			handler = null;
		}
		
		if (baseModel != null) {
			baseModel = null;
		}
		super.onDestroy();
	}
	
	@Override
	public void setViewModel(ViewModel viewModel) {
		// TODO Auto-generated method stub
		baseModel = viewModel;
	}

	@Override
	public ViewModel getViewModel() {
		// TODO Auto-generated method stub
		return baseModel;
	}
	
	/**
	 * 回调处理中心
	 */
	@Override
	public Handler getHandler() {
		// TODO Auto-generated method stub
		return handler;
	}
	
	@SuppressLint("HandlerLeak")
	//TODO ???:此处为何泄露?
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			Log.d("BaseActivity","CallBack:" + msg);
			switch (msg.what) {
			case 0:
				//返回成功
				if (baseModel != null){
					baseModel.setModel((ViewModel)msg.obj);
					refreshData(msg.obj.toString());
				}
				break;
			default:
				//返回失败
				requestFailed("", 1, "");
				break;
			}
		}
	};
	
	@Override
	public void refreshData(String token) {
		// TODO Auto-generated method stub
		Log.d("BaseActivity", "RefreshUI" + token);
	}

	@Override
	public void requestFailed(String token, int code, String errorMsg) {
		// TODO Auto-generated method stub
		Log.d("baseActivity", "RequestFailed:" + token + "Code:" + code + "ErrorMsg:" + errorMsg);
	}

}
