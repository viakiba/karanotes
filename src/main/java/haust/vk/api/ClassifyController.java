package haust.vk.api;

import haust.vk.entity.Articleclassify;
import haust.vk.entity.Userinfo;
import haust.vk.exception.GlobalErrorInfoException;
import haust.vk.exception.code.JsonKeyValueErrorInfoEnum;
import haust.vk.exception.code.NodescribeErrorInfoEnum;
import haust.vk.exception.code.SuccessMessageCodeInfoEnum;
import haust.vk.exception.code.TokenidErrorInfoEnum;
import haust.vk.result.ResultBody;
import haust.vk.service.ClassifyService;
import haust.vk.utils.JsonToMap;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClassifyController {
	@Resource
	private ClassifyService classifyServiceImpl;
	@Resource
	private JsonToMap jsonToMap;
	
	@RequestMapping(value="/clssify/insert",method=RequestMethod.POST)
	public ResultBody insertClassify(HttpServletRequest req, HttpServletResponse resp) throws GlobalErrorInfoException{
		Map classifyMapinfo = (Map) req.getAttribute("jsoninfo");
		Userinfo userinfo = (Userinfo) req.getAttribute("userinfo");
		
		String classify_content = String.valueOf(classifyMapinfo.get("classify_content"));
		if( classify_content == null || "".equals(classify_content) || "null".equals(classify_content)){
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		
		classifyMapinfo.put("user_id", userinfo.getUser_id());
		
		try {
			classifyMapinfo = classifyServiceImpl.insertClassify(classifyMapinfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		return new ResultBody(classifyMapinfo);
	}
	
	@RequestMapping(value="/clssify/delete",method=RequestMethod.POST)
	public ResultBody deleteClassify(HttpServletRequest req, HttpServletResponse resp) throws GlobalErrorInfoException{
		Map classifyMapinfo = (Map) req.getAttribute("jsoninfo");
		Userinfo userinfo = (Userinfo) req.getAttribute("userinfo");
		String classify_id = String.valueOf(classifyMapinfo.get("classify_id"));
		if( classify_id == null || "".equals(classify_id) || "null".equals(classify_id)){
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		if(userinfo.getUser_id().equals(classify_id)){
			classifyMapinfo.clear();
			classifyMapinfo.put("operate", "0");
			return new ResultBody(classifyMapinfo);
		}
		classifyMapinfo.put("user_id", userinfo.getUser_id());
		try {
			classifyMapinfo = classifyServiceImpl.deleteClassify(classifyMapinfo);
		} catch (Exception e) {
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		return new ResultBody(classifyMapinfo);
	}
	
	@RequestMapping(value="/extra/clssify/getall/{userid}",method=RequestMethod.GET)
	public ResultBody getallClassify(@PathVariable String userid) throws GlobalErrorInfoException{
		List<Articleclassify> list = null;
		try {
			list = classifyServiceImpl.getallClassify(userid);
		} catch (GlobalErrorInfoException e) {
			throw new GlobalErrorInfoException(TokenidErrorInfoEnum.USER_CONNOT_BE_FOUND);
		}catch (Exception e) {
			e.printStackTrace();
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		return new ResultBody(list);
	}
	
	@RequestMapping(value="/clssify/update",method=RequestMethod.POST)
	public ResultBody updateClassify(HttpServletRequest req, HttpServletResponse resp) throws GlobalErrorInfoException{
		Map classifyMapinfo = (Map) req.getAttribute("jsoninfo");
		
		String classify_id = String.valueOf(classifyMapinfo.get("classify_id"));
		String classify_content = String.valueOf(classifyMapinfo.get("classify_content"));
		
		if( classify_id == null || "".equals(classify_id) || "null".equals(classify_id) || classify_content == null || "".equals(classify_content) || "null".equals(classify_content)){
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		
		try {
			classifyServiceImpl.updateClassify(classifyMapinfo);
		} catch (Exception e) {
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		return new ResultBody(SuccessMessageCodeInfoEnum.SUCCESS_CODE_MESSAGE);
	}
}
