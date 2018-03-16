package pers.hjc.entity;

public class CutAndRotate
{
	private double x;
	private double y;
	private double width;
	private double height;
	private int rotate;

	public CutAndRotate()
	{
		super();
	}

	public CutAndRotate(double x, double y, double width, double height, int rotate)
	{
		super();
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

}
