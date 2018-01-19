package com.thermofisher.sts.demo;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class ConfigUtil {
	private static final String CFG_FILE = "classpath:config.prop";
	
	public ConfigUtil() {
		props = new HashMap<>();
	}
	
	public void init() throws IOException {
		Properties p = new Properties();
		InputStream in = null;
		try {
			in = new URL(CFG_FILE).openStream();
			p.load(in);
		} finally {
			if (in != null) {
				in.close();
			}
		}
		
		for (Entry<Object, Object> es: p.entrySet()) {
			props.put((String) es.getKey(), (String) es.getValue()); 
		}
	}
	
	public String getValue(String key) {
		return props == null ? null : props.get(key);
	}
	
	private Map<String, String> props;
}
