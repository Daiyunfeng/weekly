package pers.hjc.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "index_image")
public class IndexImage
{
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ID;

	@Column(nullable = false, columnDefinition = "varchar(255) default '/upload/indeximage/default.jpg'")
	private String image = "/upload/indeximage/default.jpg";

	@NotBlank(message = "标题不能为空")
	@Size(min = 0, max = 20, message = "标题不能超过20字")
	@Column(nullable = false, columnDefinition = "varchar(20) default '无题'")
	private String title = "无题";

	@Size(min = 0, max = 30, message = "描述不能超过30字")
	@Column(nullable = true, columnDefinition = "varchar(30) default ''")
	private String description = "";

	@Column(nullable = false)
	private int orders;

	public IndexImage()
	{
		super();
	}

	public IndexImage(Long iD, String image, String title, String description)
	{
		super();
		ID = iD;
		this.image = image;
		this.title = title;
		this.description = description;
	}

	public IndexImage(Long iD, String image, String title, String description, int orders)
	{
		super();
		ID = iD;
		this.image = image;
		this.title = title;
		this.description = description;
		this.orders = orders;
	}

	public Long getID()
	{
		return ID;
	}

	public void setID(Long iD)
	{
		ID = iD;
	}

	public String getImage()
	{
		return image;
	}

	public void setImage(String image)
	{
		this.image = image;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public int getOrders()
	{
		return orders;
	}

	public void setOrders(int orders)
	{
		this.orders = orders;
	}

}
