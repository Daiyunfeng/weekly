package pers.hjc.entity;

import java.util.Date;

import pers.hjc.model.Comment;

public class CommentMessage
{
	private Long ID;

	private String content;

	private Date updateTime;

	private Long userID;

	private String author;

	private Long role;

	private String head;

	public CommentMessage(Long iD, String content, Date updateTime, Long userID, String author, Long role, String head)
	{
		super();
		ID = iD;
		this.content = content;
		this.updateTime = updateTime;
		this.userID = userID;
		this.author = author;
		this.role = role;
		this.head = head;
	}

	public CommentMessage(Comment comment)
	{
		this(comment.getID(), comment.getContent(), comment.getUpdateTime(), comment.getUser().getID(),
				comment.getUser().getRealname(), comment.getUser().getRole().getID(), comment.getUser().getHead());
	}

	public Long getID()
	{
		return ID;
	}

	public void setID(Long iD)
	{
		ID = iD;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public Date getUpdateTime()
	{
		return updateTime;
	}

	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}

	public Long getUserID()
	{
		return userID;
	}

	public void setUserID(Long userID)
	{
		this.userID = userID;
	}

	public String getAuthor()
	{
		return author;
	}

	public void setAuthor(String author)
	{
		this.author = author;
	}

	public Long getRole()
	{
		return role;
	}

	public void setRole(Long role)
	{
		this.role = role;
	}

	public String getHead()
	{
		return head;
	}

	public void setHead(String head)
	{
		this.head = head;
	}

}
