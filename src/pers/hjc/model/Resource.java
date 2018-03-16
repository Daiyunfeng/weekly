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
 * 资源
 * 
 * @author Administrator
 * @data 2018年1月30日
 */
@Entity
@Table(name = "resource")
public class Resource implements Serializable
{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "res_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ID;

	@Column(name = "url", length = 255, nullable = false)
	private String resource;

	@Column(name = "is_use")
	private int isUse = 1;

	@ManyToMany
	@Cascade(CascadeType.SAVE_UPDATE) // 使用hibernate注解级联保存和更新
	@JoinTable(name = "role_res", joinColumns = { @JoinColumn(name = "res_id") }, // JoinColumns定义本方在中间表的主键映射
	inverseJoinColumns = { @JoinColumn(name = "role_id") }) // inverseJoinColumns定义另一在中间表的主键映射
	private Set<Role> roles;

	public Long getID()
	{
		return ID;
	}

	public void setID(Long ID)
	{
		this.ID = ID;
	}

	public String getResource()
	{
		return resource;
	}

	public void setResource(String resource)
	{
		this.resource = resource;
	}

	public int getIsUse()
	{
		return isUse;
	}

	public void setIsUse(int isUse)
	{
		this.isUse = isUse;
	}

	public Set<Role> getRoles()
	{
		return roles;
	}

	public void setRoles(Set<Role> roles)
	{
		this.roles = roles;
	}

}
