package pers.hjc.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pers.hjc.dao.impl.ArticleDaoImpl;
import pers.hjc.model.Article;
import pers.hjc.model.Article;

@Service
public class ArticleService
{
	@Autowired
	private ArticleDaoImpl articleDao;

	@Transactional(readOnly = false)
	public Boolean addArticle(Article article) throws Exception
	{
		// Date date = new Date(System.currentTimeMillis());
		// article.setUpdateTime(date);
		if (article.getArticleContent() == null)
		{
			throw new Exception("内容不能为空");
		}
		Boolean flag = articleDao.addArticle(article);
		return flag;
	}

	@Transactional(readOnly = false)
	public Boolean updateArticle(Article article) throws Exception
	{
		Date date = new Date(System.currentTimeMillis());
		article.setUpdateTime(date);
		if (article.getArticleContent() == null)
		{
			throw new Exception("内容不能为空");
		}
		Boolean flag = articleDao.updateArticle(article);
		return flag;
	}

	@Transactional(readOnly = true)
	public Article findArticle(Long articleID) throws Exception
	{
		Article article = articleDao.findByArticleID(articleID);
		return article;
	}

	@Transactional(readOnly = true)
	public List<Article> findArticleByTitle(Integer page, Integer rows, String title, Boolean flag, String orderBy)
			throws Exception
	{
		formatOrder(orderBy);
		List<Article> articles = articleDao.findArticleByTitle(page, rows, title, flag, orderBy);
		return articles;
	}

	@Transactional(readOnly = true)
	public List<Article> findAllArticle(Boolean flag, String orderBy) throws Exception
	{
		formatOrder(orderBy);
		List<Article> articles = articleDao.findAllArticle(flag, orderBy);
		return articles;
	}

	@Transactional(readOnly = true)
	public List<Article> findArticleByPageBetween(Date beginTime, Date endTime, Integer page, Integer rows,
			Boolean flag, String orderBy) throws Exception
	{
		formatOrder(orderBy);
		List<Article> articles = articleDao.findArticleByPageBetween(beginTime, endTime, page, rows, flag, orderBy);
		return articles;
	}

	@Transactional(readOnly = true)
	public List<Article> findAllArticleBetween(Date beginTime, Date endTime, Boolean flag, String orderBy)
			throws Exception
	{
		formatOrder(orderBy);
		List<Article> articles = articleDao.findAllArticleBetween(beginTime, endTime, flag, orderBy);
		return articles;
	}

	@Transactional(readOnly = true)
	public List<Article> findArticleByPage(Integer page, Integer rows, Boolean flag, String orderBy) throws Exception
	{
		formatOrder(orderBy);
		List<Article> articles = articleDao.findArticleByPage(page, rows, flag, orderBy);
		return articles;
	}

	@Transactional(readOnly = true)
	public int count(Boolean flag) throws Exception
	{
		return articleDao.count(flag);
	}

	@Transactional(readOnly = true)
	public List<Article> searchArticle(Map<String, Object> params, Boolean flag) throws Exception
	{
		return articleDao.searchArticle(params, flag);
	}

	private String formatOrder(String orderBy)
	{
		if (orderBy == null)
		{
			return "updateTime";
		}
		if (orderBy.equals("updateTime"))
		{
			return orderBy;
		}
		if (orderBy.equals("title"))
		{
			return orderBy;
		}
		if (orderBy.equals("user"))
		{
			return "user.realname";
		}
		return "updateTime";
	}
}
