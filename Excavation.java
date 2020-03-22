import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Program to determine the most optimal rectangle for generating the largest
 * sum of integers from a given input file
 * 
 * @author Deron Washington II
 *
 */
public class Excavation
{
	/**
	 * Read input file and populate the input array
	 * 
	 * @param _inputArr = 2d array to hold the contents of the input file
	 * @return = the populated array
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
				for (int iCol = 0; iCol < N; iCol++)
				{
					// store current integer value in array
					currInt = scan.nextInt();
					_inputArr[iRow][iCol] = currInt;
				}

			}
		} catch (FileNotFoundException FNF)
		{
			FNF.printStackTrace();
			System.out.println("File is not found.");
		} finally
		{
			scan.close();
		}

		return _inputArr;
	}

	/**
	 * Write output file
	 * 
	 * @param upperLeft = upperLeft corner of rectangle that contains max sum
	 * @param lowerRight = lowerRight corner of rectangle that contains max sum
	 */
	public static void writeToOutputFile(Point upperLeft, Point lowerRight)
	{
		File outFile = new File("output.txt");
		PrintWriter pWriter = null;

		try
		{
			pWriter = new PrintWriter(outFile);

			upperLeft.x++;
			upperLeft.y++;
			lowerRight.x++;
			lowerRight.y++;
			
			pWriter.write(upperLeft.x + " " + upperLeft.y + "\n");
			pWriter.write(lowerRight.x + " " + lowerRight.y + "\n");
			
		} catch (FileNotFoundException fnf)
		{
			fnf.printStackTrace();
			System.out.println("The output file could not be found.");
		} finally
		{
			pWriter.close();
		}
	}

	/**
	 * Find the maximum sum and the largest rectangle
	 * that produces the maximum sum
	 * @param matrix = 2d array
	 * @return
	 * 				= a Rectangle object which holds the max sum,
	 * 				   the upper left corner of the rectangle that has
	 * 				   the max sum and the lower right corner of the 
	 * 				   rectangle that produces the max sum
	 */
	public static Rectangle maxSumRectangle(int [][] matrix) 
	{
		int numOfRows = matrix.length;
		int numOfCols = matrix[0].length;
		int tempTop = 0;
		int tempBottom = 0;
		myPoint globalLR = null;
		PointObj tempLR = null;
		int accumulatingSum[][] = new int[numOfRows][numOfCols];
		int localMax = 0;
		int globalMax = 0;
		int top = 0;
		int bottom = 0;


		for (int rowStart = 0; rowStart < numOfRows; rowStart++) 
		{
			// grab a row from input array
			for (int iCol = 0; iCol < numOfCols; iCol++) 
			{
				accumulatingSum[rowStart][iCol] = matrix[rowStart][iCol];
			}

			// setup
			tempTop = rowStart;
			tempBottom = rowStart;

			
			for (int rowEnd = rowStart; rowEnd < numOfRows; rowEnd++) 
			{
				// find the optimal window in one row (left to right)
				tempLR = kadane(rowEnd, accumulatingSum);
				int sum = tempLR.maxSum;

				// find largest sum for each row
				localMax = Math.max(localMax, sum);

				if (localMax == sum)
				{
					// update right window
					tempBottom = rowEnd;
				}

				else
				{
					// reset
					tempBottom = rowEnd + 1;
				}

				if (localMax > globalMax)
				{
					globalMax = localMax;
					top = tempTop;
					bottom = tempBottom;
					globalLR = tempLR.aPoint;
				}

				// update the sum of the accumulated rows
				if (rowEnd+1 < numOfRows) 
				{
					for (int c = 0; c < numOfCols; c++) 
					{
						accumulatingSum[rowEnd+1][c] = accumulatingSum[rowEnd][c] + matrix[rowEnd+1][c];
					}
				}
			}
		}

		// create the unique identifiers of this rectangle using the top 
		// bottom window and the left right window
		myPoint upperLeft = new myPoint(top, globalLR.iPoint.x);
		myPoint lowerRight = new myPoint(bottom, globalLR.iPoint.y);
		
		return new Rectangle(globalMax, upperLeft, lowerRight);
	}

	/**
	 * Determines the window that produces the largest sum
	 * when the numbers at all the indices within the window 
	 * are added together
	 * @param indexOfRow = index of the row
	 * @param preSum = holds the accumulating sum of all rows 
	 * 								 up to row equivalent to indexOfRow - 1
	 * @return
	 * 				= object containing the maximum sum in this row
	 * 				   and the window where this maximum sum
	 * 				   occurred
	 */
	public static PointObj kadane(int indexOfRow, int[][] preSum) 
	{
		int[] arr = preSum[indexOfRow];
		int localMax = Integer.MIN_VALUE;
		int globalMax = Integer.MIN_VALUE;
		int sum = 0;
		int negSum = Integer.MIN_VALUE;
		int tempLeft = 0;
		int tempRight = 0;
		int left = 0;
		int right = 0;

		for (int iRow = 0; iRow < arr.length; iRow++) 
		{
			// if sum is greater than 0 continue adding
			if (sum + arr[iRow] > 0) 
			{
				sum = sum + arr[iRow];
			}
			else // reset if sum become negative
			{
				sum = 0;
				negSum = sum + arr[iRow];
				
				// reset
				if (iRow + 1 < arr.length)
				{
					tempLeft = iRow + 1;
					tempRight = iRow + 1;
				}
			}

			localMax = Math.max(sum, localMax);

			// update the ending of the window
			if (localMax == sum && sum > 0)
			{
				// update right side of window
				tempRight = iRow;
			}

			// update global max even if the global max is negative
			if (negSum > globalMax)
			{
				globalMax = negSum;
				left = iRow;
				right = iRow;
				continue;
			}

			// update global max if local max is greater than global max (positive)
			if (localMax > globalMax)
			{
				globalMax = localMax;
				left = tempLeft;
				right = tempRight;
			}
		}

		// create object to return
		return new PointObj(localMax, new Point(left, right));
	}

	public static void main(String[] args)
	{

		int [][] inputArr = null;
		inputArr = readInputFile(inputArr);
		Rectangle optimalRectangle = null;
		optimalRectangle = maxSumRectangle(inputArr);
		writeToOutputFile(optimalRectangle.upperLeftCoordinate.iPoint, optimalRectangle.lowerRightCoordinate.iPoint);

	}

}
