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
		String imgid = null;
        if(file != null){  
            //重命名上传后的文件名  
    		imgid = String.valueOf(snowflakeIdUtil.nextId());
            path = "d:/images/"+filetype+"/" + imgid;  
            File localFile = new File(path);
			file.transferTo(localFile);
        }
		return imgid;  
	}
}
