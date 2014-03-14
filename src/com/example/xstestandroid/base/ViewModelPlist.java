package com.example.xstestandroid.base;

import java.util.HashMap;

public class ViewModelPlist {
	
	public static HashMap<String, String> plist = new HashMap<String, String>();
	
	static {
		//主视图 Home
		plist.put("com.example.xstestandroid.MainActivity", "com.example.xstestandroid.main.MainViewModel");
	}

}
