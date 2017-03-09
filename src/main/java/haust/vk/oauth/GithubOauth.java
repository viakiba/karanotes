package haust.vk.oauth;

import haust.vk.utils.NetReqUtil;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("oauth")
public class GithubOauth {
	private String client_id = "bb6d0dcfc6a909e2ccbf";
	private String client_secret = "7932c255b62ff6bc3a1bb7a913252907fbf07e01";
	private String accesstokenurl = "https://github.com/login/oauth/access_token";
	private String userinfourl = "https://api.github.com/user?access_token=";
	
	@Resource
	private NetReqUtil netReqUtil;
	
	/**
	 * 接口移动端   github 登录
	 * 
	 * 拼接链接  https://github.com/login/oauth/authorize?client_id=bb6d0dcfc6a909e2ccbf&redirect_uri=http://fb4dd5d5.ngrok.io/KaraNotes/oauth/login&scope=user:email
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value="/login")
	public @ResponseBody Map MGithubLogin(String code){
		String email = NetMethod(code);
		Map map = new HashMap();
		map.put("test", "haha");
		return map;
	}
	
	/**
	 * 接口web端  github 登录
	 */
	public String WGithubLogin(@RequestBody String json){
		
		
		return "redirect:/ toList ";
	}
	
	//公共方法
	public String NetMethod(String code){
		String params = "client_id="+client_id+"&client_secret="+client_secret+"&code="+code+"&redirect_uri="+"http://fb4dd5d5.ngrok.io/KaraNotes/oauth/accesstoken";
		String email = null;
		try {
			String str = netReqUtil.loadJson(accesstokenurl+"?"+params);
			System.out.println(str);
			Map access = JSON.parseObject(str, Map.class);
			String access_token = (String) access.get("access_token");
			str = netReqUtil.loadJson(userinfourl+access_token);
			Map userinfo = JSON.parseObject(str, Map.class);
			email = (String) userinfo.get("email");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return email;
	}
}
