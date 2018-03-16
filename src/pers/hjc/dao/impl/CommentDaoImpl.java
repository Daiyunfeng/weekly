package pers.hjc.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pers.hjc.dao.CommentDao;
import pers.hjc.model.Comment;

@Repository
@SuppressWarnings("unchecked")
public class CommentDaoImpl implements CommentDao
{
	@Autowired
	BaseDaoImpl<Comment> baseDao;

	@Override
	public List<Comment> findAllComment(Long articleID, Boolean flag) throws Exception
	{
		Object[] param = new Object[2];
		param[0] = articleID;
		param[1] = "updateTime";
		if (flag)
		{
			return baseDao.findSQLToEntity("SELECT * FROM article_comment WHERE article_id = ? ORDER BY ?",
					Comment.class, param);
		}
		else
		{
			return baseDao.findSQLToEntity(
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
			return baseDao.findSQLToEntity("SELECT * FROM article_comment WHERE article_id = ORDER BY ? ",
					Comment.class, page, rows, param);
		}
		else
		{
			return baseDao.findSQLToEntity(
					"SELECT * FROM article_comment WHERE article_id = ? AND is_use = 1 ORDER BY ?", Comment.class, page,
					rows, param);
		}
	}

	@Override
	public Boolean addComment(Comment comment) throws Exception
	{
		Serializable commentID = baseDao.save(comment);
		if (commentID == null)
		{
			return false;
		}
		return true;
	}

	@Override
	public Boolean deleteComment(Comment comment) throws Exception
	{
		comment.setIsUse(1);
		baseDao.update(comment);
		return true;
	}

	@Override
	public Comment findByCommentID(Long ID) throws Exception
	{
		Comment comment = null;
		comment = baseDao.get(Comment.class, ID);
		return comment;
	}
}
