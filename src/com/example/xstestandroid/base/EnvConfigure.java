package com.example.xstestandroid.base;

import com.example.xstestandroid.home.City;

public class EnvConfigure {
	
	/**
	 * 存储已经定位的城市
	 */
	private City currentCity;
	private String KEY_CURRENT_CITY = "current_city";
	public void setCurrentCity(City city){
		currentCity = city;
	}
	
	public City getCurrentCity(){
		return currentCity;
	}

}
