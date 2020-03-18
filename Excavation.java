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
	 * 				= an array of point variables 
	 * 					[0] = the optimal left-right window
	 * 					[1] = the optimal top-bottom window
	 */
	public static Point[] readInputFile(int[][] _inputArr)
	{
		int[] colSums = null;
		int[] rowSums = null;
		Point[] optimalWindows = new Point[2];
		int currInt = 0;
		File inFile = new File("input.txt");
		Scanner scan = null;

		try
		{
			scan = new Scanner(inFile);
			final int N = scan.nextInt();
			
			// declare arrays since we now know the size
			_inputArr = new int[N][N];
			colSums = new int[N];
			rowSums = new int[N];
			
			// compute the sums of rows and cols as the input is read
			for (int iRow = 0; iRow < N; iRow++)
			{
				for(int iCol = 0; iCol < N; iCol++)
				{
					// store current integer value in array
					currInt = scan.nextInt();
					_inputArr[iRow][iCol] = currInt;
					
					// compute sum for row 
					rowSums[iRow] += currInt;
					
					// compute col sum
					colSums[iCol] += currInt;
				}					
			}
			
			// get left-right optimal window
			Point leftRightWindow = findOptimalWindow(rowSums);
			Point topBottomWindow = new Point(0,0);
			
			// 2A. REDEFINE THE COL SUMS ARRAY BASED ON 
			// THE OPTIMAL ROW WINDOW IF NECESSARY
			
			// this is how big to make the array of colSums after the 
			// optimalWindow for left to right is found
			int colSumsLength = (leftRightWindow.y - leftRightWindow.x) + 1;
	
			// no need for new array because it is the same array
			if (colSumsLength == colSums.length)
				topBottomWindow = findOptimalWindow(colSums);
			
			// create and populate new array
			else
			{
				int[] newColSums = new int[colSumsLength];
				int index = leftRightWindow.x;
				
				// populate new array
				for (int iRow = 0; iRow < colSumsLength; iRow++)
				{
					newColSums[iRow] = colSums[index];
					
					//update
					index++;
				}
				
				// get top-bottom optimal window
				topBottomWindow = findOptimalWindow(newColSums);
			}
			
			// store the optimalWindow Rectangle coordinates
			optimalWindows[0] = leftRightWindow;
			optimalWindows[1] = topBottomWindow;
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
		
		return optimalWindows;
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


			pWriter.write(_leftRightWindow.x + " " + _topBottomWindow.x + "\n");
			pWriter.write(_leftRightWindow.y + " " + _topBottomWindow.y + "\n");
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
	 * Find the window that holds the largestSum between left and right
	 * @param oneRow = the row with the largest size
	 * @return
	 * 				=  point that holds the size of the window that maximizes sum between
	 *	 				left side of window(first coordinate) of oneRow
	 *	 				right side of window(second coordinate) of oneRow
	 */
	public static Point findOptimalWindow(int[] oneRow)
	{		
		int localMax = 0;
		int globalMax = Integer.MIN_VALUE;
		int localTempLeft = 0;
		int localTempRight = 0;
		int globalTempLeft = 0;
		int globalTempRight = 0;

		for(int i = 0; i < oneRow.length; i++)
		{
			// find local max by comparing the current row 
			// and local max + current row and determining the max 
			localMax = Integer.max(oneRow[i], localMax + oneRow[i]);

			// only change tempLeft if starting the new max from oneRow[i] & not at the end
			if (oneRow[i] == localMax && i != oneRow.length - 1) 
			{
				localTempLeft = i;
				localTempRight = i;
			}

			// change tempRight every iteration to update the new window
			else 
				localTempRight = i;

			if (localMax > globalMax)
			{
				globalMax = localMax;
				globalTempLeft = localTempLeft;
				globalTempRight = localTempRight;
			}
		}

		return new Point(globalTempLeft, globalTempRight);
	}


	public static void main(String[] args)
	{
		//final long startTime = System.currentTimeMillis();
		
		int [][] inputArr = null;
		Point[] optimalWindow = readInputFile(inputArr);
		writeToOutputFile(optimalWindow[0], optimalWindow[1]);
		
		//final long endTime = System.currentTimeMillis();

		//System.out.println("Total execution time: " + (endTime - startTime));

	}

}
