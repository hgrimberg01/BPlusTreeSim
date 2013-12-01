/**
 * 
 */
package edu.ku.eecs.db.APlusTree;

import edu.ku.eecs.db.disk.TreeNode;

/**
 * @author QtotheC
 *
 */
public class InternalNodeFullException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4477582053467017193L;
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
