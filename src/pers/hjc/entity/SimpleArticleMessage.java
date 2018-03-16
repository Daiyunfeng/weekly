package pers.hjc.entity;

import java.util.Comparator;
import java.util.Date;
import java.util.Set;

import pers.hjc.model.Article;
import pers.hjc.model.Comment;

/**
 * 分页时显示简单信息
 * 
 * @author Administrator
 *
 */
public class SimpleArticleMessage
{
	private Long articleID;

	private String title;

	private Date updateTime;

	private Long userID;

	private String author;

	private int commentNumber;

	public static Comparator<SimpleArticleMessage> sortByUpdateTime = new Comparator<SimpleArticleMessage>()
	{
		public int compare(SimpleArticleMessage o1, SimpleArticleMessage o2)
		{
			return o2.getUpdateTime().compareTo(o1.getUpdateTime());
		}
	};

	public SimpleArticleMessage()
	{
	};

	public SimpleArticleMessage(Long articleID, String title, Date updateTime, Long userID, String author,
			int commentNumber)
	{
		super();
		this.articleID = articleID;
		this.title = title;
		this.updateTime = updateTime;
		this.userID = userID;
		this.author = author;
		this.commentNumber = commentNumber;
	}

	public SimpleArticleMessage(Article article)
	{
		this(article.getID(), article.getTitle(), article.getUpdateTime(), article.getUser().getID(),
				article.getUser().getRealname(), article.getArticleComment().size());

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

	public String getAuthor()
	{
		return author;
	}

	public void setAuthor(String author)
	{
		this.author = author;
	}

	public Long getUserID()
	{
		return userID;
	}

	public void setUserID(Long userID)
	{
		this.userID = userID;
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
