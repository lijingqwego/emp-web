package com.kaisn.utils;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * 加载属性文件工具类
 *
 * @author lijing
 *
 */
public class PropertiesUtil {

	private static Properties properties = null;

	private static Logger logger = Logger.getLogger(PropertiesUtil.class);

	// 文件类型
	public static final int xmlType = 1;//xml
	public static final int defaultType = 0;//key=value

	/**
	 * 实例化对象（单例模式）
	 */
	private static void init() {
		if (properties == null) {
			synchronized (Properties.class) {
				if (properties == null) {
					properties = new Properties();
				}
			}
		}
	}

	/**
	 * 获取Properties实例，并载入文件
	 *
	 * @param fileName 文件名称
	 * @param type 文件类型
	 * @return
	 */
	public static Properties getProperties(String fileName, int type) {
		InputStream inputStream = null;
		try {
			init();
			inputStream = PropertiesUtil.class.getResourceAsStream(fileName);
			switch (type) {
				case xmlType:
					properties.loadFromXML(inputStream);
					break;
				default:
					properties.load(inputStream);
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error("close stream failure.",e);
				}
			}
		}
		return properties;
	}

	/**
	 * 存入数据
	 *
	 * @param proMap 传入参数
	 * @param fileName 文件名称
	 * @param type 文件类型
	 * @param comments 文件描述
	 */
	public static void setPropertys(Map<String, String> proMap, String fileName, int type, String comments) {
		if (proMap != null) {
			init();
			FileOutputStream outputStream = null;
			try {
				outputStream = new FileOutputStream(fileName);
				for (Entry<String, String> pro : proMap.entrySet()) {
					properties.setProperty(pro.getKey(), pro.getValue());
				}
				switch (type) {
					case xmlType:
						properties.storeToXML(outputStream, comments);
						break;
					default:
						properties.store(outputStream, comments);
						break;
				}
			} catch (Exception e) {
				logger.error("load stream failure.",e);
			} finally {
				if (outputStream != null) {
					try {
						outputStream.close();
					} catch (IOException e) {
						logger.error("close stream failure.",e);
					}
				}
			}
		}
	}

	/**
	 * 清空对象
	 */
	public static void clear() {
		properties = null;
	}

}
