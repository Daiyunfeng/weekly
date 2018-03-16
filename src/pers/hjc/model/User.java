package pers.hjc.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 用户表 user 姓名 学号 电话号码 登陆密码 头像
 * 
 * @author Administrator
 */

@Entity
@Table(name = "user")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "operations", "roles", "menus" })
public class User implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "user_id", nullable = false, unique = true)
	private Long ID;

	@Column(length = 100, nullable = false)
	private String password;

	@Column(length = 50, nullable = false)
	private String realname;

	@Column(nullable = false)
	private Long phone;

	@Column(nullable = false, columnDefinition = "varchar(255) default '/upload/head/default.png'")
	private String head = "/upload/head/default.png";

	@Column(name = "is_use")
	private int isUse = 1;

	/*
	 * @Column(name = "in_index",columnDefinition = "int(2) default 0") private
	 * int inIndex = 0;
	 */

	@OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", nullable = false)
	private Role role;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "user")
	private Set<Article> articles;

	public void addArticle(Article article)
	{

		if (articles == null)
		{
			articles = new HashSet<>();
			articles.add(article);
		}
		else
		{
			articles.add(article);
		}
	}

	public Long getID()
	{
		return ID;
	}

	public void setID(Long iD)
	{
		ID = iD;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getRealname()
	{
		return realname;
	}

	public void setRealname(String realname)
	{
		this.realname = realname;
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

	public int getIsUse()
	{
		return isUse;
	}

	public void setIsUse(int isUse)
	{
		this.isUse = isUse;
	}

	public Role getRole()
	{
		return role;
	}

	public void setRole(Role role)
	{
		this.role = role;
	}

	public Set<Article> getArticles()
	{
		return articles;
	}

	public void setArticles(Set<Article> articles)
	{
		this.articles = articles;
	}

	/*
	 * public int getInIndex() { return inIndex; }
	 * 
	 * public void setInIndex(int inIndex) { this.inIndex = inIndex; }
	 */

}
