package com.example.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

public class AsyncTaskActivity extends Activity {
	
	//UI触发事件
	public void downLoad(View view){
		DownLoadTask task = new DownLoadTask(this);
		try {
			task.execute(new URL("www.baidu.com"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	class DownLoadTask extends AsyncTask<URL, Integer, String>{
		ProgressDialog log;
		
		//记录进度
		int hasRead = 0;
		Context context;
		
		public DownLoadTask(Context ctx) {
			// TODO Auto-generated constructor stub
			context = ctx;
		}
		
		//下载URL内容
		protected String doInBackground(URL... params) {
			StringBuilder sb = new StringBuilder();
			
			try {
				//打开Connection连接对应的输入流，包装成reader
				URLConnection connection = params[0].openConnection();
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
					hasRead ++;
					publishProgress(hasRead);
				}
				return sb.toString();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		};
		
		//显示结果
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			//结果返回 返回结果内容
			Toast.makeText(AsyncTaskActivity.this, result, Toast.LENGTH_LONG).show();
			
			log.dismiss();
		}
		
		//下载过程中 显示进度条
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			log = new ProgressDialog(context);
			log.setTitle("任务正在执行中");
			log.setMessage("正在执行中,请等待...");
			log.setCancelable(false);
			log.setMax(202);
			log.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			log.setIndeterminate(false);
			log.show();
		}
		
		//更新下载进度
		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			//更新进度
			Toast.makeText(AsyncTaskActivity.this, values[0], Toast.LENGTH_LONG).show();
			log.setProgress(values[0]);
		}
	}

}
