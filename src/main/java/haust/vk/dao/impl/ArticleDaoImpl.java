package haust.vk.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;

import haust.vk.dao.ArticleDao;
import haust.vk.entity.Articleabstract;
import haust.vk.entity.Articletag;

@Repository
public class ArticleDaoImpl implements ArticleDao{
	
	@Resource
	private SqlSessionFactory sqlSessionFactory;
	
	@Override
	public void insertArticleabstract(Articleabstract articleabstract) {
		SqlSession os = sqlSessionFactory.openSession();
		os.insert("insertArticleabstract", articleabstract);
		os.close();
	}

	@Override
	public void insertArticlecontent(Map articlecontentmap) {
		SqlSession os = sqlSessionFactory.openSession();
		os.insert("insertArticlecontent", articlecontentmap);
		os.close();
	}

	@Override
	public void insertArticletag(Articletag article_tag) {
		SqlSession os = sqlSessionFactory.openSession();
		os.insert("insertArticletag", article_tag);
		os.close();
	}

	@Override
	public void deleteArticleabstract(String article_id) {
		SqlSession os = sqlSessionFactory.openSession();
		os.delete("deleteArticleabstract", article_id);
		os.close();
	}

	@Override
	public void deleteArticlecontent(String article_id) {
		SqlSession os = sqlSessionFactory.openSession();
		os.delete("deleteArticlecontent",article_id);
		os.close();
	}

	@Override
	public void deleteArticletag(String article_id) {
		SqlSession os = sqlSessionFactory.openSession();
		os.delete("deleteArticletag", article_id);
		os.close();
	}

	@Override
	public void updateArticletag(Articletag article_tag) {
		SqlSession os = sqlSessionFactory.openSession();
		os.update("updateArticletag", article_tag);
		os.close();
	}

	@Override
	public void updateArticleabstract(Articleabstract article_abstract) {
		SqlSession os = sqlSessionFactory.openSession();
		os.update("updateArticleabstract", article_abstract);
		os.close();
	}

	@Override
	public void updateArticlecontent(Map articlecontentmap) {
		SqlSession os = sqlSessionFactory.openSession();
		os.update("updateArticlecontent", articlecontentmap);
		os.close();
	}

	@Override
	public List<Map> selectArticleLikeTitle(String articletitle) {
		SqlSession os = sqlSessionFactory.openSession();
		List<Map> articleTitle = os.selectList("selectArticleLikeTitle", articletitle);
		os.close();
		return articleTitle;
	}

	
}
