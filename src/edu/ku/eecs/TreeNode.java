/**
 * 
 */
package edu.ku.eecs;


/**
 * @author QtotheC
 *
 */
public abstract class TreeNode {
	protected int numElements;
	protected int[] keys;
	protected int[] pointers;
	protected int treeOrder;
	
	public TreeNode() {
		numElements = 0;
		treeOrder = 3;
	}
	
	public abstract int search(int key);
	
	public abstract void insert(int key, int value);
	
	public abstract void delete(int key);
	
	public abstract boolean isLeaf();
	
	public abstract byte[] toBytes();
}
