package com.example.android;

public class Book {
	String name; // 书名
	float price; // 价格
	String date; // 日期

	public Book() {
		name = "";
		price = 0;
		date = "";
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "name:" + name + ";price:" + price + ";date:" + date;
	}
}
