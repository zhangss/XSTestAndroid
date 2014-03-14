package com.example.xstestandroid.base;

/**
 * 所有的Activity都需要实现此接口，用于框架统一控制
 */
import android.os.Handler;

public interface Controller {
	/**
	 * ViewModel的存取方法
	 */
	public void setViewModel(ViewModel viewModel);
	public ViewModel getViewModel();
	
	/**
	 * 请求成功 刷新UI 
	 * @param token 接口对应的token,用于校验
	 */
	public void refreshData(String token);

	/**
	 * 请求失败 统一处理
	 * @param token 接口对应的token,用于校验
	 * @param code  返回码
	 * @param errorMsg 返回的错误信息
	 */
	public void requestFailed(String token, int code, String errorMsg);
	
	/**
	 * 获取Handler
	 */
	public Handler getHandler();
}
