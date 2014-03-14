package com.example.xstestandroid.utils;

import java.util.Comparator;

import com.example.xstestandroid.base.BaseListModel;

/**
 * 
 * @author xiaanming
 * 
 */
public class PinyinComparator implements Comparator<BaseListModel> {

	public int compare(BaseListModel o1, BaseListModel o2) {
		if (o1.sortLetters.equals("*") || o2.sortLetters.equals("#")) {
			//return -1;
			return o1.sortLetters.compareTo(o2.sortLetters);
		} else if (o1.sortLetters.equals("#") || o2.sortLetters.equals("*")) {
			//return 1;
		    return o1.sortLetters.compareTo(o2.sortLetters);
		} else {
			return o1.sortLetters.compareTo(o2.sortLetters);
		}
	}
}