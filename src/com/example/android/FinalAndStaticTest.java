package com.example.android;

import android.app.ActionBar.Tab;
import android.util.Log;

/**
 * Final 和 Static
 * 1.final表示终态的或者无法改变的.
 *   1.1 final的类不能被继承，类中的方法默认都是final. 故该类没有子类,不能被拓展.
 *   1.2 final的方法不能被子类覆盖，但可以被继承使用. 故1.该方法不能被修改2.编译器处理final方法时会转入内嵌机制,大大提高执行效率.
 *       1.2.1 final不能被用来修饰构造方法.
 *       1.2.2 private方法不能被子类覆盖/重写,不能被子类继承使用,故private方法默认是final类型的.
 *   1.3 final的成员变量表示常量，只能被赋值一次，赋值之后值不会被改变.
 *       1.3.1 final可以声明final空白,先声明后给初值,但是必须在使用之前给初值.
 *   1.4 final的函数参数，你可以使用它的内容，但是不能给他赋值。多线程操作的函数参数必须是final类型的
 *  
 * 2.static表示全局或者静态，用来修饰方法、变量或者代码块，Java中没有全局变量的概念。static的主要作用就是无需实例化即可引用。
 *   2.1 被static修饰的成员或者方法独立于该类的任何对象，不许实例化该类对象即可使用.
 *   2.2 不推荐使用实例化的对象去访问静态成员或者方法
 *   2.3 public修饰的static变量/方法相当于全局变量/方法,所有该类的实例共用同一个变量/方法.
 *   2.4 private修饰的static变量/方法可以在该类的静态成员方法或者静态代码块中使用，但是不能被其他类调用。
 *   2.5 static修饰的代码块，当Java虚拟机(JVM)加载类时会运行该代码块。
 *   2.6 ???:普通的内部类不能有static字段和数据
 *   
 * 3.static+final = 全局常量。变量只赋值一次，方法不可以被覆盖，不用实例化可以直接引用
 * 
 * 4.属性默认情况下是 default 不需要书写。 权限属性代表着访问权限
 *   public: Java语言中访问限制最宽的修饰符，一般称之为“公共的”。被其修饰的类、属性以及方法不仅可以跨类访问，而且允许跨包（package）访问。
 *   private: Java语言中对访问权限限制的最窄的修饰符，一般称之为“私有的”。被其修饰的类、属性以及方法只能被该类的对象访问，其子类不能访问，更不能允许跨包访问。
 *   protect: 介于public 和 private 之间的一种访问修饰符，一般称之为“保护形”。被其修饰的类、属性以及方法只能被类本身的方法及子类访问，即使子类在不同的包中也可以访问。
 *   default: 即不加任何访问修饰符，通常称为“默认访问模式“。该模式下，只允许在同一个包中进行访问。
 *   
 * @author saic
 *
 */
@SuppressWarnings("unused")
public class FinalAndStaticTest {
	
	final String tag = "FinalAndStatic";
	
	/**
	 * 1.1 TODO  
	 * The type SubFinalBook cannot subclass the final class FinalAndStaticTest.FinalBook
	 */
//	final class FinalBook {
	class FinalBook {
		
		public final void testMethods(){
			Log.v(tag, "Super Final Methods");
			testMethodsTwo("123");
		}
		
		private void testMethodsTwo(final String msg){
			Log.v(tag, "Super Private Methods");
		}
	}
	
	class SubFinalBook extends FinalBook{
		
		/**
		 * 1.2 可以调用 但是不能覆盖
		 * Cannot override the final method from FinalAndStaticTest.FinalBook
		 */
//		public final void testMethods(){
		public final void testMethodsSub(){
			Log.v(tag, "Sub Methods");
			this.testMethods();
			super.testMethods();	
			
			testMethodsTwo("123");
		};
		
		/**
		 * 1.2.2 private方法为私有方法,不会被继承也不会被覆盖,如果子类包含同样的方法，不会重写父类的方法的。
		 */
		private void testMethodsTwo(final String msg){
			
			/**
			 * 1.3 
			 * The final local variable a may already have been assigned
			 */
			final int a; //final空白
			a = 3;
//			a = 4;  //第二次赋值报错
//			b = 2;  //第二次赋值报错
//			str = "11";  //第二次赋值报错

			/**
			 * 1.4
			 * The final local variable msg cannot be assigned. It must be blank and not using a compound assignment
			 */
//			msg = "";
		};
			
		//实例变量
		final int b = 1;
		private final String str = "123";
				
	}
	
	class Book {
		/**
		 * 2.6 TODO;???:
		 * The field a cannot be declared static in a non-static inner type, unless initialized with a constant expression
		 */
//		static int a;
	}
	
	//静态变量
	/**
	 * 2.1
	 * 静态变量在内存中只有一个拷贝,只分配一次内存,在加载类的时候分配内存空间。
	 * 静态方法是被该类的所以实例共用，故方法内不能含有this.super等关键字，只能访问所属类的静态变量/方法，因为实例变量是跟某个特定对象关联的.
	 * static方法不能是抽象的abstract,必须被实现。
	 * 静态代码块是类中独立于类成员的静态语句块，可以有多个，位置随意，不在任何方法内，通常用来初始化静态成员。
	 */
	static int a;
	private static int b;
	public static int c;
	static {
		a = 1;
		/**
		 * 不能引用非静态
		 */
//		Log.v(tag, a + "");
		Log.v(b + "", a  + "");
	}
	
	
}
