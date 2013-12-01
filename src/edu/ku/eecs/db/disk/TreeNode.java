/**
 * 
 */
package edu.ku.eecs.db.disk;

import java.nio.ByteBuffer;
import java.util.Arrays;

import edu.ku.eecs.db.APlusTree.InternalNode;
import edu.ku.eecs.db.APlusTree.KeyNotFoundException;
import edu.ku.eecs.db.APlusTree.LeafNode;
import edu.ku.eecs.db.APlusTree.LeafUnderflowException;


/**
 * @author QtotheC
 *
 */
public abstract class TreeNode {
	protected int[] keys;
	protected int[] pointers;
	protected int treeOrder;
	protected PageTable pages;
	protected boolean isRoot;
	
	public TreeNode(PageTable pages, int order) {
		this.pages = pages;
		treeOrder = order;
		isRoot = false;
	}
	
	public abstract int search(int key) throws Exception;
	
	public abstract void insert(int key, int value) throws Exception;
	
	public abstract void delete(int key) throws KeyNotFoundException, LeafUnderflowException, Exception;
	
	public abstract boolean isLeaf();
	
	protected abstract byte[] flatten();
	protected abstract void unflatten(byte[] bytes);
	
	public byte[] toBytes() {
		byte typeByte = (byte) (
				((isLeaf()) ? 0 : 1) +
				((isRoot()) ? 5 : 0));
		byte[] node = flatten();
		return ByteBuffer.allocate(node.length+1).put(typeByte).put(node).array();
	}
	
	public static TreeNode fromBytes(byte[] bytes, PageTable pages, int order) throws Exception {
		if (bytes[0] == 0 || bytes[0] == 5) { // leaf node
			LeafNode leaf = new LeafNode(pages, order);
			leaf.unflatten(Arrays.copyOfRange(bytes, 1, bytes.length));
			if (bytes[0] == 5) { leaf.isRoot(true); }
			return leaf;
		}
		else if (bytes[0] == 1 || bytes[0] == 6) { // internal node
			InternalNode node = new InternalNode(pages, order);
			node.unflatten(Arrays.copyOfRange(bytes, 1, bytes.length));
			if (bytes[0] == 6) { node.isRoot(true); }
			return node;
		}
		throw new Exception();
	}
	
	public TreeNode fromBytes(byte[] bytes) throws Exception {
		return fromBytes(bytes, pages, treeOrder);
	}
	
	public TreeNode getNode(int pageID) throws Exception {
		return fromBytes(pages.getIndexedPage(pageID).contents);
	}
	
	public void isRoot(boolean root) { isRoot = root; }
	public boolean isRoot() { return isRoot; }
	
	public abstract int numElements();
	public boolean isFull() {
		return numElements() >= treeOrder;
	}
	
	public int[] keys() { return keys; }
	public int[] pointers() { return pointers; }
}
