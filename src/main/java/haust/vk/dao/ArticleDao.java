package haust.vk.dao;

import java.util.List;
import java.util.Map;

import haust.vk.entity.Articleabstract;
import haust.vk.entity.Articletag;

public interface ArticleDao {
	/**
	 * 插入文章的摘要
	 * @param articleabstract
	 */
	public void insertArticleabstract(Articleabstract articleabstract) throws Exception;
	
	/**
	 * 插入文章的正文
	 * @param articlecontentmap
	 */
	public void insertArticlecontent(Map articlecontentmap) throws Exception;
	/**
	 * 插入文章的标签
	 * @param article_tag
	 */
	public void insertArticletag(Articletag article_tag) throws Exception;
	/**
	 * 根据article_id删除文章摘要
	 * @param article_id
	 */
	public void deleteArticleabstract(String article_id) throws Exception;
	/**
	 * 根据article_id删除文章正文
	 * @param string
	 */
	public void deleteArticlecontent(String string) throws Exception;
	/**
	 * 根据article_id删除文章的标签
	 * @param string
	 */
	public void deleteArticletag(String string) throws Exception;
	/**
	 * 更新文章标签
	 * @param article_tag
	 */
	public void updateArticletag(Articletag article_tag) throws Exception;
	/**
	 * 更新文章摘要
	 * @param article_abstract
	 */
	public void updateArticleabstract(Articleabstract article_abstract) throws Exception;

	public void updateArticlecontent(Map articlecontentmap) throws Exception;

	public List<Map> selectArticleLikeTitle(String articletitle) throws Exception;

	public void updateArticleClassify(Map classifyids) throws Exception;
	/**
	 * 获取个人列表   不分类/分类 均可  分页
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Articleabstract> selectArticleListByPerson(Map map) throws Exception;
	/**
	 * 获取文章详情   不包括评论 selectArticleAbstract selectArticleContent selectArticleTag selectArticleClassify
	 * @param articleid
	 * @return
	 * @throws Exception
	 */
	public Map selectArticleAbstract(String articleid)  throws Exception;

	public String selectArticleContent(String articleid)  throws Exception;

	public String selectArticleTag(String articleid) throws Exception;

	public String selectArticleClassify(String classify_id) throws Exception;

	public void updateOneNum(Map commentMap) throws Exception;

	public List<Map> selectArticleAbstractByUserid(String user_id) throws Exception;

	public List<Map> selectArticleByClassifyid(Map map) throws Exception;

	public List<Map> selectArticleBySearch(Map map) throws Exception;

	public List<Map> selectArticleByListUserid(Map res) throws Exception;
	
}
