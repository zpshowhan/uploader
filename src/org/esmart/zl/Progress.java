/**   
* @Title: Progress.java 
* @Package org.esmart.zl 
* @Description: TODO(用一句话描述该文件做什么) 
* @Company:方正
* @author zhaolei  
* @date 2017年8月10日 下午5:01:37 
* @version V1.0   
*/
package org.esmart.zl;

import java.util.Hashtable;

/** 
* @ClassName: Progress 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @Company:方正
* @author zhaolei 
* @version 1.0 2017年8月10日 下午5:01:37 
*/
public class Progress {

	//为了防止多用户并发，使用线程安全的Hashtable
	 private static Hashtable<Object, Object> table = new Hashtable<Object, Object>();
	 
	 public static void put(Object key, Object value){
	     table.put(key, value);
	 }
	
	 public static Object get(Object key){
	    return table.get(key);
	}
	
	public static Object remove(Object key){
	    return table.remove(key);
	}
}
