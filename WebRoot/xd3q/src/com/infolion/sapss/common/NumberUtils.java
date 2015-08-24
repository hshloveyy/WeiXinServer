package com.infolion.sapss.common;

import java.math.BigDecimal;

public class NumberUtils {
	
	public static String round(double v,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
                "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(v);
        BigDecimal one = new BigDecimal("1");
        return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).toString();
    }
	
	public static void main(String[] args){
		System.out.println((!"2".equals("4")&&!"5".equals("4") && !"6".equals("4")&&!"12".equals("4")));
		String va = "中灿,大日,华宇能源,润乾,欧美龙,杰信,环亚鞋塑,沙骑士,鼎发体育,桑秀贸易,明贺,富丽,大唐商贸,鑫白羽,杭华实业,利保嘉,紫兴纸,鸿悦,巨溪,长天";
	    //String i[] = va.split(",");
		//select * from ygetlifnr where name like '%%' union all
		//select * from ygetlifnr where name like '%%' union all
	    for(String i : va.split(",")){
	    	//System.out.println("select * from ygetlifnr where name1 like '%"+i+"%' union all");
	    }
	}

}
