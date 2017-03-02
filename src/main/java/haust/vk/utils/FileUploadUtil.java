package haust.vk.utils;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploadUtil {
	
	@Resource
	private SnowflakeIdUtil snowflakeIdUtil;
	
	/**
	 * 上传用户图片
	 * @param file
	 * @param filetype
	 * @return
	 * @throws IOException
	 */
	public String uploadUserImage(MultipartFile file ,String filetype) throws IOException{
		String path=null;
		String imgname = null;
        if(file != null){  
            //重命名上传后的文件名  
        	imgname = String.valueOf(snowflakeIdUtil.nextId())+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
            path = "d:/images/"+filetype+"/" + imgname;  
            File localFile = new File(path);
			file.transferTo(localFile);
        }
		return imgname;  
	}
}
