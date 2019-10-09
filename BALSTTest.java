import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
/*
 * Name: Sam Peaslee
 * Email: speaslee@wisc.edu
 * project: p1 Balanced Search Tree - BALSTTest.java
 * Lecture: 002
 * Description: This class provides JUnit test methods to test the functionality of
 * my balanced search tree implementation 
 */


//@SuppressWarnings("rawtypes")
public class BALSTTest {

    BALST<String,String> balst1;	
    BALST<Integer,String> balst2;

    /**
     * @throws java.lang.Exception
     */
    @BeforeEach
    void setUp() throws Exception {
        balst1 = createInstance();
	balst2 = createInstance2();
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterEach
    void tearDown() throws Exception {
        balst1 = null;
	balst2 = null;
    }

    protected BALST<String, String> createInstance() {
        return new BALST<String,String>();
    }

    protected BALST<Integer,String> createInstance2() {
        return new BALST<Integer,String>();
    }

    /** 
     * Insert three values in sorted order and then check 
     * the root, left, and right keys to see if rebalancing 
     * occurred.
     */
    @Test
    void testBALST_001_insert_sorted_order_simple() {
        try {
            balst2.insert(10, "10");
            if (!balst2.getKeyAtRoot().equals(10)) 
                fail("Red Black Tree insert at root does not work");
            
            balst2.insert(20, "20");
            if (!balst2.getKeyOfRightChildOf(10).equals(20)) 
                fail("Red Black Tree insert to right child of root does not work");
            
            balst2.insert(30, "30");
            Integer k = balst2.getKeyAtRoot();
            if (!k.equals(20)) 
                fail("Red Black Tree  rotate does not work");
            
            // IF rebalancing is working,
            // the tree should have 20 at the root
            // and 10 as its left child and 30 as its right child
            balst2.print();
            Assert.assertEquals(balst2.getKeyAtRoot(), new Integer(20));
            Assert.assertEquals(balst2.getKeyOfLeftChildOf(20),new Integer(10));
            Assert.assertEquals(balst2.getKeyOfRightChildOf(20),new Integer(30));
            
        } catch (Exception e) {
            e.printStackTrace();
            fail( "Unexpected exception Red Black Tree 001: "+e.getMessage() );
        }
    }

    /** 
     * Insert three values in reverse sorted order and then check 
     * the root, left, and right keys to see if rebalancing 
     * occurred in the other direction.
     */
    @Test
    void testBALST_002_insert_reversed_sorted_order_simple() {
        
        try {
            balst2.insert(30, "10");
            if (!balst2.getKeyAtRoot().equals(30)) 
                fail("Red Black Tree insert at root does not work");
            
            balst2.insert(20, "20");
            if (!balst2.getKeyOfLeftChildOf(30).equals(20)) 
                fail("Red Black Tree insert to right child of root does not work");
            
            balst2.insert(10, "30");
            Integer k = balst2.getKeyAtRoot();
            if (!k.equals(20)) 
                fail("Red Black Tree rotate does not work");
            
            // IF rebalancing is working,
            // the tree should have 20 at the root
            // and 10 as its left child and 30 as its right child

            Assert.assertEquals(balst2.getKeyAtRoot(), new Integer(20));
            Assert.assertEquals(balst2.getKeyOfLeftChildOf(20),new Integer(10));
            Assert.assertEquals(balst2.getKeyOfRightChildOf(20),new Integer(30));
            
        } catch (Exception e) {
            e.printStackTrace();
            fail( "Unexpected exception Red Black Tree 002: "+e.getMessage() );
        }
        
    }

    /** 
     * Insert three values so that a right-left rotation is
     * needed to fix the balance.
     * Then check the root, left, and right keys to see if rebalancing 
     * occurred in the other direction.
     */
    @Test
    void testBALST_003_insert_smallest_largest_middle_order_simple() {
        try {
            balst2.insert(10, "10");
            if (!balst2.getKeyAtRoot().equals(10)) 
                fail("Red Black Tree  insert at root does not work");
            
            balst2.insert(30, "30");
            if (!balst2.getKeyOfRightChildOf(10).equals(30)) 
                fail("Red Black Tree insert to right child of root does not work");
            
            balst2.insert(20, "20");
            Integer k = balst2.getKeyAtRoot();
            if (!k.equals(20)) 
                fail("Red Black Tree  rotate does not work");
            // IF rebalancing is working,
            // the tree should have 20 at the root
            // and 10 as its left child and 30 as its right child

            Assert.assertEquals(balst2.getKeyAtRoot(), new Integer(20));
            Assert.assertEquals(balst2.getKeyOfLeftChildOf(20),new Integer(10));
            Assert.assertEquals(balst2.getKeyOfRightChildOf(20),new Integer(30));

            balst2.print();
            
        } catch (Exception e) {
            e.printStackTrace();
            fail( "Unexpected exception Red Black Tree 003: "+e.getMessage() );
        }
        
    }

    /** 
     * Insert three values so that a left-right rotation is
     * needed to fix the balance.
     * 
     * Example: 30-10-20
     * 
     * Then check the root, left, and right keys to see if rebalancing 
     * occurred in the other direction.
     */
    @Test
    void testBALST_004_insert_largest_smallest_middle_order_simple() {
        try {
            balst2.insert(30, "30");
            if (!balst2.getKeyAtRoot().equals(30)) 
                fail("Red Black Tree  insert at root does not work");
            
            balst2.insert(10, "10");
            if (!balst2.getKeyOfLeftChildOf(30).equals(10)) 
                fail("Red Black Tree  insert to right child of root does not work");
            
            balst2.insert(20, "20");
            Integer k = balst2.getKeyAtRoot();
            if (!k.equals(20)) 
                fail("Red Black Tree rotate does not work");
            // IF rebalancing is working,
            // the tree should have 20 at the root
            // and 10 as its left child and 30 as its right child

            Assert.assertEquals(balst2.getKeyAtRoot(), new Integer(20));
            Assert.assertEquals(balst2.getKeyOfLeftChildOf(20),new Integer(10));
            Assert.assertEquals(balst2.getKeyOfRightChildOf(20),new Integer(30));

            balst2.print();
            
        } catch (Exception e) {
            e.printStackTrace();
            fail( "Unexpected exception Red Black Tree  004: "+e.getMessage() );
        }    
    }
    /**
     * Test to see if tree is has correct balanced structure after several inserts 
     * Drew tree with these inserts on paper and compared with what happens with my implementation 
     * 1)Left rotate when 18 is inserted
     * 2)Re-coloring done when 23 is insert
     * 3)Right rotate when 1 is inserted
     * 4)Right left when 20 is inserted 
     * 5)Re-Coloring when 15 is inserted 
     * 6)Left right rotate when 17 is inserted 
     */
    @Test
    void testBALST005_retains_balanced_structure_after_several_inserts() {
    	try {
    		balst2.insert(7,"");
    		balst2.insert(14,"");
    		balst2.insert(18, "");
    		balst2.insert(23, "");
    		balst2.insert(4, "");
    		balst2.insert(1, "");
    		balst2.insert(20,"");
    		balst2.insert(15,"");
    		balst2.insert(17,"");
    	    if (!balst2.getKeyAtRoot().equals(14) |!balst2.getKeyOfLeftChildOf(14).equals(4)
    	    		|!balst2.getKeyOfRightChildOf(14).equals(20)) {
    	    	 fail("Red Black Tree  insert at root does not work");
    	    }
    	    if (!balst2.getKeyOfLeftChildOf(4).equals(1)
    	    		|!balst2.getKeyOfRightChildOf(4).equals(7)) {
    	    	 fail("Red Black Tree  insert does not work");
    	    }
    	    if (!balst2.getKeyOfLeftChildOf(20).equals(17)
    	    		|!balst2.getKeyOfRightChildOf(20).equals(23)) {
    	    	 fail("Red Black Tree  insert does not work");
    	    }
    	    if (!balst2.getKeyOfLeftChildOf(17).equals(15)
    	    		|!balst2.getKeyOfRightChildOf(17).equals(18)) {
    	    	 fail("Red Black Tree  insert does not work");
    	    }
    	    if (balst2.getKeyOfLeftChildOf(18) != null
    	    		|balst2.getKeyOfRightChildOf(18) != null) {
    	    	 fail("Red Black Tree  insert does not work");
    	    }
    	    if (balst2.getKeyOfLeftChildOf(15) != null
    	    		|balst2.getKeyOfRightChildOf(15) != null) {
    	    	 fail("Red Black Tree  insert does not work");
    	    }
    	    if (balst2.getKeyOfLeftChildOf(1) != null
    	    		|balst2.getKeyOfRightChildOf(1) != null) {
    	    	 fail("Red Black Tree  insert does not work");
    	    }
    	    if (balst2.getKeyOfLeftChildOf(7) != null
    	    		|balst2.getKeyOfRightChildOf(7) != null) {
    	    	 fail("Red Black Tree  insertdoes not work");
    	    }
    		}catch(Exception e) {
    		e.printStackTrace();
    		fail();
    	}
    }
    /*
     * Checks that height is correctly inserted after multiple inserts 
     */
    @Test
    void testBALST006_check_height() {
        try {
            balst2.insert(30, "30");
            balst2.insert(10, "10");
            balst2.insert(20, "20");
            if(balst2.getHeight() != 2) {
            	fail("Height not calculated correcty");
            }
            
            balst2.insert(35, "30");
            balst2.insert(40, "10");
            balst2.insert(15, "30");
            balst2.insert(16,"30");
            if(balst2.getHeight() != 3) {
            	fail("Height not calculated correcty");
            }
           
            balst2.insert(55, "30");
            balst2.insert(56, "10");
            balst2.insert(12, "30");
            balst2.insert(17,"30");
            balst2.insert(13,"30");
            if(balst2.getHeight() != 4) {
            	fail("Height not calculated correcty");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            fail( "Unexpected exception Red Black Tree"+e.getMessage() );
        }
    }
    /*
     * Test that the get method correctly returns the value associated with a key 
     */
    @Test
    void testBALST007_insert_many_keys_and_get_thier_values() {
        try {
            balst2.insert(30, "30");
            balst2.insert(10, "10");
            balst2.insert(20, "20");
            balst2.insert(55, "55");
            balst2.insert(78, "78");
            balst2.insert(11,"11");
            balst2.insert(14, "14");
            balst2.insert(43, "43");
            balst2.insert(12, "12");
            balst2.insert(15,"15");
            balst2.insert(18,"18");
            if(!balst2.get(30).equals("30")) {
            	fail();
            }
            if(!balst2.get(10).equals("10")) {
            	fail();
            }
            
            if(!balst2.get(20).equals("20")) {
            	fail();
            }
            if(!balst2.get(55).equals("55")) {
            	fail();
            }
            if(!balst2.get(78).equals("78")) {
            	fail();
            }
            if(!balst2.get(11).equals("11")) {
            	fail();
            }
            if(!balst2.get(12).equals("12")) {
            	fail();
            }
            if(!balst2.get(15).equals("15")) {
            	fail();
            }
            if(!balst2.get(18).equals("18")) {
            	fail();
            }     
        } catch (Exception e) {
            e.printStackTrace();
            fail( "Unexpected exception Red Black Tree  004: "+e.getMessage() );
        }
	}

	/*
	 * Test that contains returns true for a key that is in the tree and false for a
	 * key that is not
	 */
	@Test
	void testBALST008_insert_many_keys_and_search() {
		try {
			balst2.insert(30, "30");
			balst2.insert(10, "10");
			balst2.insert(20, "20");
			balst2.insert(55, "55");
			balst2.insert(78, "78");
			balst2.insert(11, "11");
			balst2.insert(12, "12");
			balst2.insert(15, "15");
			balst2.insert(18, "18");
			if (!balst2.contains(30) || !balst2.contains(10) || !balst2.contains(20) || !balst2.contains(55)
					|| !balst2.contains(78) || !balst2.contains(11) || !balst2.contains(12) || !balst2.contains(15)
					|| !balst2.contains(18)) {
				fail();
			}

			if (balst2.contains(1000)) {
				fail();
			}

		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected exception Red Black Tree  004: " + e.getMessage());
		}
	}
    
    /*
	 * Inserts keys, removes two, then checks that the keys that were removed are
	 * gone and the keys not removed are still there. Delete method does not check
	 * to see if Red Black Properties a retained
	 */
	@Test
	void testBALST009_insert_many_keys_delete_keys_then_insert_deleted_key() {
		try {
			balst2.insert(30, "30");
			balst2.insert(10, "10");
			balst2.insert(20, "20");
			balst2.insert(55, "55");
			balst2.insert(78, "78");
			balst2.insert(11, "11");
			balst2.insert(14, "14");
			balst2.insert(43, "43");
			balst2.insert(12, "12");
			balst2.insert(15, "15");
			balst2.insert(18, "18");
			
			balst2.remove(43);
			balst2.remove(12);
			if (balst2.contains(43) || balst2.contains(12)) {
				fail();
			}
			if (!balst2.contains(30) || !balst2.contains(10) || !balst2.contains(20) || !balst2.contains(55)
					|| !balst2.contains(78) || !balst2.contains(11) || !balst2.contains(14) || !balst2.contains(15)
					|| !balst2.contains(18)) {
				fail();
			}
			balst2.insert(43, "");
			if (!balst2.contains(43)) {
				fail();
			}

		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected exception Red Black Tree  004: " + e.getMessage());
		}
	}
	/*
	 * Test that inOrder method properly stores keys 
	 */
	
	@Test
	void testBALST010_InOrderTraversal() {
		try {
		balst2.insert(50, "");
		balst2.insert(60, "");
		balst2.insert(20,"");
		balst2.insert(70, "");
		balst2.insert(65,"");
	
		ArrayList<Integer> test = new ArrayList<>();
		test.add(20); test.add(50); test.add(60); test.add(65); test.add(70);
		List<Integer> inOrder = balst2.getInOrderTraversal();
		for(int i = 0; i < inOrder.size(); i++) {
			if(inOrder.get(i) != test.get(i)) {
				fail();
			}
		}
		
		}catch(Exception e) {
			e.printStackTrace();
			fail("Unexpected exception Red Black Tree  004: " + e.getMessage());
		}
	}
	
	/*
	 * Test that the preOrder Method properly stores keys 
	 */
	@Test
	void testBALST011_preOrderTraversal() {
		try {
		balst2.insert(50, "");
		balst2.insert(60, "");
		balst2.insert(20,"");
		balst2.insert(70, "");
		balst2.insert(65,"");

		ArrayList<Integer> test = new ArrayList<>();
		test.add(50); test.add(20); test.add(65); test.add(60); test.add(70);
		List<Integer> inOrder = balst2.getPreOrderTraversal();
		for(int i = 0; i < inOrder.size(); i++) {
			if(inOrder.get(i) != test.get(i)) {
				fail();
			}
		}
		
		}catch(Exception e) {
			e.printStackTrace();
			fail("Unexpected exception Red Black Tree  004: " + e.getMessage());
		}
	}
	/*
	 * Test that the postOrder Method properly stores keys 
	 */
	@Test
	void testBALST012_postOrderTraversal() {
		try {
		balst2.insert(50, "");
		balst2.insert(60, "");
		balst2.insert(20,"");
		balst2.insert(70, "");
		balst2.insert(65,"");
		ArrayList<Integer> test = new ArrayList<>();
		test.add(20); test.add(60); test.add(70); test.add(65); test.add(50);
		List<Integer> inOrder = balst2.getPostOrderTraversal();
		for(int i = 0; i < inOrder.size(); i++) {
			if(inOrder.get(i) != test.get(i)) {
				fail();
			}
		}
		
		}catch(Exception e) {
			e.printStackTrace();
			fail("Unexpected exception Red Black Tree  004: " + e.getMessage());
		}
	}
	
	
	/*
	 * Test that the postOrder Method properly stores keys 
	 */
	@Test
	void testBALST013_levelOrderTraversal() {
		try {
		balst2.insert(50, "");
		balst2.insert(60, "");
		balst2.insert(20,"");
		balst2.insert(70, "");
		balst2.insert(65, "");
		ArrayList<Integer> test = new ArrayList<>();
		test.add(50); test.add(20); test.add(65); test.add(60); test.add(70);
		List<Integer> inOrder = balst2.getLevelOrderTraversal();
		for(int i = 0; i < inOrder.size(); i++) {
			if(inOrder.get(i) != test.get(i)) {
				fail();
			}
		}
		
		}catch(Exception e) {
			e.printStackTrace();
			fail("Unexpected exception Red Black Tree  004: " + e.getMessage());
		}
	}
	
	/**
	 * Insert 1,2,3,4,5,6,7,8,9 in sorted order and prints tree 
	 */
	@Test
	void test_print_structure() {
		try {
			balst2.insert(1, "");
			balst2.insert(2, "");
			balst2.insert(3,"");
			balst2.insert(4, "");
			balst2.insert(5, "");
			balst2.insert(6, "");
			balst2.insert(7,"");
			balst2.insert(8,"");
			balst2.insert(9,"");
			balst2.print();
			System.out.println("Inserted in sorted order 1,2,3,4,5,6,7,8,9");
			System.out.println("Thats a good looking tree right there!!!");
		}catch(Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	/*
	 * Test that the methods function correctly on an empty tree 
	 */
	@Test
	void test_functionality_of_methods_on_empty_tree() {
		if (!(balst2.getKeyAtRoot() == null)) {
			fail("Check getKeyAtRoot");
		}
		try {
			balst2.getKeyOfLeftChildOf(20);
			fail("Check getKeyOfLeftChild");
		} catch (KeyNotFoundException e) {

		} catch (IllegalNullKeyException e) {

		}
		try {
			balst2.getKeyOfRightChildOf(20);
			fail("Check getKeyOfRightChild");
		} catch (KeyNotFoundException e) {

		} catch (IllegalNullKeyException e) {

		}

		if (balst2.getHeight() != 0) {
			fail("Check getHeight");
		}
		if (balst2.numKeys() != 0) {
			fail("Check numKeys");
		}
		try {
			if (balst2.contains(11)) {
				fail("Check contains");
			}
		} catch (IllegalNullKeyException e) {

		}

		try {
			balst2.remove(24);
			fail("Check remove");
		} catch (KeyNotFoundException e) {

		} catch (IllegalNullKeyException e) {

		}
		try {
			balst2.get(1);
			fail("Check get");
		} catch (KeyNotFoundException e) {

		} catch (IllegalNullKeyException e) {

		}
	}
	/*
	 * Test that methods function correctly on a tree with only a root
	 */
	@Test
	void test_functionality_of_methods_on_tree_with_1_node() {
		try {
			balst2.insert(11, "11");
			if (balst2.getKeyAtRoot() != 11) {
				fail("Check getKeyAtRoot");
			}
			if (balst2.getKeyOfLeftChildOf(11) != null) {
				fail("Check getKeyOfRightChild");
			}

			if (balst2.getKeyOfRightChildOf(11) != null) {
				fail("Check getKeyOfRightChild");
			}

			if (balst2.getHeight() != 1) {
				fail("Check getHeight");
			}
			if (balst2.numKeys() != 1) {
				fail("Check numKeys");
			}
			if (!balst2.contains(11)) {
				fail("Check contains");
			}

			if (!balst2.get(11).equals("11")) {
				fail("Check get");
			}
			if (!balst2.remove(11)) {
				fail("Check Remove");
			}

		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected Exception");
		}
	}
	/*
	 * Test that insert method proper increases number of keys and
	 * remove method properly decreases number of keys 
	 */
	@Test
	void test_remove_properly_decreases_number_of_keys() {
		try {
			balst1.insert("SAM","SAM");
			balst1.insert("bob","bob");
			balst1.insert("SAM1","SAM1");
			balst1.insert("bob1","bob1");
			if(balst1.numKeys() != 4) {
				fail("Check insert");
			}
			balst1.remove("bob");
			if(balst1.numKeys() != 3) {
				fail("Check remove");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	/*
	 * Test that methods properly throws exceptions when needed,
	 * also that they throw the correct exception 
	 */
	@Test
	void test_proper_exceptions_are_thrown() {
		try {
			balst2.insert(null, "2");
			fail("IllegalNullException not thrown");
		}catch (IllegalNullKeyException e) {
		}catch (DuplicateKeyException e) {
			fail();	
		}
		try {
			balst2.insert(1, "1");
			balst2.insert(24, "24");
			balst2.insert(1, "12");
			fail("DuplicateKeyException not thrown");
		}catch (IllegalNullKeyException e) {
			fail();
		}catch (DuplicateKeyException e) {
		}
		try {
			balst2.remove(null);
			fail("IllegalNullKeyException not thrown");
		}catch (IllegalNullKeyException e) {
			
		}catch ( KeyNotFoundException e) {
			fail();
		}
		try {
			balst2.remove(122);
			fail("KeyNotFoundException not thrown");
		}catch (IllegalNullKeyException e) {
			fail();
		}catch (KeyNotFoundException e) {
		
		}
		
		try {
			balst2.get(null);
			fail("IllegalNullKeyException not thrown");
		}catch (IllegalNullKeyException e) {
			
		}catch ( KeyNotFoundException e) {
			fail();
		}
		try {
			balst2.get(122);
			fail("KeyNotFoundException not thrown");
		}catch (IllegalNullKeyException e) {
			fail();
		}catch (KeyNotFoundException e) {
		
		}
	}
		
}
