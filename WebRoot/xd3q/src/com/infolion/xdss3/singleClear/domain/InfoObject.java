package com.infolion.xdss3.singleClear.domain;

import java.util.List;

import com.infolion.xdss3.voucher.domain.Voucher;

/**
 * * <pre>
 * (客户全面清帐(InfoObject),信息类
 * </pre>
 * 
 * @author zhongzh
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class InfoObject implements IInfo{

//	返回一些信息（包括错误信息，提示信息）
	private String info;
//	返回错误代码
	private String code;
//	是否正确
	private boolean isRight;

	/**
	 * 返回一些信息（包括错误信息，提示信息）
	 * @return
	 */
	public String getInfo() {
		return info;
	}
	/***
	 * 返回一些信息（包括错误信息，提示信息）
	 * @param info
	 */
	public void setInfo(String info) {
		this.info = info;
	}
	/**
	 * 返回错误代码
	 * @return
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 返回错误代码
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/***
	 * 是否正确
	 * @return
	 */
	public boolean isRight() {
		return isRight;
	}
	/****
	 * 是否正确
	 * @param isRight
	 */
	public void setRight(boolean isRight) {
		this.isRight = isRight;
	}	
}
