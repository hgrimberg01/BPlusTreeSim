/**
 * 
 */
package edu.ku.eecs;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

/**
 * @author QtotheC
 * 
 */
public class APlusTreeTest {
	APlusTree treeUnderTest;
	int sequentialElements;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		treeUnderTest = new APlusTree();
		sequentialElements = 24; // number of sequential elements to test
	}

	/**
	 * Test method for {@link edu.ku.eecs.APlusTree#insert(int, int)}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testInsert() throws Exception {
		// Test adding 3 elements, non-ordered
		int[] addQueue = new int[] { 3, 9, 6 };
		for (int i = 0; i < addQueue.length; i++) {
			treeUnderTest.insert(addQueue[i], addQueue[i] + 1);
		}
		for (int i = 0; i < addQueue.length; i++) {
			assertEquals(treeUnderTest.search(addQueue[i]), addQueue[i] + 1);
		}

		// Test adding another element, non-ordered. This should make the root
		// split.
		addQueue = new int[] { 4 };
		for (int i = 0; i < addQueue.length; i++) {
			treeUnderTest.insert(addQueue[i], addQueue[i] + 1);
		}
		for (int i = 0; i < addQueue.length; i++) {
			assertEquals(treeUnderTest.search(addQueue[i]), addQueue[i] + 1);
		}

		// Test adding two more elements. This should cause another split, and
		// fill the root.
		addQueue = new int[] { 5, 8 };
		for (int i = 0; i < addQueue.length; i++) {
			treeUnderTest.insert(addQueue[i], addQueue[i] + 1);
		}
		for (int i = 0; i < addQueue.length; i++) {
			assertEquals(treeUnderTest.search(addQueue[i]), addQueue[i] + 1);
		}

		// Test adding two more elements. This should cause another split, and
		// cause the root to split.
		addQueue = new int[] { 7, 10 };
		for (int i = 0; i < addQueue.length; i++) {
			treeUnderTest.insert(addQueue[i], addQueue[i] + 1);
		}
		for (int i = 0; i < addQueue.length; i++) {
			assertEquals(treeUnderTest.search(addQueue[i]), addQueue[i] + 1);
		}

		// Test adding two more elements. This should cause another split.
		addQueue = new int[] { 11, 12 };
		for (int i = 0; i < addQueue.length; i++) {
			treeUnderTest.insert(addQueue[i], addQueue[i] + 1);
		}
		for (int i = 0; i < addQueue.length; i++) {
			assertEquals(treeUnderTest.search(addQueue[i]), addQueue[i] + 1);
		}

		// Test adding two more elements. This should cause another split, and
		// fill the root again.
		addQueue = new int[] { 13, 14 };
		for (int i = 0; i < addQueue.length; i++) {
			treeUnderTest.insert(addQueue[i], addQueue[i] + 1);
		}
		for (int i = 0; i < addQueue.length; i++) {
			assertEquals(treeUnderTest.search(addQueue[i]), addQueue[i] + 1);
		}

		// Test adding four more elements. This should cause the root to split
		addQueue = new int[] { 18, 17, 16, 15 };
		for (int i = 0; i < addQueue.length; i++) {
			treeUnderTest.insert(addQueue[i], addQueue[i] + 1);
		}
		for (int i = 0; i < addQueue.length; i++) {
			assertEquals(treeUnderTest.search(addQueue[i]), addQueue[i] + 1);
		}

		System.out.println(treeUnderTest.levelOrderTraverse());
	}

	/**
	 * Test method for {@link edu.ku.eecs.APlusTree#insert(int, int)}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testReverseInsert() throws Exception {
		// Test adding elements, descending order
		int numElements = sequentialElements;
		for (int i = numElements - 1; i >= 0; i--) {
			treeUnderTest.insert(i, i + 1);
		}
		System.out.println(treeUnderTest.levelOrderTraverse());
		for (int i = numElements - 1; i >= 0; i--) {
			assertEquals(treeUnderTest.search(i), i + 1);
		}

	}

	/**
	 * Test method for {@link edu.ku.eecs.APlusTree#insert(int, int)}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSequentialInsert() throws Exception {
		// Test adding elements, ascending order
		for (int i = 0; i < sequentialElements; i++) {
			treeUnderTest.insert(i, i + 1);
		}
		System.out.println(treeUnderTest.levelOrderTraverse());
		for (int i = 0; i < sequentialElements; i++) {
			assertEquals(treeUnderTest.search(i), i + 1);
		}
	}

	/**
	 * Test method for {@link edu.ku.eecs.APlusTree#insert(int, int)}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRandomInsert() throws Exception {
		// Test adding elements, random order
		int numElements = sequentialElements;
		ArrayList<Integer> list = new ArrayList<Integer>(numElements);
		for (int i = 0; i < numElements; i++) {
			list.add(i);
		}
		Collections.shuffle(list);
		for (int i = 0; i < numElements; i++) {
			treeUnderTest.insert(list.get(i), list.get(i) + 1);
		}
		System.out.println(treeUnderTest.levelOrderTraverse());
		for (int i = 0; i < numElements; i++) {
			assertEquals(treeUnderTest.search(list.get(i)), list.get(i) + 1);
		}
	}

	/**
	 * Test method for {@link edu.ku.eecs.APlusTree#search(int)}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSearch() throws Exception {
		// Test adding 50 elements, random order
		int numElements = 50;
		ArrayList<Integer> list = new ArrayList<Integer>(numElements);
		for (int i = 0; i < numElements; i++) {
			list.add(i);
		}
		Collections.shuffle(list);
		for (int i = 0; i < numElements; i++) {
			treeUnderTest.insert(list.get(i), list.get(i) + 1);
		}
		System.out.println(treeUnderTest.levelOrderTraverse());
		for (int i = 0; i < numElements; i++) {
			assertEquals(treeUnderTest.search(list.get(i)), list.get(i) + 1);
		}
		for (int i = 0; i < numElements; i++) {
			int deletedKey = list.get(i);
			treeUnderTest.delete(deletedKey);
			assertEquals(-1, treeUnderTest.search(deletedKey));
		}
	}

	/**
	 * Test method for {@link edu.ku.eecs.APlusTree#delete(int)}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDescendingDelete() throws Exception {
		// Test adding and deleting elements, descending order
		int numElements = sequentialElements;
		for (int i = numElements - 1; i >= 0; i--) {
			treeUnderTest.insert(i, i + 1);
		}
		System.out.println(treeUnderTest.levelOrderTraverse());
		for (int i = numElements - 1; i >= 0; i--) {
			treeUnderTest.delete(i);
			System.out.println(treeUnderTest.levelOrderTraverse());
		}
		for (int i = numElements - 1; i >= 0; i--) {
			assertEquals(-1, treeUnderTest.search(i));
		}
	}

	/**
	 * Test method for {@link edu.ku.eecs.APlusTree#delete(int)}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAscendingDelete() throws Exception {
		// Test adding and deleting elements, ascending order
		int numElements = sequentialElements;
		for (int i = 0; i < numElements; i++) {
			treeUnderTest.insert(i, i + 1);
		}
		System.out.println(treeUnderTest.levelOrderTraverse());
		for (int i = 0; i < numElements; i++) {
			treeUnderTest.delete(i);
		}
		for (int i = 0; i < numElements; i++) {
			assertEquals(-1, treeUnderTest.search(i));
		}
	}

	/**
	 * Test method for {@link edu.ku.eecs.APlusTree#delete(int)}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testInternalDeleteBorrowLeft() throws Exception {
		// Test internal node borrowing from left
		int numElements = 12;
		for (int i = 0; i < numElements; i++) {
			treeUnderTest.insert(i, i + 1);
		}
		treeUnderTest.delete(0);
		System.out.println(treeUnderTest.levelOrderTraverse());
		for (int i = numElements - 1; i > 0; i--) {
			treeUnderTest.delete(i);
			assertEquals(-1, treeUnderTest.search(i));
		}
	}

	/**
	 * Test method for {@link edu.ku.eecs.APlusTree#delete(int)}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testInternalDeleteBorrowRight() throws Exception {
		// Test internal node borrowing from right
		int numElements = 12;
		for (int i = 0; i < numElements; i++) {
			treeUnderTest.insert(i, i + 1);
		}
		treeUnderTest.delete(11);
		System.out.println(treeUnderTest.levelOrderTraverse());
		treeUnderTest.delete(0);
		System.out.println(treeUnderTest.levelOrderTraverse());
		assertEquals(-1, treeUnderTest.search(0));
		assertEquals(-1, treeUnderTest.search(11));
	}
	
	@Test
	public void testLinkedListGeneration() throws Exception {
		int numElements = sequentialElements;
		LinkedList<int[]> referenceList = new LinkedList<int[]>();
		for (int i = 0; i < numElements; i++) {
			treeUnderTest.insert(i, i + 1);
			referenceList.add(new int[] {i, i+1});
		}
		System.out.println(treeUnderTest.levelOrderTraverse());
		LinkedList<int[]> generatedList = treeUnderTest.values();
		for (int i=0; i< numElements;i++) {
			assertArrayEquals(referenceList.get(i), generatedList.get(i));
		}
	}
	
	@Test
	public void testLinkedListGenerationWithDeletions() throws Exception {
		int numElements = sequentialElements;
		ArrayList<Integer> referenceList = new ArrayList<Integer>();
		for (int i = 0; i < numElements; i++) {
			treeUnderTest.insert(i, i + 1);
			referenceList.add(i);
		}
		Random rd = new Random();
		int numToDelete = rd.nextInt(numElements);
		for (int i=0; i<numToDelete; i++) {
			treeUnderTest.delete(i);
			referenceList.remove(new Integer(i));
		}
		System.out.println(treeUnderTest.levelOrderTraverse());
		LinkedList<int[]> generatedList = treeUnderTest.values();
		for (int i=0; i< numElements-numToDelete;i++) {
			assertArrayEquals(new int[] {referenceList.get(i), referenceList.get(i)+1}, generatedList.get(i));
		}
	}

}
