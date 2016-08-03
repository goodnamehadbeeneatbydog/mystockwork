import java.io.IOException;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CouponMain {

	public static void main(String[] args) throws IOException {

		String url = "http://a.jd.com/coupons.html?st=2&page=";//最大面额
		//String url ="http://a.jd.com/coupons.html?cat=15&page=";//其他 
		//String url ="http://a.jd.com/coupons.html?cat=13&page=";//食品饮料
		for(int i=1;i<101;i++){
			try{getCouponInfo(url,i,70);}catch(Exception ex){continue;}
		}
	}
	
	public static void getCouponInfo(String url,int i,int zhekou) throws IOException{
		Document doc = Jsoup.connect(url+i).get();
		Elements coupon = doc.select(".q-type");
		//Elements limitPrice = doc.select(".limit .ftx-06");
		int num=0;
		
		for(Element e :coupon){
			num++;
			String pricestr = e.select(".num").first().text();
			String limitstr = e.select(".ftx-06").first().text();
			limitstr = Pattern.compile("[^0-9]").matcher(limitstr).replaceAll("").toString();
			int dazhe =0;
			double price = Double.parseDouble(pricestr);
			double limit = Double.parseDouble(limitstr);
			try{
				dazhe = ((int)(price*100/limit));
			}catch(Exception ex){continue;}
			String isDone = e.parent().select(".q-opbtns a").first().text();
			if(dazhe>=zhekou)
			System.out.println(price +" : "+limit+" and dazhe ==> "+dazhe+"% 所处位置 Page "+i+"第 "+(num%3==0?(num/3):((num/3)+1))+" 行，第 "+(num%3==0?3:num%3));
			
		}
	}

}
