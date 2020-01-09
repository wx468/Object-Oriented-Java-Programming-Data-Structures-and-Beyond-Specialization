package spelling;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/***************************************************************
 *  Name:    Wei Xu
 *
 *  Date: Jan 8th, 2020
 *
 *  Description:  --------------------Week 5------------------------------
 *                 Test DictionaryHashSetMatchCase class,
 *                 Should be case-sensitive:
 *
 *                 You probably want to allow the first letter in a non-capitalized
 *                 word to be allowed to be capitalized, but you should not allow
 *                 the first letter in a capitalized word to be non-capitalized
 *                 (e.g. "Hello" should always be OK, but "christine" should not).
 *
 *                 Words in all caps are also OK. E.g. HELLO or CHRISTINE
 *
 ****************************************************************/

public class DictionaryHashSetMCTester {
    
    
    private String dictFile = "data/words.small.txt";
    
    DictionaryHashSetMatchCase emptyDict;
    DictionaryHashSetMatchCase smallDict;
    DictionaryHashSetMatchCase largeDict;
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        emptyDict = new DictionaryHashSetMatchCase();
        smallDict = new DictionaryHashSetMatchCase();
        largeDict = new DictionaryHashSetMatchCase();
        
        smallDict.addWord("hello");
        smallDict.addWord("Amber");
        smallDict.addWord("help");
        smallDict.addWord("help");
        
        smallDict.addWord("a");
        smallDict.addWord("subsequent");
        
        DictionaryLoader.loadDictionary(largeDict, dictFile);
    }
    
    
    /**
     * Test if the size method is working correctly.
     */
    @Test
    public void testSize() {
        assertEquals("Testing size for empty dict", 0, emptyDict.size());
        assertEquals("Testing size for small dict", 5, smallDict.size());
        assertEquals("Testing size for large dict", 4440, largeDict.size());
    }
    
    /**
     * Test the isWord method
     */
    @Test
    public void testIsWord() {
        assertEquals("Testing isWord on empty: Hello", false, emptyDict.isWord("Hello"));
        assertEquals("Testing isWord on small: Hello", true, smallDict.isWord("Hello"));
        assertEquals("Testing isWord on large: Hello", true, largeDict.isWord("Hello"));
        
        assertEquals("Testing isWord on small: hello", true, smallDict.isWord("hello"));
        assertEquals("Testing isWord on large: hello", true, largeDict.isWord("hello"));
        assertEquals("Testing isWord on small: hello", false, smallDict.isWord("amber"));
        assertEquals("Testing isWord on small: hello", true, smallDict.isWord("HELLO"));
        
        assertEquals("Testing isWord on small: hellow", false, smallDict.isWord("hellow"));
        assertEquals("Testing isWord on large: hellow", false, largeDict.isWord("hellow"));
        
        assertEquals("Testing isWord on empty: empty string", false, emptyDict.isWord(""));
        assertEquals("Testing isWord on small: empty string", false, smallDict.isWord(""));
        assertEquals("Testing isWord on large: empty string", false, largeDict.isWord(""));
        
        assertEquals("Testing isWord on small: no", false, smallDict.isWord("no"));
        assertEquals("Testing isWord on large: no", true, largeDict.isWord("no"));
        
        assertEquals("Testing isWord on small: subsequent", true, smallDict.isWord("subsequent"));
        assertEquals("Testing isWord on large: subsequent", true, largeDict.isWord("subsequent"));
        
        
    }
    
    /**
     * Test the addWord method
     */
    @Test
    public void addWord() {
        
        
        assertEquals("Asserting hellow is not in empty dict", false, emptyDict.isWord("hellow"));
        assertEquals("Asserting hellow is not in small dict", false, smallDict.isWord("hellow"));
        assertEquals("Asserting hellow is not in large dict", false, largeDict.isWord("hellow"));
        
        emptyDict.addWord("hellow");
        smallDict.addWord("hellow");
        largeDict.addWord("hellow");
        
        assertEquals("Asserting hellow is in empty dict", true, emptyDict.isWord("hellow"));
        assertEquals("Asserting hellow is in small dict", true, smallDict.isWord("hellow"));
        assertEquals("Asserting hellow is in large dict", true, largeDict.isWord("hellow"));
        
        assertEquals("Asserting xyzabc is not in empty dict", false, emptyDict.isWord("xyzabc"));
        assertEquals("Asserting xyzabc is not in small dict", false, smallDict.isWord("xyzabc"));
        assertEquals("Asserting xyzabc is in large dict", false, largeDict.isWord("xyzabc"));
        
        
        emptyDict.addWord("Xyzabc");
        smallDict.addWord("Xyzabc");
        largeDict.addWord("Xyzabc");
        
        assertEquals("Asserting xyzabc is in empty dict", false, emptyDict.isWord("xyzabc"));
        assertEquals("Asserting xyzabc is in small dict", true, smallDict.isWord("XYZABC"));
        
        
        assertEquals("Testing isWord on empty: empty string", false, emptyDict.isWord(""));
        assertEquals("Testing isWord on small: empty string", false, smallDict.isWord(""));
        assertEquals("Testing isWord on large: empty string", false, largeDict.isWord(""));
        
        assertEquals("Testing isWord on small: no", false, smallDict.isWord("no"));
        assertEquals("Testing isWord on large: no", true, largeDict.isWord("no"));
        
        assertEquals("Testing isWord on small: subsequent", true, smallDict.isWord("subsequent"));
        assertEquals("Testing isWord on large: subsequent", true, largeDict.isWord("subsequent"));
        
        
    }
    
    
}
