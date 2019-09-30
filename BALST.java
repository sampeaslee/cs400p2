import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Class to implement a BalanceSearchTree. Can be of type AVL or Red-Black.
 * Note which tree you implement here and as a comment when you submit.
 * 
 * @param <K> is the generic type of key
 * @param <V> is the generic type of value
 */
public class BALST<K extends Comparable<K>, V> implements BALSTADT<K, V> {
	
	//Pirvate inner class storing node of the tree 
	private class BSTNode<K,V> {
	    
		K key;
	    V value;
	    BSTNode<K,V> left;
	    BSTNode<K,V> right;
	    int balanceFactor;
	    int height;
	    

	    /**
	     * @param key
	     * @param value
	     * @param leftChild
	     * @param rightChild
	     */
	    BSTNode(K key, V value, BSTNode<K,V>  leftChild, BSTNode<K,V> rightChild) {
	        this.key = key;
	        this.value = value;
	        this.left = leftChild;
	        this.right = rightChild;
	        this.height = 0;
	        this.balanceFactor = 0;
	    }
	    
	    BSTNode(K key, V value) { this(key,value,null,null); }
	    
	}//End of private inner class 
	
	

	private BSTNode<K, V> root;

	private int numKeys;

	public BALST() {
	}

	

	@Override
	public K getKeyAtRoot() {
		if(root == null ) {
			return null;
		}
		return root.key;
	}
    /**
     * Tries to find a node with a key that matches the specified key.
     * If a matching node is found, it returns the returns the key that is in the left child.
     * If the left child of the found node is null, returns null.
     * 
     * @param key A key to search for
     * @return The key that is in the left child of the found key
     * 
     * @throws IllegalNullKeyException if key argument is null
     * @throws KeyNotFoundException if key is not found in this BST
     */
	@Override
	public K getKeyOfLeftChildOf(K key) throws IllegalNullKeyException, KeyNotFoundException {
		if(key == null) {
			throw new  IllegalArgumentException();
		}
		BSTNode<K,V> LeftChild = lookup(root, key).left;//Left child of node with key, if key is found, otherwise throws key not found Exception
		if(LeftChild  == null) {//If left child is null returns null
			return null;
		}else {
			return LeftChild.key;//returns key of left child 
		}
	}
    
    /**
     * Tries to find a node with a key that matches the specified key.
     * If a matching node is found, it returns the returns the key that is in the right child.
     * If the right child of the found node is null, returns null.
     * 
     * @param key A key to search for
     * @return The key that is in the right child of the found key
     * 
     * @throws IllegalNullKeyException if key is null
     * @throws KeyNotFoundException if key is not found in this BST
     */

	@Override
	public K getKeyOfRightChildOf(K key) throws IllegalNullKeyException, KeyNotFoundException {
		if(key == null) {
			throw new  IllegalArgumentException();
		}
		BSTNode<K,V> RightChild = lookup(root, key).right;//Right child of node with key, if key is found, otherwise throws key not found Exception
		if(RightChild  == null) {//If right child is null returns null
			return null;
		}else {
			return RightChild.key;//returns key of right child 
		}
	}
	
	/**
	 * Helper method for getKeyOfRightChildOf and getKeyOfLeftChildOf methods 
	 * Recursively looks through the tree to see if the key is present. If key not present throws KeyNotFoundExcepton 
	 * If key is present returns the node that stores the key 
	 * @param node - node to see if key is present in
	 * @param key - key to look for in node 
	 * @return node that stores key 
	 * @throws KeyNotFoundException if lookup reaches end of tree without finding key then exception is thrown 
	 */
	
	private BSTNode<K,V> lookup(BSTNode<K,V> node, K key) throws KeyNotFoundException{
		if(node == null) {
			throw new KeyNotFoundException();
		}
		if(key.compareTo(node.key) == 0) {
			return node;
		}
		
		if(key.compareTo(node.key) < 0) {
			return lookup(node.left, key);
			
		}else {
			return lookup(node.right, key);
		}
		
	}
	
	
    /**
     * Returns the height of this BST.
     * H is defined as the number of levels in the tree.
     * 
     * If root is null, return 0
     * If root is a leaf, return 1
     * Else return 1 + max( height(root.left), height(root.right) )
     * 
     * Examples:
     * A BST with no keys, has a height of zero (0).
     * A BST with one key, has a height of one (1).
     * A BST with two keys, has a height of two (2).
     * A BST with three keys, can be balanced with a height of two(2)
     *                        or it may be linear with a height of three (3)
     * ... and so on for tree with other heights
     * 
     * @return the number of levels that contain keys in this BINARY SEARCH TREE
     */
	
	@Override
	public int getHeight() {
		if(root == null) {
			return 0;
		}
		return height(root);
	}
	/**
	 * Helper method for getHeight that recursively goes through a tree adding max heights of its subtrees to calculate total height of tree 
	 * @param node
	 * @return the height of the tree 
	 */
	private int height(BSTNode<K,V> node) {
		if(node == null) {
			return 0;
		}
		if(node.left == null & node.right == null) {
			return 1;
		}	
        int heightLeft = height(node.left);//Recursively get the height of the nodes left subtree
        int heightRight = height(node.right);//Recursively get the height of the nodes right subtree 
        int maxHeight = 0;
        //maxHeight is the height of the taller subtree 
        if(heightLeft >= heightRight) {
        	maxHeight = heightLeft;
        }else {
        	maxHeight = heightRight;
        }
		return 1 + maxHeight;//returns maxHeight plus 1, to include the node in its height  
		
		
	}
	
