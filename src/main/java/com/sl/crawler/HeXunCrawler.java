package com.sl.crawler;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sl.crawler.dao.HeXunDAO;
import com.sl.crawler.utils.InitConfigUtils;

public class HeXunCrawler {

	public static void main(String[] args) throws IOException {
		
		HeXunCrawler crawler = new HeXunCrawler();
		
		crawler.initHeXunData(false);
		//getDay();
		/*HeXunDAO dao = new HeXunDAO();
		HeXunCrawler crawler= new HeXunCrawler();
		 dao.insert(crawler.crawlerHeXunData("2016-08-16",1), "2016-08-16");*/
	}

	
	public List<Map<String,String>> crawlerHeXunData(String day,int page) throws ClientProtocolException, IOException{
		String url = "http://vol.stock.hexun.com/Data/Stock/StkRankDetail.ashx?date="+day+"&groupby=Z01&addby=3&plate=1&count=20&stateType=down&titType=4&page="+page+"&callback=hx_json14713622024116381875";
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(url);
		get.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0");

		RequestConfig config = RequestConfig.custom().setConnectTimeout(15000).setConnectionRequestTimeout(10000)
				.setSocketTimeout(5000).build();
		get.setConfig(config);
		HttpResponse respone = client.execute(get);
		
		String result = EntityUtils.toString(respone.getEntity(),"utf-8");
		
		System.out.println(result);
		result = result.replaceAll("<\\w+\\s+\\w+=\\\"\\w+(\\W+|\\w+)\\w+\\\">|</\\w+>", "");
		System.out.println(result);
		result = result.substring(result.indexOf("(")+1, result.length()-1);
		System.out.println(result);
		
		Map<String,Object> mapResult = convertToMap(result);
		
		//Set<String> keys =mapResult.keySet();
		/*for(String key :keys){
			System.out.println(mapResult.get(key));
		}*/
		@SuppressWarnings("unchecked")
		List<Map<String,String>> values = (List<Map<String, String>>) mapResult.get("list");
		
		return values;
	}
	
	/**
	 * 
	 */
	public void initHeXunData(boolean delete){
		int crawlerDay = InitConfigUtils.getCrawlerDay();
		Calendar cal = Calendar.getInstance();
		HeXunDAO dao = new HeXunDAO();
		
		if(delete)
		dao.deleteHeXunDatas();
		
		List<Map<String,String>> insertList = new ArrayList<Map<String,String>>();
		//crawlerDay初始化天数记录，当爬取的日期有最新资金数据时才减1
		while(crawlerDay>0){
			String day = getCrawlerDay(cal);
			int page=1;
			boolean reduce = false;
			//爬取XXXX-XX-XX日的资金数据，循环到结尾break
			while(true){
				try {
					List<Map<String,String>>  stocks = crawlerHeXunData(day,page);
					insertList.addAll(stocks);
					//判断此日期是否为正常股市开市日期，非开市日期资金数据为空
					if(page ==1 && stocks!=null && stocks.size()>0){
						reduce = true;
					}
					//判断是否爬取到当然数据的最后一页
					if (stocks==null || stocks.size()<=0)
						break;
					
					//自动翻页爬取
					page++;
				} catch (Exception e) {
					page++;
					continue;
					//e.printStackTrace();
				} 
			}
			
			if(reduce){
				dao.insert(insertList, day);
				insertList.clear();
			crawlerDay--;
			}
			
		}
		dao.closeConn();
	}
	public static String getCrawlerDay(Calendar cal){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String res = format.format(cal.getTime());
		cal.add(Calendar.DATE, -1);
		return res;
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
