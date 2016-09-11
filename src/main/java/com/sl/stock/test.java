package com.sl.stock;

/**
 * Created by Administrator on 2016/8/31.
 */
public class test {
    private static test ourInstance = new test();

    public static test getInstance() {
        return ourInstance;
    }

    private test() {
    }
    
    public static void main(String[] args){
    	System.out.println("2016-09-03".compareTo("2016-09-09"));
    }
}
