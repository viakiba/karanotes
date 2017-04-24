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
public class CollectController {
	
	@RequestMapping(value="/collect/insert",method=RequestMethod.POST)
	public String insertCollect(HttpServletRequest req,HttpServletResponse resp){
		
		return null;
	}
	
	@RequestMapping(value="/collect/delete",method=RequestMethod.POST)
	public String deleteCollect(HttpServletRequest req,HttpServletResponse resp){
		
		return null;
	}
	
	@RequestMapping(value="/extra/collect/select/{tokenid}",method=RequestMethod.POST)
	public String selectByTokenid(@PathVariable String tokenid){
		
		return null;
	}
	
	@RequestMapping(value="/collect/select",method=RequestMethod.POST)
	public String selectByTokenid(HttpServletRequest req,HttpServletResponse resp){
		
		return null;
	}
}
