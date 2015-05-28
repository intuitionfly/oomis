package com.oomis.pojo;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.cglib.beans.BeanGenerator;
import org.springframework.cglib.beans.BeanMap;

public class DynamicBean {

	/**
	 * Entity Object
	 */
	private Object object = null;

	/**
	 * Property map
	 */
	private BeanMap beanMap = null;
	
	public DynamicBean() {
		super();
	}

	public DynamicBean(Map<String, String> propertyMap) {
		this.object = generateBean(propertyMap);
		this.beanMap = BeanMap.create(this.object);
	}

	/**
	 * set the value to the bean
	 * 
	 * @param property
	 * @param value
	 */
	public void setValue(String property, Object value) {
		beanMap.put(property, value);
	}

	/**
	 * get the value from the property name
	 * 
	 * @param property
	 * @return value
	 */
	public Object getValue(String property) {
		return beanMap.get(property);
	}

	/**
	 * get the bean object
	 * 
	 * @return
	 */
	public Object getObject() {
		return this.object;
	}

	private Object generateBean(Map<String, String> propertyMap) {
		BeanGenerator generator = new BeanGenerator();
		Set<String> keySet = propertyMap.keySet();
		for (Iterator<String> i = keySet.iterator(); i.hasNext();) {
			String key = (String) i.next();
			try {
				generator.addProperty(key, Class.forName(propertyMap.get(key)));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return generator.create();
	}

}