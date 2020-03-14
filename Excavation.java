import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * 
 * @author Deron Washington II
 *
 */
public class Excavation
{

	/**
	 * Find Next occurrence of find and return the index
	 * directly following find
	 * @param find = the string to find within str
	 * @param str = the string where you looking for str
	 * @param i = index to start from
	 * @return
	 * 				= the index after find
	 * 					OR
	 * 				= -999 if not found
	 */
	public static int skipPastNext(String find, String str, int i)
	{
		int sizeOfFind = find.length();
		int originalI = i;
		int j = 0;

		for (; i < str.length(); i++)
		{		
			while (str.charAt(i) == find.charAt(j))
			{
				i++;
				j++;

				if (j == sizeOfFind)
				{
					return i + 1;
				}
			}

			// reset
			j = 0;
			i = originalI;
		}

		return -999;
	}

	/**
	 * Find next occurrence of the character and return
	 * the index directly following the character
	 * @param c = character we are looking to find
	 * @param str = the string where you are looking for c
	 * @param i = index to start from
	 * @return
	 * 				= the index after find
	 * 					OR
	 * 				= -999 if not found
	 * 			
	 */
	public static int skipPastNext(char c, String str, int i)
	{
		for (; i < str.length(); i++)
		{
			if (str.charAt(i) == c)
				return i + 1;
		}

		return -999;
	}

	/**
	 * Function to get rid of the white space in parameter
	 * @param str = string to operate on
	 * @return
	 * 				= the string without white space
	 */
	public static String noWhiteSpace(String str)
	{
		String temp = "";

		for (int i = 0; i < str.length(); i++)
			if (str.charAt(i) != 32)
				temp += str.charAt(i);

		return temp;
	}

	/**
	 * Return the integer that corresponds to the number
	 * in the parameter
	 * @param str = string value that will be converted
	 * @return
	 * 				= the integer version of str
	 * 					Ex. str = "11 " or "11" or "   11     " then return 11;
	 * 				OR
	 * 				= null
	 */
	public static Integer getInteger(String str)
	{
		try
		{
			return Integer.parseInt(noWhiteSpace(str));
		}
		catch (NumberFormatException NFE)
		{
			return null;
		}

	}

	/**
	 * Read input file and populate the inputArr
	 * @param _inputArr = the array to hold the contents of the input file
	 */
	public static void readInputFile(ArrayList<ArrayList<Integer>> _inputArr)
	{
		File inFile = new File("input.txt");
		int iRow = 0;
		int endingIndex = 0;
		String oneLine = "";
		String oneNum = "";
		Scanner read = null;

		try 
		{
			read = new Scanner(inFile);

			while (read.hasNext() == true)
			{				
				// grab one line from the file
				oneLine = read.nextLine();

				// instantiate a row
				_inputArr.add(new ArrayList<Integer>());

				// convert oneLine string to all integers
				for (int iCol = 0; iCol < oneLine.length(); iCol++)
				{
					// instantiate columns within row i
					if (iCol == 0)
						_inputArr.get(iRow).addAll(new ArrayList<Integer>());

					// 1. Get next int and add to inputArr
					endingIndex = skipPastNext(' ', oneLine, iCol);

					// if there is a space remaining within oneLine
					if (endingIndex != -999)
						oneNum = oneLine.substring(iCol, endingIndex);

					else // remainder of oneLine is a number
						oneNum = oneLine.substring(iCol);

					_inputArr.get(iRow).add(getInteger(oneNum));

					// update iCol
					iCol += oneNum.length() - 1;
					// end 1.
				}

				// go to next row in inputArr
				iRow++;
			}

		}
		catch (FileNotFoundException fnf)
		{
			fnf.printStackTrace();
			System.out.println("The input file could not be found.");
		}

		read.close();

	}

	/**
	 * Finds the largest Sum in an ArrayList
	 * @param oneRow = one Row of a multidimensional arrayList
	 * @return
	 * 				= the largest sum of a contiguous block of an arrayList
	 */
	public static int findLargestSum(ArrayList<Integer> oneRow)
	{
		int size = oneRow.size();
		int localMax = 0; 
		int globalMax = Integer.MIN_VALUE;

		for (int i = 0; i < size; i++)
		{
			localMax = Integer.max(oneRow.get(i), oneRow.get(i) + localMax);

			if (localMax > globalMax)
				globalMax = localMax;
		}

		return globalMax;
	}

