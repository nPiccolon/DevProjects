
public class SortSearch<T> 
{
	
	
	/*
	 * selection sort method. Scans the entire list to find the subsequent smallest value, 
	 * and places that value in the next ascending position in the list.
	 */
	public void selectionSort (Comparable <T>[] list)
	{
		int min;
		Comparable<T> temp;
		for(int index = 0; index<list.length-1; index++)
		{
			min = index;
			for (int scan = index + 1; scan < list.length; scan++)
				if (list[scan].compareTo((T)list[min])<0)
					min = scan;
			
			temp = list[min];				//swaps the values
			list[min] = list[index];
			list[index] = temp;
			
		}
	} //end selectionSort
	
	
	
	/*
	 * binarySearch method. Relying on the sorted list, the method will find the middle value and split the list in half.
	 * It will decide which half to search based on whether the middle value is too high or too low,
	 * then find the middle value of the 50% list, and so on.
	 */
	public Comparable<T> binarySearch (Comparable<T>[] list, Comparable <T> target)
	{
		int min = 0, max = list.length - 1, mid = 0;
		boolean found = false;
		while (!found && min <= max)
		{
			mid = (min + max) / 2;
			if (list[mid].equals(target))
			{
				found = true;
				System.out.println ("Index: " + mid);    //prints the index location of the found object.
			}
			else 
				if (target.compareTo((T)list[mid]) < 0)
					max = mid - 1;
				else
					min = mid + 1;
		}
		
		if (found)
			return list[mid];
		else
			return null;
	}//end binarySearch
	
	
}//end class
