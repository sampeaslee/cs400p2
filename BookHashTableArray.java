import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

// TODO: comment and complete your HashTableADT implementation
//
// TODO: implement all required methods
// DO ADD REQUIRED PUBLIC METHODS TO IMPLEMENT interfaces
//
// DO NOT ADD ADDITIONAL PUBLIC MEMBERS TO YOUR CLASS 
// (no public or package methods that are not in implemented interfaces)
//
// TODO: describe the collision resolution scheme you have chosen
// identify your scheme as open addressing or bucket
//
// if open addressing: describe probe sequence 
// if buckets: describe data structure for each bucket
//
// TODO: explain your hashing algorithm here 
// NOTE: you are not required to design your own algorithm for hashing,
//       since you do not know the type for K,
//       you must use the hashCode provided by the <K key> object

/** HashTable implementation that uses:
 * @param <K> unique comparable identifier for each <K,V> pair, may not be null
 * @param <V> associated value with a key, value may be null
 */
public class BookHashTableArray implements HashTableADT<String, Book> {

    /** The initial capacity that is used if none is specifed user */
    static final int DEFAULT_CAPACITY = 101;
    
    /** The load factor that is used if none is specified by user */
    static final double DEFAULT_LOAD_FACTOR_THRESHOLD = 0.75;
    
    private LinkedList<KeyValue>[] hashTable;
    private double loadFactorThreshold;
    private int numKeys;
    
    private class KeyValue{
        String key;
        Book value;
        KeyValue(String key, Book value) {
            this.key = key;
            this.value = value;
        }
        public String getKey(){
            return this.key;
        }
        public Book getValue() {
            return this.value;
        }
    }
    /**
     * REQUIRED default no-arg constructor
     * Uses default capacity and sets load factor threshold 
     * for the newly created hash table.
     */
    public BookHashTableArray() {
        this(DEFAULT_CAPACITY,DEFAULT_LOAD_FACTOR_THRESHOLD);
    }
    
    /**
     * Creates an empty hash table with the specified capacity 
     * and load factor.
     * @param initialCapacity number of elements table should hold at start.
     * @param loadFactorThreshold the ratio of items/capacity that causes table to resize and rehash
     */
    public BookHashTableArray(int initialCapacity, double loadFactorThreshold) {
        // TODO: comment and complete a constructor that accepts initial capacity 
        // and load factor threshold and initializes all fields
       hashTable =  new LinkedList[initialCapacity];
       this.loadFactorThreshold = loadFactorThreshold;
    }
    // Add the key,value pair to the data structure and increase the number of keys.
    // If key is null, throw IllegalNullKeyException;
    // If key is already in data structure, throw DuplicateKeyException();
    @Override
    public void insert(String key, Book value)throws IllegalNullKeyException, DuplicateKeyException {
        if(key == null) {
            throw new IllegalNullKeyException();
        }    
        LinkedList<KeyValue> bucket;//Create LinkedList reference
        KeyValue keyVal = new KeyValue(key,value);//Create KeyValue reference
        int hashCode = key.hashCode()  &  0x7FFFFFFF;//Calculate hashcode for key
        int hashIndex = hashCode % hashTable.length;//Get hash index by modding hashcode with hashTable size     
        if(hashTable[hashIndex] == null) {
            //LinkedList at the hashIndex is not initialized need to create a new empty linked list
            bucket = new LinkedList<>();
        }else {
            //LinkedList at hashIndex is already initialized 
            bucket = hashTable[hashIndex];
        }    
        if(numKeys == 0) {
            //There are no keys stored in the hashtable, so just add the key to it
              bucket.add(keyVal);
              hashTable[hashIndex] = bucket;
              numKeys++;        
        }else {
        try {
            //Using get to search through hashtable to see if the key is already stored or not
            get(key);
            //If get does not throw a KeyNotFoundException, key is already in the hashtable 
            throw new DuplicateKeyException();
            
        }catch(KeyNotFoundException e) {
            //KeyNotFoundException thrown by get method, key can be added to the hashTable 
            bucket.add(keyVal);
            hashTable[hashIndex] = bucket;
            numKeys++;
        }
       }
    }
 
    
    // If key is found, 
    //    remove the key,value pair from the data structure
    //    decrease number of keys.
    //    return true
    // If key is null, throw IllegalNullKeyException
    // If key is not found, return false  
    @Override
    public boolean remove(String key) throws IllegalNullKeyException {
       if(key == null) {
           throw new IllegalNullKeyException();
       }
       LinkedList<KeyValue> bucket;//Create LinkedList Reference
       KeyValue keyValue;//Create KeyValue reference
       int hashCode = key.hashCode()  &  0x7FFFFFFF;//Calculate hashcode for key
       int hashIndex = hashCode % hashTable.length;
       if (hashTable[hashIndex] == null) {
           //Nothing stored at that index in the hashtable yet
           return false;
       } else {
           bucket = hashTable[hashIndex];
           if(bucket.size() == 0) {
               //LinkedList is empty no keys stored
               return false;
           }
       }
       //Linked List not empty need to search through the list and remove key if its present 
       for (int i = 0; i < bucket.size(); i++) {
           keyValue = bucket.get(i);
           if (key.equals(keyValue.getKey())) {
               bucket.remove(keyValue);
               numKeys--;
               return true;
           }
       }
       //Key was not found in the linked list, so nothing was removed
       return false;
    }

