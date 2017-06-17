package com.hy.turing.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hy.turing.request.RequestEntity;
import com.hy.turing.util.Aes;
import com.hy.turing.util.Md5;
import com.hy.turing.util.PostServer;

/**
 * 加密请求测试类
 * 
 * @author 图灵机器人
 *
 */
public class TuringUtils {
	private static final String TURING_OPENAPI_API = "http://www.tuling123.com/openapi/api";
	// 图灵网站上的secret
	private final static String secret = "62e25be2eeb1d008";
	// 图灵网站上的apiKey
	private final static String apiKey = "2abf8ec29a0f462db089ba3371bc8989";

	public static void main(String[] args) {
		System.out.println(TuringUtils.askToTuring("", "15084929682"));
	}
	
	public static String askToTuring(String info, String userId) {

		RequestEntity requestEntity = new RequestEntity(apiKey, info, null, userId);
		// 待加密的json数据
		String data = JSON.toJSONString(requestEntity);
		// 获取时间戳
		String timestamp = String.valueOf(System.currentTimeMillis());

		// 生成密钥
		String keyParam = secret + timestamp + apiKey;
		String key = Md5.MD5(keyParam);

		// 加密
		Aes mc = new Aes(key);
		data = mc.encrypt(data);

		// 封装请求参数
		JSONObject json = new JSONObject();
		json.put("key", apiKey);
		json.put("timestamp", timestamp);
		json.put("data", data);
		// 请求图灵api
		String result = PostServer.SendPost(json.toString(), TURING_OPENAPI_API);
		return result;
	}

}