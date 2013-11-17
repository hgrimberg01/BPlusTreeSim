/**
 * 
 */
package edu.ku.eecs;

/**
 * @author QtotheC
 *
 */
public class InternalNodeFullException extends Exception {
	public int tinyPtr;
	public TreeNode tinyNode;
	public int bigPtr;
	public TreeNode bigNode;
	
	public InternalNodeFullException(int tinyPtr, TreeNode tiny, int bigPtr, TreeNode big) {
		this.tinyPtr = tinyPtr;
		this.tinyNode=tiny;
		this.bigPtr=bigPtr;
		this.bigNode=big;
	}
}
