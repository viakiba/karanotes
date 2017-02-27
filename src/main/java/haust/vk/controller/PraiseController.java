package haust.vk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="praise")
public class PraiseController {
	@RequestMapping(value="/insert",method=RequestMethod.POST)
	public String insertPraise(){
		
		return null;
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public String deletePraise(){
		
		return null;
	}
	
	@RequestMapping(value="/select",method=RequestMethod.POST)
	public String selectByTokenid(){
		
		return null;
	}
}
