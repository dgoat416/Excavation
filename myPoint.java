import java.awt.Point;

public class myPoint implements Comparable<Point>
{
	public Point iPoint;
	
	@Override
	public String toString()
	{
		return iPoint.toString();
	}
	/**
	* Compares two points
	* @param p = another point object for comparison
	 */
	@Override
	public int compareTo(Point p)
	{
		// TODO Auto-generated method stub
		if (iPoint.x == p.x)
		{
			if (iPoint.y == p.y)
				return 0;
			else if (iPoint.y < p.y)
				return -1;
			else if (iPoint.y > p.y)	
				return 1;
		}
		else if (iPoint.x < p.x )
		{
			return -1;
//			if (iPoint.y == p.y)
//				return -1;
//			else if (iPoint.y < p.y)
//				return -1;
//			else if (iPoint.y > p.y)	
//				return 1;
		}
		
		else if (iPoint.x > p.x)
			return 1;
		
		// error
		return -999;
	}

	public myPoint()
	{
		iPoint = new Point(0,0);
	}
	
	public myPoint(int x, int y)
	{
		iPoint  = new Point();
		iPoint.x = x;
		iPoint.y = y;
	}
	
	public myPoint(Point p)
	{
		iPoint = new Point();
		iPoint.x = p.x;
		iPoint.y = p.y;
	}
}
