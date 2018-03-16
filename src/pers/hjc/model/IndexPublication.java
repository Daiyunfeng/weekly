package pers.hjc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "index_publication")
public class IndexPublication
{
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ID;
	
	@Lob
	private String content;
	
	@Column(name = "is_use")
	private int isUse = 1;
	
	public IndexPublication()
	{
		super();
	}

	public IndexPublication(Long iD, String content)
	{
		super();
		ID = iD;
		this.content = content;
	}

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

	public int getIsUse()
	{
		return isUse;
	}

	public void setIsUse(int isUse)
	{
		this.isUse = isUse;
	}
	
	
}
