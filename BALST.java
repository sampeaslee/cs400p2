import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
/**
 * 
 * Class to implement a BalanceSearchTree. Can be of type AVL or Red-Black.
 * Note which tree you implement here and as a comment when you submit.
 * 
 * @param <K> is the generic type of key
 * @param <V> is the generic type of value
 */
public class BALST<K extends Comparable<K>, V> implements BALSTADT<K, V> {
	
	enum Color {
		RED, BLACK;
	}
	//Public inner class storing node of the tree 
	private class BSTNode<K,V> {
	    
		K key;
	    V value;
	    BSTNode<K,V> left;
	    BSTNode<K,V> right;
	    BSTNode<K,V> parent;
	    Color color;

	    /**
	     * @param key
	     * @param value
	     * @param leftChild
	     * @param rightChild
	     */
	  BSTNode(K key, V value, Color color,BSTNode<K,V>  leftChild, BSTNode<K,V> rightChild, BSTNode<K,V> parent) {
	        this.key = key;
	        this.value = value;
	        this.color = color;
	        this.left = leftChild;
	        this.right = rightChild;
	        this.parent = parent;
	
	    }
	  
	  

		/**
		 * Left rotate around a BSTNode
		 * @param node- rotated around
		 * @return reference to updated node 
		 */
	  //LOOOOK AT THIS SHIT
		private BSTNode<K,V> leftRotate() throws KeyNotFoundException{
			BSTNode<K,V> temp = this.right;
			this.right = temp.left;
			temp.left = this;
			
			temp.parent = this.parent;
			this.parent = temp;
			if(this.right != null) {
			this.right.parent = this;
			}
			
			
		
			return temp;
		}	
		/**
		 * Right rotate around a BSTNode
		 * @param node- rotated around
		 * @return reference to updated node 
		 */	
		private  BSTNode<K,V> rightRotate() throws KeyNotFoundException{
			BSTNode<K,V> temp = this.left;
			this.left = temp.right;
			temp.right = this;
			
			temp.parent = this.parent;
			this.parent= temp;
			
			if(this.right != null) {
			this.right.parent = this;
			}
			return temp;
		}
	    
	 // BSTNode(K key, V value) { this(key,value,null,null,null); }  
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
	
	public BSTNode<K,V> lookup(K Key) throws KeyNotFoundException{
		return lookup(root, Key);
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
		List<K> levelOrder = new ArrayList<>();
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
			//If key is null throw Exception
			throw new IllegalNullKeyException();
		}		
		root = insert(root,key,value,null);	
		try {
			repair(lookup(key));
			while(root.parent != null) {
				root = root.parent;
				root.color = Color.BLACK;
			}		
		}catch(Exception e) {
			e.printStackTrace();
		}		
	}
	
