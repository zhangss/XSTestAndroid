package com.example.xstestandroid.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

/**
 * 此类中不使用单例类，使用静态函数
 * 1.静态函数的全局变量必须也是静态的? 例如sp //TODO
 * @author zhangss
 *
 */

public class PreferenceUtils {

	//用户配置项
	private static SharedPreferences sp;
	private static String CONFIGURE_PREFERENCES = "configure_preferences";
	private void configurationEnvironment(Context context){
		sp = context.getSharedPreferences(CONFIGURE_PREFERENCES, Context.MODE_PRIVATE);
	}
	
	/**
	 * 保存对象到首选项中
	 * @param object 对象
	 * @param key    对应的key
	 * @return {@link Boolean}
	 */
	public static <T> Boolean setObjetToPreferences(T object, String key){
		if(object == null || key == null){
			return false;
		}
		
		SharedPreferences.Editor editor = sp.edit();
		//
		List<Object> list = new ArrayList<Object>();
		list.add(object);
		
		//对象转换为二进制
		ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
		ObjectOutputStream objOutStream;
		try {
			objOutStream = new ObjectOutputStream(byteOutStream);
			objOutStream.writeObject(list);
			
			//Base64字符集编码 保存
			String objString = new String(Base64.encode(byteOutStream.toByteArray(), Base64.DEFAULT));
			editor.putString(key, objString);
			editor.commit();
			
			objOutStream.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			try {
				byteOutStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}
	
	/**
	 * 根据Key取出存在首选项中的对象
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getObjectFromPreference(String key){
		List<Object> list;
		//获取内容 Base64解密
		String objString = sp.getString(key, "");
		byte[] buffer = Base64.decode(objString.getBytes(), Base64.DEFAULT);
		
		//二进制转换为对象
		ByteArrayInputStream byteInputStream = new ByteArrayInputStream(buffer);
		ObjectInputStream objInputStream;
		try {
			objInputStream = new ObjectInputStream(byteInputStream);
			
			list = (List<Object>) objInputStream.readObject();
			objInputStream.close();
			//TODO 此处有问题
			return (T) list.get(0);
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				byteInputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return null;
	}
}
