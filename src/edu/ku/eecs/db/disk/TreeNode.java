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
 * @author QtotheC Abstract Class that simulates a given node a tree. Every node
 *         descends from this.
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

	/**
	 * Abstract method to search
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public abstract int search(int key) throws Exception;

	/**
	 * Abstract method to insert
	 * 
	 * @param key
	 * @param value
	 * @throws Exception
	 */
	public abstract void insert(int key, int value) throws Exception;

	/**
	 * Abstract method to delete
	 * 
	 * @param key
	 * @throws KeyNotFoundException
	 * @throws LeafUnderflowException
	 * @throws Exception
	 */
	public abstract void delete(int key) throws KeyNotFoundException,
			LeafUnderflowException, Exception;

	/**
	 * Abstract method to check if we are a leaf
	 * 
	 * @return
	 */
	public abstract boolean isLeaf();

	/**
	 * Abstract flatten to byte array
	 * 
	 * @return
	 */
	protected abstract byte[] flatten();

	/**
	 * Abstract unflatten from byte array
	 * 
	 * @param bytes
	 */
	protected abstract void unflatten(byte[] bytes);

	/**
	 * Helper function to flattent a node to bytes.
	 * 
	 * @return
	 */
	public byte[] toBytes() {
		byte typeByte = (byte) (((isLeaf()) ? 0 : 1) + ((isRoot()) ? 5 : 0));
		byte[] node = flatten();
		return ByteBuffer.allocate(node.length + 1).put(typeByte).put(node)
				.array();
	}

	/**
	 * Helper function to unflatten a node from bytes.
	 * 
	 * @param bytes
	 * @param pages
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public static TreeNode fromBytes(byte[] bytes, PageTable pages, int order)
			throws Exception {
		if (bytes[0] == 0 || bytes[0] == 5) { // leaf node
			LeafNode leaf = new LeafNode(pages, order);
			leaf.unflatten(Arrays.copyOfRange(bytes, 1, bytes.length));
			if (bytes[0] == 5) {
				leaf.isRoot(true);
			}
			return leaf;
		} else if (bytes[0] == 1 || bytes[0] == 6) { // internal node
			InternalNode node = new InternalNode(pages, order);
			node.unflatten(Arrays.copyOfRange(bytes, 1, bytes.length));
			if (bytes[0] == 6) {
				node.isRoot(true);
			}
			return node;
		}
		throw new Exception();
	}

	/**
	 * 
	 * @param bytes
	 * @return
	 * @throws Exception
	 */
	public TreeNode fromBytes(byte[] bytes) throws Exception {
		return fromBytes(bytes, pages, treeOrder);
	}

	/**
	 * 
	 * @param pageID
	 * @return
	 * @throws Exception
	 */
	public TreeNode getNode(int pageID) throws Exception {
		return fromBytes(pages.getIndexedPage(pageID).contents);
	}

	/**
	 * 
	 * @param root
	 */
	public void isRoot(boolean root) {
		isRoot = root;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isRoot() {
		return isRoot;
	}

	/**
	 * 
	 * @return
	 */
	public abstract int numElements();

	/**
	 * 
	 * @return
	 */
	public boolean isFull() {
		return numElements() >= treeOrder;
	}

	/**
	 * 
	 * @return
	 */
	public int[] keys() {
		return keys;
	}

	/***
	 * 
	 * @return
	 */
	public int[] pointers() {
		return pointers;
	}
}
