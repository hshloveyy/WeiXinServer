package com.infolion.platform.core.web;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;

public class EnhanceMultiActionController extends BaseMultiActionController{
	
	
	public Map<String, String> extractFR(HttpServletRequest req) {
		try {
			String wait = java.net.URLDecoder.decode(req.getQueryString(),
					"UTF-8");
			String ar1[] = wait.split("&");
			Map<String, String> map = new HashMap<String, String>();
			String str = "";
			String ar2[];
			for (int i = 0; i < ar1.length; i++) {
				str = ar1[i];
				ar2 = str.split("=");
				if (ar2.length == 1)
					continue;
				map.put(ar2[0], ar2[1]);
			}
			return map;
		} catch (UnsupportedEncodingException e) {
		}
		return Collections.EMPTY_MAP;
	}
	
	public String extractFR(HttpServletRequest req, String parm) {
		try {
			String wait = java.net.URLDecoder.decode(req.getQueryString(),
					"UTF-8");
			int pos = wait.indexOf(parm) + parm.length() + 1;
			wait = wait.substring(pos);
			pos = wait.indexOf("&");
			if (pos != -1) {
				wait = wait.substring(0, pos);
			}
			return wait;
		} catch (UnsupportedEncodingException e) {
		}
		return null;
	}
	
	public UserContext getUserContext(){
		return UserContextHolder.getLocalUserContext().getUserContext();
	}

}
