package com.example.android.javatest;

import java.util.ArrayList;

public class ListTest 
{

	public static void isContainObject() 
	{
		ArrayList<String> stringList = new ArrayList<String>();
		String abc = "abc";
		String abc2 = "abc";
		stringList.add(abc);
		if (abc.equals(abc2))
		{
			System.out.println("Equal!");
		}
		
		if (stringList.contains(abc2))
		{
			stringList.add(abc2);
		}
	}
	
}