	/**
    * Returns the keys of the data structure in sorted order.
    * In the case of binary search trees, the visit order is: L V R
    * 
    * If the SearchTree is empty, an empty list is returned.
    * 
    * @return List of Keys in-order
    */	
	@Override
	public List<K> getInOrderTraversal() {
		ArrayList <K> inOrderList  =  new ArrayList<>();
		inOrder(root, inOrderList);
		return inOrderList;
	}
	private  void inOrder(BSTNode<K,V> node, ArrayList <K> list){
		if(node == null) {
			return;
		}
		 inOrder(node.left,list);
		 list.add(node.key);
		 inOrder(node.right, list);
	}
    /**
     * Returns the keys of the data structure in pre-order traversal order.
     * In the case of binary search trees, the order is: V L R
     * 
     * If the SearchTree is empty, an empty list is returned.
     * 
     * @return List of Keys in pre-order
     */
	@Override
	public List<K> getPreOrderTraversal() {
		ArrayList<K> preOrderList = new ArrayList<>();
		preOrder(root, preOrderList);
		return preOrderList;
	}
	
	private void preOrder(BSTNode<K,V> node, ArrayList <K> list) {
		if(node == null) {
			return;
		}
		 list.add(node.key);
		 preOrder(node.left,list);
		 preOrder(node.right, list);		
	}
    /**
     * Returns the keys of the data structure in post-order traversal order.
     * In the case of binary search trees, the order is: L R V 
     * 
     * If the SearchTree is empty, an empty list is returned.
     * 
     * @return List of Keys in post-order
     */
	@Override
	public List<K> getPostOrderTraversal() {
		ArrayList<K> postOrderList = new ArrayList<K>();
		postOrder(root, postOrderList);
		return postOrderList;
	}
	
	private void postOrder(BSTNode<K,V> node, ArrayList<K> list) {
		if(node == null) {
			return;
		} 
		 
		 postOrder(node.left,list);
		 postOrder(node.right, list);
		 list.add(node.key);
	}

    /**
     * Returns the keys of the data structure in level-order traversal order.
     * 
     * The root is first in the list, then the keys found in the next level down,
     * and so on. 
     * 
     * If the SearchTree is empty, an empty list is returned.
     * 
     * @return List of Keys in level-order
     */
	@Override
	public List<K> getLevelOrderTraversal() {
		ArrayList<K> levelOrder = new ArrayList<>();
		for(int i = 0; i < getHeight(); i++) {
			levelOrder(root, i, levelOrder);
		}
		return levelOrder;
	}
	
	private void levelOrder(BSTNode<K,V> node, int level, List<K> list) {
		if(node == null) {
			return;
		}
		if(level == 0) {
			list.add(node.key);
		}
		levelOrder(node.left, level - 1, list);
		levelOrder(node.right, level - 1, list);	
	}

	@Override
	public void insert(K key, V value) throws IllegalNullKeyException, DuplicateKeyException {
		if (key == null) {
			throw new IllegalNullKeyException();
		}		
		root = insert(root,key,value);
	}
	
	private BSTNode<K,V> insert(BSTNode<K,V> node, K key, V value) throws DuplicateKeyException{
		if(node == null) {
			return new BSTNode<K,V>(key, value, null, null);
		}
		if(node.key.compareTo(key) == 0) {
			throw new DuplicateKeyException();
		}
		
		if(key.compareTo(node.key) < 0) {
			node.left = insert(node.left, key, value);
		}else {
			 node.right = insert(node.right, key, value);
		}
		
		return node;
	}

	@Override
	public boolean remove(K key) throws IllegalNullKeyException, KeyNotFoundException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public V get(K key) throws IllegalNullKeyException, KeyNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean contains(K key) throws IllegalNullKeyException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int numKeys() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void print() {
		// TODO Auto-generated method stub

	}
	
	public void printTree() {
		if(root == null) {
			return;
		}

		for(int i = 0; i < getHeight(); i++ ) {	
		printTree(root, i);
		}
	}
	
	private void printTree(BSTNode<K,V> node, int level) {
		if(node == null) {
			return;
		}
		if(level == 0) {
			System.out.println(node.key);
		}else {
			printTree(node.left, level - 1);
			printTree(node.right, level -1);
		}
				
		 
	}

	
	public static void main(String[]args) {
		BALST<Integer,Integer> bst = new BALST<>();
		try {
		bst.insert(10, 1);
		bst.insert(15, 1);
		bst.insert(7, 1);
		bst.insert(8, 1);
		bst.insert(6, 1);
		bst.insert(14, 1);
	
		}catch(IllegalNullKeyException e){
			
			
		}catch(DuplicateKeyException e) {
			
		}
		bst.printTree();
		List<Integer> test = bst.getLevelOrderTraversal();
		System.out.println();
		for(int i = 0; i < test.size(); i++) {
			System.out.print(test.get(i) + " ");
		}
	}
	

	
}
