package main;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.PriorityQueue;

import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.Out;
import edu.princeton.cs.introcs.StdOut;

/**
 * @author Rob Shelly
 *
 */
public class HuffmanEncoder {
	
	private HashMap<Character, Integer> frequencyTable;
	private PriorityQueue<HuffmanNode> queue;
	private HashMap<Character, String> codes;
	
	static long magicNumber = 13101424;
	static String startOfFileString= "13106";
	static char eof = '#';
	
	HuffmanNode rootNode;
	
	private static boolean DEBUG;
	
	/**
	 * Constructor for HuffmanTree.
	 */
	public HuffmanEncoder() {
		frequencyTable = new HashMap<>();
		queue = new PriorityQueue<>();
		codes = new HashMap<>();
	}
	
	/**
	 * Builds a table of character frequencies using plainText. 
	 */
	private void analyseFrequencies(String plainText) {
		
		for (char c: plainText.toCharArray()) {
			if (!frequencyTable.containsKey(c)) {
				frequencyTable.put(c, 1);
			} else {
				frequencyTable.put(c, frequencyTable.get(c) + 1);
			}
		}
	}
	
	/**
	 * Builds a Huffman Tree using the the nodes in the priority queue.
	 * 
	 * @return A HuffmanNode which is the root node of the HuffmanTree.
	 */
	private HuffmanNode buildTree() {
		
		// Fill priority queue with forest trees
		for (char c: frequencyTable.keySet()) {
			queue.add(new HuffmanNode(c, frequencyTable.get(c), null, null));
		}
		
		// Build single tree from forest
		while (queue.size() > 1) {
			HuffmanNode left = queue.remove();
			HuffmanNode right = queue.remove();
			
			queue.add(new HuffmanNode('*',
					left.getFrequency() + right.getFrequency(), left, right));
		}
		
		// Return tree in the form of root node.
		return queue.remove();
	}
	
	/**
	 * Calculates the Huffman Codes. 
	 */
	private void calculateCodes() {
		String code = "";
		createCode(rootNode, code);
		
		if (DEBUG) {
			for (char c: codes.keySet()) {
				StdOut.println(c + ": " + codes.get(c));
			}
		}
	}
	
	/**
	 * Recursive method for creating HuffmanCodes for each
	 * node in a huffman tree.
	 * 
	 * @param node The current node.
	 * @param code The current code.
	 */
	private void createCode(HuffmanNode node, String code) {
		
		if (node.getLeft() != null) {
			code += "0";
			createCode(node.getLeft(), code);
			code = code.substring(0, code.length() -1);
		} 
		
		if (node.getRight() != null) {
			code += "1";
			createCode(node.getRight(), code);
		} 
		
		if (node.isLeaf()) {
			codes.put(node.getCharacter(), code);
		}
	}
	
	
	/**
	 * Encode the plainttext and output it to a file.
	 */
	public void encode(String plainText) {
		
		analyseFrequencies(plainText);
		rootNode = buildTree();
		calculateCodes();
		
		
		Out dataOut = new Out("data/encoded-txt.txt");
		

		dataOut.println(magicNumber);
		
		for (Character c: frequencyTable.keySet()) {
			dataOut.println(c + "|" + frequencyTable.get(c));
		}
		
		dataOut.println(startOfFileString);
		
		for (Character c: plainText.toCharArray()) {
			dataOut.print(codes.get(c));			
		}
		
		dataOut.println(eof);
	}

	public String decode(File file) throws IOException {
		
		frequencyTable.clear();
		queue.clear();
		codes.clear();

		String decodedTxt = "";
		
		
		In dataIn = new In(file);

		if (!dataIn.isEmpty()) {

			// First check for magic number
			String dataLine = dataIn.readLine();
			
			if (Long.parseLong(dataLine) == (magicNumber)) {
				
				dataLine = dataIn.readLine();
				while (!dataLine.equals(startOfFileString) ) {
					parseHuffmanCode(dataLine);
					dataLine = dataIn.readLine();		
				}
				
				// All character frequencies have now been read
				// Build tree
				buildTree();
				
				// Now read the message and decode
				// Until end of file code reached
				
				// Set current node to root and read first bit
				char currentBit = dataIn.readChar();
				HuffmanNode currentNode = rootNode;
				
				while (currentBit != eof) {

					// Move left or right in tree according to current bit	
					if (currentBit == '0') currentNode = currentNode.getLeft();
					else currentNode = currentNode.getRight();
					
					if (currentNode.isLeaf()) {
						// Add decoded char to decoded text
						decodedTxt += currentNode.getCharacter();
						// Reset to root node and read next bit
						currentNode = rootNode;
					}
					currentBit = dataIn.readChar();
				}

			} else
				throw new IOException("Error reaading file");

		}

		return decodedTxt;
	}

	private void parseHuffmanCode(String charCodePair) {

		String delims = "[|]";
		String[] pair = charCodePair.split(delims);
		frequencyTable.put(pair[0].charAt(0), Integer.parseInt(pair[1]));
	}
	
	/**
	 * Getter for the table of frequencies.
	 * 
	 * @return hashmap of the table of frequencies
	 */
	public HashMap<Character, Integer> getFrequencyTable() {
		return frequencyTable;
	}
	
	/**
	 * Setter for the table of frequencies
	 * 
	 * @param frequencyTable A hashmap of the table of frequencies.
	 */
	public void setFrequencyTable(HashMap<Character, Integer> frequencyTable) {
		this.frequencyTable = frequencyTable;
	}
	
	/**
	 * Getter for the root node of the Huffman Tree.
	 * 
	 * @return a HuffmanNode which id the root of the Huffman Tree.
	 */
	public HuffmanNode getRoot() {
		return rootNode;
	}
	
	/**
	 * Getter for the Huffman codes.
	 *  
	 * @return a hashmap of character mapped to their Huffman codes
	 */
	public HashMap<Character, String> getCodes() {
		return codes;
	}

}
