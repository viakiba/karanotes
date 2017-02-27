package haust.vk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="comment")
public class CommentController {
	
	@RequestMapping(value="/insert",method=RequestMethod.POST)
	public String insertComment(@RequestBody String comment){
		
		
		
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
