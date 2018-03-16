package pers.hjc.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pers.hjc.GlobelVariable;
import pers.hjc.dao.CommentDao;
import pers.hjc.model.Article;
import pers.hjc.model.Comment;
import pers.hjc.model.User;
import pers.hjc.util.MD5Util;

@Service
public class CommentService
{
	@Autowired
	private CommentDao commentDao;

	@Autowired
	private ArticleService articleService;

	@Transactional(readOnly = false)
	public Boolean addComment(Comment comment) throws Exception
	{
		return commentDao.addComment(comment);
	}

	@Transactional(readOnly = false)
	public Boolean deleteComment(Long ID) throws Exception
	{
		Comment comment = commentDao.findByCommentID(ID);
		if (comment != null)
		{
			return commentDao.deleteComment(comment);
		}
		else
		{
			throw new Exception("此评论不存在");
		}
	}

	@Transactional(readOnly = true)
	public List<Comment> findAllComment(Long articleID, Boolean flag) throws Exception
	{
		Article article = articleService.findArticle(articleID);
		if (flag != true)
		{
			if (article.getIsUse() == 0)
			{
				throw new Exception("文章不存在");
			}
		}
		List<Comment> comments = commentDao.findAllComment(articleID, flag);
		return comments;
	}

	@Transactional(readOnly = true)
	public List<Comment> findCommentByPage(Long articleID, Integer page, Integer rows, Boolean flag) throws Exception
	{
		Article article = articleService.findArticle(articleID);
		if (flag != true)
		{
			if (article.getIsUse() == 0)
			{
				throw new Exception("文章不存在");
			}
		}
		List<Comment> comments = commentDao.findCommentByPage(articleID, page, rows, flag);
		return comments;
	}
}
