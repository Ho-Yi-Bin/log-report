
package org.yfr.manager;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * application context manager .<br>
 * initializing an application context <b>(ApplicationContext)</b> in it .<br>
 * holding this object with singleton pattern .<br>
 * the other object can via the getBean method to get bean in application context .
 * 
 * <p>setup time <b>Apr 9, 2015 1:56:22 PM .</b></p>
 *
 * @author Vincent Huang
 */
public enum ApplicationContextManager {

	/** instance . */
	INSTANCE;

	/** application context . */
	private ApplicationContext context = new FileSystemXmlApplicationContext("conf/applicationContext.xml");

	/**
	 * get bean in application context with name .
	 * 
	 * @param name - the name <b>(String)</b> to set
	 * 
	 * @return bean
	 */
	public Object getBean(String name) {
		return context.getBean(name);
	}

	/**
	 * get bean in application context with name and specific class type .
	 * 
	 * @param name - the name <b>(String)</b> to set
	 * @param classType - the class type <b>(Class&lt;T&gt;)</b> to set
	 * 
	 * @return bean with specific class type
	 */
	public <T> T getBean(String name, Class<T> classType) {
		return context.getBean(name, classType);
	}

}
