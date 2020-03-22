import java.awt.Point;

/**
 * 
 * @author Deron Washington II
 *
 */
public class PointObj
{

	// data members
	public myPoint aPoint;
	public int maxSum;
	
	public String toString()
	{
		return aPoint.toString() + ", "  + ", " + maxSum + " maxSum";
	}
	
	// Constructors
	public PointObj()
	{
		aPoint = new myPoint(0,0);
		maxSum = 0;
	}
	
	public PointObj(int sum, Point p)
	{
		aPoint = new myPoint();
		aPoint.iPoint = p;
		maxSum = sum;
	}
}
