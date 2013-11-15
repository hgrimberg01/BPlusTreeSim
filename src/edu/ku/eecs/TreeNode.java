/**
 * 
 */
package edu.ku.eecs;

/**
 * @author QtotheC
 *
 */
public abstract class TreeNode {
	
	public TreeNode() {
		
	}
	
	public abstract int search(int key);
	
	public abstract void insert(int key, int value);
	
	public abstract void delete(int key);
	
	public abstract boolean isLeaf();
}
