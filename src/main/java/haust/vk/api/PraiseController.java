package haust.vk.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(maxAge=800,origins="*",methods={RequestMethod.GET, RequestMethod.POST})
public class PraiseController {
	
	@RequestMapping(value="/praise/insert",method=RequestMethod.POST)
	public String insertPraise(HttpServletRequest req,HttpServletResponse resp){
		
		return null;
	}
	
	@RequestMapping(value="/praise/delete",method=RequestMethod.POST)
	public String deletePraise(HttpServletRequest req,HttpServletResponse resp){
		
		return null;
	}
	
	@RequestMapping(value="/extra/praise/selectnum/{tokenid}",method=RequestMethod.GET)
	public String selectPraiseNotifynum(@PathVariable String tokenid){
		
		return null;
	}
	
	@RequestMapping(value="/praise/select",method=RequestMethod.POST)
	public String selectPraiseNotifyByTokenid(HttpServletRequest req,HttpServletResponse resp){
		
		return null;
	}
}
