package pers.hjc.entity;

import java.util.Date;
import java.util.Set;

import pers.hjc.model.Article;
import pers.hjc.model.Comment;

public class ArticleMessage
{
	private Long articleID;

	private String title;

	private Date updateTime;

	private Long userID;

	private String author;

	private String content;

	private int commentNumber;

	public ArticleMessage()
	{
	};

	public ArticleMessage(Long articleID, String title, Date updateTime, Long userID, String author, String content,
			int commentNumber)
	{
		super();
		this.articleID = articleID;
		this.title = title;
		this.updateTime = updateTime;
		this.userID = userID;
		this.author = author;
		this.content = content;
		this.commentNumber = commentNumber;
	}

	public ArticleMessage(Article article)
	{
		this(article.getID(), article.getTitle(), article.getUpdateTime(), article.getUser().getID(),
				article.getUser().getRealname(), article.getArticleContent().getContent(), 0);

		Set<Comment> comments = article.getArticleComment();
		int count = 0;
		for (Comment comment : comments)
		{
			if (comment.getIsUse() == 1)
			{
				count++;
			}
		}
		this.setCommentNumber(count);
	}

	public Long getArticleID()
	{
		return articleID;
	}

	public void setArticleID(Long articleID)
	{
		this.articleID = articleID;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
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

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public int getCommentNumber()
	{
		return commentNumber;
	}

	public void setCommentNumber(int commentNumber)
	{
		this.commentNumber = commentNumber;
	}

}
