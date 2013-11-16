/**
 * 
 */
package edu.ku.eecs;

/**
 * @author QtotheC
 * http://cheezburger.com/7902608384
 */
public class APlusTree {
	private int rootPage;
	
	public APlusTree() {
		// TODO create a new TreeNode, put it somewhere.
		LeafNode root = new LeafNode();
		byte[] flatRoot = root.toBytes();
	}
	
	public int search(int key) {
		// TODO get the page from the pageTable, reconstitute the node, and run search on it
		return -1;
	}
	
	public void delete(int key) {
		
	}
	
	public void insert(int key, int value) {
		
	}
}
