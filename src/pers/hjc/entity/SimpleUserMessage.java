package pers.hjc.entity;

import pers.hjc.model.User;

/**
 * 分页时显示简单信息
 * 
 * @author Administrator
 *
 */
public class SimpleUserMessage
{
	private Long userID;

	private String realname;

	private Long phone;

	private String head;

	public SimpleUserMessage()
	{
	};

	public SimpleUserMessage(Long userID, String realname, Long phone, String head)
	{
		super();
		this.userID = userID;
		this.realname = realname;
		this.phone = phone;
		this.head = head;
	}

	public SimpleUserMessage(User user)
	{
		this(user.getID(), user.getRealname(), user.getPhone(), user.getHead());
	}

	public Long getUserID()
	{
		return userID;
	}

	public void setUserID(Long userID)
	{
		this.userID = userID;
	}

	public String getHead()
	{
		return head;
	}

	public void setHead(String head)
	{
		this.head = head;
	}

	public Long getPhone()
	{
		return phone;
	}

	public void setPhone(Long phone)
	{
		this.phone = phone;
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
