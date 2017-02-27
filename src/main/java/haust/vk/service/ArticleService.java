package haust.vk.service;

import java.util.List;
import java.util.Map;

public interface ArticleService {
	
	/**
	 * 增加文章
	 * @param articleMap
	 * @param device_type
	 * @return
	 */
	public Map insertArticle(String articleMap,String device_type) throws Exception;
	/**
	 * 删除文章
	 * @param articleMap
	 * @return
	 */
	public Map deleteArticle(String articleMap) throws Exception;
	/**
	 * 更新文章
	 * @param deleteinfo
	 * @param string
	 * @return
	 */
	public Map updateArticle(String deleteinfo, String devicetype) throws Exception;
	/**
	 * 标题搜索  使用分页
	 * @param articleMap
	 * @return
	 */
	public List<Map> selectArticleTitle(String articleMap) throws Exception;
	
}
