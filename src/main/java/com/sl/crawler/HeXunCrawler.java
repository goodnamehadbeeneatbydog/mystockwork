package com.sl.crawler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HeXunCrawler {

	public static void main(String[] args) throws IOException {
		String url = "http://vol.stock.hexun.com/Data/Stock/StkRankDetail.ashx?date=2016-08-16&groupby=Z01&addby=3&plate=1&count=20&stateType=down&titType=4&page=2&callback=hx_json14713622024116381875";
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(url);
		get.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0");

		RequestConfig config = RequestConfig.custom().setConnectTimeout(15000).setConnectionRequestTimeout(10000)
				.setSocketTimeout(5000).build();
		get.setConfig(config);
		HttpResponse respone = client.execute(get);
		
		String result = EntityUtils.toString(respone.getEntity(),"utf-8");
		
		System.out.println(result);
		result = result.replaceAll("<\\w+\\s+\\w+=\\\"\\w+\\\">|</\\w+>", "");
		System.out.println(result);
		result = result.substring(result.indexOf("(")+1, result.length()-1);
		System.out.println(result);
		
		Map<String,Object> mapResult = convertToMap(result);
		
		Set<String> keys =mapResult.keySet();
		/*for(String key :keys){
			System.out.println(mapResult.get(key));
		}*/
		@SuppressWarnings("unchecked")
		List<Map<String,String>> values = (List<Map<String, String>>) mapResult.get("list");
		values.stream().forEach(value->System.out.println(value));
		//keys.stream().forEach(key->System.out.println(key+" : "+mapResult.get(key)));
	}

	public static Map<String,Object> convertToMap(String stockStr){
		
		Map<String,Object> result = new HashMap<String,Object>();
		ObjectMapper objMapper = new ObjectMapper();
		objMapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		//objMapper.configure(Feature.ALLOW_NON_NUMERIC_NUMBERS,true);
		//objMapper.configure(Feature.ALLOW_MISSING_VALUES, true);
		//objMapper.configure(Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
		//objMapper.configure(Feature.ALLOW_NUMERIC_LEADING_ZEROS, true);
		objMapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
		//objMapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		try {
			result = objMapper.readValue(stockStr, new TypeReference<Map<String,Object>>(){
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return result;
	}
}
