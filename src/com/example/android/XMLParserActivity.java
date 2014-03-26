package com.example.android;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.xstestandroid.R;

/**
 * XML解析
 * 
 * @author saic
 * 
 */
public class XMLParserActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contacts_test_layout);

		Button btn = (Button) findViewById(R.id.spAction);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Book book = booksFromXML().get(0);
				TextView nameLabel = (TextView) findViewById(R.id.nameLabel);
				TextView priceLabel = (TextView) findViewById(R.id.phoneLabel);
				nameLabel.setText(book.name);
				priceLabel.setText(book.price + "");
			}
		});
	}

	// 解析XML内容
	private ArrayList<Book> booksFromXML() {
		ArrayList<Book> books = new ArrayList<Book>();

		// 生成解析器
		XmlResourceParser parser = getResources().getXml(R.xml.books);
		try {
			//根据end标签做判断 循环解析
			while (parser.getEventType() != XmlResourceParser.END_DOCUMENT) {
				Log.v("XMLParser", "TagType:" + XmlResourceParser.TYPES[parser.getEventType()]);
				if (parser.getEventType() == XmlResourceParser.START_TAG) {
					String tagName = parser.getName();
					if (tagName.equals("book")) {
						Book book = new Book();
						book.price = Float.valueOf(parser.getAttributeValue(null, "price"));
						book.date = parser.getAttributeValue(1);
						book.name = parser.nextText();
						books.add(book);
					}
				}
				parser.next();
			}
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return books;
	}

}
