package com.example.xstestandroid.home;

import com.example.xstestandroid.base.BaseListModel;

public class City extends BaseListModel {
	
	public long id;    //城市id
	public String name;    //城市名称
	@Deprecated
	public String stat;    //状态(0:不支持砍价1:支持砍价)

	public String toString() {
		return "City - "+ 
			"; id:" + id + 
			"; name:" + name + 
			"; stat:" + stat ;
	}

	public City(){
		id = 0;
		name = "";
		stat = "";
	}

	public static String testJson(){
		return "{\"id\":0,\"name\":\"\",\"stat\":\"\"}";
	}

}
