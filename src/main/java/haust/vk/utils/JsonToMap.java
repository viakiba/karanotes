package haust.vk.utils;

import java.util.Map;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;

@Component
public class JsonToMap {
	public static Map jsonToMapUtil(String str) throws Exception{
		Map map = JSON.parseObject(str, Map.class);
		return map;
	}
}
