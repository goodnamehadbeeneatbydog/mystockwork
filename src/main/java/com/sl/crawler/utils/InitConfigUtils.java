package com.sl.crawler.utils;

import java.io.IOException;
import java.util.Properties;

public class InitConfigUtils {

	public static int getCrawlerDay() {
		Properties pro = new Properties();
		try {
			pro.load(InitConfigUtils.class.getResourceAsStream("/stock_init.properties"));
			return Integer.parseInt(pro.getProperty("init_day"));
		} catch (IOException e) {
		}
		return 120;
	}
}
