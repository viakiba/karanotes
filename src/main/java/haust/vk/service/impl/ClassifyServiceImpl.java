package haust.vk.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import haust.vk.dao.ArticleDao;
import haust.vk.dao.ClassifyDao;
import haust.vk.dao.UserloginDao;
import haust.vk.entity.Articleclassify;
import haust.vk.exception.GlobalErrorInfoException;
import haust.vk.exception.code.TokenidErrorInfoEnum;
import haust.vk.service.ClassifyService;
import haust.vk.utils.SnowflakeIdUtil;

import org.springframework.stereotype.Service;

@Service
public class ClassifyServiceImpl implements ClassifyService{
	
	@Resource
	private ClassifyDao classifyDaoImpl;

	@Resource
	private UserloginDao userloginDaoImpl;
	
	@Resource
	private ArticleDao articleDaoImpl;
	
	@Resource
	private SnowflakeIdUtil snowflakeIdUtil;
	
	public Map insertClassify(Map map) throws Exception{
		Articleclassify articleclassify = null;
		String user_id = (String) map.get("user_id");
		//查重
		String classifyByContent = classifyDaoImpl.getClassifyByContentAndUserid(map);
		if(classifyByContent == null ){
			//插入
			long classify_id = snowflakeIdUtil.nextId();
			articleclassify = new Articleclassify();
			articleclassify.setClassify_id(String.valueOf(classify_id));
			articleclassify.setUser_id(user_id);
			articleclassify.setClassify_content( (String) map.get("classify_content"));
			classifyDaoImpl.insertClassify(articleclassify);
			map.clear();
			map.put("operate", 1);
		}else{
			map.clear();
			map.put("operate", "0");
		}
		return map;
	}

	@Override
	public Map deleteClassify(Map map) throws Exception {
		String classify_id = (String) map.get("classify_id");
		String user_id = (String) map.get("user_id");
		map.put("classify_id_old", classify_id);
		map.put("classify_id_new", user_id);
		articleDaoImpl.updateArticleClassify(map);
		classifyDaoImpl.deleteClassify(classify_id);
		map.clear();
		map.put("operate", "1");
		return map;
	}

	@Override
	public Map updateClassify(Map map) throws Exception {
		Articleclassify articleclassify = new Articleclassify();
		String classify_content = (String) map.get("classify_content");
		String classify_id = map.get("classify_id").toString();
		articleclassify.setClassify_id(classify_id);
		articleclassify.setClassify_content(classify_content);
		classifyDaoImpl.updateClassify(articleclassify);
		return map;
	}
	
	@Override
	public List<Articleclassify> getallClassify(String user_id) throws Exception {
		//获取分类
		Map map = new HashMap<>();
		map.put("user_id", user_id);
		List<Articleclassify> getallClassify = classifyDaoImpl.getallClassify(map);
		return getallClassify;
	}
	
}
