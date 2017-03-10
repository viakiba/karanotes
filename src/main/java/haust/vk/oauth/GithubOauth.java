package haust.vk.oauth;

import haust.vk.service.UserinfoService;
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
	//online
	/*private String client_id = "e12e95e867e11d74e4c1";
	private String client_secret = "9571e2f50a58b42fa66f2e34285810fc72a79826";*/
	//test
	private String client_id = "bb6d0dcfc6a909e2ccbf";
	private String client_secret = "7932c255b62ff6bc3a1bb7a913252907fbf07e01";
	
	private String accesstokenurl = "https://github.com/login/oauth/access_token";
	private String userinfourl = "https://api.github.com/user?access_token=";
	@Resource
	private UserinfoService userinfoServiceImpl;
	
	@Resource
	private NetReqUtil netReqUtil;
	
	/**
	 * github 登录/注册
	 * 
	 * 拼接链接  https://github.com/login/oauth/authorize?client_id=bb6d0dcfc6a909e2ccbf&redirect_uri=http://2d42ba82.ngrok.io/KaraNotes/oauth/login&scope=user:email
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value="/login")
	public @ResponseBody Map MGithubLogin(String code){
		Map map = NetMethod(code);
		String email = null;
		String id = null;
		//
		Integer messcode = (Integer) map.get("messcode");
		if(messcode == 1){
			//邮箱拿到
			email = (String) map.get("email");
			id = (String) map.get("id");
			//唯一性检查
			try {
				map = userinfoServiceImpl.selectByEmail(email);
				Integer success = (Integer) map.get("success");
				if(success == 1){
					//有存在  则返回  messcode = 4
					
					
				}else{
					//不存在  模拟用户注册操作 并设置 githubid  messcode = 1
					
					
					//返回密码/及其用户名
					
					
					
				}
			} catch (Exception e) {
				e.printStackTrace();
				map.clear();
				//系统异常  messcode = 3
				
				
				
			}
		}else{
			//github 响应异常  messcode = 2
			return map;	
		}
		return map;
	}
	
	
	
	
	/**
	 * 公共方法
	 * @param code
	 * @return
	 */
	public Map NetMethod(String code) {
		String params = "client_id="+client_id+"&client_secret="+client_secret+"&code="+code+"&redirect_uri="+"http://2d42ba82.ngrok.io/KaraNotes/oauth/accesstoken";
		String email = null;
		String id = null;
		Map map = new HashMap();
		try {
			String str = netReqUtil.loadJson(accesstokenurl+"?"+params);
			System.out.println(str);
			Map access = JSON.parseObject(str, Map.class);
			String access_token = (String) access.get("access_token");
			str = netReqUtil.loadJson(userinfourl+access_token);
			System.out.println(str);
			Map userinfo = JSON.parseObject(str, Map.class);
			email = (String) userinfo.get("email");
			id = (String) userinfo.get("id");
			map.put("email", email);
			map.put("id", email);
			map.put("messcode", 1);
		} catch (Exception e) {
			e.printStackTrace();
			map.clear();
			map.put("messcode", 2);
		}
		return map;
	}
}
