package haust.vk.utils;

public class GetOsversionUtil {
	public static final String basepath;
	public static final String ossplit;
	public static final String imgurl = "http://karanotes.viakiba.cn/karanotes/file/imgs/";
	
	static {
		System.out.println("=======");
		String property = System.getProperty("os.name");
		if(property.contains("Linux")){
			basepath = "/usr/local/karanotes/";
			ossplit = "/";
		}else{
			basepath = "d:\\karanotes\\";
			ossplit = "\\";
		}
	}
	
	public static String getBasepath() {
		return basepath;
	}
	
	public static String getOssplit() {
		return ossplit;
	}
}
