/**
 * 
 */
package edu.ku.eecs;

import java.util.Arrays;

/**
 * @author QtotheC
 * http://cheezburger.com/7902608384
 */
public class APlusTree {
	private int rootPage;
	private PageTable pages;
	private int treeOrder;
	
	public APlusTree() throws Exception {
		pages = new PageTable(100);
		treeOrder = 3;
		LeafNode root = new LeafNode(pages, treeOrder);
		byte[] flatRoot = root.toBytes();
		rootPage = pages.getNewPage();
		pages.getIndexedPage(rootPage).contents = flatRoot;
	}
	
	public int search(int key) throws Exception {
		Page p = pages.getIndexedPage(rootPage);
		TreeNode root = TreeNode.fromBytes(p.contents, pages, treeOrder);
		return root.search(key);
	}
	
	public void delete(int key) {
		
	}
	
	public void insert(int key, int value) throws Exception {
		Page p = pages.getIndexedPage(rootPage);
		TreeNode root = TreeNode.fromBytes(p.contents, pages, treeOrder);
		// TODO search to make sure it doesn't already exist?
		if (!root.isLeaf()) {
			
		}
		else { // root node is a leaf node
			if (!root.isFull()) {
				// root is not full, add element to root.
				root.insert(key, value);
				p.contents = root.toBytes();
			}
			else {
				// root is full. (elements = order). Split and add a new root.
				LeafNode tinyLeaf = new LeafNode(pages, treeOrder);
				LeafNode bigLeaf = new LeafNode(pages, treeOrder);
				int insertIndex = root.keys().length;
				for (int i=0; i < root.keys().length; i++) { // find insertion point
					if (root.keys()[i] >= key) {
						if (root.keys()[i] == key) throw new Exception("Element already exists.");
						insertIndex = i;
					}
				}
				for (int i=0; i< insertIndex; i++) { // add all keys before insertion point
					if (i <= Math.floor(treeOrder/2)) {
						tinyLeaf.keys()[i] = root.keys()[i];
						tinyLeaf.pointers()[i] = root.pointers()[i];
					}
					else {
						bigLeaf.keys()[i] = root.keys()[i];
						bigLeaf.pointers()[i] = root.pointers()[i];
					}
				}
				if (insertIndex <= Math.floor(treeOrder/2)) {
					tinyLeaf.keys()[insertIndex] = key;
					tinyLeaf.pointers()[insertIndex] = value;
				}
				else {
					bigLeaf.keys()[insertIndex] = key;
					bigLeaf.pointers()[insertIndex] = value;
				}
				for (int i=insertIndex; i<root.keys().length; i++) { // add all keys after insertion point
					if (i+1 <= Math.floor(treeOrder/2)) {
						tinyLeaf.keys()[i+1] = root.keys()[i];
						tinyLeaf.pointers()[i+1] = root.pointers()[i];
					}
					else {
						bigLeaf.keys()[i+1] = root.keys()[i];
						bigLeaf.pointers()[i+1] = root.pointers()[i];
					}
				}
				int tinyPage = pages.getNewPage();
				int bigPage = pages.getNewPage();
				pages.getIndexedPage(tinyPage).contents = tinyLeaf.toBytes();
				pages.getIndexedPage(bigPage).contents = bigLeaf.toBytes();
				root.keys()[0] = tinyLeaf.keys()[tinyLeaf.numElements()-1]; // push up the largest key of the left most node to the root
				root.pointers()[0] = tinyPage;
				root.pointers()[1] = bigPage;
				pages.getIndexedPage(rootPage).contents = root.toBytes();
			}
		}
	}
}
