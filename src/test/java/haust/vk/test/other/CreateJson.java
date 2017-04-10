package haust.vk.test.other;

import haust.vk.entity.Articlecontent;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class CreateJson {
	public static void main(String[] args) {
		Map map = new HashMap();
		String[] str = new String[]{"1","2","3","5"};
		map.put("str", str);
		Articlecontent articlecontent = new Articlecontent();
		articlecontent.setArticle_content("啊是否了解啊放假啊水立方拉萨解放拉萨就");
		articlecontent.setArticle_id("4sadad");
		map.put("articlecontent", articlecontent);
		System.out.println(JSON.toJSONString(map));
		String str1 = JSON.toJSONString(map);
		Map parseObject = JSON.parseObject(str1, Map.class );
		System.out.println(parseObject);
		Object object = parseObject.get("str");
		System.out.println(object);
		//["1","2","3","5"]
		System.out.println("=====================");
		String tag = "{\"tag\":"+object+"}";
		System.out.println(tag);
		String substring = object.toString().substring(1,object.toString().length()-1).replace("\"", "");
		System.out.println(substring.split(",")[0]);
		Object object2 =  parseObject.get("articlecontent");
		System.out.println(object2);
		Articlecontent parseObject2 = JSON.parseObject(object2.toString(), Articlecontent.class);
		System.out.println(parseObject2.toString());
		Object a = new Object();
	}
}
