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
	
	/**
	 * 查看关注人列表
	 * @param insertinfo
	 * @return
	 */
	@ResponseBody 
	@RequestMapping(value="/select")
	public String selectFollowList(@RequestBody String insertinfo){
		
		return null;
	}
	
	/**
	 * 查看关注人通知
	 * @param insertinfo
	 * @return
	 */
	@ResponseBody 
	@RequestMapping(value="/select")
	public String selectFollowNotify(@RequestBody String insertinfo){
		
		return null;
	}
}
