package com.example.xstestandroid.ga;

import android.content.Context;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.ExceptionParser;
import com.google.analytics.tracking.android.ExceptionReporter;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.GAServiceManager;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.StandardExceptionParser;
import com.google.analytics.tracking.android.Tracker;

public class GACollector {

	/**
	 * GA类管理类 1.管理Tracker:确保一个trackerId对应一个Tracker对象 2.管理GA的日志，发送频率等
	 */
	public static GoogleAnalytics ga;

	/**
	 * Tracker 打点逻辑管理类 1.发送打点信息到GA
	 */
	public static Tracker tracker;

	/**
	 * EasyTracker 提供便捷的打点方法
	 */
	public static EasyTracker easyTracker;

	/**
	 * 解析一个抛出的异常，转换为一个简短而有意义的描述信息
	 */
	public static ExceptionParser exceptionParser;
	
	/**
	 * 捕获最深层次的异常原因，反馈出异常的信息(类型、类名、方法和线程名)
	 */
	public static StandardExceptionParser standardExceptionParser;

	/**
	 * 管理GA服务端的控制调度和AnalyticsStore
	 */
	public static GAServiceManager gaServiceManager;
	// ServiceManager//deprecated

	/**
	 * ExceptionReporter 捕获不能catch的异常，上传GA 1.通过此方式上传的异常被定为致命的异常
	 * 2.setExceptionParser()方法调用之后立即发送到GA
	 */
	public static ExceptionReporter uncaughtExceptionHandler;

	private static GACollector collector;

	public static GACollector shareInstance() {
		synchronized (GACollector.class) {
			if (collector == null) {
				collector = new GACollector();
			}
			return collector;
		}
	}

	public void initEasyTracker(Context ctx) {
		shareInstance();
		if (null != ctx) {

			ga = GoogleAnalytics.getInstance(ctx);
			// ga.closeTracker("trackerName");
			// //根据Tracker名称销毁tracker,如果trackerId可以当做name使用
			// ga.getDefaultTracker();//如果tracker存在，默认情况下返回第一个创建的tracker，否则返回空。
			// ga.setDefaultTracker(easyTracker);//设置默认Tracker;
			// ga.getLogger();//获取当前正在使用的logger
			// ga.setLogger(null);//为GA设置一个logger
			// ga.getLogger().setLogLevel(LogLevel.VERBOSE);//设置输出级别
			// ga.isDryRunEnabled();//是否开启试运行 推荐Debug模式使用
			// ga.setDryRun(true);//试运行模式表示所有的GA收集操作均是本地操作，不发送到服务器
			// ga.setAppOptOut(true);//设置是否发送信息给GA
			// ga.getAppOptOut();//application-level退出选项是否打开,如果打开则不会有任何信息发送到GA

			tracker = ga.getTracker("UA-49189520-1");
			// tracker = ga.getTracker("trackName","trackId");// UA-xxxx-y
			// //先设置内容 在发送信息
			// tracker.set(Fields.SCREEN_NAME, "activityName");
			// tracker.send(MapBuilder.createEvent("UX", "Click", "Button",
			// null)
			// .build());
			// //直接发送
			// tracker.send(MapBuilder.createEvent("UX", "Click", "Action",
			// null)
			// .set(Fields.SCREEN_NAME, "PopAction").build());
			// tracker.getName();

			easyTracker = EasyTracker.getInstance(ctx);
			easyTracker.activityStart(null);// Activity开始
			easyTracker.activityStop(null);// Activity结束
			easyTracker.send(MapBuilder
					.createEvent("UX", "Click", "Action", null)
					.set(Fields.SCREEN_NAME, "PopAction").build());

			gaServiceManager = GAServiceManager.getInstance();
			gaServiceManager.dispatchLocalHits();// 立即上传所有的信息
			gaServiceManager.setLocalDispatchPeriod(180); // 每180S检查上传一次本地记录，负数或者0需要关闭自动提交
			gaServiceManager.setForceLocalDispatch();//

			/**
			 * 设置异常处理器 使用自动检测器检测出来的所有异常都被视为致命异常 异常的描述部分自动被填充为异常类型、类名、方法名和线程名。
			 */
			uncaughtExceptionHandler = new ExceptionReporter(tracker,
					gaServiceManager,
					Thread.getDefaultUncaughtExceptionHandler(), ctx);
			Thread.setDefaultUncaughtExceptionHandler(uncaughtExceptionHandler);
			uncaughtExceptionHandler.getExceptionParser();//
			uncaughtExceptionHandler.setExceptionParser(exceptionParser);
			// uncaughtExceptionHandler.uncaughtException(thread, ex);

		}
	}

	/**
	 * Interface
	 * 
	 * @param name
	 */
	public void onPageStart(String pageName) {
		tracker.set(Fields.SCREEN_NAME, pageName);
		tracker.send(MapBuilder.createAppView().build());
	}

	public void onException(Context context, String pageName, Throwable exception) {
		tracker.set(Fields.SCREEN_NAME, pageName);
		standardExceptionParser = new StandardExceptionParser(context, null);
		//为GA生成合适的异常描述信息
		String exceptionDesc = standardExceptionParser.getDescription(Thread.currentThread().getName(),exception);
		//false means non fatal exception 非致命异常
		tracker.send(MapBuilder.createException(exceptionDesc,false).build());
	}
	
	/**
	 * 
	 * @param pageName 
	 * @param category 必填
	 * @param action   必填
	 * @param label    非必填
	 * @param value    非必填
	 */
	public void onEvent(String pageName, String category, String action, String label, long value){
		tracker.set(Fields.SCREEN_NAME, pageName);
		tracker.send(MapBuilder.createEvent(category, action, label, value).build());
	}
	
    /**
     * Track a custom dimension.<br>
     * 
     * Dimensions describe the data. Think of a dimension as describing the
     * "what", as in "what Android version do they use" or "what city is the
     * visitor from" or "what screen were viewed".<br>
     * Dimensions correspond to the rows in a report.
     * 
     * @param screenName
     * @param index
     * @param value
     */
    public void trackCustomDimension(String screenName, int index, String value) {
        tracker.set(Fields.SCREEN_NAME, screenName);
        tracker.send(MapBuilder.createAppView().set(Fields.customDimension(index), value).build());
    }

    /**
     * Track a custom metric.<br>
     * 
     * Metrics measure the data. Metrics are elements about a dimension that can
     * be measured. Think of a metric as answering "how many" or "how long", as
     * in "how many visits" or "how long a user was on the app".<br>
     * A metric corresponds to a column in a report.
     * 
     * @param screenName
     * @param index
     * @param value
     */
    public void trackCustomMetric(String screenName, int index, String value) {
        tracker.set(Fields.SCREEN_NAME, screenName);
        tracker.set(Fields.customMetric(index), value);
        tracker.send(MapBuilder.createAppView().build());
    }

		
}