	private BSTNode<K,V> insert(BSTNode<K,V> node, K key, V value, BSTNode<K,V> parent) throws DuplicateKeyException{
		
		if(node == null) {
			numKeys++;
			return new BSTNode<K,V>(key, value,Color.RED, null, null, parent);
		}
		if(node.key.compareTo(key) == 0) {
			throw new DuplicateKeyException();
		}
		
		if(key.compareTo(node.key) < 0) {
			node.left = insert(node.left, key, value,node);
		}else {
			
			 node.right = insert(node.right, key, value,node);
		}
		return node;
	}

	
	private void repair(BSTNode<K,V> node) throws IllegalNullKeyException, KeyNotFoundException{
		if(node.parent == null) {
			System.out.println("ROOT CASE" + node.key);
			//Root case
			node.color = Color.BLACK;
			return;
		}
		BSTNode<K,V> sibling = this.getSibling(node.parent.key);
		BSTNode<K,V> grandMa = node.parent.parent;
		
		if(node.parent.color == Color.BLACK ) {
			System.out.println("NO Violations" + node.key);
			//If parent is black no violation 
			return;
		}else if(sibling != null && sibling.color == Color.RED ) {
			System.out.println("ReColoring");
			node.parent.color = Color.BLACK;
			sibling.color = Color.BLACK;
			if(grandMa != root) {
			grandMa.color = Color.RED;
			if(grandMa.parent.color == Color.RED) {
				System.out.println("Recursive repair");
				repair(grandMa);
			}
			}
			
			
		}else {
			if(node == node.parent.right && node.parent == grandMa.left) {
				System.out.println("A");
				node.parent = node.parent.leftRotate();
				grandMa.left = node.parent;
				node = node.parent.left;
				
			}else if(node == node.parent.left && node.parent == grandMa.right) {
				System.out.println("B");
				node.parent = node.parent.rightRotate();
				grandMa.right = node.parent;
				node = node.parent.right;
			}
			
			if(node == node.parent.right) {
				System.out.println("C" + node.key);
				 if(grandMa.parent != null && grandMa.parent.left == grandMa) {
					 grandMa = grandMa.leftRotate();
					 grandMa.parent.left = grandMa;
				 }else {
					 grandMa = grandMa.leftRotate();
					 if(grandMa.parent != null) {
					 grandMa.parent.right = grandMa;
					 }
				 }
				 grandMa.left.color = Color.RED;
				 node.parent.color = Color.BLACK;

					

			}else {	
				if(grandMa.parent != null && grandMa.parent.left == grandMa) {
					System.out.println("D");
					grandMa = grandMa.rightRotate();
					grandMa.parent.left = grandMa;
				}else {
					System.out.println("E" + node.key);
					 grandMa = grandMa.rightRotate();
					 if(grandMa.parent != null) {
					 grandMa.parent.right = grandMa;
					 }else {
						 //node.color = Color.BLACK;
					 }
							 
				}
				node.parent.color = Color.BLACK;
				grandMa.right.color = Color.RED;
		
			}
			
			
		}	
	}
	 /** 
	    * If key is found, remove the key,value pair from the data structure and decrease num keys.
	    * If key is not found, do not decrease the number of keys in the data structure.
	    * If key is null, throw IllegalNullKeyException
	    * If key is not found, throw KeyNotFoundException().
	    * Removing the key does not look to repair Red Black Properties, they could be violated depending on 
	    * the node removed. 
	    */
	@Override
	public boolean remove(K key) throws IllegalNullKeyException, KeyNotFoundException {
		if(key == null) {
			throw new IllegalNullKeyException();
		}
			lookup(key);
			root = remove(root, key);
			numKeys--;
			return true;		
	}
	
	private BSTNode<K,V> remove(BSTNode<K,V> node, K key) {
		if(node == null) {
			return null;
		}
		if(key.compareTo(node.key) == 0 ) {
			if(node.left == null && node.right == null) {
				return null;
			}
			if(node.left == null) {
				return node.right;
			}
			if(node.right == null) {
				return node.left;
			}
			
			K inOrderPred = inOrderPred(node.left);
			node.key = inOrderPred;
			node.left = remove(node.left,inOrderPred);
		}
		
		if(key.compareTo(node.key) < 0) {
			node.left = remove(node.left, key);
		}else {
			node.right = remove(node.right,key);
		}
		
		return node;
	}
	
	private K inOrderPred(BSTNode<K,V> node) {
		if(node.right == null) {
			return node.key;
		}else {
			return inOrderPred(node.right);
		}
	}
	
    /**
     *  Returns the value associated with the specified key
     *
     * Does not remove key or decrease number of keys
     * If key is null, throw IllegalNullKeyException 
     * If key is not found, throw KeyNotFoundException().
     */
	@Override
	public V get(K key) throws IllegalNullKeyException, KeyNotFoundException {	
			if(key == null) {
				throw new IllegalNullKeyException();
			}
			BSTNode<K,V> node = lookup(root, key);
			return node.value;
	}
	//Get parent of key 
	private K getParent(K key) throws IllegalNullKeyException, KeyNotFoundException{
		if(key == null) {
			throw new IllegalNullKeyException();
		}
		BSTNode<K,V> node = lookup(root,key);
		if(node == root) {// Root doesn't not have a parent Node
			return null;
		}
		return(node.parent.key);
		
	}
	//Get sibling of key 
	private BSTNode<K,V> getSibling(K key) throws IllegalNullKeyException, KeyNotFoundException{
		if(key == null) {
			throw new IllegalNullKeyException();
		}
			BSTNode<K,V> node = lookup(root, key);
			if(node == root) {//node is root has no siblings 
				return null;
			}
			BSTNode<K,V> parentNode = node.parent;
			K leftChildKey = getKeyOfLeftChildOf(parentNode.key);
			K rightChildKey = getKeyOfRightChildOf(parentNode.key);
			//Parent node only has one child node has no siblings 
			if(leftChildKey == null |  rightChildKey == null) {
				return null;
			}else {
				if(node.key.compareTo(leftChildKey) == 0) {
					return lookup(root,rightChildKey);
				}else {
					return lookup(root,leftChildKey);
				}
			}	
	}
	
	

	
    /** 
     * Returns true if the key is in the data structure
     * If key is null, throw IllegalNullKeyException 
     * Returns false if key is not null and is not present 
     */
	@Override
	public boolean contains(K key) throws IllegalNullKeyException {
		if(key == null) {
			throw new IllegalNullKeyException();
		}
		try {
		lookup(root, key);
		}catch(KeyNotFoundException e) {
			return false;
		}
		return true;
	}


