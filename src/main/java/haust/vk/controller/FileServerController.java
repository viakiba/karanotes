package haust.vk.controller;

import haust.vk.service.UserinfoService;
import haust.vk.utils.FileUploadUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value="file")
public class FileServerController {
	
	@Resource
	private FileUploadUtil fileUploadUtil;
	
	@Resource
	private UserinfoService userinfoServiceImpl;
	
	/**
	 * 用户头像 用户背景图 
	 * @param file
	 * @param json
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/upload/{filetype}/{token_id}",method=RequestMethod.POST,consumes={"image/jpeg","image/png","image/jpg"})
	public @ResponseBody Map FileUpload(@PathVariable String filetype,@PathVariable String token_id, @RequestParam("userimg") MultipartFile file,HttpServletRequest request){
		Map map = new HashMap();
		String imgid = null;
		if("headimg".equals(filetype)){
			try {
				imgid = fileUploadUtil.uploadUserImage(file, filetype);
			} catch (IOException e) {
				map.put("success", -1);
				map.put("messcode","7 不可察系统异常" );
				e.printStackTrace();
				return map;
			}
			map.put("success", 1);
			map.put("messcode", 2);
			map.put("imgid", imgid);
			return map;
		}else if("backimg".equals(filetype)){
			try {
				imgid = fileUploadUtil.uploadUserImage(file, filetype);
			} catch (IOException e) {
				map.put("success", -1);
				map.put("messcode","7 不可察系统异常" );
				e.printStackTrace();
				return map;
			}
		}
		map.put("success", -1);
		map.put("messcode","5" );
		return map;
	}
}
