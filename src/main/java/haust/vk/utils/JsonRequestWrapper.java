package haust.vk.utils;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class JsonRequestWrapper extends HttpServletRequestWrapper{
	
	private final String body;
	
	public JsonRequestWrapper(HttpServletRequest request) throws IOException {
		super(request);
		StringBuffer sb = new StringBuffer();
        byte[] b = new byte[1024];  
        int len = 0; 
        InputStream inputStream = null;
        try {
            inputStream = request.getInputStream();
            if (inputStream != null) {
            	//在这里就要直接拿到utf8格式   防止乱码
            	while ((len = inputStream.read(b)) != -1){  
            		sb.append(new String(b, 0, len, "utf-8")); 
                }  
            } else {
                sb.append("");
            }
        } finally {
        	if( inputStream != null){
        		 inputStream.close();
        	}
        }
        body = sb.toString();
	}
	
	public String getBody() {
		return this.body;
	}
}