    /**
     *  Returns the number of key,value pairs in the data structure
     */
	@Override
	public int numKeys() {
		return numKeys;
	}
    /**
     * Print the tree. 
     *
     * For our testing purposes: all keys that we insert in the tree
     * will have a string length of exactly 2 characters.
     * example: numbers 10-99, or strings aa - zz, or AA to ZZ
     *
     * This makes it easier for you to not worry about spacing issues.
     *
     * You can display in any of a variety of ways, but we should see
     * a tree that we can identify left and right children of each node
     *
     * For example: 
     
       |       |-------50
       |-------40
       |       |-------35
       30
       |-------20
       |       |-------10
       
       Look from bottom to top. Inorder traversal of above tree (10,20,30,35,40,50)
       
       Or, you can display a tree of this kind.
       
           30
           /\
          /  \
         20  40
         /   /\
        /   /  \
       10  35  50 

       Or, you can come up with your own orientation pattern, like this.

       10                 
               20
                       30
       35                
               40
       50                  

       The connecting lines are not required if we can interpret your tree.

     */
	@Override
	public void print() {
		if(root == null) {
			return;
		}
		
		print(root, 0, numKeys);
	}	
	public void print(BSTNode<K,V> node, int spaces, int count) {
		if(node == null){
		return;
		}
		spaces += count;
		print(node.right, spaces, count);
		for(int i = count; i < spaces; i ++) {
			System.out.print(" ");
		}
			System.out.println(node.key);
		
		print(node.left, spaces, numKeys);
	}

	public static void main(String[]args) {
		
		BALST<Integer,Integer> bst = new BALST<>();
		try {
		bst.insert(8, 1);
		bst.insert(9, 1);
		bst.insert(7, 1);
		bst.insert(6,1);
		bst.insert(5,1);
		bst.insert(4,1);
		bst.insert(3,1);
		bst.insert(2,1);
		//System.out.println("root " + bst.lookup(1).parent.key);
		/*
		System.out.println("1" + bst.lookup(1).color);
		System.out.println("2" + bst.lookup(2).color);
		System.out.println("3 " + bst.lookup(3).color);
		System.out.println("4" + bst.lookup(4).color);
		System.out.println("5" + bst.lookup(5).color);
		*/
		System.out.println("6" + bst.lookup(6).color);
		System.out.println("4" + bst.lookup(4).color);
		System.out.println("5" + bst.lookup(5).color);
		
		//bst.insert(7,1);
		//bst.insert(8,15);
		//bst.insert(, 1);
		bst.print();

		
		

		BALST<Integer,Integer> rightLeft = new BALST<>();
		rightLeft.insert(20,1);
		rightLeft.insert(25,1);
		rightLeft.insert(24,1);
		rightLeft.insert(27,1);
		rightLeft.insert(28,1);
		rightLeft.insert(26,1);
		rightLeft.insert(16,1);
		rightLeft.insert(17, 1);


		rightLeft.print();
		
		
		BALST<Integer,Integer> test = new BALST<>();
		System.out.println("Recoloring test");
		test.insert(56, 1);
		test.insert(77, 1);
		test.insert(45,11);
		test.insert(12, 1);
		test.print();
		//System.out.println("root " + test.lookup(56).parent.color);
		System.out.println("right " + test.lookup(77).color);
		System.out.println("left " + test.lookup(45).color);
		System.out.println("left left " + test.lookup(12).color);
		
		System.out.println();
		System.out.println("GRANDMA IS ROOT!!!");
		BALST<Integer,Integer> test2 = new BALST<>();
		System.out.println("Node is parent right and parent is grandmas right");
		test2.insert(56, 1);
		//test.insert(77, 1);
		test2.insert(45,11);
		test2.insert(60, 1);
		test2.insert(70,1);
		test2.print();
		System.out.println();
		System.out.println("Node is parents right, parents in grandmas left");
		BALST<Integer,Integer> test3 = new BALST<>();
		test3.insert(56, 1);
		test3.insert(40, 1);
		test3.insert(45,1);
		test3.print();
		System.out.println();
		BALST<Integer,Integer> test4 = new BALST<>();
		System.out.println("Node is parents left and parent is grandmas left");
		test4.insert(56, 1);
		test4.insert(46, 1);
		test4.insert(26,1);
		test4.print();
		System.out.println();
		BALST<Integer,Integer> test5 = new BALST<>();
		System.out.println("Node is parents left and parent is grandmas right");
		test5.insert(56, 1);
		test5.insert(66, 1);
		test5.insert(60,1);
		test5.print();
		System.out.println();
		System.out.println("GRANDMA IS NOT ROOT");
		BALST<Integer,Integer> test6 = new BALST<>();	
		System.out.println("");
		test6.insert(50, 1);
		test6.insert(60, 1);
		test6.insert(20,1);
		test6.insert(70, 1);
		test6.insert(65,1);
		test6.print();
		System.out.println();

		System.out.println();
		
		BALST<Integer,Integer> test7 = new BALST<>();	
		System.out.println("");
		test7.insert(1, 1);
		test7.insert(2, 1);
		test7.insert(3,1);
		test7.insert(4,1);
		test7.print();
		System.out.println(" " + test7.root.color + test7.root.left.color  +test7.root.right.color);
		System.out.println();
		
		test7.print();
		System.out.println();

		}catch(IllegalNullKeyException e){
			e.printStackTrace();
			
		}catch(DuplicateKeyException e) {
			e.printStackTrace();
			
		}catch(KeyNotFoundException e) {
			e.printStackTrace();
		}

		
	}
	

	
}

