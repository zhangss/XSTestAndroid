package com.example.android;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.xstestandroid.R;

@SuppressLint("HandlerLeak")
public class UpdateAPKActivity extends Activity {

	public File apkFile;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0x123: {
				Log.v("Update", "reveiveMessage");
				installAPK(apkFile);
				break;
			}
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contacts_test_layout);

		registerSDCardListener();

		Button btn = (Button) findViewById(R.id.spAction);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 百度贴吧测试url
				final String urlString = "http://gdown.baidu.com/data/wisegame/6eb90709a4e436f2/baidutieba_80.apk";

				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						apkFile = downloadAPK(urlString);
						Log.v("Upload", "SendMessage");
						handler.sendEmptyMessage(0x123);
					}
				}).start();
			}
		});
	}

	// 解析XML内容
	@SuppressWarnings("unused")
	private File downloadAPK(String urlString) {
		/**
		 * Andorid文件路径问题
		 */
		String path = null;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED) && false) {
			path = Environment.getExternalStorageDirectory().getAbsolutePath();

		} else {
			path = createFileInAppData("update");
			Log.e("Update",
					"SDCard State:" + Environment.getExternalStorageState());
		}
		File file = new File(path, "update.apk");
//		File file = new File(path + "/update.apk");
//		createFileInSDCard("AndroidTest");
		if (file.exists()) {
			Log.e("Update", "Update is Exist!");
			return file;
		}
		Log.e("zhangss", "Download URL:" + urlString);
		URL url;
		try {
			url = new URL(urlString);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			InputStream is = conn.getInputStream();
			FileOutputStream fos = new FileOutputStream(file);
			BufferedInputStream bis = new BufferedInputStream(is);
			// 分配内存过多会直接崩溃 byte 分配1k空间
			byte[] buffer = new byte[1024];
			int len;
			int count = 0;
			while ((len = bis.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
				count++;
				Log.v("Update", "Progress" + count);
			}
			fos.close();
			bis.close();
			is.close();
			return file;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}
		return null;
	}

	/**
	 * 安装APK
	 * 
	 * @param file
	 */
	private void installAPK(File file) {
		Log.v("Update", "InstallAPK:" + file.toString());
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		startActivity(intent);
	}
	
	/**
	 * 在SD卡目录下创建文件夹
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unused")
	private String createFileInSDCard(String name) {
		String path = null;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + name;
			File file = new File(path);
			//创建文件夹
			file.mkdirs();
			Log.v("Update", "CreateFileInSDCardSuccess");
		}
		return path;
	}
	
	/**
	 * 在data/data目录下创建文件夹
	 * @param name
	 * @return
	 */
	private String createFileInAppData(String name) {
		String path = null;
		path = this.getFilesDir() + "/" + name;
		File file = new File(path);
		//创建文件夹
		Boolean isSuccess = file.mkdirs();
		Log.v("Update", "CreateFileInDataSuccess" + isSuccess.toString());
		return path;
	}

	/**
	 * 删除安装完成之后的APK
	 */
	@SuppressWarnings("unused")
	private void deleteAPK() {
		if (apkFile.exists()) {
			apkFile.delete();
		}
	}

	/**
	 * 监听应用的事件
	 * 
	 * @author saic
	 * 
	 */
	public class GETBroadcast extends BroadcastReceiver {

		/**
		 * 更新的时候会顺序调用:remove/add/replace
		 */
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			Log.v("Update", "ActionType:" + action);
			if (action.equals(Intent.ACTION_PACKAGE_ADDED)) {
				// 添加
			} else if (action.equals(Intent.ACTION_PACKAGE_REMOVED)) {
				// 删除
			} else if (action.equals(Intent.ACTION_PACKAGE_REPLACED)) {
				// 替换
			} else if (action.equals(Intent.ACTION_PACKAGE_CHANGED)) {
				// 改变
			} else if (action.equals(Intent.ACTION_PACKAGE_RESTARTED)) {
				// 重启
			} else if (action.equals(Intent.ACTION_PACKAGE_INSTALL)) {
				// 安装
			} else {

			}
		}
	}

	private final GETBroadcast apkInstallListener = new GETBroadcast();

	private void registerSDCardListener() {
		IntentFilter intentFilter = new IntentFilter(
				Intent.ACTION_MEDIA_MOUNTED);
		intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
		intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
		intentFilter.addAction(Intent.ACTION_PACKAGE_REPLACED);
		intentFilter.addDataScheme("package");
		registerReceiver(apkInstallListener, intentFilter);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		//注销监听
		unregisterReceiver(apkInstallListener);
		super.onDestroy();
	}
}
