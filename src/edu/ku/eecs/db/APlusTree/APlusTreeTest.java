/**
 * 
 */
package edu.ku.eecs.db.APlusTree;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

/**
 * @author QtotheC
 *
 */
public class APlusTreeTest {
	APlusTree treeUnderTest;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		treeUnderTest = new APlusTree();
	}
	
	/**
	 * Test method for {@link edu.ku.eecs.db.APlusTree.APlusTree#insert(int, int)}.
	 * @throws Exception 
	 */
	@Test
	public void testInsert() throws Exception {
		// Test adding 3 elements, non-ordered
		int[] addQueue = new int[] { 3, 9, 6 };
		for (int i=0; i<addQueue.length; i++) {
			treeUnderTest.insert(addQueue[i], addQueue[i]+1);
		}
		for (int i=0; i<addQueue.length; i++) {
			assertEquals(treeUnderTest.search(addQueue[i]), addQueue[i]+1);
		}
		
		// Test adding another element, non-ordered. This should make the root split.
		addQueue = new int[] { 4 };
		for (int i=0; i<addQueue.length; i++) {
			treeUnderTest.insert(addQueue[i], addQueue[i]+1);
		}
		for (int i=0; i<addQueue.length; i++) {
			assertEquals(treeUnderTest.search(addQueue[i]), addQueue[i]+1);
		}
		
		// Test adding two more elements. This should cause another split, and fill the root.
		addQueue = new int[] { 5, 8 };
		for (int i=0; i<addQueue.length; i++) {
			treeUnderTest.insert(addQueue[i], addQueue[i]+1);
		}
		for (int i=0; i<addQueue.length; i++) {
			assertEquals(treeUnderTest.search(addQueue[i]), addQueue[i]+1);
		}
		
		// Test adding two more elements. This should cause another split, and cause the root to split.
		addQueue = new int[] { 7, 10 };
		for (int i=0; i<addQueue.length; i++) {
			treeUnderTest.insert(addQueue[i], addQueue[i]+1);
		}
		for (int i=0; i<addQueue.length; i++) {
			assertEquals(treeUnderTest.search(addQueue[i]), addQueue[i]+1);
		}
		
		// Test adding two more elements. This should cause another split.
		addQueue = new int[] { 11, 12 };
		for (int i=0; i<addQueue.length; i++) {
			treeUnderTest.insert(addQueue[i], addQueue[i]+1);
		}
		for (int i=0; i<addQueue.length; i++) {
			assertEquals(treeUnderTest.search(addQueue[i]), addQueue[i]+1);
		}
		
		// Test adding two more elements. This should cause another split, and fill the root again.
		addQueue = new int[] { 13, 14 };
		for (int i=0; i<addQueue.length; i++) {
			treeUnderTest.insert(addQueue[i], addQueue[i]+1);
		}
		for (int i=0; i<addQueue.length; i++) {
			assertEquals(treeUnderTest.search(addQueue[i]), addQueue[i]+1);
		}
		
		// Test adding four more elements. This should cause the root to split
		addQueue = new int[] { 18, 17, 16, 15 };
		for (int i=0; i<addQueue.length; i++) {
			treeUnderTest.insert(addQueue[i], addQueue[i]+1);
		}
		for (int i=0; i<addQueue.length; i++) {
			assertEquals(treeUnderTest.search(addQueue[i]), addQueue[i]+1);
		}
		
		System.out.println(treeUnderTest.levelOrderTraverse());
	}
	
	/**
	 * Test method for {@link edu.ku.eecs.db.APlusTree.APlusTree#insert(int, int)}.
	 * @throws Exception 
	 */
	@Test
	public void testReverseInsert() throws Exception {
		// Test adding 18 elements, descending order
		int numElements = 18;
		for (int i=numElements-1; i>=0; i--) {
			treeUnderTest.insert(i, i+1);
		}
		System.out.println(treeUnderTest.levelOrderTraverse());
		for (int i=numElements-1; i>=0; i--) {
			assertEquals(treeUnderTest.search(i), i+1);
		}
		
	}
	
	/**
	 * Test method for {@link edu.ku.eecs.db.APlusTree.APlusTree#insert(int, int)}.
	 * @throws Exception 
	 */
	@Test
	public void testSequentialInsert() throws Exception {
		// Test adding 18 elements, ascending order
		int numElements = 18;
		for (int i=0; i<numElements; i++) {
			treeUnderTest.insert(i, i+1);
		}
		System.out.println(treeUnderTest.levelOrderTraverse());
		for (int i=0; i<numElements; i++) {
			assertEquals(treeUnderTest.search(i), i+1);
		}
	}
	
	/**
	 * Test method for {@link edu.ku.eecs.db.APlusTree.APlusTree#insert(int, int)}.
	 * @throws Exception 
	 */
	@Test
	public void testRandomInsert() throws Exception {
		// Test adding 18 elements, random order
		int numElements = 18;
		ArrayList<Integer> list = new ArrayList<Integer>(numElements);
		for (int i=0; i<numElements; i++) {
			list.add(i);
		}
		Collections.shuffle(list);
		for (int i=0; i<numElements; i++) {
			treeUnderTest.insert(list.get(i), list.get(i)+1);
		}
		System.out.println(treeUnderTest.levelOrderTraverse());
		for (int i=0; i<numElements; i++) {
			assertEquals(treeUnderTest.search(list.get(i)), list.get(i)+1);
		}
	}

	/**
	 * Test method for {@link edu.ku.eecs.db.APlusTree.APlusTree#search(int)}.
	 * @throws Exception 
	 */
	@Test
	public void testSearch() throws Exception {
		// Test adding 50 elements, random order
		int numElements = 50;
		ArrayList<Integer> list = new ArrayList<Integer>(numElements);
		for (int i=0; i<numElements; i++) {
			list.add(i);
		}
		Collections.shuffle(list);
		for (int i=0; i<numElements; i++) {
			treeUnderTest.insert(list.get(i), list.get(i)+1);
		}
		System.out.println(treeUnderTest.levelOrderTraverse());
		for (int i=0; i<numElements; i++) {
			assertEquals(treeUnderTest.search(list.get(i)), list.get(i)+1);
		}
		for (int i=0; i<numElements; i++) {
			int deletedKey = list.get(i);
			treeUnderTest.delete(deletedKey);
			assertEquals(-1, treeUnderTest.search(deletedKey));
		}
	}

	/**
	 * Test method for {@link edu.ku.eecs.db.APlusTree.APlusTree#delete(int)}.
	 * @throws Exception 
	 */
	@Test
	public void testDescendingDelete() throws Exception {
		// Test adding and deleting 18 elements, descending order
		int numElements = 18;
		for (int i=numElements-1; i>=0; i--) {
			treeUnderTest.insert(i, i+1);
		}
		System.out.println(treeUnderTest.levelOrderTraverse());
		for (int i=numElements-1; i>=0; i--) {
			treeUnderTest.delete(i);
			System.out.println(treeUnderTest.levelOrderTraverse());
		}
		for (int i=numElements-1; i>=0; i--) {
			assertEquals(-1, treeUnderTest.search(i));
		}
	}

	/**
	 * Test method for {@link edu.ku.eecs.db.APlusTree.APlusTree#delete(int)}.
	 * @throws Exception 
	 */
	@Test
	public void testAscendingDelete() throws Exception {
		// Test adding and deleting 18 elements, ascending order
		int numElements = 18;
		for (int i=0; i<numElements; i++) {
			treeUnderTest.insert(i, i+1);
		}
		System.out.println(treeUnderTest.levelOrderTraverse());
		for (int i=0; i<numElements; i++) {
			treeUnderTest.delete(i);
		}
		for (int i=0; i<numElements; i++) {
			assertEquals(-1, treeUnderTest.search(i));
		}
	}

	/**
	 * Test method for {@link edu.ku.eecs.db.APlusTree.APlusTree#delete(int)}.
	 * @throws Exception 
	 */
	@Test
	public void testInternalDeleteBorrowLeft() throws Exception {
		// Test internal node borrowing from left
		int numElements = 12;
		for (int i=0; i<numElements; i++) {
			treeUnderTest.insert(i, i+1);
		}
		treeUnderTest.delete(0);
		System.out.println(treeUnderTest.levelOrderTraverse());
		for (int i=numElements-1; i>0; i--) {
			treeUnderTest.delete(i);
			assertEquals(-1, treeUnderTest.search(i));
		}
	}

	/**
	 * Test method for {@link edu.ku.eecs.db.APlusTree.APlusTree#delete(int)}.
	 * @throws Exception 
	 */
	@Test
	public void testInternalDeleteBorrowRight() throws Exception {
		// Test internal node borrowing from right
		int numElements = 12;
		for (int i=0; i<numElements; i++) {
			treeUnderTest.insert(i, i+1);
		}
		treeUnderTest.delete(11);
		System.out.println(treeUnderTest.levelOrderTraverse());
		treeUnderTest.delete(0);
		System.out.println(treeUnderTest.levelOrderTraverse());
		assertEquals(-1, treeUnderTest.search(0));
		assertEquals(-1, treeUnderTest.search(11));
	}


}