/*
private void repair(BSTNode<K,V> node) throws IllegalNullKeyException, KeyNotFoundException{
	if(node.parent == null) {
		System.out.println("ROOT CASE" + node.key);
		//Root case
		node.color = Color.BLACK;
		return;
	}
	BSTNode<K,V> sibling = this.getSibling(node.parent.key);
	BSTNode<K,V> grandMa = node.parent.parent;
	
	if(node.parent.color == Color.BLACK ) {
		System.out.println("NO Violations" + node.key + node.color);
		//If parent is black no violation 
		return;
	}else if(sibling != null && sibling.color == Color.RED ) {
		System.out.println("ReColoring");
		node.parent.color = Color.BLACK;
		sibling.color = Color.BLACK;
		if(grandMa != root) {
		grandMa.color = Color.RED;
		if(grandMa.parent.color == Color.RED) {
			System.out.println("Recursive repair");
			repair(grandMa);
		}
		}
		
		
	}else {
		if(node == node.parent.right && node.parent == grandMa.left) {
			System.out.println("A");
			node.parent = node.parent.leftRotate();
			grandMa.left = node.parent;
			node = node.parent.left;
			
		}else if(node == node.parent.left && node.parent == grandMa.right) {
			System.out.println("B");
			node.parent = node.parent.rightRotate();
			grandMa.right = node.parent;
			node = node.parent.right;
		}
		
		if(node == node.parent.right) {
			System.out.println("C" + node.key);
			 if(grandMa.parent != null && grandMa.parent.left == grandMa) {
				 grandMa = grandMa.leftRotate();
				 grandMa.parent.left = grandMa;
			 }else {
				 grandMa = grandMa.leftRotate();
				 if(grandMa.parent != null) {
				 grandMa.parent.right = grandMa;
				 }
			 }
		}else {		
			if(grandMa.parent != null && grandMa.parent.left == grandMa) {
				System.out.println("D");
				grandMa = grandMa.rightRotate();
				grandMa.parent.left = grandMa;
			}else {
				System.out.println("E" + node.key);
				 grandMa = grandMa.rightRotate();
				 if(grandMa.parent != null) {
				 grandMa.parent.right = grandMa;
				 }
		
			}
			node.parent.color = Color.BLACK;
			if(grandMa.parent != null){
			grandMa.color = Color.RED;
			}
		}
		//node.parent.color = Color.BLACK;
		//grandMa.color = Color.RED;
	}	
}
*/