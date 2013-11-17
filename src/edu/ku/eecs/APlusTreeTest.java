/**
 * 
 */
package edu.ku.eecs;

import static org.junit.Assert.*;

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
	 * Test method for {@link edu.ku.eecs.APlusTree#insert(int, int)}.
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
	}

	/**
	 * Test method for {@link edu.ku.eecs.APlusTree#search(int)}.
	 */
	@Test
	public void testSearch() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link edu.ku.eecs.APlusTree#delete(int)}.
	 */
	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}


}
