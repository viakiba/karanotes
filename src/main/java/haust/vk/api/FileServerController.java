package haust.vk.api;

import haust.vk.dao.UserloginDao;
import haust.vk.exception.GlobalErrorInfoException;
import haust.vk.exception.code.NodescribeErrorInfoEnum;
import haust.vk.service.UserinfoService;
import haust.vk.utils.GetOsversionUtil;
import haust.vk.utils.SnowflakeIdUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value="file")
public class FileServerController {
	private static Logger logger = Logger.getLogger(FileServerController.class);
	@Resource
	private UserinfoService userinfoServiceImpl;
	@Resource
	private UserloginDao userloginDao;
	@Resource
	private SnowflakeIdUtil snowflakeIdUtil;
	
	/**
	 * 图片上传地址imgtype   文章(article)   用户头像(logo)  用户背景图(backlogo) 
	 */
	@RequestMapping(value = "/imgs/{imgtype}",method=RequestMethod.POST)
	public void articleImgUpload(@PathVariable("imgtype") String imgtype,@RequestParam("img") MultipartFile img,HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException, GlobalErrorInfoException{
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		PrintWriter rgw = null;
		String path =  null;
		File file = null;
		Map map = null;
		String token_id = null;
		String user_id = null;
		
		switch (imgtype) {
			case "article":
				long nextId = snowflakeIdUtil.nextId();
				String name = String.valueOf(nextId)+".png";
				path = GetOsversionUtil.getBasepath()+"article"+GetOsversionUtil.getOssplit() + name;
				file = new File(path);
				try {
					img.transferTo(file);
					rgw = resp.getWriter();
				} catch (IOException e) {
					logger.error(""+path);
					e.printStackTrace();
				}
				rgw.write(GetOsversionUtil.imgurl+"article/"+name);
				if(rgw != null){
					rgw.flush();
					rgw.close();
				}
				break;
			case "logo":
				token_id = (String) req.getParameter("token_id");
				user_id = userloginDao.selectUseridByTokenid(token_id);
				if(user_id == null){
					try {
						rgw = resp.getWriter();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					rgw.write("error");
					if(rgw != null){
						rgw.flush();
						rgw.close();
					}
					break;
				}
				path = GetOsversionUtil.getBasepath()+"logo"+GetOsversionUtil.getOssplit() + user_id+".png";
				
				file = new File(path);
				try {
					img.transferTo(file);
					rgw = resp.getWriter();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				map = new HashMap<>();
				map.put("user_id",user_id);
				map.put("headimg",user_id+".png");
				try {
					userinfoServiceImpl.updateUserlogo(map);
				} catch (Exception e1) {
					throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
				}
				
				rgw.write(user_id+".png");
				if(rgw != null){
					rgw.flush();
					rgw.close();
				}
				break;
			case "backlogo":
				token_id = (String) req.getParameter("token_id");
				user_id = userloginDao.selectUseridByTokenid(token_id);
				if(user_id == null){
					try {
						rgw = resp.getWriter();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					rgw.write("error");
					if(rgw != null){
						rgw.flush();
						rgw.close();
					}
					break;
				}
				path = GetOsversionUtil.getBasepath()+"backlogo"+GetOsversionUtil.getOssplit() + user_id+".png";
				
				file = new File(path);
				try {
					img.transferTo(file);
					rgw = resp.getWriter();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				map = new HashMap<>();
				map.put("user_id",user_id);
				map.put("backimg",user_id+".png");
				try {
					userinfoServiceImpl.updateUserBacklogo(map);
				} catch (Exception e1) {
					throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
				}
				rgw.write(user_id+".png");
				if(rgw != null){
					rgw.flush();
					rgw.close();
				}
				break;
			default:
			try {
				rgw = resp.getWriter();
			} catch (IOException e) {
				e.printStackTrace();
			}
				rgw.write("error");
				rgw.close();
				break;
		}
	}
	
	/**
	 * 图片下载地址
	 */
	@RequestMapping(value = "/imgs/{imgtype}/{imgname}",method=RequestMethod.GET)
	public void articleImgDownload(@PathVariable("imgtype") String imgtype,@PathVariable("imgname") String imgname,HttpServletRequest req, HttpServletResponse resp) throws IOException{
		String pathname = imgtype;
		File file = new File(GetOsversionUtil.getBasepath()+imgtype+GetOsversionUtil.getOssplit()+imgname+".png");
		if(file.exists()){
			//读取要下载的文件，保存到文件输入流
			FileInputStream in = new FileInputStream(file);
			//创建输出流
			OutputStream out = resp.getOutputStream();
			//创建缓冲区
			byte buffer[] = new byte[1024];
			int len = 0;
			//循环将输入流中的内容读取到缓冲区当中
			while((len=in.read(buffer))>0){
				//输出缓冲区的内容到浏览器，实现文件下载
				out.write(buffer, 0, len);
		    }
			//关闭文件输入流
			in.close();
			//关闭输出流
			out.close();
		}else{
			PrintWriter pw = resp.getWriter();
			pw.write("图片未找到");
			pw.close();
		}
	}
}
