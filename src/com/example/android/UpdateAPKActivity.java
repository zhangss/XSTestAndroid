package com.example.android;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
				if (apkFile != null){
					installAPK(apkFile);
				}else {
					Log.e("Update", "下载失败,File 不存在!");
				}
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
				
//				final String urlString = "http://gdown.baidu.com/data/wisegame/6eb90709a4e436f2/baidutieba_80.apk";
				final String urlString = "http://10.32.17.125:8080/BarTackApp.apk";
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
	private File downloadAPK(String urlString) {
		/**
		 * Andorid文件路径问题 优先创建SD卡目录
		 */
		String path = createFileInSDCard("update");
		if (path == null) {
			path = createFileInAppData("update");
		}
		File file = new File(path, "update.apk");
		if (file.exists()) {
			Log.e("Update", "Update is Exist!");
			file.delete();
			// return file;
		}
		Log.e("zhangss", "Download URL:" + urlString);
		URL url;
		try {
			url = new URL(urlString);
			long timeStart = System.currentTimeMillis();
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			String fileName = getFileName(conn);
			InputStream is = conn.getInputStream();
			FileOutputStream fos = new FileOutputStream(file);
			BufferedInputStream bis = new BufferedInputStream(is);
			int total = conn.getContentLength();
			int progress = 0;
			// 分配内存过多会直接崩溃 byte 分配1k空间为1024
			// 减少分配的空间 可以降低下载文件内容不完全的问题
			byte[] buffer = new byte[64];
			int len;
			int count = 0;
			while ((len = bis.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
				count++;
				progress += len;
				Log.v("Update", "Progress" + count);
			}
			if (progress != total) {
				Log.v("Update", "DownLoad Incomplete");
			}
			Log.v("Update", progress + "/" + total);
			Log.v("Update", "DownLoad Time:"
					+ (System.currentTimeMillis() - timeStart) + "");
			bis.close();
			fos.close();
			is.close();
			conn.disconnect();
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
	 * 获取下载的文件名称
	 * @param conn
	 * @return
	 */
	@SuppressWarnings("unused")
	private String getFileName(HttpURLConnection conn) {
		String filename = "";
		boolean isok = false;
		// 从UrlConnection中获取文件名称
		Map<String, List<String>> hf = conn.getHeaderFields();
		if (hf == null) {
			return null;
		}
		Set<String> key = hf.keySet();
		if (key == null) {
			return null;
		}

		for (String skey : key) {
			List<String> values = hf.get(skey);
			for (String value : values) {
				String result;
				try {
					result = new String(value.getBytes("ISO-8859-1"), "GBK");
					int location = result.indexOf("filename");
					if (location >= 0) {
						result = result.substring(location + "filename".length());
						filename = result.substring(result.indexOf("=") + 1);
						isok = true;
					}
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (isok) {
				break;
			}
		}
		// 从路径中获取
		if (filename == null || "".equals(filename)) {
			String url = conn.getURL().toString();
			filename = url.substring(url.lastIndexOf("/") + 1);
		}
		return filename;
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
	 * 
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unused")
	private String createFileInSDCard(String name) {
		String path = null;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			path = Environment.getExternalStorageDirectory().getAbsolutePath()
					+ "/" + name;
			File file = new File(path);
			// 创建文件夹
			file.mkdirs();
			Log.v("Update", "CreateFileInSDCardSuccess");
		} else {
			Log.e("Update",
					"SDCard State:" + Environment.getExternalStorageState());
		}
		return path;
	}

	/**
	 * 在data/data目录下创建文件夹
	 * 
	 * @param name
	 * @return
	 */
	private String createFileInAppData(String name) {
		String path = null;
		path = getApplicationContext().getFilesDir() + "/" + name;
		File file = new File(path);
		// 创建文件夹
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
		// 注销监听
		unregisterReceiver(apkInstallListener);
		super.onDestroy();
	}
}
