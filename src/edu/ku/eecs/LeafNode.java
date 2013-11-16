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
		keys = new int[treeOrder];
		rids = new int[treeOrder];
		siblingPtr = -1;
	}

	@Override
	public int search(int key) {
		for (int i=0; i< keys.length; i++) {
			if (key == keys[i]) return rids[i];
		}
		return -1;
	}

	@Override
	public void insert(int key, int value) {
		if (numElements > keys.length -1) {
			// no more room for insertion
			// throw some exception
		}
		else {
			int insertIndex = numElements;
			keys[insertIndex] = key;
			rids[insertIndex] = value;
		}
	}

	@Override
	public void delete(int key) {
		if (numElements == 0) {
			// throw some exception
		}
		// TODO check if there will be an unusual case from this deletion
	}

	@Override
	public boolean isLeaf() {
		return true;
	}
}
