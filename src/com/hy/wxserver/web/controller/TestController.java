package com.hy.wxserver.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ��������ַ�������
 * @author heshaohua E-mail:386184368@qq.com
 * @version ����ʱ�䣺2014-4-1 ����05:20:26 
 */

@Controller
public class TestController {
	
	@RequestMapping(value = "/test")
	public String test() {
		return "index";
	}
	
}
