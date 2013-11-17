/**
 * 
 */
package edu.ku.eecs;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

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
		Page rootPg = pages.getIndexedPage(rootPage);
		rootPg.contents = Arrays.copyOf(flatRoot, rootPg.contents.length);
	}
	
	public int search(int key) throws Exception {
		Page p = pages.getIndexedPage(rootPage);
		TreeNode root = TreeNode.fromBytes(p.contents, pages, treeOrder);
		return root.search(key);
	}
	
	public void delete(int key) {
		
	}
	
	public String levelOrderTraverse() throws Exception {
		String output = "";
		Page p = pages.getIndexedPage(rootPage);
		TreeNode root = TreeNode.fromBytes(p.contents, pages, treeOrder);
		Queue<TreeNode> curLevel = new LinkedList<TreeNode>();
		curLevel.add(root);
		Queue<TreeNode> nextLevel = new LinkedList<TreeNode>();
		do {
			Queue<String> curLine = new LinkedList<String>();
			while (!curLevel.isEmpty()) {
				TreeNode curNode = curLevel.poll();
				if (!curNode.isLeaf()) {
					curLine.add(String.valueOf(curNode.pointers()[0]));
					nextLevel.add(curNode.getNode(curNode.pointers()[0]));
					for (int i=0; i<curNode.numElements()-1; i++) {
						curLine.add(
								curNode.keys()[i] + "(" + curNode.pointers()[i+1] + ")"
								);
						nextLevel.add(curNode.getNode(curNode.pointers()[i+1]));
					}
				}
				else {
					for (int i=0; i<curNode.numElements(); i++) {
						curLine.add(
								curNode.keys()[i] + "(" + curNode.pointers()[i] + ")"
								);
					}
				}
				while (!curLine.isEmpty()) {
					output += curLine.poll();
					if (!curLine.isEmpty()) output+= ", ";
				}
				output += "\n";
			}
			curLevel = nextLevel;
			nextLevel = new LinkedList<TreeNode>();
		}
		while(!curLevel.isEmpty());
		return output;
	}
	
	public void insert(int key, int value) throws Exception { // catch KeyExistsException to detect if key already exists
		Page p = pages.getIndexedPage(rootPage);
		TreeNode root = TreeNode.fromBytes(p.contents, pages, treeOrder);
		if (!root.isLeaf()) {
			InternalNode node = (InternalNode)root;
			try {
				node.insert(key, value);
				p.contents = Arrays.copyOf(node.toBytes(), p.contents.length);
			}
			catch (InternalNodeFullException e) {
				InternalNode leftNode = new InternalNode(pages, treeOrder);
				InternalNode rightNode = new InternalNode(pages, treeOrder);
				int pushedUpKey = e.tinyNode.keys()[e.tinyNode.numElements()-1]; // biggest element of left side split node
				int pushupInsertIndex = node.numElements()-1;
				for (int i=0; i<node.keys().length; i++) {
					if (node.keys()[i] >= pushedUpKey || node.keys()[i] == -1) {
						pushupInsertIndex = i;
					}
				}
				int nodeTransitionIndex = (int) Math.ceil(treeOrder/2); // the index after which keys are put in rightNode
				int iterator = 0;
				for (int i=0; i<treeOrder; i++) {
					if (i == pushupInsertIndex) {
						if (i <= nodeTransitionIndex) {
							leftNode.keys()[i] = pushedUpKey;
						}
						else {
							rightNode.keys()[i-nodeTransitionIndex-1] = pushedUpKey;
						}
					}
					else {
						if (i <= nodeTransitionIndex) {
							leftNode.keys()[i] = node.keys()[iterator];
						}
						else {
							rightNode.keys()[i-nodeTransitionIndex-1] = node.keys()[iterator];
						}
						iterator++;
					}
				}
				iterator = 0;
				for (int i=0; i<treeOrder+1; i++) { // reconnect pointers
					if (i == pushupInsertIndex) {
						if (i <= nodeTransitionIndex) {
							leftNode.pointers()[i] = e.tinyPtr;
						}
						else {
							rightNode.pointers()[i-nodeTransitionIndex-1] = e.tinyPtr;
						}
					}
					else if (i == pushupInsertIndex+1) {
						if (i <= nodeTransitionIndex) {
							leftNode.pointers()[i] = e.bigPtr;
						}
						else {
							rightNode.pointers()[i-nodeTransitionIndex-1] = e.bigPtr;
						}
						// TODO: destroy page for current target.pointers()[iterator] value?
						iterator++;
					}
					else {
						if (i <= nodeTransitionIndex) {
							leftNode.pointers()[i] = node.pointers()[iterator];
						}
						else {
							rightNode.pointers()[i-nodeTransitionIndex-1] = node.pointers()[iterator];
						}
						iterator++;
					}
				}
				int pushKey = leftNode.keys()[leftNode.numElements()-1];
				leftNode.keys()[leftNode.numElements()-1] = -1;
				int leftPage = pages.getNewPage(); Page leftPg = pages.getIndexedPage(leftPage);
				int rightPage = pages.getNewPage(); Page rightPg = pages.getIndexedPage(rightPage);
				leftPg.contents = Arrays.copyOf(leftNode.toBytes(), leftPg.contents.length);
				rightPg.contents = Arrays.copyOf(rightNode.toBytes(), rightPg.contents.length);
				
				root = new InternalNode(pages, treeOrder);
				root.isRoot(true);
				root.keys()[0] = pushKey; // push up key
				root.pointers()[0] = leftPage; // push up the new pointers
				root.pointers()[1] = rightPage;
				p.contents = Arrays.copyOf(root.toBytes(), p.contents.length);
			}
		}
		else { // root node is a leaf node
			if (!root.isFull()) {
				// root is not full, add element to root.
				root.insert(key, value);
				p.contents = Arrays.copyOf(root.toBytes(), p.contents.length);
			}
			else {
				// root is full. (elements = order). Split and add a new root.
				LeafNode node = (LeafNode)root;
				LeafNode tinyLeaf = new LeafNode(pages, treeOrder);
				LeafNode bigLeaf = new LeafNode(pages, treeOrder);
				int insertIndex = node.insertionPoint(key); // find insertion point
				int nodeTransitionIndex = (int) Math.ceil(treeOrder/2);
				int iterator = 0;
				for (int i=0; i<=treeOrder; i++) {
					if (i == insertIndex) {
						if (i <= nodeTransitionIndex) {
							tinyLeaf.keys()[i] = key;
							tinyLeaf.pointers()[i] = value;
						}
						else {
							bigLeaf.keys()[i-nodeTransitionIndex-1] = key;
							bigLeaf.pointers()[i-nodeTransitionIndex-1] = value;
						}
					}
					else {
						if (i <= nodeTransitionIndex) {
							tinyLeaf.keys()[i] = node.keys()[iterator];
							tinyLeaf.pointers()[i] = node.pointers()[iterator];
						}
						else {
							bigLeaf.keys()[i-nodeTransitionIndex-1] = node.keys()[iterator];
							bigLeaf.pointers()[i-nodeTransitionIndex-1] = node.pointers()[iterator];
						}
						iterator++;
					}
				}
				int tinyPage = pages.getNewPage(); Page tinyPg = pages.getIndexedPage(tinyPage);
				int bigPage = pages.getNewPage(); Page bigPg = pages.getIndexedPage(bigPage);
				tinyLeaf.siblingPtr(bigPage);
				tinyPg.contents = Arrays.copyOf(tinyLeaf.toBytes(), tinyPg.contents.length);
				bigPg.contents = Arrays.copyOf(bigLeaf.toBytes(), bigPg.contents.length);
				
				// create a new root
				InternalNode newRoot = new InternalNode(pages, treeOrder);
				newRoot.isRoot(true);
				newRoot.keys()[0] = tinyLeaf.keys()[tinyLeaf.numElements()-1]; // push up the largest key of the left most node to the root
				newRoot.pointers()[0] = tinyPage;
				newRoot.pointers()[1] = bigPage;
				p.contents = Arrays.copyOf(newRoot.toBytes(), p.contents.length);
			}
		}
	}
}
