package com.example.xstestandroid.base;

/**
 * 1.在Manifest中的application标签中增加 android:name="com.example.xstestandroid.base.MainApplication"
 * 替换本类为程序的application类
 */

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

public class MainApplication extends Application {
	
	private static String BAIDU_LOCATION_AK = "7uS3PvTMyPe5CT1fCzaP0SzP";
	
	public static Context context;
	
	/**
	 * 百度定位功能
	 */
	private LocationClient locationClient = null;    //client必须在主线程中声明生成
	private BDLocationListener mLocationListener = null;
	public GeofenceClient geofenceClient = null;
	
	
	@Override
	public void onCreate(){
		super.onCreate();
		
		//获取应用的上下文
		context = getApplicationContext();
		
		//设置百度定位
		locationClient = new LocationClient(this);
		locationClient.setAccessKey(BAIDU_LOCATION_AK);
		
		//注册监听函数
		mLocationListener = new MyLocationListener();
		locationClient.registerLocationListener(mLocationListener);
		
		geofenceClient = new GeofenceClient(this);
	}
	
	/**
	 * 监听位置更新 两种实现方法
	 */
	public class MyLocationListener implements BDLocationListener{
		
		/**
		 * 实现1:接收异步返回的定位结果
		 */
		@Override
		public void onReceiveLocation(BDLocation location){
			if (location == null){
				return;
			}
			if (location.getLocType() == BDLocation.TypeGpsLocation){
				//GPS定位信息
			}else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
				//网络定位信息(WIFI和基站定位)
			}else if (location.getLocType() == BDLocation.TypeOffLineLocation){
				//离线定位请求返回
			}else if (location.getLocType() == BDLocation.TypeOffLineLocationFail){
				//离线定位请求发返回失败
			}
			
			String cityName = location.getCity();
			double latitude = location.getLatitude();
			double longitude = location.getLongitude();
			Log.v("MainApplication", "Location" + cityName + latitude + longitude);
			locationClient.stop();
			
			/**
		      StringBuffer sb = new StringBuffer(256);
		      sb.append("time : ");
		      sb.append(location.getTime());
		      sb.append("\nerror code : ");
		      sb.append(location.getLocType());
		      sb.append("\nlatitude : ");
		      sb.append(location.getLatitude());
		      sb.append("\nlontitude : ");
		      sb.append(location.getLongitude());
		      sb.append("\nradius : ");
		      sb.append(location.getRadius());
		      if (location.getLocType() == BDLocation.TypeGpsLocation){
		           sb.append("\nspeed : ");
		           sb.append(location.getSpeed());
		           sb.append("\nsatellite : ");
		           sb.append(location.getSatelliteNumber());
		           } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
		           sb.append("\naddr : ");
		           sb.append(location.getAddrStr());
		        } 
		        */
		}

		/**
		 * 实现2 接收异步返回的POI(Point Of Interest兴趣点/信息点)查询结果
		 * 每个POI包含四个方面:名称、类别、经度、纬度
		 */
		@Override
		public void onReceivePoi(BDLocation location) {
			// TODO Auto-generated method stub
			if (location == null){
				return;
			}
			
			StringBuffer sb = new StringBuffer(256);
			sb.append("Poi time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
			}
			if (location.hasPoi()) {
				sb.append("\nPoi:");
				sb.append(location.getPoi());
			} else {
				sb.append("noPoi information");
			}
			Log.v("MainApplication", "Location" + sb);
		}
	} 
	
	/**
	 * 设置定位参数
	 */
	public void setLocationOption(){
		LocationClientOption option = new LocationClientOption();
		/**
		 * 定位模式:高精度定位模式：这种定位模式下，会同时使用网络定位和GPS定位，优先返回最高精度的定位结果；
		 *         低功耗定位模式：这种定位模式下，不会使用GPS，只会使用网络定位（Wi-Fi和基站定位）；
		 *         仅用设备定位模式：这种定位模式下，不需要连接网络，只使用GPS进行定位，这种模式下不支持室内环境的定位。
		 */
		option.setLocationMode(LocationMode.Hight_Accuracy);
		
		option.setOpenGps(true);
		/**
		 * 定位SDK可以返回bd09、bd09ll、gcj02三种类型坐标，
		 * 若需要将定位点的位置通过百度Android地图SDK进行地图展示，请返回bd09ll，将无偏差的叠加在百度地图上。
		 */
		option.setCoorType("bd09ll");
		option.setServiceName("com.baidu.location.service.4.1");
		/**
		 *  设置是否要返回地址信息，默认为无地址信息。
		 *  String 值为all时，表示返回地址信息。其他值都表示不返回地址信息。
		 */
		option.setAddrType("all");
		/**
		 * 返回的定位结果包含地址信息
		 */
		option.setIsNeedAddress(true);
		/**
		 * 返回结果包含手机的机头方向
		 */
		option.setNeedDeviceDirect(true);
		/**
		 * 设置定位模式，小于1秒则一次定位;大于等于1秒则定时定位
		 * 设置300ms
		 */
		option.setScanSpan(300);
		/**
		 * 设置网络优先,不设置，默认gps优先
		 */
		option.setPriority(LocationClientOption.NetWorkFirst);
		option.disableCache(true);
		locationClient.setLocOption(option);		
	}
	
	/**
	 * 
	 */
	public void startLocation(){
		locationClient.start();
		if (locationClient != null && locationClient.isStarted()){
			//发起定位或者POI查询请求
			locationClient.requestLocation();
//			locationClient.requestPoi();
			//发起离线定位请求
//			locationClient.requestOfflineLocation();
		}
		else {
			 Log.d("MainApplication", "locationClient is null or not started");
		}
	}
}
