package com.sl.stock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kevinsawicki.http.HttpRequest;

public class StockMain {

	private static Logger logger = LoggerFactory.getLogger(StockMain.class);
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String response = HttpRequest.get(
				"http://hq.sinajs.cn/?list=sh000001,sz000882").body();
		String[] ress = response.replaceAll("\n", "").split(";",0);
		System.out.println(ress.length);
		for (String str : ress) {
			logger.debug("before : "+str.lastIndexOf("\""));
			logger.debug("before : "+(str.length()-1));
			logger.debug("str : "+str);
			str = str.substring(str.indexOf("\"") + 1, str.lastIndexOf("\""));
			//str = str.substring(str.indexOf("\"") + 1, str.length()-1);
			logger.debug("end : "+str);

		}
	}

}