	/**
	 * Find the window that holds the largestSum between left and right
	 * @param oneRow = the row with the largest size
	 * @return
	 * 				=  point that holds the size of the window that maximizes sum between
	 *	 				left side of window(first coordinate) of oneRow
	 *	 				right side of window(second coordinate) of oneRow
	 */
	public static Point findLargestWindowLR(ArrayList<Integer> oneRow, ArrayList<Integer> _maxes)
	{		
		int localMax = 0;
		int globalMax = Integer.MIN_VALUE;
		int tempLeft = 0;
		int tempRight = 0;

		for(int i = 0; i < oneRow.size(); i++)
		{
			// find local max by comparing the current row 
			// and local max + current row and determining the max 
			localMax = Integer.max(oneRow.get(i), localMax + oneRow.get(i));

			// only change tempLeft if starting the new max from oneRow[i]
			if (oneRow.get(i) == localMax) 
			{
				tempLeft = i;
				tempRight = i;
			}

			// change tempRight every iteration to update the new window
			else 
				tempRight = i;

			if (localMax > globalMax)
				globalMax = localMax;
		}

		// return globalMax to get the max sum
		_maxes.add(globalMax);
		return new Point(tempLeft, tempRight);
	}

	/**
	 * Find the window that holds the largestSum between top and bottom
	 * @param _maxes = the maxes of each line at the width specified by leftRight
	 * @param leftRight = the Point holding 
	 * 								(left side window index, right side window index)
	 * @return
	 * 				= point that holds the size of the window that maximizes sum between
	 * 					top of window (first coordinate) and bottom window (second coordinate)
	 */
	public static Point findLargestWindowTB(Point leftRight, ArrayList<ArrayList<Integer>> _inputArr)
	{
		int localMax = 0;
		int globalMax = Integer.MIN_VALUE;
		int tempBottom = 0;
		int tempTop = 0;

		//		for (int iRow = 0; iRow < 10; iRow++)
		//		{
		//			localMax = Integer.max(_maxes.get(iRow), _maxes.get(iRow) + localMax);
		//
		//			// only change tempLeft if starting the new max from oneRow[i]
		//			if (_maxes.get(iRow) == localMax) 
		//			{
		//				tempTop = iRow;
		//				tempBottom = iRow;
		//			}
		//
		//			// change tempRight every iteration to update the new window
		//			else 
		//				tempBottom = iRow;
		//
		//			if (localMax > globalMax)
		//			{
		//				globalMax = localMax;
		//			}
		//		}

		for (int j = leftRight.x; j < leftRight.y; j++)
		{
			localMax = 0;
			globalMax = Integer.MIN_VALUE;
			tempBottom = 0;
			tempTop = 0;
			for (int iRow = 0; iRow < 10; iRow++)
			{
				localMax = Integer.max(_inputArr.get(iRow).get(j), _inputArr.get(iRow).get(j) + localMax);

				// only change tempLeft if starting the new max from oneRow[i]
				if (_inputArr.get(iRow).get(j) == localMax) 
				{
					tempTop = iRow;
					tempBottom = iRow;
				}

				// change tempRight every iteration to update the new window
				else 
					tempBottom = iRow;

				if (localMax > globalMax)
				{
					globalMax = localMax;
				}
			}
		}

		return new Point(tempTop, tempBottom);
	}

	/**
	 * Determines the index of the largest row
	 * @param _inputArr = the structure to find the largest row in
	 * @return
	 * 				= index of the largest row
	 */
	public static int findLargestRow(ArrayList<ArrayList<Integer>> _inputArr)
	{
		int max = 0;
		int previousMax = 0;
		int currSize = 0;
		int row = 0;

		for (int i = 0; i < _inputArr.size(); i++)
		{
			currSize = _inputArr.get(i).size();
			max = Integer.max(currSize, max);

			// update row if find a new max
			if (previousMax != max)
			{
				row = i;
				previousMax = max;
			}
		}

		return row;
	}

