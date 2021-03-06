package pers.hjc.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import pers.hjc.dao.ArticleDao;
import pers.hjc.model.Article;

@SuppressWarnings("unchecked")
@Repository
public class ArticleDaoImpl extends BaseDaoImpl<Article> implements ArticleDao
{

	@Override
	public List<Article> findAllArticle(Boolean flag, String orderBy) throws Exception
	{
		Object[] param = new Object[1];
		param[0] = orderBy;
		if (flag)
		{
			return find("FROM Article ORDER BY ?", param);
		}
		else
		{
			return find("FROM Article WHERE isUse = 1 ORDER BY ?", param);
		}
	}

	@Override
	public List<Article> findArticleByPage(Integer page, Integer rows, Boolean flag, String orderBy) throws Exception
	{
		Object[] param = new Object[1];
		param[0] = orderBy;
		if (flag)
		{
			return find("FROM Article ORDER BY ?", page, rows, param);
		}
		else
		{
			return find("FROM Article WHERE isUse = 1 ORDER BY ?", page, rows, param);
		}
	}

	@Override
	public List<Article> findAllArticleBetween(Date beginTime, Date endTime, Boolean flag, String orderBy)
			throws Exception
	{
		Object[] param = new Object[3];
		param[0] = beginTime;
		param[1] = endTime;
		param[2] = orderBy;
		if (flag)
		{
			return find("FROM Article WhERE updateTime BETWEEN ? AND ? ORDER BY ?", param);
		}
		else
		{
			return find("FROM Article WHERE updateTime BETWEEN ? AND ? AND isUse = 1 ORDER BY ?", param);
		}
	}

	@Override
	public List<Article> findArticleByPageBetween(Date beginTime, Date endTime, Integer page, Integer rows,
			Boolean flag, String orderBy) throws Exception
	{
		Object[] param = new Object[3];
		param[0] = beginTime;
		param[1] = endTime;
		param[2] = orderBy;
		if (flag)
		{
			return find("FROM Article WHERE updateTime BETWEEN ? AND ? ORDER BY ?", page, rows, param);
		}
		else
		{
			return find("FROM Article WHERE updateTime BETWEEN ? AND ? AND isUse = 1 ORDER BY ?", page, rows,
					param);
		}
	}

	@Override
	public List<Article> findArticleByTitle(Integer page, Integer rows, String title, Boolean flag, String orderBy)
			throws Exception
	{
		Object[] param = new Object[2];
		param[0] = title;
		param[1] = orderBy;
		if (flag)
		{
			return find("FROM Article WHERE title = ? ORDER BY ?", page, rows, param);
		}
		else
		{
			return find("FROM Article WHERE title = ? AND isUse = 1 ORDER BY ?", page, rows, param);
		}
	}

	@Override
	public int count(Boolean flag) throws Exception
	{
		Object[] param = new Object[1];
		param[0] = 1;
		if (flag)
		{
			return count("FROM Article", param).intValue();
		}
		else
		{
			return count("FROM Article WHERE isUse = ?", param).intValue();
		}
	}

	@Override
	public List<Article> searchArticle(Map<String, Object> params, Boolean flag) throws Exception
	{
		Object[] param;
		if (params.size() == 0)
		{
			if (flag)
			{
				return find("FROM Article", null);
			}
			else
			{
				return find("FROM Article WHERE isUse = 1", null);
			}
		}
		StringBuilder hql = new StringBuilder("FROM Article WHERE ");
		if (params.get("beginTime") != null)
		{
			param = new Object[params.size() - 1];
		}
		else
		{
			param = new Object[params.size()];
		}
		int count = 0;
		if (params.get("user.realname") != null)
		{
			hql.append("user.realname = ? ");
			param[count++] = params.get("user.realname");
		}
		if (params.get("user.ID") != null)
		{
			if (count != 0)
			{
				hql.append("AND user.ID = ? ");
			}
			else
			{
				hql.append("user.ID = ? ");
			}
			param[count++] = params.get("user.ID");
		}
		if (params.get("title") != null)
		{
			if (count != 0)
			{
				hql.append("AND title = ? ");
			}
			else
			{
				hql.append("title = ? ");
			}
			param[count++] = params.get("title");
		}
		if (params.get("beginTime") != null)
		{
			if (count != 0)
			{
				hql.append("AND updateTime BETWEEN ? AND ? ");
			}
			else
			{
				hql.append("updateTime BETWEEN ? AND ? ");
			}
			param[count++] = params.get("beginTime");
			param[count++] = params.get("endTime");
		}
		if (flag)
		{
			return find(hql.toString(), param);
		}
		else
		{
			if (count != 0)
			{
				hql.append("AND isUse = 1");
			}
			else
			{
				hql.append("isUse = 1");
			}
			return find(hql.toString(), param);
		}
	}
	public void deleteArticle(Article article) throws Exception
	{
		article.setIsUse(0);
		update(article);
	}
}
