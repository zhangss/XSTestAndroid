package com.example.android;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 长按触发发送短信事件
 * 2.外部事件监听器类 与GUI单独出来使用，解除了与GUI的耦合，适用于多个GUI均需要监听的事件。
 * 不推荐将业务逻辑和事件监听器耦合，应该严格分离GUI显示逻辑和业务逻辑
 * 
 * @author saic
 * 
 */
public class SendSMSListener implements OnLongClickListener {

	private Activity activity;
	private EditText address;
	private EditText content;

	public SendSMSListener(Activity temActivity, EditText addressEditText,
			EditText contentEditText) {
		this.activity = temActivity;
		this.address = addressEditText;
		this.content = contentEditText;
	}

	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		String addressString = address.getText().toString();
		String contentString = content.getText().toString();

		// 调用系统短信管理器发送短信
		SmsManager smsManager = SmsManager.getDefault();
		PendingIntent sendIntent = PendingIntent.getBroadcast(activity, 0,
				new Intent(), 0);
		smsManager.sendTextMessage(addressString, null, contentString,
				sendIntent, null);
		Toast.makeText(activity, "短信发送成功", Toast.LENGTH_LONG).show();
		return false;
	}

}
