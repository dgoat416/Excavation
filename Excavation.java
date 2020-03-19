import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Program to determine the most optimal
 * rectangle for generating the largest sum
 * of integers from a given input file
 * @author Deron Washington II
 *
 */
public class Excavation
{
	/**
	 * Read input file and populate the input array
	 * @param _inputArr = 2d array to hold the contents of the input file
	 * @return
	 * 				= the populated array
	 */
	public static int[][] readInputFile(int[][] _inputArr)
	{
		int currInt = 0;
		File inFile = new File("input.txt");
		Scanner scan = null;

		try
		{
			scan = new Scanner(inFile);
			final int N = scan.nextInt();

			// declare arrays since we now know the size
			_inputArr = new int[N][N];

			// compute the sums of rows and cols as the input is read
			for (int iRow = 0; iRow < N; iRow++)
			{
				for(int iCol = 0; iCol < N; iCol++)
				{
					// store current integer value in array
					currInt = scan.nextInt();
					_inputArr[iRow][iCol] = currInt;
				}		

			}
		}
		catch(FileNotFoundException FNF)
		{
			FNF.printStackTrace();
			System.out.println("File is not found.");
		}
		finally
		{
			scan.close();
		}

		return _inputArr;
	}

	/**
	 * Write output file 
	 * @param _leftRightWindow = optimal left and right window
	 * @param _topBottomWindow = optimal top and bottom window
	 */
	public static void writeToOutputFile(Point _leftRightWindow, Point _topBottomWindow)
	{
		File outFile = new File("output.txt");
		PrintWriter pWriter = null;

		try 
		{
			pWriter = new PrintWriter(outFile);

			Point temp = new Point(1,1);
			_leftRightWindow.x = _leftRightWindow.x + temp.x;
			_leftRightWindow.y = _leftRightWindow.y + temp.y;
			_topBottomWindow.x = _topBottomWindow.x + temp.x;
			_topBottomWindow.y = _topBottomWindow.y + temp.y;


			//pWriter.write(_leftRightWindow.x + " " + _topBottomWindow.x + "\n");
			//pWriter.write(_leftRightWindow.y + " " + _topBottomWindow.y + "\n");
			
			pWriter.write(_topBottomWindow.x + " " + _leftRightWindow.x + "\n");
			pWriter.write(_topBottomWindow.y + " " + _leftRightWindow.y + "\n");
		}
		catch (FileNotFoundException fnf)
		{
			fnf.printStackTrace();
			System.out.println("The output file could not be found.");
		}
		finally
		{	
			pWriter.close();
		}
	}

	/**
	 * Find the optimal rectangle to house the largest sum
	 * @param _inputArr = 2d input array
	 * @return
	 * 				= an array of point variables 
	 * 					[0] = the optimal left-right window
	 * 					[1] = the optimal top-bottom window
	 */
	private static Point[] findOptimalRectangle(int [][] _inputArr) 
	{ 
		int n = _inputArr.length;  
		
		// houses the total sum at current row (increasing by column)
		int totalSumCurrRow[][] = new int[n+1][n]; 

		// get the sums as you go down row by row 
		// each row is a sum of the rows above it
		// use these later to find the maximum sum by 
		// eliminating and adding rows and then finding out which
		// ones create the largest sum
		for(int i = 0; i < n; i++) 
		{ 
			for(int j = 0; j < n; j++) 
			{ 
				totalSumCurrRow[i+1][j] = totalSumCurrRow[i][j] + _inputArr[i][j]; 
			} 
		} 

		int globalSum = -999; 
		int localSum = 0; 
		int top = 0;
		int bottom = 0;
		int left = 0;
		int right = 0; 
		int curColStart = 0; 
		
		// try every combination and track the sum using the array created above
		for(int rowStart = 0; rowStart < n; rowStart++) 
		{ 
			for(int row = rowStart; row < n; row++)
			{ 
				// reinitialize
				localSum = 0; 
				curColStart = 0; 
				
				for(int col = 0; col < n; col++) 
				{ 
					localSum += totalSumCurrRow[row+1][col] - totalSumCurrRow[rowStart][col]; 
					
					// update globalSum if the localSum is greater
					if(globalSum < localSum) 
					{ 
						globalSum = localSum; 
						top = rowStart; 
						bottom = row; 
						left = curColStart; 
						right = col; 
					} 
				} 
			} 
		} 

		Point[] optimalRectangle = new Point[2];
		optimalRectangle[0] = new Point(left, right);
		optimalRectangle[1] = new Point(top, bottom);
		
		return optimalRectangle; 
	} 

	public static void main(String[] args)
	{
		
		int [][] inputArr = null;
		inputArr = readInputFile(inputArr);
		Point[] optimalWindow = null;
		optimalWindow = findOptimalRectangle(inputArr);
		writeToOutputFile(optimalWindow[0], optimalWindow[1]);

	}

}
