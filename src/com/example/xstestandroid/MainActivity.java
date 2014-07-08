package com.example.xstestandroid;
/**
 * APP初始化及启动类
 * An activity is a single, focused thing that the user can do. 
 * Almost all activities interact with the user, 
 * so the Activity class takes care of creating a window for you in which you can place your UI with setContentView(View). 
 * While activities are often presented to the user as full-screen windows, 
 * they can also be used in other ways: 
 * as floating windows (via a theme with windowIsFloating set) or embedded inside of another activity (using ActivityGroup). 
 * There are two methods almost all subclasses of Activity will implement:
 * onCreate(Bundle) is where you initialize your activity. 
 * Most importantly, here you will usually call setContentView(int) with a layout resource defining your UI, 
 * and using findViewById(int) to retrieve the widgets in that UI that you need to interact with programmatically.
 * onPause() is where you deal with the user leaving your activity. 
 * Most importantly, any changes made by the user should at this point be committed (usually to the ContentProvider holding the data).
 */

/**
 * Activities in the system are managed as an activity stack. 
 * When a new activity is started, it is placed on the top of the stack and becomes the running activity 
 * the previous activity always remains below it in the stack, 
 * and will not come to the foreground again until the new activity exits.
 */

import java.util.ArrayList;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.example.xstestandroid.base.BaseFragmentActivity;
import com.example.xstestandroid.base.MainApplication;
import com.example.xstestandroid.base.Route;
import com.example.xstestandroid.home.HomeFragment;
import com.example.xstestandroid.main.MainViewModel;
import com.example.xstestandroid.other.OtherFragment;
import com.example.xstestandroid.other.OtherFragment.ListFragmentCallBacks;

public class MainActivity extends BaseFragmentActivity implements ListFragmentCallBacks{

	private View mainView;
	private MainViewModel mainModel;
	private LinearLayout mainContent;
	private LinearLayout tabbar;
	private RadioButton tabbarHome;
	private RadioButton tabbarCar;
	private RadioButton tabbarBargain;
	private RadioButton tabbarMy;
	private RadioButton tabbarMore;
	
	/**
	 * TODO ???:
	 */
	private RadioButton[] tabItems;

	/**
	 * VC列表
	 */
	private ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
	private FragmentManager fm;
	private FragmentTransaction ft;
	private int currentIndex;
	/**
	 * TODO ???:
	 */
	private Intent intent;
	
	/**
	 * APP初始化
	 * protected void onCreate (Bundle savedInstanceState)
	 * Added in API level 1
	 * Called when the activity is starting. 
	 * This is where most initialization should go: calling setContentView(int) to inflate the activity's UI, 
	 * using findViewById(int) to programmatically interact with widgets in the UI, 
	 * calling managedQuery(android.net.Uri, String[], String, String[], String) to retrieve cursors for data being displayed, etc.
	 * 
	 * You can call finish() from within this function, 
	 * in which case onDestroy() will be immediately called 
	 * without any of the rest of the activity lifecycle (onStart(), onResume(), onPause(), etc) executing.
	 * 
	 * Derived classes must call through to the super class's implementation of this method. 
	 * If they do not, an exception will be thrown.
	 * 
	 * Parameters savedInstanceState
	 * If the activity is being re-initialized after previously being shut down then this Bundle contains the data 
	 * it most recently supplied in onSaveInstanceState(Bundle). Note: Otherwise it is null.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v("LifeCycel", "OnCreate");
		
		//TODO SetViewHere
		mainView = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
		setContentView(mainView);
		
		mainModel = (MainViewModel)Route.shareInstance().getViewModelWithClass("com.example.xstestandroid.MainActivity");
		this.setBaseModel(mainModel);
		
		//TODO 获取设备相关信息
		TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		String imei = tm.getDeviceId();
		Log.v("MainActivity", "IMEI:" + imei);
		
		PackageManager manager = getPackageManager();
		String packageName = getPackageName();
		PackageInfo info = new PackageInfo();
		try {
			info = manager.getPackageInfo(packageName, 0);
		} catch (NameNotFoundException e) {
			Log.e("MainActivity", "获取APP包信息异常");
			e.printStackTrace();
		}
		Log.v("MainActivity", "IMEI:" + info.versionName);

		//TODO APP Launch Logic		
		appLanchLogic();
	}
	
	/**
	 * Called after onStop() when the current activity is being re-displayed to the user 
	 * (the user has navigated back to it). 
	 * It will be followed by onStart() and then onResume().
	 * 
	 * For activities that are using raw Cursor objects 
	 * (instead of creating them through managedQuery(android.net.Uri, String[], String, String[], String), 
	 * this is usually the place where the cursor should be requeried (because you had deactivated it in onStop().
	 * 
	 * Derived classes must call through to the super class's implementation of this method. 
	 * If they do not, an exception will be thrown.
	 */
	@Override
	protected void onRestart(){
		Log.v("LifeCycel", "onRestart");
		super.onRestart();
	}
	
