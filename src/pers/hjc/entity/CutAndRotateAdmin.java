package pers.hjc.entity;

/**
 * JackSon 默认是驼峰式..所以如果为ID的话无法转..之前都写的ID还好是用spring注入
 * 
 * @author Administrator
 *
 */
public class CutAndRotateAdmin
{
	private Long iD;
	private double x;
	private double y;
	private double width;
	private double height;
	private int rotate;

	public CutAndRotateAdmin()
	{
		super();
	}

	public CutAndRotateAdmin(Long iD, double x, double y, double width, double height, int rotate)
	{
		super();
		this.iD = iD;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.rotate = rotate;
	}

	public double getX()
	{
		return x;
	}

	public void setX(double x)
	{
		this.x = x;
	}

	public double getY()
	{
		return y;
	}

	public void setY(double y)
	{
		this.y = y;
	}

	public double getWidth()
	{
		return width;
	}

	public void setWidth(double width)
	{
		this.width = width;
	}

	public double getHeight()
	{
		return height;
	}

	public void setHeight(double height)
	{
		this.height = height;
	}

	public int getRotate()
	{
		return rotate;
	}

	public void setRotate(int rotate)
	{
		this.rotate = rotate;
	}

	public Long getiD()
	{
		return iD;
	}

	public void setiD(Long iD)
	{
		this.iD = iD;
	}

}
