package com.kaisn.druid;

import org.apache.commons.io.IOUtils;

import java.io.*;

public class ResourceReaderUtil {

	private static final String RESOURCE_URL = "src/main/resources/";

	public static String loadData(String resource) {
		StringBuffer buffer = new StringBuffer();
		FileReader fileReader = null;
		BufferedReader bufr=null;
		try {
			fileReader = new FileReader(RESOURCE_URL + resource);
			bufr = new BufferedReader(fileReader);
			String line;
			while ((line = bufr.readLine()) != null) {
				buffer.append(line);
				buffer.append("\r\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			IOUtils.closeQuietly(bufr);
			IOUtils.closeQuietly(fileReader);
		}
		return buffer.toString();
	}
}
