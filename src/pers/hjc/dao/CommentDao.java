package pers.hjc.dao;

import java.util.List;

import pers.hjc.model.Comment;

public interface CommentDao extends BaseDao<Comment>
{
	List<Comment> findAllComment(Long articleID, Boolean flag) throws Exception;

	List<Comment> findCommentByPage(Long articleID, Integer page, Integer rows, Boolean flag) throws Exception;

	void deleteComment(Comment comment) throws Exception;
}
