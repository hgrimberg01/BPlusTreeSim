/**
 * 
 */
package edu.ku.eecs;

/**
 * @author QtotheC
 *
 */
public class LeafNode extends TreeNode {
	private int[] keys;
	private int[] rids;
	private int siblingPtr;
	
	public LeafNode()
	{
		keys = new int[3];
		rids = new int[3];
		siblingPtr = -1;
	}

	@Override
	public int search(int key) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void insert(int key, int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isLeaf() {
		return true;
	}
}