	/**
	 * Called after onCreate(Bundle) — or after onRestart() when the activity had been stopped, 
	 * but is now again being displayed to the user. 
	 * It will be followed by onResume().
	 * 
	 * Derived classes must call through to the super class's implementation of this method. 
	 * If they do not, an exception will be thrown.
	 */
	@Override
	protected void onStart(){
		Log.v("LifeCycel", "onStart");
		super.onStart();
	}
		
	/**
	 * Called after onRestoreInstanceState(Bundle), onRestart(), or onPause(), 
	 * for your activity to start interacting with the user. 
	 * This is a good place to begin animations, open exclusive-access devices (such as the camera), etc.
	 * 
	 * Keep in mind that onResume is not the best indicator that your activity is visible to the user; 
	 * a system window such as the keyguard may be in front. 
	 * Use onWindowFocusChanged(boolean) to know for certain that your activity is visible to the user (for example, to resume a game).
	 * 
	 * Derived classes must call through to the super class's implementation of this method. 
	 * If they do not, an exception will be thrown.
	 */
	@Override
	protected void onResume(){
		Log.v("LifeCycel", "onResume");
		super.onResume();
	}
	
	/**
	 * Called as part of the activity lifecycle when an activity is going into the background, but has not (yet) been killed. 
	 * The counterpart to onResume().
	 * 
	 * When activity B is launched in front of activity A, this callback will be invoked on A.
	 * B will not be created until A's onPause() returns, so be sure to not do anything lengthy here.
	 * This callback is mostly used for saving any persistent state the activity is editing, 
	 * to present a "edit in place" model to the user and making sure nothing is lost 
	 * if there are not enough resources to start the new activity without first killing this one. 
	 * This is also a good place to do things like stop animations and 
	 * other things that consume a noticeable amount of CPU in order to make the switch to the next activity as fast as possible, 
	 * or to close resources that are exclusive access such as the camera.
	 * 
	 * In situations where the system needs more memory it may kill paused processes to reclaim resources. 
	 * Because of this, you should be sure that all of your state is saved by the time you return from this function. 
	 * In general onSaveInstanceState(Bundle) is used to save per-instance state in the activity and 
	 * this method is used to store global persistent data (in content providers, files, etc.)
	 * 
	 * After receiving this call you will usually receive a following call to onStop() 
	 * (after the next activity has been resumed and displayed), 
	 * however in some cases there will be a direct call back to onResume() without going through the stopped state.
	 * 
	 * Derived classes must call through to the super class's implementation of this method. 
	 * If they do not, an exception will be thrown.
	 */
	@Override
	protected void onPause(){
		super.onPause();
		Log.v("LifeCycel", "onPause");
	}
	
	/**
	 * Called when you are no longer visible to the user. 
	 * You will next receive either onRestart(), onDestroy(), or nothing, depending on later user activity.
	 * 
	 * Note that this method may never be called, in low memory situations 
	 * where the system does not have enough memory to keep your activity's process running after its onPause() method is called.
	 * 
	 * Derived classes must call through to the super class's implementation of this method. 
	 * If they do not, an exception will be thrown.
	 */
	@Override
	protected void onStop(){
		super.onStop();
		Log.v("LifeCycel", "onStop");
	}
	
