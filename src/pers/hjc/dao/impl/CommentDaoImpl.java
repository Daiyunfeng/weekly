package pers.hjc.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import pers.hjc.dao.CommentDao;
import pers.hjc.model.Comment;

@Repository
@SuppressWarnings("unchecked")
public class CommentDaoImpl extends BaseDaoImpl<Comment> implements CommentDao
{

	@Override
	public List<Comment> findAllComment(Long articleID, Boolean flag) throws Exception
	{
		Object[] param = new Object[2];
		param[0] = articleID;
		param[1] = "updateTime";
		if (flag)
		{
			return findSQLToEntity("SELECT * FROM article_comment WHERE article_id = ? ORDER BY ?",
					Comment.class, param);
		}
		else
		{
			return findSQLToEntity(
					"SELECT * FROM article_comment WHERE article_id = ? AND is_use = 1 ORDER BY ?", Comment.class,
					param);
		}
	}

	@Override
	public List<Comment> findCommentByPage(Long articleID, Integer page, Integer rows, Boolean flag) throws Exception
	{
		Object[] param = new Object[2];
		param[0] = articleID;
		param[1] = "updateTime";
		if (flag)
		{
			return findSQLToEntity("SELECT * FROM article_comment WHERE article_id = ORDER BY ? ",
					Comment.class, page, rows, param);
		}
		else
		{
			return findSQLToEntity(
					"SELECT * FROM article_comment WHERE article_id = ? AND is_use = 1 ORDER BY ?", Comment.class, page,
					rows, param);
		}
	}


	@Override
	public void deleteComment(Comment comment) throws Exception
	{
		comment.setIsUse(0);
		update(comment);
	}
}
