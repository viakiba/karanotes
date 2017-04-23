package haust.vk.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import haust.vk.entity.Userinfo;
import haust.vk.exception.GlobalErrorInfoException;
import haust.vk.exception.code.JsonKeyValueErrorInfoEnum;
import haust.vk.exception.code.NodescribeErrorInfoEnum;
import haust.vk.exception.code.SuccessMessageCodeInfoEnum;
import haust.vk.result.ResultBody;
import haust.vk.service.FaqinfoService;
import haust.vk.service.UserinfoService;
import haust.vk.service.impl.FaqinfoServiceImpl;

@RestController
@CrossOrigin(maxAge=800,origins="*",methods={RequestMethod.GET, RequestMethod.POST})
public class FaqController {
	
	private static Logger logger = Logger.getLogger(FaqController.class);
	
	@Resource
	private UserinfoService userinfoServiceImpl;
	@Resource
	private FaqinfoService faqinfoServiceImpl;
	
	@RequestMapping(value="/user/insertfaq")
	public ResultBody CrosHelpResp(HttpServletRequest req, HttpServletResponse resp) throws GlobalErrorInfoException{
		
		Userinfo userinfo = (Userinfo) req.getAttribute("userinfo");
		Map jsoninfo = (Map) req.getAttribute("jsoninfo");
		String faqcontent = (String) jsoninfo.get("faq_content");
		if("".equals(faqcontent) || "null".equals(faqcontent) || faqcontent == null){
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		jsoninfo.put("user_id", userinfo.getUser_id());
		
		try {
			faqinfoServiceImpl.insertFaq(jsoninfo);
		} catch (Exception e) {
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		return new ResultBody(SuccessMessageCodeInfoEnum.SUCCESS_CODE_MESSAGE);
	}
	
	@RequestMapping(value="/extra/user/readfaq/{pagenum}/{pagesize}")
	public ResultBody readFaq(@PathVariable String pagenum,@PathVariable String pagesize) throws GlobalErrorInfoException{
		Integer num ;
		Integer size ;
		try{
			num = Integer.valueOf(pagenum);
			size = Integer.valueOf(pagesize);
		}catch(NumberFormatException e){
			e.printStackTrace();
			logger.debug(e);
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		List<Map> list ;
		Map map = new HashMap<>();
		map.put("start", num*size);
		map.put("pagesize", size);
		try {
			map = faqinfoServiceImpl.selectFromFaqinfo(map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e);
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		return new ResultBody(map);
	}
	
	@RequestMapping(value="/extra/user/deletefaq/{faqid}")
	public ResultBody deleteFaq(@PathVariable String faqid) throws GlobalErrorInfoException{
		try {
			faqinfoServiceImpl.deleteFaqinfo(faqid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e);
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		return new ResultBody(new HashMap<>());
	}
}
