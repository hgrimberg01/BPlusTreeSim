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
	
	public void delete(int key) throws Exception {
		Page p = pages.getIndexedPage(rootPage);
		TreeNode root = TreeNode.fromBytes(p.contents, pages, treeOrder);
		if (!root.isLeaf()) { // root is not a leaf
			try {
				root.delete(key);
				p.contents = Arrays.copyOf(root.toBytes(), p.contents.length);
			}
			catch (InternalUnderflowException e) {
				// since this is the root, if it underflows, we need to change it to a leaf.
				LeafNode node = new LeafNode(pages, treeOrder);
				node.isRoot(true);
				int iterator = 0;
				if (root.numElements() == 0) {
					// We've got problems if the root has no pointers on it
					throw new Exception();
				}
				else {
					TreeNode child = root.getNode(root.pointers()[0]);
					if (child.isLeaf()) { // children are leaves. Grab pointers from children
						for (int i=0; i<root.numElements(); i++) {
							child = root.getNode(root.pointers()[i]);
							for (int j=0; j<child.numElements(); j++) {
								node.keys()[iterator] = child.keys()[j];
								node.pointers()[iterator] = child.pointers()[j];
								iterator++;
							}
						}
						p.contents = Arrays.copyOf(node.toBytes(), p.contents.length);
					}
					else { // children are internal nodes. There should only be one internal node left. Make it root.
						InternalNode newRoot = (InternalNode) child;
						pages.deletePage(rootPage); // delete old root from pages
						rootPage = root.pointers()[0]; // set new root reference
						newRoot.isRoot(true);
						p = pages.getIndexedPage(rootPage);
						p.contents = Arrays.copyOf(newRoot.toBytes(), p.contents.length);
					}
				}				
			}
		}
		else { // root is a leaf
			// This makes things rather easy. Just delete it.
			root.delete(key);
			p.contents = Arrays.copyOf(root.toBytes(), p.contents.length);
		}
	}
	
	public String levelOrderTraverse() throws Exception {
		String output = "";
		Page p = pages.getIndexedPage(rootPage);
		TreeNode root = TreeNode.fromBytes(p.contents, pages, treeOrder);
		Queue<TreeNode> curLevel = new LinkedList<TreeNode>();
		curLevel.add(root);
		Queue<TreeNode> nextLevel = new LinkedList<TreeNode>();
		Queue<String> curLine = new LinkedList<String>();
		do {
			while (!curLevel.isEmpty()) {
				TreeNode curNode = curLevel.poll();
				if (!curNode.isLeaf()) { // node is not a leaf
					curLine.add(String.valueOf(curNode.pointers()[0]));
					nextLevel.add(curNode.getNode(curNode.pointers()[0]));
					for (int i=0; i<curNode.numElements()-1; i++) {
						curLine.add(
								curNode.keys()[i] + "(" + curNode.pointers()[i+1] + ")"
								);
						nextLevel.add(curNode.getNode(curNode.pointers()[i+1]));
					}
				}
				else { // node is a leaf
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
		root.isRoot(true);
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
						break;
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
				tinyLeaf.rightSiblingPtr(bigPage);
				tinyLeaf.rightSiblingPtr(bigPage);
				bigLeaf.leftSiblingPtr(tinyPage);
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
	
	/**
	 * Return a linked list of all the values
	 * @return
	 * @throws Exception 
	 */
	public LinkedList<int[]> values() throws Exception {
		Page p = pages.getIndexedPage(rootPage);
		TreeNode root = TreeNode.fromBytes(p.contents, pages, treeOrder);
		// get down to the leaf level
		TreeNode activeNode = root;
		LinkedList<int[]> list = new LinkedList<int[]>();
		while (!activeNode.isLeaf()) {
			activeNode = activeNode.getNode(activeNode.pointers()[0]);
		}
		LeafNode leaf = (LeafNode)activeNode;
		while (leaf != null) {
			for (int i=0; i<leaf.numElements(); i++) { // add all the key value pairs for this node
				int[] keyValue = new int[] { leaf.keys()[i], leaf.pointers()[i] };
				list.add(keyValue);
			}
			if (leaf.rightSiblingPtr() != -1) {
				leaf = (LeafNode) leaf.getNode(leaf.rightSiblingPtr());
			}
			else {
				leaf = null;
			}
		}
		return list;
	}
}
