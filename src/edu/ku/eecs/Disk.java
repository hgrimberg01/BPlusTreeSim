package edu.ku.eecs;

/***
 * 
 * @author hgrimberg
 * 
 */
public class Disk {
	private PageTable pageTable;
	private static final int DEFAULT_PAGE_SIZE = 500;

	/**
	 * 
	 * @param sizeInBytes
	 */
	public Disk(int sizeInBytes) {

		int numberOfPages = sizeInBytes / DEFAULT_PAGE_SIZE;
		setPageTable(new PageTable(numberOfPages, DEFAULT_PAGE_SIZE));
	}

	/**
	 * 
	 * @param sizeInBytes
	 * @param pageSize
	 */
	public Disk(int sizeInBytes, int pageSize) {
		int numberOfPages = sizeInBytes / pageSize;
		setPageTable(new PageTable(numberOfPages, pageSize));

	}

	/**
	 * 
	 * @return
	 */
	public PageTable getPageTable() {
		return pageTable;
	}

	/**
	 * 
	 * @param pageTable
	 */
	public void setPageTable(PageTable pageTable) {
		this.pageTable = pageTable;
	}

}
