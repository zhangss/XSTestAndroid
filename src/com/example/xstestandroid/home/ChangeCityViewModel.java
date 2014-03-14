package com.example.xstestandroid.home;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.xstestandroid.base.ViewModel;
import com.example.xstestandroid.utils.CharacterParser;
import com.example.xstestandroid.utils.PinyinComparator;

@SuppressWarnings("serial")
public class ChangeCityViewModel extends ViewModel{

	private List<City> dataList; //数据列表
	private CharacterParser parser;  //取汉字首字母
	private PinyinComparator comparator; //比对拼音顺序
	
	@Override
	public void setModel(ViewModel viewModel) {
		// TODO Auto-generated method stub
		super.setModel(viewModel);
		if(cityList != null){
			//初始化
			parser = CharacterParser.getInstance();
			comparator = new PinyinComparator();
			//数据源处理
			dataList = fillData(cityList);
			//数据排序
			Collections.sort(dataList, comparator);
		}
	}
	
	/**
	 * 根据城市名称 填充排序所需内容
	 * @param data 城市列表
	 * @return
	 */
	private List<City> fillData(List<City> data){
		List<City> resultData = new ArrayList<City>();
		
		City currentCity = new City();
		currentCity.id = -1;
		currentCity.name = "正在定位";
		
		currentCity.layoutLetter = "*";
		currentCity.sortLetters = "*";
		resultData.add(currentCity);
		
		for (City temp : cityList){
			//City名字拼音转汉字
			String nameInPinyin = parser.getSelling(temp.name);
			String sortStirng = nameInPinyin.substring(0, 1).toUpperCase();
			
			//正则表达式判断 填写排序及显示的内容
			if (sortStirng.matches("[A-Z]")){
				temp.sortLetters = sortStirng.toUpperCase();
				temp.layoutLetter = sortStirng.toUpperCase();
			}
			else{
				temp.sortLetters = "#";
				temp.layoutLetter = "#";
			}
		}
		return resultData;
	}
}
