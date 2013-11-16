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
public class LeafNode extends TreeNode {
	private int siblingPtr;
	
	public LeafNode()
	{
		keys = new int[treeOrder];
		Arrays.fill(keys, -1);
		pointers = new int[treeOrder];
		Arrays.fill(keys, -1);
		siblingPtr = -1;
	}

	@Override
	public int search(int key) {
		for (int i=0; i< keys.length; i++) {
			if (key == keys[i]) return pointers[i];
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
			pointers[insertIndex] = value;
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
	
	@Override
	public byte[] toBytes() {
		int keySize = 9;
		int ptrSize = 6;
		int siblingPtrSize = 4;
		ByteBuffer buff = ByteBuffer.allocate(keySize * treeOrder + ptrSize * treeOrder + siblingPtrSize);
		buff.order(ByteOrder.nativeOrder());
		for (int i=0; i<keys.length; i++) {
			buff.putInt(keys[i]);
			buff.position(buff.position()+(keySize-4));
			buff.putInt(pointers[i]);
			buff.position(buff.position()+(ptrSize-4));
		}
		buff.putInt(siblingPtr);
		return buff.array();
	}
}
