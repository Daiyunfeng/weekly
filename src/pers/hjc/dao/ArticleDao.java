package pers.hjc.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import pers.hjc.model.Article;

public interface ArticleDao extends BaseDao<Article>
{
	List<Article> findAllArticle(Boolean flag, String orderBy) throws Exception;

	List<Article> findArticleByPage(Integer page, Integer rows, Boolean flag, String orderBy) throws Exception;

	List<Article> findAllArticleBetween(Date beginTime, Date endTime, Boolean flag, String orderBy) throws Exception;

	List<Article> findArticleByPageBetween(Date beginTime, Date endTime, Integer page, Integer rows, Boolean flag,
			String orderBy) throws Exception;

	List<Article> findArticleByTitle(Integer page, Integer rows, String title, Boolean flag, String orderBy)
			throws Exception;

	List<Article> searchArticle(Map<String, Object> params, Boolean flag) throws Exception;

	int count(Boolean flag) throws Exception;
	
	void deleteArticle(Article article) throws Exception;
}
