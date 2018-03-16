package pers.hjc.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

public class LoginMessage
{
	@NotNull(message = "学号不能为空")
	@Range(min = 1000000L, max = 9999999999999L, message = "学号位数错误")
	private Long userID;

	@NotBlank(message = "密码不能为空")
	@Size(min = 6, max = 20, message = "密码是6-20个字符的数字或字母组合")
	private String password;

	@NotBlank(message = "验证码不能为空")
	private String captcha;

	public Long getUserID()
	{
		return userID;
	}

	public void setUserID(Long userID)
	{
		this.userID = userID;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getCaptcha()
	{
		return captcha;
	}

	public void setCaptcha(String captcha)
	{
		this.captcha = captcha;
	}

}
