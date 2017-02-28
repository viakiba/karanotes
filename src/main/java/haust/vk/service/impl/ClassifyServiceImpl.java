package haust.vk.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import haust.vk.dao.ArticleDao;
import haust.vk.dao.ClassifyDao;
import haust.vk.dao.UserloginDao;
import haust.vk.entity.Articleclassify;
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
	
	public Map insertClassify(Map map){
		Articleclassify articleclassify = null;
		String token_id = String.valueOf(map.get("token_id"));
		String classify_content = String.valueOf(map.get("classify_content"));
		if(token_id == null | classify_content == null){
			map.clear();
			map.put("success", -1);
			map.put("messcode", 4);
			return map;
		}
		String user_id = userloginDaoImpl.selectUseridByTokenid(token_id);
		
		if(user_id == null){
			map.clear();
			map.put("success", -1);
			map.put("messcode", 2);
			return map;
		}
		
		try{
			//查重
			map.put("user_id", user_id);
			String classifyByContent = classifyDaoImpl.getClassifyByContentAndUserid(map);
			if(classifyByContent == null ){
				//插入
				long classify_id = snowflakeIdUtil.nextId();
				articleclassify = new Articleclassify();
				articleclassify.setClassify_id(String.valueOf(classify_id));
				articleclassify.setUser_id(user_id);
				articleclassify.setClassify_content(classifyByContent);
				classifyDaoImpl.insertClassify(articleclassify);
				map.clear();
				map.put("success", 1);
				return map;
			}
		}catch(Exception e){
			map.clear();
			map.put("success", -1);
			map.put("messcode", "5 不可预见错误");
			return map;
		}
		map.clear();
		map.put("success", -1);
		map.put("messcode", 1);
		return map;
	}

	@Override
	public Map deleteClassify(Map map) throws Exception {
		String token_id = map.get("token_id").toString();
		String classify_id = map.get("classify_id").toString();
		//判断json 键值对
		if( token_id == null | classify_id == null){
			map.clear();
			map.put("success", -1);
			map.put("messcode", 4);
			return map;
		}
		
		String user_id = userloginDaoImpl.selectUseridByTokenid(token_id);
		if(user_id == null){
			map.clear();
			map.put("success", -1);
			map.put("messcode", 1);
			return map;
		}
		//查询该用户 拥有分类个数
		map.put("user_id", user_id);
		List<Articleclassify> articleclassifyList = classifyDaoImpl.getallClassify(map);
		
		//超过一个允许删除
		if(articleclassifyList.size() > 1){
			//先把删除所属的分类id修改直保留的地方
			String classify_id2 = articleclassifyList.get(articleclassifyList.size() - 1).getClassify_id();
			map.clear();
			map.put("classify_id_old", classify_id);
			map.put("classify_id_new", classify_id2);
			articleDaoImpl.updateArticleClassify(map);
		}else{
			map.clear();
			map.put("success", -1);
			map.put("messcode", 5);
			return map;
		}
		//然后删除该分类
		classifyDaoImpl.deleteClassify(classify_id);
		map.clear();
		map.put("success", 1);
		map.put("messcode", 2);
		return map;
	}

	@Override
	public Map updateClassify(Map map) throws Exception {
		Articleclassify articleclassify = null;
		String token_id = map.get("token_id").toString();
		String classify_id = map.get("classify_id").toString();
			String user_id = userloginDaoImpl.selectUseridByTokenid(token_id);
			if(user_id != null & classify_id != null){
				articleclassify = new Articleclassify();
				articleclassify.setClassify_id(classify_id);
				classifyDaoImpl.updateClassify(articleclassify);
				map.clear();
				map.put("success", 1);
				map.put("messcode", 2);
				return map;
			}
		map.clear();
		map.put("success", -1);
		if(user_id != null){
			map.put("messcode", 4);
		}
		map.put("messcode", 1);
		return map;
	}
	
	@Override
	public Map getallClassify(Map map) throws Exception {
		//检查json键值对是否规范
		String token_id = map.get("token_id").toString();
		if(token_id == null){
			map.clear();
			map.put("success", -1);
			map.put("messcode", 4);
			return map;
		}
		//检查token_id 是否有效
		String user_id = userloginDaoImpl.selectUseridByTokenid(token_id);
		if(user_id == null){
			map.clear();
			map.put("success", -1);
			map.put("messcode", 1);
			return map;
		}
		//获取分类
		map.clear();
		map.put("user_id", user_id);
		List<Articleclassify> getallClassify = classifyDaoImpl.getallClassify(map);
		map.remove("user_id");
		map.put("classify", getallClassify);
		map.put("success", 1);
		map.put("messcode", 2);
		return map;
	}
	
}
