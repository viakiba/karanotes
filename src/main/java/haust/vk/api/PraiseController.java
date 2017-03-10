package haust.vk.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="praise")
public class PraiseController {
	
	/**
	 * 点赞
	 * @return
	 */
	@RequestMapping(value="/insert",method=RequestMethod.POST)
	public String insertPraise(){
		
		return null;
	}
	
	/**
	 * 取消点赞
	 * @return
	 */
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public String deletePraise(){
		
		return null;
	}
	
	/**
	 * 查看点赞通知 分页
	 * @return
	 */
	@RequestMapping(value="/select",method=RequestMethod.POST)
	public String selectPraiseNotifyByTokenid(){
		
		return null;
	}

}
