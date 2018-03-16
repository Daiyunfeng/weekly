package pers.hjc.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 周报表实际内容article_content 不删除 根据article的is_use 判断是否使用
 * 
 * @author Administrator
 */
@Entity
@Table(name = "article_content")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "operations", "roles", "menus" })
public class ArticleContent implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "article_content_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ID;

	@Lob
	private String content;

	// 双向
	@OneToOne(cascade = CascadeType.REFRESH, mappedBy = "articleContent")
	private Article article;

	public Long getID()
	{
		return ID;
	}

	public void setID(Long ID)
	{
		this.ID = ID;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
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