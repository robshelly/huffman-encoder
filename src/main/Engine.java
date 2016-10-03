package main;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;

import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.Out;
import edu.princeton.cs.introcs.StdOut;

/**
 * @author Rob Shelly
 *
 */
public class Engine {
	
	File file;
	String plainText;
	HuffmanEncoder encoder;
	
	
	public Engine() {
		String plainText = "";
		encoder = new HuffmanEncoder();
	}
	
	public static void main(String[] args) throws IOException {
		Engine engine = new Engine();
		
		engine.plainText = engine.readText(new File("data/test.txt"));
		engine.encoder.encode(engine.plainText);
		
		engine.printFrequencyTable(engine.encoder.getFrequencyTable());
		engine.printBinaryTree(engine.encoder.getRoot(), 0);
		engine.printCodes(engine.encoder.getCodes());
		
	  StdOut.println(engine.encoder.decode(new File("data/encoded-txt.txt")));
		
	}
	
	/**
	 * Setter for file.
	 * 
	 * @param file to set.
	 */
	public void setFile(File file) {
		this.file = file;
	}
	
	
	/**
	 * Reads in text from a file.
	 * 
	 * @param fileUrl The path of the file to read from.
	 * @return A string containing the text read.
	 */
	public String readText(File file) {
		
		String str = "";
		
		In dataIn = new In(file);
		
		while (!dataIn.isEmpty()) {
			str += dataIn.readAll();
		}		
		return str;
	}
	
	/**
	 * Prints frequency table to console.
	 * For testing purposes.
	 */
	private void printFrequencyTable(HashMap<Character, Integer> frequencyTable) {
		for (char c: frequencyTable.keySet()) {
			StdOut.println(c + ": " + frequencyTable.get(c));
		}
	}

	
	/**
	 * Prints binary tree to console
	 * For testing purposes.
	 * 
	 * 
	 * sourced from:
	 * http://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram
	 * 
	 * @param root
	 * @param level
	 */
	public void printBinaryTree(HuffmanNode root, int level) {
		if (root == null)
			return;
		printBinaryTree(root.getRight(), level + 1);
		if (level != 0) {
			for (int i = 0; i < level - 1; i++)
				StdOut.print("|\t");
			StdOut.println("|-------" + root.getCharacter());
		} else
			StdOut.println(root.getCharacter());
		printBinaryTree(root.getLeft(), level + 1);
	}
	
	public void printCodes(HashMap<Character, String> codes) {
		for (char c: codes.keySet()) {
			StdOut.println(c + "; " + codes.get(c));
		}
	}	
}
