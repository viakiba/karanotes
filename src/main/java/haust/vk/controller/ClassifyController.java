package haust.vk.controller;

import haust.vk.service.ClassifyService;
import haust.vk.utils.JsonToMap;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="clssify")
public class ClassifyController {
	@Resource
	private ClassifyService classifyServiceImpl;
	
	@Resource
	private JsonToMap jsonToMap;
	
	@RequestMapping(value="/insert",method=RequestMethod.POST)
	public @ResponseBody Map insertClassify(@RequestBody String classifyInfo){
		Map classifyMapinfo = null;
		try {
			jsonToMap.jsonToMapUtil(classifyInfo);
		} catch (Exception e) {
			e.printStackTrace();
			classifyMapinfo = new HashMap();
			classifyMapinfo.put("success", -1);
			classifyMapinfo.put("messcode", 3);
			return classifyMapinfo;
		}
		try {
			classifyMapinfo = classifyServiceImpl.insertClassify(classifyMapinfo);
		} catch (Exception e) {
			classifyMapinfo.clear();
			classifyMapinfo.put("success", -1);
			classifyMapinfo.put("messcode", 4);
			e.printStackTrace();
		}
		return classifyMapinfo;
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public @ResponseBody Map deleteClassify(@RequestBody String classifyInfo){
		Map classifyMapinfo = null;
		try {
			classifyMapinfo = jsonToMap.jsonToMapUtil(classifyInfo);
		} catch (Exception e) {
			e.printStackTrace();
			classifyMapinfo = new HashMap();
			classifyMapinfo.put("success", -1);
			classifyMapinfo.put("messcode", 3);
			return classifyMapinfo;
		}
		
		try {
			classifyMapinfo = classifyServiceImpl.deleteClassify(classifyMapinfo);
		} catch (Exception e) {
			e.printStackTrace();
			classifyMapinfo = new HashMap();
			classifyMapinfo.put("success", -1);
			classifyMapinfo.put("messcode", 3);
			return classifyMapinfo;
		}
		
		return classifyMapinfo;
	}
	
	@RequestMapping(value="/getall",method=RequestMethod.POST)
	public @ResponseBody Map getallClassify(@RequestBody String classifyinfo){
		Map mapInfo = null;
		try {
			jsonToMap.jsonToMapUtil(classifyinfo);
		} catch (Exception e) {
			e.printStackTrace();
			mapInfo = new HashMap();
			mapInfo.put("success", -1);
			mapInfo.put("messcode", 3);
			return mapInfo;
		}
		try {
			mapInfo = classifyServiceImpl.getallClassify(mapInfo);
		} catch (Exception e) {
			mapInfo.clear();
			mapInfo.put("success", -1);
			mapInfo.put("messcode", "5 不可与之错误");
			e.printStackTrace();
			return mapInfo;
		}
		
		return mapInfo;
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public @ResponseBody Map updateClassify(@RequestBody String classifyInfo){
		Map classifyMapinfo = null;
		try {
			jsonToMap.jsonToMapUtil(classifyInfo);
		} catch (Exception e) {
			e.printStackTrace();
			classifyMapinfo = new HashMap();
			classifyMapinfo.put("success", -1);
			classifyMapinfo.put("messcode", 3);
		}
		
		try {
			classifyMapinfo = classifyServiceImpl.updateClassify(classifyMapinfo);
		} catch (Exception e) {
			classifyMapinfo.clear();
			classifyMapinfo.put("success", -1);
			classifyMapinfo.put("messcode", 4);
			e.printStackTrace();
		}
		
		return classifyMapinfo;
	}
	
	
}
