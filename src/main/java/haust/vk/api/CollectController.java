package haust.vk.api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(maxAge=800,origins="*",methods={RequestMethod.GET, RequestMethod.POST})
public class CollectController {
	
	@RequestMapping(value="/collect/insert",method=RequestMethod.POST)
	public String insertCollect(){
		
		return null;
	}
	
	@RequestMapping(value="/collect/delete",method=RequestMethod.POST)
	public String deleteCollect(){
		
		return null;
	}
	
	@RequestMapping(value="/collect/select",method=RequestMethod.POST)
	public String selectByTokenid(){
		
		return null;
	}
}
