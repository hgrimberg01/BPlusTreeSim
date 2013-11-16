/**
 * 
 */
package edu.ku.eecs;

/**
 * @author QtotheC
 * http://cheezburger.com/7902608384
 */
public class APlusTree {
	private int rootPage;
	private PageTable pages;
	
	public APlusTree() throws Exception {
		pages = new PageTable(100);
		LeafNode root = new LeafNode();
		byte[] flatRoot = root.toBytes();
		rootPage = pages.getNewPage();
		pages.getIndexedPage(rootPage).contents = flatRoot;
	}
	
	public int search(int key) throws Exception {
		Page p = pages.getIndexedPage(rootPage);
		TreeNode root = TreeNode.fromBytes(p.contents);
		return root.search(key);
	}
	
	public void delete(int key) {
		
	}
	
	public void insert(int key, int value) {
		
	}
}
