package haust.vk.service;

import java.util.Map;

public interface ArticleService {
	
	/**
	 * 增加文章
	 * @param articleMap
	 * @param device_type
	 * @return
	 */
	public Map insertArticle(String articleMap,String device_type);
	/**
	 * 更新文章
	 * @param articleMap
	 * @return
	 */
	public Map updateArticle(String articleMap);
	/**
	 * 删除文章
	 * @param articleMap
	 * @return
	 */
	public Map deleteArticle(String articleMap);
	/**
	 * 标题
	 * @param articleMap
	 * @return
	 */
	public Map selectArticleTitle(String articleMap);
	
}
