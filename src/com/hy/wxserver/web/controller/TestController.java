package com.hy.wxserver.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hy.wxserver.web.pojo.Message;

/**
 * 核心请求分发控制器
 * @author heshaohua E-mail:386184368@qq.com
 * @version 创建时间：2014-4-1 下午05:20:26 
 */

@Controller
@RequestMapping("test")
public class TestController {
	
	@RequestMapping(value = "jsp")
	public String jsp() {
		return "index";
	}
	
	@ResponseBody
	@RequestMapping(value = "json")
	public String json() {
		return new Message().toString();
	}
}
