package com.example.android;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.logging.Logger;

import android.app.Activity;
import android.os.Bundle;
import android.util.PrintWriterPrinter;

/**
 * Java中所有的异常都是从Throwable继承而来，Throwable被定义在jave.lang包中，并且有两个直接子类——
 * Exception和Error。 其中Error指的是Java虚拟机的内部系统错误，而且通常情况下我们无法处理此类错误。
 * 今天我们主要讨论Exception，所有其他的异常都是Exception的子类，而且只有这些异常才是程序员可以处理的。
 * 如上图所示，Exception有两个重要的直接子类
 * ，分别是IOException和RuntimeException。IOException类用于处理程序的输入输出异常，包括键盘锁定或文件异常等；
 * RuntimeException类用于处理从程序逻辑中产生的错误，例如访问数组下标越界、空指针异常等。
 * 
 * 1、如果无法处理某个异常，那就不要捕获它；
 * 2、捕获异常要按照从小到大的顺序（例如应该先捕获NullPointerException，然后RuntimeException，最后Exception）；
 * 3、尽量在靠近异常被抛出的地方捕获异常； 4、在捕获异常的地方将它记录到日志中，除非您打算将它重新抛出； 5、按照您的异常处理必须多精细来构造您的方法；
 * 6、需要用几种类型的异常就用几种，尤其是对于应用程序异常。 规则 #1: 总是捕获扔出异常的类型而不要理睬异常的超类。
 * 为了遵守通常的代码习惯，你可以采用Exception类的大写字母作为变量名，如下所示： catch(FileNotFoundException fnfe)
 * 以及 catch(SQLException sqle) 规则 # 2:
 * 决不让catch块留空。在很多情况下虽然确实编写了try/catch块但在代码的catch部分却什么都没有做。或者，如果采用了日志API（Logging
 * API），那么请编写代码把异常写到日志中。 规则 # 3: 决不扔出Exception基类的实例。开发人员应当总是扔出自己创建的异常类。
 * 
 * @author saic
 * 
 */
public class ExceptionActivity extends Activity {

	@SuppressWarnings("serial")
	public static class MyException extends Exception {
		
		public MyException(){
			/**
			 * 把日志信息封装到类中
			 */
			StringWriter trace = new StringWriter();
			printStackTrace(new PrintWriter(trace));
			logger.severe(trace.toString());
		};
		public MyException(String msg) {
			super(msg);
		};
		
		private Logger logger = Logger.getLogger("MyException");
		
		/**
		 * 重写获取描述信息方法
		 */
		@Override
		public String getMessage() {
			// TODO Auto-generated method stub
			return "MyException:" + super.getMessage();
		}
	}
	
	public static void f() throws MyException {
		System.out.println("Throwing MyException Form f()");
		throw new MyException();
	}
	
	public static void g() throws MyException{
		System.out.println("Throwing MyException From g()");
		throw new MyException("Originate in g()");
	}	

	public static void h(String msg) throws MyException{
		System.out.println("Throwing MyException From g()");
		throw new MyException("Originate in g()");
	}	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		try {
			f();
		} catch (MyException e) {
			// TODO: handle exception
			e.printStackTrace(System.out);
		}
		
		try {
			g();
		} catch (MyException e) {
			// TODO: handle exception
			e.printStackTrace(System.err);
		}
	}
	
	public static void main(String[] args) throws Throwable {
		try {
			f();
		} catch (MyException e) {
			// TODO: handle exception
			e.printStackTrace(System.out);
		}
		
		try {
			g();
		} catch (MyException e) {
			// TODO: handle exception
			e.printStackTrace(System.err);
			//更新异常内容 重新抛出异常
			throw e.fillInStackTrace();
		}
		
		try {
			h(null);
		} catch (MyException e) {
			// TODO: handle exception
			e.printStackTrace(System.err);
		}
		
		try {
			ArrayList<String> strings = new ArrayList<String>();
			@SuppressWarnings("unused")
			String string = strings.get(1);	
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
			e.printStackTrace(System.err);
		}
		
		for(int i= 0; i < 10; i ++){
			try {
				g();
			} catch (MyException e) {
				// TODO: handle exception
			} finally{
				/*
				 * Finally中的代码 无论是否能抓到异常都会执行
				 * Finally不受return和break等语句影响，哪怕try中代码已经return结果，finally也一定会执行。
				 * Finally中可以用来回收内存(Java有垃圾回收机制,故无需这样做)
				 * Finally需要清理一些资源或者把一些资源恢复到初始状态，如打开的文件或者网络.
				 * 如果try中抛出异常继而在finally中也抛出异常,或者直接return,那么try中的异常就会被替代(return则不会抛出异常)，造成异常丢失。
				 */
				if (i >= 2) {
					break;
				}
			}
			
		}
				
	}

}
