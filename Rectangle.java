import java.awt.Point;

public class Rectangle
{
	public int sum;
	public myPoint upperLeftCoordinate;
	public myPoint lowerRightCoordinate;
	
	// Default Constructor
	public Rectangle()
	{
		sum = 0;
		upperLeftCoordinate = new myPoint();
		lowerRightCoordinate = new myPoint();
	}
	
	// Parameterized Constructor
	public Rectangle(int maxSum, Point upperLeft, Point topB)
	{
		sum = maxSum;
		upperLeftCoordinate = new myPoint(upperLeft);
		lowerRightCoordinate = new myPoint(topB);
	}
	
	// Parameterized Constructor
	public Rectangle(int maxSum, myPoint leftR, myPoint topB)
	{
		sum = maxSum;
		upperLeftCoordinate = leftR;
		lowerRightCoordinate = topB;
	}
}
