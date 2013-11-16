/**
 * 
 */
package edu.ku.eecs;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * @author QtotheC
 *
 */
public class InternalNode extends TreeNode {
	private int[] keys;
	private int[] pointers;
	
	public InternalNode()
	{
		keys = new int[treeOrder-1];
		Arrays.fill(keys, -1);
		pointers = new int[treeOrder];
		Arrays.fill(pointers, -1);
	}

	/* (non-Javadoc)
	 * @see edu.ku.eecs.TreeNode#search(int)
	 */
	@Override
	public int search(int key) throws Exception {
		for (int i=0; i<keys.length;i++) {
			if (key <= keys[i]) {
				return getNode(pointers[i]).search(key);
			}
		}
		// Hasn't been found at other pointers. Search last pointer.
		return getNode(pointers[treeOrder - 1]).search(key);
	}

	/* (non-Javadoc)
	 * @see edu.ku.eecs.TreeNode#insert(int, int)
	 */
	@Override
	public void insert(int key, int value) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see edu.ku.eecs.TreeNode#delete(int)
	 */
	@Override
	public void delete(int key) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isLeaf() {
		return false;
	}
	
	@Override
	protected byte[] flatten() {
		int keySize = 9;
		int ptrSize = 4;
		ByteBuffer buff = ByteBuffer.allocate(keySize * (treeOrder - 1) + ptrSize * treeOrder);
		buff.order(ByteOrder.nativeOrder());
		buff.putInt(pointers[0]);
		buff.position(buff.position()+(ptrSize-4));
		for (int i=0; i<keys.length; i++) {
			buff.putInt(keys[i]);
			buff.position(buff.position()+(keySize-4));
			buff.putInt(pointers[i+1]);
			buff.position(buff.position()+(ptrSize-4));
		}
		return buff.array();
	}

	public static TreeNode unflatten(byte[] array) {
		int keySize = 9;
		int ptrSize = 4;
		ByteBuffer buff = ByteBuffer.wrap(array);
		buff.order(ByteOrder.nativeOrder());
		InternalNode newNode = new InternalNode();
		newNode.pointers[0] = buff.getInt();
		buff.position(buff.position()+(ptrSize-4));
		for (int i=0; i<newNode.keys.length; i++) {
			newNode.keys[i] = buff.getInt();
			buff.position(buff.position()+(keySize-4));
			newNode.pointers[i+1] = buff.getInt();
			buff.position(buff.position()+(ptrSize-4));
		}
		return newNode;
	}

}
