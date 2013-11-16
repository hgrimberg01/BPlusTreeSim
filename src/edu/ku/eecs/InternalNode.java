/**
 * 
 */
package edu.ku.eecs;

/**
 * @author QtotheC
 *
 */
public class InternalNode extends TreeNode {
	private int[] keys;
	private int[] pointers;
	
	public InternalNode()
	{
		keys = new int[2];
		pointers = new int[3];
	}

	/* (non-Javadoc)
	 * @see edu.ku.eecs.TreeNode#search(int)
	 */
	@Override
	public int search(int key) {
		// TODO Auto-generated method stub
		
		return 0;
	}

	/* (non-Javadoc)
	 * @see edu.ku.eecs.TreeNode#insert(int, int)
	 */
	@Override
	public void insert(int key, int value) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see edu.ku.eecs.TreeNode#delete(int)
	 */
	@Override
	public void delete(int key) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isLeaf() {
		return false;
	}

}
