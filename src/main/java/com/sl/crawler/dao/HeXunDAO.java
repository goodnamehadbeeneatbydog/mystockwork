package com.sl.crawler.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeXunDAO {

	private static Logger logger = LoggerFactory.getLogger(HeXunDAO.class);
	public void deleteHeXunDatas(){
		Connection con = DBManager.getInstance().getConnection();
		QueryRunner runner = new QueryRunner(true);
		try {
			runner.update(con, "truncate table stock.money_in_out_his");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void insert(List<Map<String, String>> stocks, String date) {
		Connection con = DBManager.getInstance().getConnection();

		QueryRunner runner = new QueryRunner(true);

		Object[][] insertObj = new Object[stocks.size()][];
		for (int i=0;i<stocks.size();i++) {
			Map<String, String> stock = stocks.get(i);
			Object[] objAttr = new Object[10];
			objAttr[0] = stock.get("data0");
			String stockName = stock.get("data1");
			objAttr[1] = stockName.substring(0,stockName.indexOf("("));
			objAttr[2] = stockName.substring(stockName.indexOf("(") + 1, stockName.indexOf(")"));
			objAttr[3] = checkDouble(stock.get("data4"));
			objAttr[4] = checkDouble(stock.get("data5"));
			objAttr[5] = stock.get("data6").replaceAll("%", "");
			objAttr[6] = stock.get("data7").replaceAll("%", "");
			objAttr[7] = stock.get("data9");
			objAttr[8] = stock.get("data10").replaceAll("%", "");
			objAttr[9] = date;
			insertObj[i]  = objAttr;
			
		}

		try {
			runner.batch(con,
					"insert into stock.money_in_out_his (sn,stock_name,stock_code,all_money_in,org_money_in,org_all_rate,money_in_rate,inc_day,org_deal_rate,date) values(?,?,?,?,?,?,?,?,?,?)",
					insertObj);
			logger.info("成功插入  === "+stocks.size() +" 条记录");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally{
			//DBManager.getInstance().closeConn();
		}
	}
	
	public void insertRealTime(List<Map<String, String>> stocks, String date) {
		Connection con = DBManager.getInstance().getConnection();

		QueryRunner runner = new QueryRunner(true);

		Object[][] insertObj = new Object[stocks.size()][];
		for (int i=0;i<stocks.size();i++) {
			Map<String, String> stock = stocks.get(i);
			Object[] objAttr = new Object[10];
			objAttr[0] = stock.get("data0");
			String stockName = stock.get("data1");
			objAttr[1] = stockName.substring(0,stockName.indexOf("("));
			objAttr[2] = stockName.substring(stockName.indexOf("(") + 1, stockName.indexOf(")"));
			objAttr[3] = checkDouble(stock.get("data4"));
			objAttr[4] = checkDouble(stock.get("data5"));
			objAttr[5] = stock.get("data6").replaceAll("%", "");
			objAttr[6] = stock.get("data7").replaceAll("%", "");
			objAttr[7] = stock.get("data9");
			objAttr[8] = stock.get("data10").replaceAll("%", "");
			objAttr[9] = date;
			insertObj[i]  = objAttr;
			
		}

		try {
			runner.batch(con,
					"insert into stock.money_in_out_his_realtime (sn,stock_name,stock_code,all_money_in,org_money_in,org_all_rate,money_in_rate,inc_day,org_deal_rate,date) values(?,?,?,?,?,?,?,?,?,?)",
					insertObj);
			logger.info("成功插入  === "+stocks.size() +" 条记录");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally{
			//DBManager.getInstance().closeConn();
		}
	}
	public void closeConn(){
		DBManager.getInstance().closeConn();
	}
	
	private double checkDouble(String db){
		if(db==null || "".equals(db))
			return 0.00;
		
		return Double.parseDouble(db);
	}
}
