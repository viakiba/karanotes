package haust.vk.controller;

import haust.vk.dao.UserinfoDao;
import haust.vk.dao.UserloginDao;
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
	private UserinfoDao userinfoDaoImpl;
	
	@Resource
	private UserloginDao userloginDao;
	
	/**
	 * 用户头像 用户背景图 老图片未删除
	 * @param file
	 * @param json
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/upload/{filetype}/{token_id}",consumes={"image/jpeg","image/png","image/jpg"})
	public @ResponseBody Map FileUpload(@PathVariable String filetype,@PathVariable String token_id, @RequestParam("userimg") MultipartFile file,HttpServletRequest request){
		Map map = new HashMap();
		String imgid = null;
		String user_id = null;
		
		try {
			user_id = userloginDao.selectUseridByTokenid(token_id);
		} catch (Exception e1) {
			map.put("success", -1);
			map.put("messcode",1 );
			e1.printStackTrace();
			return map;
		}
		
		if(user_id == null){
			map.put("success", -1);
			map.put("messcode",1 );
			return map;
		}
		
		if("headimg".equals(filetype)){
			try {
				imgid = fileUploadUtil.uploadUserImage(file, filetype);
				map.put("headimg", imgid);
			} catch (IOException e) {
				map.put("success", -1);
				map.put("messcode","7 不可察系统异常" );
				e.printStackTrace();
				return map;
			}
		}else if("backimg".equals(filetype)){
			try {
				imgid = fileUploadUtil.uploadUserImage(file, filetype);
				map.put("backimg", imgid);
				
			} catch (IOException e) {
				map.put("success", -1);
				map.put("messcode","7 不可察系统异常" );
				e.printStackTrace();
				return map;
			}
		}
		
		map.put("user_id", user_id);
		try {
			userinfoDaoImpl.updateUserimg(map);
		} catch (Exception e) {
			map.clear();
			map.put("success", 1);
			map.put("messcode", 2);
			e.printStackTrace();
			return map;
		}
		map.clear();
		map.put("success", 1);
		map.put("messcode", 2);
		map.put("imgid", imgid);
		
		return map;
	}
}
