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
	public int hits;
	
	public String toString()
	{
		return aPoint.toString() + ", " + hits + "hits";
	}
	
	// Constructors
	public PointObj()
	{
		aPoint = new myPoint(0,0);
		hits = 0;
	}
	
	public PointObj(Point p, int freq)
	{
		aPoint = new myPoint();
		aPoint.iPoint = p;
		hits = freq;
	}
}
