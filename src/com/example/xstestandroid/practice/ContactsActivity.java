package com.example.xstestandroid.practice;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.xstestandroid.R;

/**
 * 访问系统通讯录 1.androidmanifest.xml中加入权限 READ_CONTACTS 2.调用系统
 * 
 * @author saic
 * 
 */

@SuppressLint("NewApi")
public class ContactsActivity extends Activity {

	private final int REQUEST_CODE = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// 加载布局文件
		setContentView(R.layout.contacts_test_layout);
		
		//查看联系人
		Button btn = (Button) findViewById(R.id.showContacts);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// 显示系统联系人界面
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_GET_CONTENT);
				intent.setType("vnd.android.cursor.item/phone");
				startActivityForResult(intent, REQUEST_CODE);
			}
		});
		
		//返回首页
		btn = (Button)findViewById(R.id.returnHome);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_HOME);
				startActivity(intent);
			}
		});
		
		//特殊操作
		btn = (Button)findViewById(R.id.spAction);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/**
				 * Intent的Data和Type会互相覆盖，以后设置的为准
				 * 如果想要同时存在就调用同时设置的接口
				 * 
				 * 如果设置Type属性，则必须在XML中intent-filter标签内容加上data android:mimeType才能启动Activity
				 * 如果设置Data属性，则XML中data标签下设置的标签内容需要data中相应的部分相同才能启动Activity
				 * 
				 * Type指定了intent可以接受的Uri的MIME类型，格式abc/xyz
				 * Uri字符串格式 scheme://host:port/path
				 * 
				 * Action+Data就等以一个动作 查看/编辑联系人 拨打电话，浏览网页等等
				 */

				Intent intent = new Intent();
				/**
				 * 浏览网页 外置浏览器打开
				 */
				intent.setAction(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("http://www.baidu.com"));
				/**
				 * 拨打电话
				 */
//				intent.setAction(Intent.ACTION_DIAL);
//				intent.setData(Uri.parse("13512345678"));
				/**
				 * 查看或者编辑联系人
				 */
//				intent.setAction(Intent.ACTION_VIEW);
//				intent.setAction(Intent.ACTION_EDIT);
//				intent.setData(Uri.parse("content://com.android.contacts/contacts/1"));
				startActivity(intent);
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case REQUEST_CODE: {
			if (resultCode == Activity.RESULT_OK) {
				// 数据返回 获取数据
				Uri contactData = data.getData();
				CursorLoader cursorLoader = new CursorLoader(this, contactData,
						null, null, null, null);
				Cursor cursor = cursorLoader.loadInBackground();
				if (cursor.moveToFirst()) {
					String contactId = cursor.getString(cursor
							.getColumnIndex(ContactsContract.Contacts._ID));
					String contactName = cursor
							.getString(cursor
									.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
					String contactPhone = "未找到该联系人电话号码";
					Cursor contactPhones = getContentResolver().query(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
									+ "=" + contactId, null, null);
					if (contactPhones.moveToFirst()) {
						contactPhone = contactPhones
								.getString(contactPhones
										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
					}
					contactPhones.close();

					TextView nameLabel = (TextView) findViewById(R.id.nameLabel);
					TextView phoneLabel = (TextView) findViewById(R.id.phoneLabel);
					nameLabel.setText(contactName);
					phoneLabel.setText(contactPhone);
				}
				cursor.close();
			}
			break;
		}
		default:
			break;
		}
	}
}