    // Returns the value associated with the specified key
    // Does not remove key or decrease number of keys
    //
    // If key is null, throw IllegalNullKeyException 
    // If key is not found, throw KeyNotFoundException().
    @Override
    public Book get(String key) throws IllegalNullKeyException, KeyNotFoundException {
        if (key == null) {
            // If key is null throw exception
            throw new IllegalNullKeyException();
        }

        LinkedList<KeyValue> bucket;//Create LinkedList Reference
        KeyValue keyValue;//Create KeyValue reference
        int hashCode = key.hashCode()  &  0x7FFFFFFF;//Calculate hashcode for key
        int hashIndex = hashCode % hashTable.length;
        if (hashTable[hashIndex] == null) {
            //Nothing stored at that index in the hashtable yet
            throw new KeyNotFoundException();
        } else {
            bucket = hashTable[hashIndex];
            if(bucket.size() == 0) {
                //LinkedList is empty no keys stored
                throw new KeyNotFoundException();
            }
        }
        //LinkedList is not empty, iterate through the list to try and find key 
        for (int i = 0; i < bucket.size(); i++) {
            keyValue = bucket.get(i);
            if (key.equals(keyValue.getKey())) {
                //key is found in the LinkedList
                return keyValue.getValue();
            }
        }
        //After iterating through list key was not found
        throw new KeyNotFoundException();
    }

    @Override
    public int numKeys() {
        return numKeys;
    }

    @Override
    public double getLoadFactorThreshold() {
        // TODO Auto-generated method stub
        return loadFactorThreshold;
    }

    @Override
    public int getCapacity() {
        return hashTable.length;
    }
    // Returns the collision resolution scheme used for this hash table.
    // Implement this ADT with one of the following collision resolution strategies
    // and implement this method to return an integer to indicate which strategy.
    //
     // 1 OPEN ADDRESSING: linear probe
     // 2 OPEN ADDRESSING: quadratic probe
     // 3 OPEN ADDRESSING: double hashing
     // 4 CHAINED BUCKET: array list of array lists
     // 5 CHAINED BUCKET: array list of linked lists
     // 6 CHAINED BUCKET: array list of binary search trees
     // 7 CHAINED BUCKET: linked list of array lists
     // 8 CHAINED BUCKET: linked list of linked lists
     // 9 CHAINED BUCKET: linked list of of binary search trees
    @Override
    public int getCollisionResolutionScheme() {
        return 5;
    }

    public static void main(String[] args) {
        try {
        ArrayList<Book> bookTable = BookParser.parse("books.csv");
        System.out.println("From CSV:"+bookTable.get(0).toString());
        System.out.println("From CSV:"+bookTable.get(1).toString());
        BookHashTableArray hashTable = new BookHashTableArray(10,.7);
        hashTable.insert("test1",bookTable.get(0) );
        hashTable.insert("test2",bookTable.get(1) );
        hashTable.insert("test3",bookTable.get(2) );
        System.out.println(hashTable.get("test1"));
        System.out.println(hashTable.get("test2"));
        System.out.println( bookTable.get(4).getKey().hashCode() &  0x7FFFFFFF);

        ArrayList<Integer> sam = new ArrayList<>(10);
        
        for(int i = 0; i < 10; i++) {
            sam.add( null);
        }
        sam.set(9,10);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

}