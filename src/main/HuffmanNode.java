package main;

/**
 * @author Rob Shelly
 * 
 *
 */
public class HuffmanNode implements Comparable<HuffmanNode>{
	
	private char character;
	private int frequency;
	private HuffmanNode left;
	private HuffmanNode right;

	/**
	 * Constructor for a Huffman Node.
	 * I.e. a node in a Huffman Tree used for Huffman Encoding. 
	 * 
	 * @param character The character at this node.
	 * @param frequency The frequency with which this character appears.
	 * @param left The left child node.
	 * @param right The right child node.
	 */
	public HuffmanNode(char character, int frequency, HuffmanNode left, HuffmanNode right) {
		this.character = character;
		this.frequency = frequency;
		this.left = left;
		this.right = right;
	}

	/**
	 * Getter for character.
	 * 
	 * @return The character.
	 */
	public char getCharacter() {
		return character;
	}

	/**
	 * Setter for character.
	 * 
	 * @param character The character to set.
	 */
	public void setCharacter(char character) {
		this.character = character;
	}

	/**
	 * Getter for frequency.
	 * 
	 * @return The frequency.
	 */
	public int getFrequency() {
		return frequency;
	}

	/**
	 * Setter for frequency.
	 * 
	 * @param frequency The frequency to set.
	 */
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	/**
	 * Getter for left node.
	 * 
	 * @return The left node.
	 */
	public HuffmanNode getLeft() {
		return left;
	}

	/**
	 * Setter for left node.
	 * 
	 * @param left The node to set.
	 */
	public void setLeft(HuffmanNode left) {
		this.left = left;
	}

	/**
	 * Getter for right node.
	 * 
	 * @return The right node.
	 */
	public HuffmanNode getRight() {
		return right;
	}

	/**
	 * Setter for right node.
	 * 
	 * @param right The node to set.
	 */
	public void setRight(HuffmanNode right) {
		this.right = right;
	}
	
	/**
	 * Checks if the node is a leaf node.
	 * I.e. has no child nodes.
	 * 
	 * @return True if leaf node, false otherwise.
	 */
	public boolean isLeaf() {
		if (left == null && right == null) return true;
		return false;
	}

	@Override
	public int compareTo(HuffmanNode that) {
		return this.frequency - that.frequency;
	}
}
