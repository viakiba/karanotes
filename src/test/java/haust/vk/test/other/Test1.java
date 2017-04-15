package haust.vk.test.other;

public class Test1 {
	
	public static final String  basepath  ;
	
	static {
		System.out.println("=======");
		String property = System.getProperty("os.name");
		System.out.println(property);
		System.out.println("=======");
		if(property.contains("Linux")){
			basepath = "/usr/local/karanotes";
		}else{
			basepath = "d:\\karanotes\\";
		}
		
	}
	public static void main(String[] args) {
		System.out.println(basepath);
	}
}
