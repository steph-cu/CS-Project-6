// this will be used to store values less than the median

import java.lang.*;
public class LeftistHeap<E extends Comparable<? super E>> // the max heap
{
	public LeftistHeap(){
		root = null; 
		amount = 0;
	}
	/****
	Goals: inserts it in or makes a new root 
	Para: x - the data put in 
	Returns: true (for everything, but to say whether it got put in or not) 
	****/
	public boolean insert(E x){
		if (root == null){
			root = new NodeEntry<E>(x, null, null);
			amount++; 
			return true;
		}
		NodeEntry<E> newEntry = new NodeEntry<>(x);
		root = merge(root, newEntry);
		amount++;
		return true; 
	}
	/****
	Goals: merge 2 heaps together, then switches if the left null path is less than the right null path
	Para: mRight keeps track of main tree, mLeft is to keep track of inserting tree
	Returns: returns mRight to the parent's right pointer
	***/
	public NodeEntry<E> merge(NodeEntry<E> mRight, NodeEntry<E> mLeft){
		if (mRight == null) return mLeft;// attach whatever's left of the merging node 
		if (mRight.compareTo(mLeft) > 0){// if greater will go to next one 
			mRight.setDirection(1, merge(mRight.getDirection(1), mLeft));//
		}
		else{// mRight <= mLeft; if less than will switch places and start again
			mRight = merge(mLeft, mRight);
		}
		if (mRight.getDirection(0) != null && mRight.getDirection(1) != null){// as we go back up we need to make changes to needed nullPaths(some will be reset as same thing)
			int leftNode = mRight.getDirection(0).getNullPath();
			int rightNode = mRight.getDirection(1).getNullPath();
			mRight.setNullPath(Math.min(leftNode, rightNode) + 1);
		}
		if (mRight.getDirection(1) == null||mRight.getDirection(0) == null || mRight.getDirection(0).getNullPath() < mRight.getDirection(1).getNullPath()){// if the left NullPath is less than the right, then switch them
			NodeEntry<E> current = mRight.getDirection(1);// will equal the right node 
			mRight.setDirection(1, mRight.getDirection(0));//
			mRight.setDirection(0, current);//
		}
		return mRight;
	}
	/****
	Goals: get the max heap up 
	Para: N/A
	Returns: returns the number just smaller than the median 
	***/
	public E deleteMax(){
		E max = root.getData();
		amount--;
		root = merge(root.getDirection(1), root.getDirection(0));
		return max;
	}
	/****
	Goals: returns how many nodes are in here
	Para:
	Returns: returns size of tree 
	***/
	public int getAmount(){return amount;}
	
	private NodeEntry<E> root;
	private int amount;
	
	private static class NodeEntry<E extends Comparable<? super E>>
	{
		private E data;
		private NodeEntry<E> right; 
		private NodeEntry<E> left;
		private int nullPath; 
		
		public NodeEntry(E newData){
			this(newData, null, null);
		}
		public NodeEntry(E newData, NodeEntry<E> directionR, NodeEntry<E> directionL){
			data = newData;
			right = directionR;
			left = directionL;
			nullPath = 0; 
		}
		
		//@Override
		public int compareTo(NodeEntry<E> compareNode){
			return data.compareTo(compareNode.getData());
		}
		
		public E getData(){return data;}
		public int getNullPath(){
			if (this == null) return -1;
			return nullPath;}
		public void setNullPath(int x){nullPath = x;}
		public NodeEntry<E> getDirection(int RL){
			switch(RL) {
				case 0: return left; 
				case 1: return right; 
				default: return null;
			}
		}
		public void setDirection(int RL, NodeEntry<E> next){
			switch(RL){
				case 0: {
					left = next;
					break;
				}
				case 1: {
					right = next; 
					break;
				}
				default: break;
			}
		}
	}
}