	/**
	 * Perform any final cleanup before an activity is destroyed. 
	 * This can happen either because the activity is finishing 
	 * (someone called finish() on it, or because the system is temporarily destroying this instance of the activity to save space. 
	 * You can distinguish between these two scenarios with the isFinishing() method.
	 * 
	 * Note: do not count on this method being called as a place for saving data! 
	 * For example, if an activity is editing data in a content provider, 
	 * those edits should be committed in either onPause() or onSaveInstanceState(Bundle), not here. 
	 * This method is usually implemented to free resources like threads that are associated with an activity, 
	 * so that a destroyed activity does not leave such things around while the rest of its application is still running. 
	 * There are situations where the system will simply kill the activity's hosting process without calling this method 
	 * (or any others) in it, so it should not be used to do things that are intended to remain around after the process goes away.
	 * 
	 * Derived classes must call through to the super class's implementation of this method. 
	 * If they do not, an exception will be thrown.
	 */
	@Override
	protected void onDestroy(){
		super.onDestroy();
		Log.v("LifeCycel", "onDestroy");
	}
		
	/**
	 * Called to retrieve per-instance state from an activity before being killed 
	 * so that the state can be restored in onCreate(Bundle) or onRestoreInstanceState(Bundle) 
	 * (the Bundle populated by this method will be passed to both).
	 * 
	 * This method is called before an activity may be killed so that when it comes back some time in the future it can restore its state. 
	 * For example, if activity B is launched in front of activity A, 
	 * and at some point activity A is killed to reclaim resources, 
	 * activity A will have a chance to save the current state of its user interface via this method so that 
	 * when the user returns to activity A, the state of the user interface can be restored via onCreate(Bundle) or onRestoreInstanceState(Bundle).
	 * 
	 * Do not confuse this method with activity lifecycle callbacks such as onPause(), 
	 * which is always called when an activity is being placed in the background or on its way to destruction, 
	 * or onStop() which is called before destruction. 
	 * One example of when onPause() and onStop() is called and not this method is 
	 * when a user navigates back from activity B to activity A: there is no need to call onSaveInstanceState(Bundle) on B 
	 * because that particular instance will never be restored, so the system avoids calling it. 
	 * An example when onPause() is called and not onSaveInstanceState(Bundle) is 
	 * when activity B is launched in front of activity A: the system may avoid calling onSaveInstanceState(Bundle) on activity A 
	 * if it isn't killed during the lifetime of B since the state of the user interface of A will stay intact.
	 * 
	 * The default implementation takes care of most of the UI per-instance state for you 
	 * by calling onSaveInstanceState() on each view in the hierarchy that has an id, 
	 * and by saving the id of the currently focused view 
	 * (all of which is restored by the default implementation of onRestoreInstanceState(Bundle)). 
	 * If you override this method to save additional information not captured by each individual view, 
	 * you will likely want to call through to the default implementation, 
	 * otherwise be prepared to save all of the state of each view yourself.
	 * 
	 * If called, this method will occur before onStop(). 
	 * There are no guarantees about whether it will occur before or after onPause().
	 * 
	 * Parameters outState
	 * Bundle in which to place your saved state.
	 */
	@Override 
	protected void onSaveInstanceState(Bundle outState){
		super.onSaveInstanceState(outState);
		Log.v("Memory", "onSaveInstanceState");
	}
	
