// - insert should be log(n)
// - find median should be constant
// - remove the median should be log(n)
// a. Separate files for leftist heap, skew heap, and  dynamic median.

// b. Short main program for dynamic median which is just a driver (calling class methods).  

// c. All methods use javadoc style comments to explain method purpose, input parameters, and return values.

// d. Delete methods/code you are not using (unless it is part of optional debugging statements)
import java.util.*;
import java.io.File;
import java.text.DecimalFormat;

public class DynamicMedian<E extends Comparable<? super E>>
{
	public DynamicMedian(){
		Greater = new SkewHeap<E>();
		Less = new LeftistHeap<E>();
		median = null;
	}
	
	/****
	Goals: to put in new number and find a new median
	Para: the data 
	Returns: true if it got put in 
	***/
	public boolean insert(E data){
		if (median == null){
			median = data;
			return true;
		}
		if (data.compareTo(median) >=0){
			Greater.insert(data);
			if (sizeBigger() > (sizeSmaller() + 1)){
				Less.insert(median);
				median = getBigger();
			}
		}
		else {// data < median
			Less.insert(data);
			if ((sizeBigger() + 1)< sizeSmaller()){
				Greater.insert(median);
				median = getSmaller();
			}
		}
		return true; 
	}
	public E getMedian(){return median;}
	public E getBigger(){return Greater.deleteMin();}
	public E getSmaller(){return Less.deleteMax();}
	private int sizeBigger(){return Greater.getAmount();}
	private int sizeSmaller(){return Less.getAmount();}
	
	public void makeMedian(E x){this.median = x;}
	
	private SkewHeap<E> Greater;
	private LeftistHeap<E> Less;
	private E median; 
	
	public static void main(String [] args)
	{
		DynamicMedian<Integer> Salaries = new DynamicMedian<>();
		int iterations = 0; 
		DecimalFormat ft = new DecimalFormat("$###,###,###");
		Integer income = 0; 
		Integer [] money = new Integer[100];
		for (int i = 0; i < 100; i++) money[i] = i;
		for (int j = 0; j < 100; j++){
			Salaries.insert(money[j]);
			if (j%5 == 0) System.out.println("iteration " + j + " Salary: " + Salaries.getMedian());
		}
	}
}