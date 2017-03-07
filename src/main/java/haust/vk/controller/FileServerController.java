package haust.vk.controller;

import haust.vk.dao.UserinfoDao;
import haust.vk.dao.UserloginDao;
import haust.vk.utils.SnowflakeIdUtil;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	private UserinfoDao userinfoDaoImpl;
	
	@Resource
	private UserloginDao userloginDao;
	
	@Resource
	private SnowflakeIdUtil snowflakeIdUtil;
	
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
		String user_id = null;
		String imgname = null;
		
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
		
		if("headimg".equals(filetype) & file != null){
			try {
				imgname = String.valueOf(snowflakeIdUtil.nextId())+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
	            String path = "d:/tomcat7/images/"+filetype+"/" + imgname;  
	            File localFile = new File(path);
				file.transferTo(localFile);
				map.put("headimg", imgname);
			} catch (IOException e) {
				map.put("success", -1);
				map.put("messcode","7 不可察系统异常" );
				e.printStackTrace();
				return map;
			}
		}else if("backimg".equals(filetype) & file!=null){
			try {
				imgname = String.valueOf(snowflakeIdUtil.nextId())+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
	            String path = "d:/tomcat7/images/"+filetype+"/" + imgname;  
	            File localFile = new File(path);
				file.transferTo(localFile);
				map.put("backimg", imgname);
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
		map.put("imgid", imgname);
		return map;
	}
	
	//文章图片的地址
	@RequestMapping("/articleimg")
	public void articleImgUpload(@RequestParam("articleimg") MultipartFile file,HttpServletRequest req, HttpServletResponse resp){
		PrintWriter rgw = null;
		long nextId = snowflakeIdUtil.nextId();
		String name = String.valueOf(nextId)+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")-1);
		
		String path = "D:/tomcat7/images/article/" + name;
		File file2 = new File(path);
		resp.setCharacterEncoding("UTF-8");
		try {
			file.transferTo(file2);
			rgw = resp.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		rgw.write("http://127.0.0.1:8080/images/"+name);
		//rgw.write("http://viakiba.cn:8080/images/"+name);
		rgw.flush();
		rgw.close();
	}
}
