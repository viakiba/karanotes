package haust.vk.controller;

import haust.vk.service.CommentService;
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
@RequestMapping(value="comment")
public class CommentController {
	
	@Resource
	private JsonToMap jsonToMap;
	
	@Resource
	private CommentService commentServiceImpl;
	
	@RequestMapping(value="/insert",method=RequestMethod.POST)
	public @ResponseBody Map insertComment(@RequestBody String comment){
		Map commentMap = null;
		try {
			commentMap = jsonToMap.jsonToMapUtil(comment);
		} catch (Exception e1) {
			commentMap = new HashMap();
			commentMap.put("success", -1);
			commentMap.put("messcode", 3);
			e1.printStackTrace();
			return commentMap;
		}
		
		try {
			commentMap = commentServiceImpl.insertComment(commentMap);
		} catch (Exception e) {
			commentMap = new HashMap();
			commentMap.put("success", -1);
			commentMap.put("messcode", "5 不可预知错误");
			e.printStackTrace();
			return commentMap;
		}
		return commentMap;
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public @ResponseBody Map deleteComment(@RequestBody String comment){
		Map commentMap = null;
		try {
			commentMap = jsonToMap.jsonToMapUtil(comment);
		} catch (Exception e1) {
			commentMap = new HashMap();
			commentMap.put("success", -1);
			commentMap.put("messcode", 3);
			e1.printStackTrace();
			return commentMap;
		}
		
		try {
			commentMap = commentServiceImpl.deleteComment(commentMap);
		} catch (Exception e) {
			commentMap = new HashMap();
			commentMap.put("success", -1);
			commentMap.put("messcode", "5 不可预知错误");
			e.printStackTrace();
			return commentMap;
		}
		return commentMap;
	}
	
	/**
	 * 通过article_id获取评论 分页
	 * @param comment
	 * @return
	 */
	@RequestMapping(value="/selectbyarticleid",method=RequestMethod.POST)
	public @ResponseBody Map selectCommentByArticleid(@RequestBody String comment){
		
		
		
		return null;
	}
	
	/**
	 * 获取评论类的通知 分页
	 * @param comment
	 * @return
	 */
	@RequestMapping(value="/select",method=RequestMethod.POST)
	public String selectCommentByTokenid(@RequestBody String comment){
		
		
		
		return null;
	}
}
