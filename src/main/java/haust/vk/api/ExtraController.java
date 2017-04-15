package haust.vk.api;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ExtraController {
	
	@RequestMapping(value="/")
	public @ResponseBody Map CrosHelpResp(HttpServletRequest req, HttpServletResponse resp){
		Enumeration<String> headerNames = req.getHeaderNames();
		while(headerNames.hasMoreElements()){
			System.out.println("*************************");
			System.out.println(headerNames.nextElement());
			//System.out.println(req.getHeader(headerNames.nextElement()));
		}
		System.out.println("=============================");
		resp.addHeader("Access-Control-Allow-Origin", "karanotes.viakiba.cn");
		resp.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT");
		//resp.addHeader("Access-Control-Allow-Headers", "GET, POST, PUT");
		resp.addHeader("Access-Control-Allow-Headers", "GET, POST, PUT");
		resp.addHeader("Access-Control-Allow-Credentials", "true");
		resp.addHeader("Access-Control-Max-Age", "1800");
		System.out.println("=============================");
		Map map = new HashMap<>();
		map.put("1", "响应跨域头信息");
		return map;
	}
}
