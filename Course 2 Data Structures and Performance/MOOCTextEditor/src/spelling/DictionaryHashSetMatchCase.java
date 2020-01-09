/**
 *
 */
package spelling;

import java.util.HashSet;


/***************************************************************
 *  Name:    Wei Xu
 *
 *  Date: Jan 8th, 2020
 *
 *  Description:  --------------------Week 5------------------------------
 *                 Improvement of DictionaryBST class,
 *                 Should be case-sensitive:
 *
 *                 You probably want to allow the first letter in a non-capitalized
 *                 word to be allowed to be capitalized, but you should not allow
 *                 the first letter in a capitalized word to be non-capitalized
 *                 (e.g. "Hello" should always be OK, but "christine" should not).
 *
 *                 Words in all caps are also OK. E.g. HELLO or CHRISTINE
 *
 ****************************************************************************/
public class DictionaryHashSetMatchCase implements Dictionary {
    
    private HashSet<String> words;
    
    public DictionaryHashSetMatchCase() {
        words = new HashSet<String>();
    }
    
    /** Add this word to the dictionary.
     * @param word The word to add
     * @return true if the word was added to the dictionary
     * (it wasn't already there). */
    @Override
    public boolean addWord(String word) {
        if (words.contains(word)) return false;
        words.add(word);
        return true;
    }
    
    /** Return the number of words in the dictionary */
    @Override
    public int size() {
        return words.size();
    }
    
    /** Is this a word according to this dictionary? */
    @Override
    public boolean isWord(String s) {
        //format xxxxx
        if (s.equals("")) {
            return false;
        }
        //format xxxxx;
        else if (s.equals(s.toLowerCase())) {
            
            return words.contains(s);
            // format Xxxxx
        } else if (s.indexOf(0) == Character.toUpperCase(s.indexOf(0)) && s.substring(1).equals(s.substring(1).toLowerCase())) {
            return (Xxxxx(s));
            //format XXXXX turns to Xxxxx
        } else if (s.equals(s.toUpperCase())) {
            s = s.charAt(0) + s.substring(1).toLowerCase();
            return (Xxxxx(s));
        }
        return false;
    }
    
    private boolean Xxxxx(String s) {
        if (words.contains(s)) {
            return true;
        } else {
            return words.contains(s.toLowerCase());
        }
    }
    
}
