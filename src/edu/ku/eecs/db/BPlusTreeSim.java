/**
 * 
 */
package edu.ku.eecs.db;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import edu.ku.eecs.db.APlusTree.APlusTree;

/**
 * @author hgrimberg
 * Main simulator launcher
 */
public class BPlusTreeSim {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		if (args.length > 0 && args.length <= 2) {
			if (args[0].equals("-h") || args[0].equals("--help")) {
				// Help Goes Here
			} else {
				if (args.length > 1) {
					Queue<Instruction> toDo = processInput(args[0]);
					String buffer = new String();
					APlusTree treeTest = new APlusTree();

					while (!toDo.isEmpty()) {
						Instruction is = toDo.poll();

						switch (is.getiType()) {

						case INSERT:
							treeTest.insert(new Integer(is.getKey()),
									new Integer(is.getVal()));

							break;
						case DELETE:
							treeTest.delete(new Integer(is.getKey()));
							break;
						case SEARCH:
							Integer retn = treeTest.search(new Integer(is
									.getKey()));

							buffer += retn.toString();
							buffer += '\n';
							break;
						case OUTPUT:

							buffer += treeTest.levelOrderTraverse();
							buffer += '\n';
							break;

						}

					}
					if (args.length == 1) {
						System.out.println(buffer);
					} else if (args.length == 2) {
				
						try {
							
							File file = new File(args[1]);
							BufferedWriter output = new BufferedWriter(
									new FileWriter(file));
							output.write(buffer);
							output.close();
						} catch (IOException e) {
							e.printStackTrace();
						}

					}

				} else {
					System.out.println("Incorrect Arguments");
				}
			}
		} else {

			System.out.println("Incorrect Number of Arguments");
		}

	}

	public static Queue<Instruction> processInput(String inputFilePath)
			throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(inputFilePath));

		String sCurrentLine;

		@SuppressWarnings("unchecked")
		Queue<Instruction> list = new LinkedList<Instruction>();

		Instruction inst;
		while ((sCurrentLine = in.readLine()) != null) {
			if (!sCurrentLine.trim().equals("")) {
				String[] ins = sCurrentLine.split(" ", 2);
				String i = ins[0].trim();

				switch (i) {
				case "Insert":
					String[] kv = ins[1].split(",");
					String k = kv[0].trim();
					String v = kv[1].trim();
					inst = new Instruction();
					inst.setKey(k);
					inst.setVal(v);
					inst.setiType(Instruction.Type.INSERT);
					list.add(inst);
					break;
				case "Delete":
					String kd = ins[1].trim();
					inst = new Instruction();
					inst.setKey(kd);
					inst.setVal(null);
					inst.setiType(Instruction.Type.DELETE);
					list.add(inst);
					break;
				case "Search":
					String ks = ins[1].trim();

					inst = new Instruction();
					inst.setKey(ks);
					inst.setVal(null);
					inst.setiType(Instruction.Type.SEARCH);
					list.add(inst);
					break;
				case "Output":

					inst = new Instruction();
					inst.setKey(null);
					inst.setVal(null);
					inst.setiType(Instruction.Type.OUTPUT);
					list.add(inst);
					break;
				}

			}
		}

		return list;

	}
}
