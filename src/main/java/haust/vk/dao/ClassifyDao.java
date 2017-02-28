package haust.vk.dao;

import java.util.List;
import java.util.Map;

import haust.vk.entity.Articleclassify;

public interface ClassifyDao {
	
	/**
	 * 添加分类
	 * @param articleclassify
	 * @throws Exception
	 */
	public void insertClassify(Articleclassify articleclassify) throws Exception;
	
	/**
	 * 删除分类
	 * @param classify_id
	 * @throws Exception
	 */
	public void deleteClassify(String classify_id) throws Exception;
	/**
	 * 更新分类
	 * @param articleclassify
	 * @throws Exception
	 */
	public void updateClassify(Articleclassify articleclassify) throws Exception;
	/**
	 * 获取所有分类
	 * @param user_id
	 * @return
	 * @throws Exception
	 */
	public List<Articleclassify> getallClassify(Map userinfo) throws Exception;
	/**
	 * 通过内容获取分类
	 * @param user_id
	 * @return
	 * @throws Exception
	 */
	public String getClassifyByContentAndUserid(Map userinfo) throws Exception;
	
}
