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
	public Map insertArticle(Map articleMapInfo) throws Exception;
	/**
	 * 删除文章
	 * @param articleMap
	 * @return
	 */
	public Map deleteArticle(Map articleMapInfo) throws Exception;
	/**
	 * 更新文章
	 * @param deleteinfo
	 * @param string
	 * @return
	 */
	public Map updateArticle(Map articleMapInfo) throws Exception;
	/**
	 * 标题搜索  使用分页
	 * @param articleMap
	 * @return
	 */
	public List<Map> selectArticleTitle(String articleMap) throws Exception;
	/**
	 * 拿到文章的详情内容
	 * @param articleid
	 * @return
	 */
	public Map selectArticleDetail(String articleid) throws Exception;
	/**
	 * 拿到个人文章列表
	 * @param user_path
	 * @return
	 * @throws Exception
	 */
	public Map selectArticleListByUserpath(String user_path) throws Exception;
	
}
