package haust.vk.dao;

import java.util.Map;

import haust.vk.entity.Articleabstract;
import haust.vk.entity.Articletag;

public interface ArticleDao {
	
	public void insertArticleabstract(Articleabstract articleabstract);
	public void insertArticlecontent(Map articlecontentmap);
	public void insertArticletag(Articletag article_tag);
	
}
