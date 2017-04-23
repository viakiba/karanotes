package haust.vk.api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(maxAge=800,origins="*",methods={RequestMethod.GET, RequestMethod.POST})
public class PraiseController {
	
	/**
	 * 点赞
	 * @return
	 */
	@RequestMapping(value="/praise/insert",method=RequestMethod.POST)
	public String insertPraise(){
		
		return null;
	}
	
	/**
	 * 取消点赞
	 * @return
	 */
	@RequestMapping(value="/praise/delete",method=RequestMethod.POST)
	public String deletePraise(){
		
		return null;
	}
	
	/**
	 * 查看点赞通知 分页
	 * @return
	 */
	@RequestMapping(value="/praise/select",method=RequestMethod.POST)
	public String selectPraiseNotifyByTokenid(){
		
		return null;
	}

}
