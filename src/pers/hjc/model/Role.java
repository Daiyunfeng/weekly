package pers.hjc.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * 角色
 * 
 * @author Administrator
 * @data 2018年1月30日
 */
@Entity
@Table(name = "role")
public class Role implements Serializable
{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "role_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ID;

	@Column(length = 50, nullable = false)
	private String description;

	@Column(name = "is_use")
	private int isUse = 1;

	@ManyToMany
	@Cascade(CascadeType.SAVE_UPDATE)
	@JoinTable(name = "role_res", joinColumns = { @JoinColumn(name = "role_id") }, // JoinColumns定义本方在中间表的主键映射
	inverseJoinColumns = { @JoinColumn(name = "res_id") }) // inverseJoinColumns定义另一在中间表的主键映射
	private Set<Resource> resources;

	public Long getID()
	{
		return ID;
	}

	public void setID(Long ID)
	{
		this.ID = ID;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public int getIsUse()
	{
		return isUse;
	}

	public void setIsUse(int isUse)
	{
		this.isUse = isUse;
	}

	public Set<Resource> getResources()
	{
		return resources;
	}

	public void setResources(Set<Resource> resources)
	{
		this.resources = resources;
	}

}
