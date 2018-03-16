package pers.hjc.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "article_comment")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "operations", "roles", "menus" })
public class Comment
{
	@Id
	@Column(name = "article_conmment_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ID;

	@Lob
	private String content;

	@org.hibernate.annotations.UpdateTimestamp
	private Date updateTime;

	@Column(name = "is_use")
	private int isUse = 1;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", unique = false, updatable = false, nullable = false)
	private User user;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "article_id", unique = false, updatable = true, nullable = false)
	private Article article;

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

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	public int getIsUse()
	{
		return isUse;
	}

	public void setIsUse(int isUse)
	{
		this.isUse = isUse;
	}

	public Article getArticle()
	{
		return article;
	}

	public void setArticle(Article article)
	{
		this.article = article;
	}

}
