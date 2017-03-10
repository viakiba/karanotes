package haust.vk.rss;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rss")
public class Feedrss {
	
	@RequestMapping(value="/simplefeed")
	public String RssMethod(){
		
		return null;
	}
}
