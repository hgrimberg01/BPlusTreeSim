/**
 * 
 */
package edu.ku.eecs;

import java.nio.ByteBuffer;
import java.util.Arrays;


/**
 * @author QtotheC
 *
 */
public abstract class TreeNode {
	protected int numElements;
	protected int[] keys;
	protected int[] pointers;
	protected int treeOrder;
	protected PageTable pages; // TODO somehow this has to get information
	
	public TreeNode() {
		numElements = 0;
		treeOrder = 3;
	}
	
	public abstract int search(int key) throws Exception;
	
	public abstract void insert(int key, int value);
	
	public abstract void delete(int key);
	
	public abstract boolean isLeaf();
	
	protected abstract byte[] flatten();
	
	public byte[] toBytes() {
		byte typeByte = (byte) ((isLeaf()) ? 0 : 1);
		byte[] node = flatten();
		return ByteBuffer.allocate(node.length+1).put(typeByte).put(node).array();
	}
	
	public static TreeNode fromBytes(byte[] bytes) throws Exception {
		if (bytes[0] == 0) { // leaf node
			return LeafNode.unflatten(Arrays.copyOfRange(bytes, 1, bytes.length));
		}
		else if (bytes[0] == 1) { // internal node
			return InternalNode.unflatten(Arrays.copyOfRange(bytes, 1, bytes.length));
		}
		throw new Exception();
	}
	
	public TreeNode getNode(int pageID) throws Exception {
		return fromBytes(pages.getIndexedPage(pageID).contents);
	}
}
