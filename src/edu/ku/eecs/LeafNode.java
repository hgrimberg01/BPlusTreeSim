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
	
	public LeafNode(PageTable pages, int order)
	{
		super(pages, order);
		keys = new int[treeOrder];
		Arrays.fill(keys, -1);
		pointers = new int[treeOrder];
		Arrays.fill(pointers, -1);
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
	public void insert(int key, int value) throws Exception {
		if (numElements() >= keys.length) {
			// no more room for insertion
			throw new LeafNodeFullException();
		}
		else {
			int insertIndex = numElements();
			for (int i=0; i<numElements(); i++) {
				if (keys[i] >= key || keys[i] == -1) {
					if (keys[i] == key) throw new KeyExistsException();
					insertIndex = i;
				}
			}
			for (int i=numElements()-1; i > insertIndex; i--) { // shift values down to make room for insertion
				keys[i+1] = keys[i];
				pointers[i+1] = pointers[i];
			}
			keys[insertIndex] = key;
			pointers[insertIndex] = value;
		}
	}
	
	public int insertionPoint(int key) throws KeyExistsException {
		for (int i=0; i<keys.length; i++ ) {
			if (keys[i] >= key || keys[i] == -1) {
				if (keys[i] == key) throw new KeyExistsException();
				return i;
			}
		}
		return numElements();
	}

	@Override
	public void delete(int key) {
		if (numElements() == 0) {
			// throw some exception
		}
		// TODO check if there will be an unusual case from this deletion
	}

	@Override
	public boolean isLeaf() {
		return true;
	}
	
	@Override
	protected byte[] flatten() {
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

	@Override
	protected void unflatten(byte[] array) {
		int keySize = 9;
		int ptrSize = 6;
		ByteBuffer buff = ByteBuffer.wrap(array);
		buff.order(ByteOrder.nativeOrder());
		for (int i=0; i<keys.length; i++) {
			keys[i] = buff.getInt();
			buff.position(buff.position()+(keySize-4));
			pointers[i] = buff.getInt();
			buff.position(buff.position()+(ptrSize-4));
		}
		siblingPtr = buff.getInt();
	}
	
	public int siblingPtr() { return siblingPtr; }
	public void siblingPtr(int ptr) { siblingPtr = ptr; }

	@Override
	public int numElements() {
		int counter = 0;
		for (int i=0; i<keys.length; i++) {
			if (keys[i] != -1) counter++;
			else break;
		}
		return counter;
	}
}
