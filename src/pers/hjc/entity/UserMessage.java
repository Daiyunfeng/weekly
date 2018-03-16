package pers.hjc.entity;

import java.util.Set;

import pers.hjc.model.Article;
import pers.hjc.model.User;

/**
 * 
 * 完整个人信息
 * 
 * @author Administrator
 *
 */
public class UserMessage
{
	private Long userID;

	private String realname;

	private Long phone;

	private String head;

	private int isUse;

	private String role;

	private int articleNumber;

	public UserMessage()
	{
	}

	public UserMessage(Long userID, String realname, Long phone, String head, int isUse, String role, int articleNumber)
	{
		super();
		this.userID = userID;
		this.realname = realname;
		this.phone = phone;
		this.head = head;
		this.isUse = isUse;
		this.role = role;
		this.articleNumber = articleNumber;
	}

	public UserMessage(User user)
	{
		this(user.getID(), user.getRealname(), user.getPhone(), user.getHead(), user.getIsUse(),
				user.getRole().getDescription(), 0);
		Set<Article> articles = user.getArticles();
		int count = 0;
		for (Article article : articles)
		{
			if (article.getIsUse() == 1)
			{
				count++;
			}
		}
		this.setArticleNumber(count);
	}

	public int getIsUse()
	{
		return isUse;
	}

	public void setIsUse(int isUse)
	{
		this.isUse = isUse;
	}

	public Long getUserID()
	{
		return userID;
	}

	public void setUserID(Long userID)
	{
		this.userID = userID;
	}

	public Long getPhone()
	{
		return phone;
	}

	public void setPhone(Long phone)
	{
		this.phone = phone;
	}

	public String getHead()
	{
		return head;
	}

	public void setHead(String head)
	{
		this.head = head;
	}

	public String getRole()
	{
		return role;
	}

	public void setRole(String role)
	{
		this.role = role;
	}

	public int getArticleNumber()
	{
		return articleNumber;
	}

	public void setArticleNumber(int articleNumber)
	{
		this.articleNumber = articleNumber;
	}

	public String getRealname()
	{
		return realname;
	}

	public void setRealname(String realname)
	{
		this.realname = realname;
	}

}