	/**
	 * This method is called after onStart() when the activity is being re-initialized from a previously saved state, 
	 * given here in savedInstanceState. 
	 * Most implementations will simply use onCreate(Bundle) to restore their state, 
	 * but it is sometimes convenient to do it here after all of the initialization has been done 
	 * or to allow subclasses to decide whether to use your default implementation. 
	 * 
	 * The default implementation of this method performs a restore of any view state 
	 * that had previously been frozen by onSaveInstanceState(Bundle).
	 * 
	 * This method is called between onStart() and onPostCreate(Bundle).
	 * 
	 * Parameters savedInstanceState
	 * the data most recently supplied in onSaveInstanceState(Bundle).
	 */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState){
		super.onRestoreInstanceState(savedInstanceState);
		Log.v("Memory", "onSaveInstanceState");
	}
	
	/**
	 * Initialize the contents of the Activity's standard options menu. You should place your menu items in to menu.
	 * This is only called once, the first time the options menu is displayed. 
	 * To update the menu every time it is displayed, see onPrepareOptionsMenu(Menu).
	 * 
	 * The default implementation populates the menu with standard system menu items. 
	 * These are placed in the CATEGORY_SYSTEM group so that they will be correctly ordered with application-defined menu items. 
	 * Deriving classes should always call through to the base implementation.
	 * 
	 * You can safely hold on to menu (and any items created from it), 
	 * making modifications to it as desired, until the next time onCreateOptionsMenu() is called.
	 * 
	 * When you add items to the menu, you can implement the Activity's onOptionsItemSelected(MenuItem) method to handle them there.
	 * 
	 * Parameters menu
	 * The options menu in which you place your items.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * APP 启动逻辑
	 */
	//TODO 此处增加APP启动逻辑...
	private void appLanchLogic(){
		setView();
		initData();
		
		fm = this.getSupportFragmentManager();  //android.app.Fragment兼容到3.0版本 SDK11
		//fm = getFragmentManager(); android.v4.app.Fragment 兼容到1.6版本
		
		intent = getIntent();
		int flag = intent.getIntExtra("flag", 0);
		//默认选中第一行
		setCurrentFragment(currentIndex);
		setCurrentPoint(currentIndex);
		currentIndex = flag;

		//GPS定位
		getCurrentLocation();
	}
	
	private void setView(){
		mainContent = (LinearLayout)findViewById(R.id.main_content);
		tabbar = (LinearLayout)findViewById(R.id.tabbar);
		tabbarHome = (RadioButton)findViewById(R.id.tabbar_home);
		tabbarCar = (RadioButton)findViewById(R.id.tabbar_car);
		tabbarBargain = (RadioButton)findViewById(R.id.tabbar_bargain);
		tabbarMy = (RadioButton)findViewById(R.id.tabbar_my);
		tabbarMore = (RadioButton)findViewById(R.id.tabbar_more);
		
		tabbar.setOnClickListener(new btnOnClick());
		tabbarHome.setOnClickListener(new btnOnClick());
		tabbarCar.setOnClickListener(new btnOnClick());
		tabbarBargain.setOnClickListener(new btnOnClick());
		tabbarMy.setOnClickListener(new btnOnClick());
		tabbarMore.setOnClickListener(new btnOnClick());
	}
	
	/**
	 * 初始化UI页面及默认状态
	 */
	private void initData(){
		//初始化Tab页
		HomeFragment homePage = new HomeFragment();
		fragmentList.add(homePage);
		OtherFragment otherPage = new OtherFragment();
		fragmentList.add(otherPage);
		OtherFragment otherPage1 = new OtherFragment();
		fragmentList.add(otherPage1);
		OtherFragment otherPage2 = new OtherFragment();
		fragmentList.add(otherPage2);
		OtherFragment otherPage3 = new OtherFragment();
		fragmentList.add(otherPage3);
		
		//设置状态
		tabItems = new RadioButton[fragmentList.size()];
		tabItems[0] = tabbarHome;
		tabItems[1] = tabbarCar;
		tabItems[2] = tabbarBargain;
		tabItems[3] = tabbarMy;
		tabItems[4] = tabbarMore;
		for (int i = 0; i < tabItems.length; i++)
		{
			tabItems[i].setTag(i);
			tabItems[i].setChecked(false);
		}
	}
	
	/**
	 * 点击响应事件
	 * @author zhangss
	 *
	 */
	//TODO 点击事件的相应方法
	private class btnOnClick implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int selectedIndex = -2;
			switch (v.getId()) {
			case R.id.tabbar:
				Log.v("Tabbar", "Tabbar");
				selectedIndex = -1;
				break;
			case R.id.tabbar_home:
				Log.v("Tabbar", "Home");
				selectedIndex = 0;
				break;
			case R.id.tabbar_car:
				Log.v("Tabbar", "Car");
				selectedIndex = 1;
				break;
			case R.id.tabbar_bargain:
				Log.v("Tabbar", "Bargain");
				selectedIndex = 2;
				break;
			case R.id.tabbar_my:
				Log.v("Tabbar", "My");
				selectedIndex = 3;
				break;
			case R.id.tabbar_more:
				Log.v("Tabbar", "More");
				selectedIndex = 4;
				break;
			default:
				Log.v("Tabbar", "UnKnow" + v.getId());
				break;
			}
			//修改UI指示状态及动画
			setCurrentFragment(selectedIndex);
			setCurrentPoint(selectedIndex);
		}
	}
	
	/**
	 * Fragment动画切换
	 * @param index
	 */
	private void setCurrentFragment(int index){
		//屏蔽无效及重复点击
		if (index < 0 || index > tabItems.length - 1 
				|| currentIndex == index){
			return;
		}
	
		ft = fm.beginTransaction();
		ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
		ft.replace(R.id.main_content, fragmentList.get(index));
		ft.addToBackStack(null);//允许back回到之前的状态
		ft.commit();
	}
	
	/**
	 * UI指示状态变更
	 * @param index
	 */
	private void setCurrentPoint(int index){
		if (index < 0 || index > tabItems.length - 1 
				|| currentIndex == index){
			return;
		}
		setAllButtonFalse();
		tabItems[currentIndex].setChecked(false);
		tabItems[currentIndex].setTextColor(this.getResources().getColor(R.color.table_textColor_normal));
		tabItems[index].setChecked(true);
		tabItems[index].setTextColor(this.getResources().getColor(R.color.table_textColor_selected));
		
		currentIndex = index;
	}
	
	/**
	 * 重置Button状态为未选中
	 */
	private void setAllButtonFalse(){
		for (int i = 0; i < tabItems.length; i++){
			tabItems[i].setChecked(false);
		}
	}
	
	/**
	 * 获取定位信息GPS
	 */
	private void getCurrentLocation(){
		MainApplication application = (MainApplication)getApplication();
		application.setLocationOption();
		application.startLocation();
	}

	//ListFragment接口
	@Override
	public void onItemSelected(Integer id, Class<?> classString) {
		// TODO Auto-generated method stub
		Log.v("Selected:", "id" + id);
		
		/**
		 * Intent是对启动意图进行的封装，包含了各种类型组件的启动(Activity,Service,BroadcastReceiver)
		 * 切换Activity 1.创建意向 2.启动Activity
		 * 直接启动或者定义一个请求码
		 */
		Intent intent = new Intent(this, classString);
				
//		startActivity(intent);
		startActivityForResult(intent, 0);
		
		/**
		 * Component
		 */
//		ComponentName comp = new ComponentName(MainActivity.this, classString);
//		Intent intent = new Intent();
//		intent.setComponent(comp);
//		startActivity(intent);
//		intent.getComponent().getPackageName();
//		intent.getComponent().getClassName();
		
	}
		
	/**
	 * Called when an activity you launched exits, giving you the requestCode
     * you started it with, the resultCode it returned, and any additional
     * data from it.  The <var>resultCode</var> will be
     * {@link #RESULT_CANCELED} if the activity explicitly returned that,
     * didn't return any result, or crashed during its operation.
     * 
     * <p>You will receive this call immediately before onResume() when your
     * activity is re-starting.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		//请求码在Activity启动时定义 结果码在结果返回触发时定义 使用setResult()返回结果
		if (requestCode == 0 && resultCode == 0) {
			//
			Bundle bundle = data.getExtras();
			String string = bundle.getString("key");
			Log.v("Reveived", string);
		}
	}
		
}
