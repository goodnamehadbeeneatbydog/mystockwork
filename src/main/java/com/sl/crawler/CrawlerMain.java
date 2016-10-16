package com.sl.crawler;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class CrawlerMain {

	public static void main(String[] args) throws IOException {
		String url = "https://rm93.com/thread.php?fid=88&page=1";
		Document doc = Jsoup.connect(url)
				.header("Cookie",
						"92172_lastpos=F88; 92172_ol_offset=69840; CNZZDATA77223=cnzz_eid%3D870430183-1472905886-%26ntime%3D1473906661"+
"; 92172_cloudClientUid=49504956; 92172_ckquestion=BVZUBVgIBAoJAW05UQAJAVRcAVRWBAQNVgkNUl1VWAYKXFcJAlpWD1ZYAFw"+
"; 92172_threadlog=%2C88%2C; 92172_readlog=%2C817996%2C817873%2C817622%2C817801%2C642261%2C816824%2C817130"+
"%2C818006%2C817877%2C; PHPSESSID=givh7pfnjue2u5c416743bd870; 92172_winduser=BlNQAlkLPQ8MAVFTBA0NAAINVwBXU1FfUl4MVQYGVVEACQRcDFRUPwcBBwBaUlIBAlc"+
"; 92172_ck_info=%2F%09.rm93.com; 92172_lastvisit=0%091473910216%09%2Fthread.php%3Ffid88%26page1; 92172_ci"+
"=thread%091473910216%09%0988")
				.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0")
				.timeout(15000).get();
		System.out.println(doc);
		
	}

	public static void httpTest() throws ClientProtocolException, IOException {
		String url = "https://rm93.com/thread.php?fid=88";
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(url);
		get.addHeader("Cookie",
				"PHPSESSID=0icrih8q7e2ssldg0r9lu7rbl2; 92172_lastpos=F88; 92172_ol_offset=59752; CNZZDATA77223=cnzz_eid%3D870430183-1472905886-%26ntime%3D1472911287; 92172_cloudClientUid=49504956; 92172_ckquestion=BVZUBFgIBQoLCW05AAcIClkLUQBTVFFfUQoJVFcBWQBZAVcABAAGAlgMAl0; 92172_winduser=BlNQAlkLPQ8MAVFTBA0NAAINVwBXU1FfUl4MVQYGVVEACQRcDFRUPw; 92172_ck_info=%2F%09.rm93.com; 92172_lastvisit=927%091472912163%09%2Fthread.php%3Ffid88; 92172_threadlog=%2C88%2C; 92172_ci=thread%091472912163%09%0988");
		get.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0");

		RequestConfig config = RequestConfig.custom().setConnectTimeout(15000).setConnectionRequestTimeout(10000)
				.setSocketTimeout(5000).build();
		get.setConfig(config);
		HttpResponse respone = client.execute(get);
		
		System.out.println(new String(EntityUtils.toString(respone.getEntity()).getBytes("ISO-8859-1"), "GBK")
				.replace("\r\n", ""));

	}
}
