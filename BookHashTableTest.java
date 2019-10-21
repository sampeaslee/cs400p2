/**
 * Filename:   TestHashTableDeb.java
 * Project:    p3
 * Authors:    Debra Deppeler (deppeler@cs.wisc.edu)
 * 
 * Semester:   Fall 2018
 * Course:     CS400
 * 
 * Due Date:   before 10pm on 10/29
 * Version:    1.0
 * 
 * Credits:    None so far
 * 
 * Bugs:       TODO: add any known bugs, or unsolved problems here
 */

import org.junit.After;
import java.io.FileNotFoundException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/** 
 * Test HashTable class implementation to ensure that required 
 * functionality works for all cases.
 */
public class BookHashTableTest {

    // Default name of books data file
    public static final String BOOKS = "books.csv";

    // Empty hash tables that can be used by tests
    static BookHashTable bookObject;
    static ArrayList<Book> bookTable;

    static final int INIT_CAPACITY = 2;
    static final double LOAD_FACTOR_THRESHOLD = 0.49;
       
    static Random RNG = new Random(0);  // seeded to make results repeatable (deterministic)

    /** Create a large array of keys and matching values for use in any test */
    @BeforeAll
    public static void beforeClass() throws Exception{
        bookTable = BookParser.parse(BOOKS);
    }
    
    /** Initialize empty hash table to be used in each test */
    @BeforeEach
    public void setUp() throws Exception {
        // TODO: change HashTable for final solution
         bookObject = new BookHashTable(INIT_CAPACITY,LOAD_FACTOR_THRESHOLD);
    }

    /** Not much to do, just make sure that variables are reset     */
    @AfterEach
    public void tearDown() throws Exception {
        bookObject = null;
    }

    private void insertMany(ArrayList<Book> bookTable, int j) 
        throws IllegalNullKeyException, DuplicateKeyException {
        for (int i = 0; i < j; i++ ) {
            bookObject.insert(bookTable.get(i).getKey(), bookTable.get(i));
        }
    }

    /** IMPLEMENTED AS EXAMPLE FOR YOU
     * Tests that a HashTable is empty upon initialization
     */
    
    @Test
    public void test000_collision_scheme() {
        if (bookObject == null)
        	fail("Gg");
    	int scheme = bookObject.getCollisionResolutionScheme();
        if (scheme < 1 || scheme > 9) 
            fail("collision resolution must be indicated with 1-9");
    }
    

    /** IMPLEMENTED AS EXAMPLE FOR YOU
     * Tests that a HashTable is empty upon initialization
     */
    @Test
    public void test000_IsEmpty() {
        //"size with 0 entries:"
        assertEquals(0, bookObject.numKeys());
    }

    /** IMPLEMENTED AS EXAMPLE FOR YOU
     * Tests that a HashTable is not empty after adding one (key,book) pair
     * @throws DuplicateKeyException 
     * @throws IllegalNullKeyException 
     */
    @Test
    public void test001_IsNotEmpty() throws IllegalNullKeyException, DuplicateKeyException {
    	bookObject.insert(bookTable.get(0).getKey(),bookTable.get(0));
        String expected = ""+1;
        //"size with one entry:"
        assertEquals(expected, ""+bookObject.numKeys());
    }
    
    /** IMPLEMENTED AS EXAMPLE FOR YOU 
    * Test if the hash table  will be resized after adding two (key,book) pairs
    * given the load factor is 0.49 and initial capacity to be 2.
    */
    
    @Test 
    public void test002_Resize() throws IllegalNullKeyException, DuplicateKeyException {
    	bookObject.insert(bookTable.get(0).getKey(),bookTable.get(0));
    	int cap1 = bookObject.getCapacity(); 
    	bookObject.insert(bookTable.get(1).getKey(),bookTable.get(1));
    	int cap2 = bookObject.getCapacity(); 	
        //"size with one entry:"
        assertTrue(cap2 > cap1 & cap1 ==2);
    }
    
    /**
     * Test to make sure that insert rehashes(re inserts) the key value pairs when a resizing happens
     * 
     */
    @Test
    public void test00_Resize_check_keys_were_reinserted() throws DuplicateKeyException, IllegalNullKeyException,
    KeyNotFoundException {
        bookObject = new BookHashTable(20,.8);
        insertMany(bookTable,17);
        if(bookObject.getCapacity() != 41) {
            fail();
        }
        for(int i = 0; i < bookObject.numKeys(); i++) {
            if(!bookObject.get(bookTable.get(i).getKey()).equals(bookTable.get(i))) {
                fail();
            }
        }
    }
    
