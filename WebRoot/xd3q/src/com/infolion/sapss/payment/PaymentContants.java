package com.infolion.sapss.payment;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class PaymentContants {
	
	
	public static String DEPT_ID_METAL = "DE6F0569-8623-4458-925D-03AB97AA485B";//金属部部门ID
	public static String BANK_DEPT_ID_METAL="310066399018010033466";//上海信达特殊帐号
	
	public static String round(String value,int mark){
		return new BigDecimal(value).setScale(mark, BigDecimal.ROUND_HALF_UP).toString();
		//return "";
	}
	
	public static void main(String[] args){
		//System.out.println("".replaceAll(" ", "").replaceAll(",", ""));
		//System.out.println(PaymentContants.round("34.2367", 3));
		//System.out.println(PaymentContants.micString("212121234.9225",".000"));
		System.out.println(PaymentContants.changeToBig(45454.96));
	}
	
	public static String changeToBig(double value){

		char[] hunit={'拾','佰','仟'};                                    //段内位置表示
		char[] vunit={'万','亿'};                                         //段名表示
		char[] digit={'零','壹','贰','叁','肆','伍','陆','柒','捌','玖'}; //数字表示
		long midVal = (BigDecimal.valueOf(value).multiply(new BigDecimal(100))).longValue();                                  //转化成整形
		String valStr=String.valueOf(midVal);                             //转化成字符串
		String head=valStr.substring(0,valStr.length()-2);                //取整数部分
		String rail=valStr.substring(valStr.length()-2);                  //取小数部分

		String prefix="";                                                 //整数部分转化的结果
		String suffix="";                                                 //小数部分转化的结果
		//处理小数点后面的数
		if(rail.equals("00")){                                           //如果小数部分为0
			suffix="整";
		} else{
			suffix=digit[rail.charAt(0)-'0']+"角"+digit[rail.charAt(1)-'0']+"分"; //否则把角分转化出来
		}
		//处理小数点前面的数
		char[] chDig=head.toCharArray();                   //把整数部分转化成字符数组
		boolean preZero=false;                             //标志当前位的上一位是否为有效0位（如万位的0对千位无效）
		byte zeroSerNum = 0;                               //连续出现0的次数
		for(int i=0;i<chDig.length;i++){                   //循环处理每个数字
			int idx=(chDig.length-i-1)%4;                    //取段内位置
			int vidx=(chDig.length-i-1)/4;                   //取段位置
			if(chDig[i]=='0'){                               //如果当前字符是0
				preZero=true;
				zeroSerNum++;                                  //连续0次数递增
				if(idx==0 && vidx >0 &&zeroSerNum < 4){
					prefix += vunit[vidx-1];
					preZero=false;                                //不管上一位是否为0，置为无效0位
				}
			}else{
				zeroSerNum = 0;                                 //连续0次数清零
				if(preZero){                                   //上一位为有效0位
					prefix+=digit[0];                                //只有在这地方用到'零'
					preZero=false;
				}
				prefix+=digit[chDig[i]-'0'];                    //转化该数字表示
				if(idx > 0) prefix += hunit[idx-1];                  
				if(idx==0 && vidx>0){
					prefix+=vunit[vidx-1];                      //段结束位置应该加上段名如万,亿
				}
			}
		}

		if(prefix.length() > 0) prefix += '元';                               //如果整数部分存在,则有圆的字样
		return prefix+suffix;                                                            //返回正确表示
	}
	
	public static String micString(String value,String mark){ 
		java.text.DecimalFormat dm = new DecimalFormat("###,###"+mark);
		return dm.format(Double.parseDouble(value));
	}
	
	public static String htmlInputMic(String value,String mark,String fileName){
		String fileNameTemp = fileName+"_temp";
		StringBuffer b = new StringBuffer("");
		b.append("<input type=text id=\""+fileNameTemp+"\" name=\""+fileNameTemp+"\" value=\""+micString(value, mark)+"\" readonly=readonly></input>");
		b.append("<input type=hidden id=\""+fileName+"\" name="+fileName+" value=\""+value+"\"></input>");
		return b.toString();
	}
	

	
}
