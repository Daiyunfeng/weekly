package pers.hjc.dao;

import java.util.List;

import pers.hjc.model.Comment;

public interface CommentDao
{
	List<Comment> findAllComment(Long articleID, Boolean flag) throws Exception;

	List<Comment> findCommentByPage(Long articleID, Integer page, Integer rows, Boolean flag) throws Exception;

	Boolean addComment(Comment comment) throws Exception;

	Boolean deleteComment(Comment comment) throws Exception;

	Comment findByCommentID(Long ID) throws Exception;
}