	public static void main(String[] args)
	{
		// array to hold the contents of the input file
		ArrayList<ArrayList<Integer>> inputArr = new ArrayList<ArrayList<Integer>>();

		// array to hold the max sums of each row (dynamic programming)
		ArrayList<Integer> maxes = new ArrayList<Integer>();

		// array to hold the max sums of each row within optimal left - right window
		ArrayList<Integer> maxesWindow = new ArrayList<Integer>();

		// holds the points
		ArrayList<PointObj> pObjArr= new ArrayList<PointObj>();
		Point temp = new Point(0,0);
		int findIndex = -10;

		// populate inputArr
		readInputFile(inputArr);
		int n = inputArr.get(0).get(0);
		inputArr.remove(0);
		//System.out.print(findLargestRow(inputArr));

		// STEP 1
		// figure out how to find the biggest sum within oneLine (left and right)
		//int index = findLargestRow(inputArr);
		Random rand = new Random();
		boolean done = false;
		int rNum = -1;
		int upperB = 0;
		int lowerB = 0;

		for (int i = 0; i < n; i++)
		{
			temp = findLargestWindowLR(inputArr.get(i), maxes);
			//			findIndex = pObjArr.indexOf(temp);
			//
			//			// if temp already exists in pObjArr
			//			if ( findIndex != - 1)
			//				pObjArr.add(new PointObj(temp, pObjArr.get(findIndex).hits++));
			//
			//			else
			//				pObjArr.add(new PointObj(temp, 1));


			// sort as you insert
			if (pObjArr.isEmpty() == false)
			{
				upperB = pObjArr.size();
				lowerB = 0;
				while (done == false)
				{
					// random num between 0 - size of arrayList
					rNum = rand.nextInt(upperB) + lowerB; 

					if (rNum > pObjArr.size() - 1)
						rNum = upperB - 1;

					if (pObjArr.get(rNum).aPoint.compareTo(temp) == 0)
					{
						pObjArr.get(rNum).hits += 1;
						break;
					}

					// aPoint < temp ?
					else if (pObjArr.get(rNum).aPoint.compareTo(temp) == -1 )
					{
						// search greater than rNum but less than upperB
						lowerB = rNum + 1;
						rNum = rand.nextInt(upperB) + lowerB;

					}

					// aPoint > temp ?
					else if (pObjArr.get(rNum).aPoint.compareTo(temp) == 1)
					{
						// search less than rNum but greater than lowerB
						upperB = rNum;
						rNum = rand.nextInt(upperB) + lowerB;
					}

					if (upperB == lowerB)
					{
						if (rNum > pObjArr.size())
							rNum = pObjArr.size();

						pObjArr.add(rNum, new PointObj(temp, 1));
						break;
					}

					else if (upperB < lowerB)
					{
						System.out.print("Error %d");
					}

				}
			}

			else // empty
			{
				pObjArr.add(new PointObj(temp, 1));
			}
		}

		// find PointObj that appeared most
		Point most = new Point(-1, -1); // (index, hits) index of pObjArr that holds the window
		for (int i = 0; i < pObjArr.size(); i++)
		{
			if (pObjArr.get(i).hits > most.y)
			{
				// update most if found more hits
				most.x = i;
				most.y = pObjArr.get(i).hits;
			}
		}

		// temp now holds window
		temp = pObjArr.get(most.x).aPoint.iPoint;
		//findLargestWindowLR(inputArr.get(index));

		// STEP 2
		// then run through every length (distance between top and bottom) of the 
		// (distance between left and right) I found above
		// calculate the 
		//		for (int i = 0; i < n; i++)
		//		{
		//				//System.out.println(findLargestWindowLR(inputArr.get(i)));
		//				//System.out.println(maxes.add(findLargestSum(inputArr.get(i))));
		//				// ALREADY HAVE THE left and right window so try all possible top and bottom values
		//				//inputArr.get(temp.x)
		//			
		//			
		//		}

		// calculate sum between optimal window left to right
		int sum = 0;
		for (int j = 0; j < n; j++)
		{
			for (int i = temp.x; i <= temp.y; i++)
			{
				sum += inputArr.get(j).get(i);
			}
			maxesWindow.add(sum);
		}

		findLargestWindowTB(temp, inputArr);
	}

}
