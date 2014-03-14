package com.example.xstestandroid.base;

import java.io.Serializable;
import java.util.List;

import com.example.xstestandroid.home.City;

public class ViewModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Model与Activity绑定
	private String bindActivityClass;
	public void setBindActivityClass(String classString){
		bindActivityClass = classString;
	}
	
	public String getBindActivityClass(){
		return bindActivityClass;
	}
	
	/**
	 * Model转换
	 */
	private ViewModel model;
	public ViewModel getModel(){
		return model;
	}
	
	public void setModel(ViewModel viewModel){
		this.model = viewModel;
	}
	
	/**
	 * 接口参数列表
	 */
	public List<City> cityList;   //城市列表
}
