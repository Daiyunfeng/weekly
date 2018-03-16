package pers.hjc.entity;

import pers.hjc.model.IndexUser;
import pers.hjc.model.User;

/**
 * 
 * 完整个人信息
 * 
 * @author Administrator
 *
 */
public class IndexUserMessage
{
	private Long id;
	
	private Long userID;

	private String realname;

	private Long phone;

	private String head;

	public IndexUserMessage()
	{
	}

	public IndexUserMessage(Long id ,Long userID, String realname, Long phone, String head)
	{
		super();
		this.id = id;
		this.userID = userID;
		this.realname = realname;
		this.phone = phone;
		this.head = head;
	}

	public IndexUserMessage(Long id ,User user)
	{
		this(id, user.getID(), user.getRealname(), user.getPhone(), user.getHead());
	}
	
	public IndexUserMessage(IndexUser user)
	{
		this(user.getID(),user.getUser());
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

	public String getRealname()
	{
		return realname;
	}

	public void setRealname(String realname)
	{
		this.realname = realname;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	
}