    /*
     * Test that insert method throws the proper exceptions 
     */
    @Test 
    public void test00_insert_throws_correct_exceptions() {
        try {
            bookObject.insert(null, bookTable.get(0));
            fail("IllegalNullKeyException NOT THROWN!!!");  
        }catch(IllegalNullKeyException e) {
            
        }catch(DuplicateKeyException e) {
            fail("DuplicateKeyExcepion should NOT Have been thrown");
        }
        try {
            bookObject.insert(bookTable.get(0).getKey(),bookTable.get(0));
            bookObject.insert(bookTable.get(1).getKey(),bookTable.get(1));
            bookObject.insert(bookTable.get(0).getKey(),bookTable.get(0));
            fail("DuplicateKeyException NOT THROWN!!!");  
        }catch(IllegalNullKeyException e) {
            fail("IllegalNullKeyException should NOT Have been thrown");
        }catch(DuplicateKeyException e) {
            
        }
    }
    /*
     * Test that get throws the proper exceptions
     */
    
    @Test 
    public void test00_get_throws_correct_exceptions() throws DuplicateKeyException {
        try {
            bookObject = new BookHashTable(101,1.0);
            bookObject.insert(bookTable.get(0).getKey(),bookTable.get(0));
            bookObject.insert(bookTable.get(1).getKey(),bookTable.get(1));
            bookObject.insert(bookTable.get(2).getKey(),bookTable.get(2));
            bookObject.get(null);
            fail("IllegalNullKeyExceptions Should have been thrown");
        }catch(IllegalNullKeyException e) {
            
        }catch(KeyNotFoundException e) {
            fail("KeyNotFoundException eshould NOT Have been Thrown");
        }
        try {
            bookObject.get(bookTable.get(4).getKey());      
            fail("KeyNotFoundException NOT THROWN!!!");
        }catch(IllegalNullKeyException e) {
            fail("IllegalNullKeyException should NOT Have been Thrown");
        }catch(KeyNotFoundException e) {
    
        }
    }
    /*
     * Insert 20 keys values and then test that get returns all 20 values correctly 
     */
    @Test
    public void test00_insert_20_keys_get_20_values() throws DuplicateKeyException, IllegalNullKeyException, 
    KeyNotFoundException {
        bookObject = new BookHashTable(101,1.0);
        insertMany(bookTable, 20);
        for(int i = 0; i < bookObject.numKeys(); i ++) {
            if(!bookObject.get(bookTable.get(i).getKey()).equals(bookTable.get(i))){
                fail();
            }
        }
    }
    
    @Test void test00_insert_key_get_value_delete_key_get_value() throws DuplicateKeyException, IllegalNullKeyException{
       try {
        bookObject.insert(bookTable.get(0).getKey(), bookTable.get(0));
        bookObject.get(bookTable.get(0).getKey());  
       }catch(KeyNotFoundException e) {
           fail("Key should have been found");
       }
       try {
           bookObject.remove(bookTable.get(0).getKey());
           bookObject.get(bookTable.get(0).getKey());  
           fail("Get should have thrown a key not found exception");
          }catch(KeyNotFoundException e) {
             
          }
    }
    /*
     * Insert 20 key value pairs check numKeys has been updated correctly.
     * Check that you can get the values of all 20 keys
     * Then delete all twenty keys 
     * Check that get throws a KeyNotFoundException for all 20 removed keys 
     */
    @Test void test00_insert_20_keys_remove_20_keys() throws DuplicateKeyException, IllegalNullKeyException{
       try {
        bookObject = new BookHashTable(101,1.0);
        insertMany(bookTable, 20);
        if(bookObject.numKeys() != 20) {
            fail("Number of keys not incremented correctly");
        }
        for(int i = 0; i < bookObject.numKeys(); i ++) {
           bookObject.get(bookTable.get(i).getKey());      
        }
       }catch(KeyNotFoundException e) {
           fail("Keys should have been found");
        }
     
        for(int i = 0; i < 20; i ++) {
            bookObject.remove(bookTable.get(i).getKey());
        try {    
            bookObject.get(bookTable.get(i).getKey()); 
            fail("KeyNotFoundExceptions should have been thrown, remove not working correctly");
            }catch(KeyNotFoundException e) {
            }
        }
           if(bookObject.numKeys() != 0) {
               fail("Number of keys not decreased correctly");
           }
      
       
       
    }

    
}

