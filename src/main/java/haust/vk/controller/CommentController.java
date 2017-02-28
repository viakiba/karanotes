package haust.vk.controller;

import haust.vk.utils.JsonToMap;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="comment")
public class CommentController {
	
	@Resource
	private JsonToMap jsonToMap;
	
	@RequestMapping(value="/insert",method=RequestMethod.POST)
	public String insertComment(@RequestBody String comment){
		Map articleMap = null;
		
		try {
			articleMap = jsonToMap.jsonToMapUtil(comment);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public String deleteComment(@RequestBody String comment){
		
		
		
		return null;
	}
	
	@RequestMapping(value="/selectbyarticleid",method=RequestMethod.POST)
	public String selectCommentByArticleid(@RequestBody String comment){
		
		
		
		return null;
	}
	
	@RequestMapping(value="/select",method=RequestMethod.POST)
	public String selectCommentByTokenid(@RequestBody String comment){
		
		
		
		return null;
	}
}
