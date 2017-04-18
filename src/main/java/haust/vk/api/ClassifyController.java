package haust.vk.api;

import haust.vk.exception.GlobalErrorInfoException;
import haust.vk.exception.code.NodescribeErrorInfoEnum;
import haust.vk.service.ClassifyService;
import haust.vk.utils.JsonToMap;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ClassifyController {
	@Resource
	private ClassifyService classifyServiceImpl;
	@Resource
	private JsonToMap jsonToMap;
	
	@RequestMapping(value="/clssify/insert",method=RequestMethod.POST)
	public @ResponseBody Map insertClassify(HttpServletRequest req, HttpServletResponse resp) throws GlobalErrorInfoException{
		Map classifyMapinfo = (Map) req.getAttribute("jsoninfo");
		try {
			classifyMapinfo = classifyServiceImpl.insertClassify(classifyMapinfo);
		} catch (Exception e) {
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		return classifyMapinfo;
	}
	
	@RequestMapping(value="/clssify/delete",method=RequestMethod.POST)
	public @ResponseBody Map deleteClassify(HttpServletRequest req, HttpServletResponse resp) throws GlobalErrorInfoException{
		Map classifyMapinfo = (Map) req.getAttribute("jsoninfo");
		
		try {
			classifyMapinfo = classifyServiceImpl.deleteClassify(classifyMapinfo);
		} catch (Exception e) {
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		
		return classifyMapinfo;
	}
	
	@RequestMapping(value="/select/clssify/getall",method=RequestMethod.POST)
	public @ResponseBody Map getallClassify(HttpServletRequest req, HttpServletResponse resp) throws GlobalErrorInfoException{
		Map mapInfo = (Map) req.getAttribute("jsoninfo");
		try {
			mapInfo = classifyServiceImpl.getallClassify(mapInfo);
		} catch (Exception e) {
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		return mapInfo;
	}
	
	@RequestMapping(value="/select/clssify/update",method=RequestMethod.POST)
	public @ResponseBody Map updateClassify(HttpServletRequest req, HttpServletResponse resp) throws GlobalErrorInfoException{
		Map classifyMapinfo = (Map) req.getAttribute("jsoninfo");
		try {
			classifyMapinfo = classifyServiceImpl.updateClassify(classifyMapinfo);
		} catch (Exception e) {
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		return classifyMapinfo;
	}
}
