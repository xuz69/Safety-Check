

/**
 * The balanced binary search tree algorithm to search the crimes
 * 
 * @author Amy Xu
 * @version java version "1.8.0_161"
 * @see BinarySearchTree
 * @see #put(Key, Value)
 * @see #search(Key)
 * @see #size()
 * 
 */
public class BalancedBinarySearchTree<Key extends Comparable<Key>, Value> {
	
	private static final boolean RED = true;
	private static final boolean BLACK = false;
	private Node root;	
	
	/**
	 * Inserts the specified key-value pair into the symbol table, overwriting the
	 * old value with the new value if the symbol table already contains the
	 * specified key.
	 *
	 * @author Amy Xu
	 * @version java version "1.8.0_161"
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	public void put(Key key, Value value){
		root = put(root, key, value);
		root.color = BLACK;
	}
	
	//insert the key-value pair in the subtree rooted at h
	private Node put(Node h, Key key, Value value){
		if (h == null){
			return new Node(key, value, 1, RED);
		}
		int cmp = compare(key, h.key);
		if (cmp < 0){
			h.left = put(h.left, key, value);
		}
		else if (cmp > 0){
			h.right = put(h.right, key, value);
		}
		else{
			h.value = value;
		}
		
		//fix-up any right-leaning links
		if (isRed(h.right) && !isRed(h.left)){
			h = rotateLeft(h);
		}
		if (isRed(h.left) && isRed(h.left.left)){
			h = rotateRight(h);
		}
		if (isRed(h.left) && isRed(h.right)){
			flipColors(h);
		}
		h.N = size(h.left) + size(h.right) + 1;
		return h;
	}
	
	private int compare(Key j, Key k) {
		if(((String) j).substring(8).compareTo(((String) k).substring(8)) < 0) {
			return -1;
		}
		if(((String) j).substring(8).compareTo(((String) k).substring(8)) > 0) {
			return 1;
		}
		else {if(j.compareTo(k) < 0) {
			return -1;
		}
		if(j.compareTo(k) > 0) {
			return 1;
		}
		}
		return 0;
	}
	
	/**
	 * Returns the value associated with the given key.
	 * 
	 * @author Amy Xu
	 * @version java version "1.8.0_161"
	 * @param key
	 *            the key
	 * @return the value associated with the given key
	 */
	public Value search(Key key) {
        return search(root, key, null);
    }

	//value associated with the given key in subtree rooted at node;
	// if the node is null, search the previous node fnode for the require;
    private Value search(Node node, Key key, Node fnode) {
        if (node == null){
        	return fnode.value;
        }
        int comp = compare(key, node.key);
        if (comp < 0){
        		if (node == root) {
        			return search(node.left, key, root);
        		}else {
        			return search(node.left, key, node);
        		}
        }else if (comp > 0){
        		if (node == root) {
        			return search(node.left, key, root);
        		}else {
        			return search(node.right, key, node);
        		}
        }
        else{
        		return node.value;
        }
    }
	
    //make a left-leaning link lean to the right
	private Node rotateRight(Node h){
		Node x = h.left;
		h.left = x.right;
		x.right = h;
		x.color = h.color;
		h.color = RED;
		x.N = h.N;
		h.N = 1 + size(h.left) + size(h.right);
		return x;
	}
	
	//make a right-leaning link lean to the left
	private Node rotateLeft(Node h){
		Node x = h.right;
		h.right = x.left;
		x.left = h;
		x.color = h.color;
		h.color = RED;
		x.N = h.N;
		h.N = 1 + size(h.left) + size(h.right);
		return x;
	}
	
	//flip the colors of a node and its two children
	private void flipColors(Node h){
		h.color = RED;
		h.left.color = BLACK;
		h.right.color = BLACK;
	}
	
	//is node x red; false if x is null?
	private boolean isRed(Node x){
		if (x == null) return false;
		return x.color == RED;
	}
	
	/**
	 * Returns the number of key-value pairs in this symbol table.
	 * 
	 * @author Amy Xu
	 * @version java version "1.8.0_161"
	 * @return the number of key-value pairs in this symbol table
	 */
	public int size(){
		return size(root); 
	}
	
	// number if nodes in subtree rooted at x; 0 if x is null
	private int size(Node x){
		if (x == null) return 0;
		else return x.N;
	}
	
	//BST helper node data type
	private class Node{
		Key key; 
		Value value; 
		Node left, right; 
		int N; 
		boolean color;
		
		public Node(Key key, Value value, int N, boolean color){
			this.key = key;
			this.value = value;
			this.N = N;
			this.color = color;
		}
	}
}