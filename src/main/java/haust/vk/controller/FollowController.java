package haust.vk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="follow")
public class FollowController {
	
	@ResponseBody 
	@RequestMapping(value="/insert")
	public String insertFollow(@RequestBody String insertinfo){
		
		return null;
	}
	
	@ResponseBody 
	@RequestMapping(value="/delete")
	public String deleteFollow(@RequestBody String insertinfo){
		
		return null;
	}
	
	@ResponseBody 
	@RequestMapping(value="/select")
	public String selectByTokenid(@RequestBody String insertinfo){
		
		return null;
	}
}
