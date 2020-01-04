/**
 *
 */
package textgen;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author UC San Diego MOOC team
 *
 */
public class MyLinkedListTester {
    
    private static final int LONG_LIST_LENGTH = 10;
    
    MyLinkedList<String> shortList;
    MyLinkedList<Integer> emptyList;
    MyLinkedList<Integer> longerList;
    MyLinkedList<Integer> list1;
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        // Feel free to use these lists, or add your own
        shortList = new MyLinkedList<String>();
        shortList.add("A");
        shortList.add("B");
        emptyList = new MyLinkedList<Integer>();
        longerList = new MyLinkedList<Integer>();
        for (int i = 0; i < LONG_LIST_LENGTH; i++) {
            longerList.add(i);
        }
        list1 = new MyLinkedList<Integer>();
        list1.add(65);
        list1.add(21);
        list1.add(42);
        
    }
    
    
    /** Test if the get method is working correctly.
     */
    /*You should not need to add much to this method.
     * We provide it as an example of a thorough test. */
    @Test
    public void testGet() {
        //test empty list, get should throw an exception
        try {
            emptyList.get(0);
            fail("Check out of bounds");
        } catch (IndexOutOfBoundsException e) {
        
        }
        
        // test short list, first contents, then out of bounds
        assertEquals("Check first", "A", shortList.get(0));
        assertEquals("Check second", "B", shortList.get(1));
        
        try {
            shortList.get(-1);
            fail("Check out of bounds");
        } catch (IndexOutOfBoundsException e) {
        
        }
        try {
            shortList.get(2);
            fail("Check out of bounds");
        } catch (IndexOutOfBoundsException e) {
        
        }
        // test longer list contents
        for (int i = 0; i < LONG_LIST_LENGTH; i++) {
            assertEquals("Check " + i + " element", (Integer) i, longerList.get(i));
        }
        
        // test off the end of the longer array
        try {
            longerList.get(-1);
            fail("Check out of bounds");
        } catch (IndexOutOfBoundsException e) {
        
        }
        try {
            longerList.get(LONG_LIST_LENGTH);
            fail("Check out of bounds");
        } catch (IndexOutOfBoundsException e) {
        }
        
    }
    
    
    /** Test removing an element from the list.
     * We've included the example from the concept challenge.
     * You will want to add more tests.  */
    //todo: what need add??
    @Test
    public void testRemove() {
        int a = list1.remove(0);
        assertEquals("Remove: check a is correct ", 65, a);
        assertEquals("Remove: check element 0 is correct ", (Integer) 21, list1.get(0));
        assertEquals("Remove: check size is correct ", 2, list1.size());
        
        // TODO: Add more tests here
    }
    
    /** Test adding an element into the end of the list, specifically
     *  public boolean add(E element)
     * */
    /**
     * WEI XU: ONLY NEED TO ADD STH, THEN CHECK IF THE LAST ONE IS THE ONE YOU WANT
     * */
    @Test
    public void testAddEnd() {
        // TODO: implement this test
        emptyList.add(52);
        assertEquals("Check first", (Integer) 52, emptyList.tail.prev.data);// can it public tail and head,prev, next?
        shortList.add("WEI XU");
        assertEquals("Check second", "WEI XU", shortList.tail.prev.data);
        list1.add(52);
        String a = list1.toString();//todo: why i cannot use toString() if i not write in the MyLinkedList class?
        System.out.println(a);
        assertEquals("WEI XU", list1.toString(), "[65, 21, 42, 52]");// cannot use this, since size() not been tested. get(list1.size() - 1));
        
    }
    
    
    /** Test adding an element into the list at a specified index,
     * specifically:
     * public void add(int index, E element)
     * */
    @Test
    public void testAddAtIndex() {
        // TODO: implement this test
        emptyList.add(0, 52);
        assertEquals("Check first", (Integer) 52, emptyList.tail.prev.data);// can it public tail and head,prev, next?
        shortList.add(1, "WEI XU");
        assertEquals("Check second", "WEI XU", shortList.get(1));
        assertEquals("Check second", "B", shortList.get(2));
        assertEquals("Check SIZE", (Integer) 3, (Integer) shortList.size());
        shortList.add(3, "WEI XU");
        assertEquals("Check second", "WEI XU", shortList.get(3));
        
    }
    
    /** Test the size of the list */
    @Test
    public void testSize() {
        assertEquals("Check size 1", (Integer) 0, (Integer) emptyList.size());// can it public tail and head,prev, next?
        assertEquals("Check size 2", (Integer) 2, (Integer) shortList.size());
        
    }
    
    /** Test setting an element in the list */
    @Test
    public void testSet() {
        // TODO: implement this test
        int a = list1.set(0, 52);
        assertEquals("Remove: check a is correct ", 65, a);
        assertEquals("Remove: check element 0 is correct ", (Integer) 52, list1.get(0));
        assertEquals("Remove: check size is correct ", 3, list1.size());
        
    }
    
    
    // TODO: Optionally add more test methods.
    @Test //must write "test" here
    public void testToString() {
        assertEquals("toString Check ", "[65, 21, 42]", list1.toString());
        
    }
    
}
