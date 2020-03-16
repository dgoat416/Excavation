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
	public int maxSum;
	
	public String toString()
	{
		if (hits > 1)
		return aPoint.toString() + ", " + hits + " hits" + ", " + maxSum + " maxSum";
		
		else if (hits == 1)
			return aPoint.toString() + ", " + hits + " hit" + ", " + maxSum + " maxSum";
		
		else 
		{
			System.out.print("Error in toString of " + aPoint.toString());
			System.exit(-1);
			return "ERROR";
		}
	}
	
	// Constructors
	public PointObj()
	{
		aPoint = new myPoint(0,0);
		hits = 0;
		maxSum = 0;
	}
	
	public PointObj(Point p, int freq, int max)
	{
		aPoint = new myPoint();
		aPoint.iPoint = p;
		hits = freq;
		maxSum = max;
	}
}
