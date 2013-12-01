package edu.ku.eecs.db.disk;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PageTest {
	Page pageUnderTest;

	/**
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		int size = 500;
		pageUnderTest = new Page(size);

		for (int i = 0; i < size; i++) {
			if (i % 2 == 0) {
				pageUnderTest.contents[i] = 2;

			} else {
				pageUnderTest.contents[i] = 1;
			}
		}

	}

	@Test
	public void testPageInt() {
		int size = pageUnderTest.contents.length;
		for (int i = 0; i < size; i++) {
			if (i % 2 == 0) {
				assertEquals(pageUnderTest.contents[i], 2);
			} else {
				assertEquals(pageUnderTest.contents[i], 1);

			}
		}
	}

	@Test
	public void testPurge() {
		pageUnderTest.purge();
		int size = pageUnderTest.contents.length;
		for (int i = 0; i < size; i++) {

			assertNotEquals(pageUnderTest.contents[i], 2);
			assertNotEquals(pageUnderTest.contents[i], 1);

		}
	}

}
