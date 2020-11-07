// - this will be used to store values that are greater than the median

// this one switches all the way back up 
public class SkewHeap<E extends Comparable<? super E>> // the min heap
{
	public SkewHeap(){
		root = null; 
		amount = 0;
	}
	
	/****
	Goals: inserts it in or makes a new root 
	Para: x - the data put in 
	Returns: true (for everything, but to say whether it got put in or not) 
	****/
	public boolean insert(E x){
		amount++;
		if (root == null){
			root = new NodeEntry<E>(x, null, null);
			return true;
		}
		NodeEntry<E> newEntry = new NodeEntry<>(x);
		root = merge(root, newEntry);
		return true; 
		//NodeEntry<E> current = root; 
	}
	
	/****
	Goals: merge 2 heaps together, then switches everything on way back up
	Para: mRight keeps track of main tree, mLeft is to keep track of inserting tree
	Returns: returns mRight to the parent's right pointer
	***/
	public NodeEntry<E> merge(NodeEntry<E> mRight, NodeEntry<E> mLeft){
		if (mRight == null) return mLeft;// attach whatever's left of the merging node 
		if (mRight.compareTo(mLeft) < 0){// if less than will go to next one 
			mRight.setDirection(1, merge(mRight.getDirection(1), mLeft));//
		}
		else{// mRight >= mLeft; if greater than will switch them and start again
			mRight = merge(mLeft, mRight);
		}
		//switches left and right 
		NodeEntry<E> current = mRight.getDirection(1);// will equal the right node 
		mRight.setDirection(1, mRight.getDirection(0));//
		mRight.setDirection(0, current);//
		return mRight; 
	}
	/****
	Goals: get the Min heap up 
	Para: N/A
	Returns: returns the number just bigger than the median 
	***/
	public E deleteMin(){
		E Min = root.getData();
		amount--;
		merge(root.getDirection(1), root.getDirection(0));
		return Min;
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
		
		public NodeEntry(E newData){
			this(newData, null, null);
		}
		public NodeEntry(E newData, NodeEntry<E> directionR, NodeEntry<E> directionL){
			data = newData;
			right = directionR;
			left = directionL;
		}
		
		//@Override
		public int compareTo(NodeEntry<E> compareNode){
			return data.compareTo(compareNode.getData());
		}
		public E getData(){return data;}
		public NodeEntry<E> getDirection(int RL){
			switch(RL){
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