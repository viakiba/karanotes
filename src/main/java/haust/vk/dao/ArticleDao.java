package haust.vk.dao;

import java.util.Map;

import haust.vk.entity.Articleabstract;
import haust.vk.entity.Articletag;

public interface ArticleDao {
	/**
	 * 插入文章的摘要
	 * @param articleabstract
	 */
	public void insertArticleabstract(Articleabstract articleabstract);
	
	/**
	 * 插入文章的正文
	 * @param articlecontentmap
	 */
	public void insertArticlecontent(Map articlecontentmap);
	/**
	 * 插入文章的标签
	 * @param article_tag
	 */
	public void insertArticletag(Articletag article_tag);
	/**
	 * 根据article_id删除文章摘要
	 * @param article_id
	 */
	public void deleteArticleabstract(String article_id);
	/**
	 * 根据article_id删除文章正文
	 * @param string
	 */
	public void deleteArticlecontent(String string);
	/**
	 * 根据article_id删除文章的标签
	 * @param string
	 */
	public void deleteArticletag(String string);
	
}
