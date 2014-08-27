package com.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 读取spring的属性文件
 * @author Administrator
 *
 */
public class SpringProperties {

	private static Properties p = null;
	
	static {
		try {
			p = new Properties();
			p.load(new BufferedInputStream(SpringProperties.class.
					getClassLoader().getResourceAsStream("spring.properties")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Properties getProperties(){
		return p;
	}
}
