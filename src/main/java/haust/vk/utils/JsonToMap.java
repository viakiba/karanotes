package haust.vk.utils;

import java.util.Map;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;

@Component
public class JsonToMap {

	public Map jsonToMapUtil(String str) throws Exception{
		str = new String(str.getBytes("ISO-8859-1"),"UTF-8");
		Map map = JSON.parseObject(str, Map.class);
		return map;
	}
}
