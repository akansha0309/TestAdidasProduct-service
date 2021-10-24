package com.adidas.helper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
	private static ConfigManager manager;

	private static final Properties PROPS = new Properties();

	private ConfigManager() {
		try  {
			FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\config.properties");
			PROPS.load(ip);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ConfigManager getInstance() {
		if (manager == null) {
			synchronized (ConfigManager.class) {
				manager = new ConfigManager();
			}
		}
		return manager;
	}

	public String getString(String key) {
		return System.getProperty(key, PROPS.getProperty(key));
	}
	
}
