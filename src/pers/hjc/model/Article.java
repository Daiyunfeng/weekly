package pers.hjc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 周报表article
 * 
 * @author Administrator
 */
@Entity
@Table(name = "article")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "operations", "roles", "menus" })
public class Article implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "article_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ID;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", unique = false, updatable = true, nullable = false)
	private User user;

	@Column(length = 50, nullable = false)
	private String title;

	@org.hibernate.annotations.UpdateTimestamp
	private Date updateTime;

	@Column(name = "is_use")
	private int isUse = 1;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "article_content_id", nullable = false)
	private ArticleContent articleContent;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "article")
	private Set<Comment> articleComment;

	public Long getID()
	{
		return ID;
	}

	public void setID(Long ID)
	{
		this.ID = ID;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
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

	public int getIsUse()
	{
		return isUse;
	}

	public void setIsUse(int isUse)
	{
		this.isUse = isUse;
	}

	public ArticleContent getArticleContent()
	{
		return articleContent;
	}

	public void setArticleContent(ArticleContent articleContent)
	{
		this.articleContent = articleContent;
	}

	public Set<Comment> getArticleComment()
	{
		return articleComment;
	}

	public void setArticleComment(Set<Comment> articleComment)
	{
		this.articleComment = articleComment;
	}

}
