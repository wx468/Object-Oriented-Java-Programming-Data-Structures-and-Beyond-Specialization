/**
 *
 */
package spelling;

import java.util.*;


/***************************************************************
 *  Name:    Wei Xu
 *
 *  Date: Jan 8th, 2020
 *
 *  Description:  --------------------Week 6------------------------------
 *                 If there is a wrong spelling words, this class will give
 *                 a list of suggestions of substitution, insertion and deletion
 *                 from one step to several steps.
 ****************************************************************/

public class NearbyWords implements SpellingSuggest {
    // THRESHOLD to determine how many words to look through when looking
    // for spelling suggestions (stops prohibitively long searching)
    // For use in the Optional Optimization in Part 2.
    private static final int THRESHOLD = 1000;
    
    Dictionary dict;
    
    public NearbyWords(Dictionary dict) {
        this.dict = dict;
    }
    
    /** Return the list of Strings that are one modification away
     * from the input string.
     * @param s The original String
     * @param wordsOnly controls whether to return only words or any String
     * @return list of Strings which are nearby the original string
     */
    public List<String> distanceOne(String s, boolean wordsOnly) {
        List<String> retList = new ArrayList<String>();
        insertions(s, retList, wordsOnly);
        substitution(s, retList, wordsOnly);
        deletions(s, retList, wordsOnly);
        return retList;
    }
    
    
    /** Add to the currentList Strings that are one character mutation away
     * from the input string.
     * @param s The original String
     * @param currentList is the list of words to append modified words
     * @param wordsOnly controls whether to return only words or any String
     * @return
     */
    public void substitution(String s, List<String> currentList, boolean wordsOnly) {
        // for each letter in the s and for all possible replacement characters
        for (int index = 0; index < s.length(); index++) {
            for (int charCode = (int) 'a'; charCode <= (int) 'z'; charCode++) {
                // use StringBuffer for an easy interface to permuting the
                // letters in the String
                StringBuffer sb = new StringBuffer(s);
                sb.setCharAt(index, (char) charCode);
                
                // if the item isn't in the list, isn't the original string, and
                // (if wordsOnly is true) is a real word, add to the list
                // todo:what's difference between wordOnly and dict.isWord?
                if (!currentList.contains(sb.toString()) &&
                        (!wordsOnly || dict.isWord(sb.toString())) &&
                        !s.equals(sb.toString())) {
                    currentList.add(sb.toString());
                }
            }
        }
    }
    
    /** Add to the currentList Strings that are one character insertion away
     * from the input string.
     * @param s The original String
     * @param currentList is the list of words to append modified words
     * @param wordsOnly controls whether to return only words or any String
     * @return
     */
    public void insertions(String s, List<String> currentList, boolean wordsOnly) {
        // TODO: Implement this method
        for (int i = 0; i <= s.length(); i++) {
            for (char charCode = 'a'; charCode <= 'z'; charCode++) {
                StringBuffer sb = new StringBuffer(s);
                sb.insert(i, charCode);
                
                if (!currentList.contains(sb.toString()) &&
                        (!wordsOnly || dict.isWord(sb.toString())) &&
                        !s.equals(sb.toString())) {
                    currentList.add(sb.toString());
                }
            }
        }
    }
    
    /** Add to the currentList Strings that are one character deletion away
     * from the input string.
     * @param s The original String
     * @param currentList is the list of words to append modified words
     * @param wordsOnly controls whether to return only words or any String
     * @return
     */
    public void deletions(String s, List<String> currentList, boolean wordsOnly) {
        // TODO: Implement this method
        for (int i = 0; i < s.length(); i++) {
            StringBuffer sb = new StringBuffer(s);
            sb.deleteCharAt(i);
            
            if (!currentList.contains(sb.toString()) &&
                    (!wordsOnly || dict.isWord(sb.toString())) &&
                    !s.equals(sb.toString())) {
                currentList.add(sb.toString());
            }
        }
    }
    
    
    /** Add to the currentList Strings that are one character deletion away
     * from the input string.
     * @param word The misspelled word
     * @param numSuggestions is the maximum number of suggestions to return
     * @return the list of spelling suggestions
     */
    @Override
    public List<String> suggestions(String word, int numSuggestions) {
        
        // initial variables
        List<String> queue = new LinkedList<String>();     // String to explore
        HashSet<String> visited = new HashSet<String>();   // to avoid exploring the same
        // string multiple times// to prevent add into queue;
        List<String> retList = new LinkedList<String>();   // words to return
        
        
        // insert first node
        queue.add(word);
        visited.add(word);
        int size = 1;
        // TODO: Implement the remainder of this method, see assignment for algorithm
        while (!queue.isEmpty() && size <= THRESHOLD) {
            String curr = ((Queue) queue).remove().toString();//todo: any easier way?
            //System.out.println("------------------queue remove-------------------" + curr);
            List<String> distanceOnc = distanceOne(curr, true);
            for (String i : distanceOnc) {
                
                if (!retList.contains(i) && dict.isWord(i)) {
                    retList.add(i);
                    //System.out.println("1:" + distanceOnc + "\t" + retList + "\t" + queue + "\t" + visited + "\t");
                    
                    if (retList.size() == numSuggestions) {
                        //System.out.println("---------size = " + size);
                        
                        return retList;
                    }
                } else {
                    //System.out.println("-----------retList contains-----------" + i);
                }
                if (!visited.contains(i)) {
                    queue.add(i);
                    visited.add(i);
                    size++;
                    //System.out.println("2:" + distanceOnc + "\t" + retList + "\t" + queue + "\t" + visited + "\t");
                    
                } else {
                    //System.out.println("-----------visit contains-----------" + i);
                }
            }
            
        }
        //System.out.println("---------size = " + size);
        
        return retList;
        
    }
    //    @Override
    //    public List<String> suggestions(String word, int numSuggestions) {
    //        if (numSuggestions < 1) {
    //            return Collections.emptyList();
    //        }
    //        // initial variables
    //        List<String> queue = new LinkedList<String>();     // String to explore
    //        HashSet<String> visited = new HashSet<String>();   // to avoid exploring the same
    //        // string multiple times// to prevent add into queue;
    //        List<String> retList = new LinkedList<String>();   // words to return
    //
    //
    //        // insert first node
    //        queue.add(word);
    //        visited.add(word);
    //        int size = 1;
    //        // TODO: Implement the remainder of this method, see assignment for algorithm
    //        while (!queue.isEmpty()) {//&& size <= THRESHOLD) {
    //            String curr = ((Queue) queue).remove().toString();//todo: any easier way?
    //            //System.out.println("------------------queue remove-------------------" + curr);
    //            List<String> distanceOne = distanceOne(curr, true);
    //            for (String i : distanceOne) {
    //                if (!visited.contains(i)) {
    //                    visited.add(i);
    //                    queue.add(i);
    //                    size++;
    //                    //System.out.println("1:" + distanceOne + "\t" + retList + "\t" + queue + "\t" + visited + "\t");
    //
    //                    //System.out.println("---------size = " + size);
    //                    if (dict.isWord(i)) {      //todo: why need do this? the distanceOne returned is already a word
    //                        retList.add(i);
    //                        //System.out.println("2:" + distanceOne + "\t" + retList + "\t" + queue + "\t" + visited + "\t");
    //
    //                        if (retList.size() == numSuggestions) {
    //                            System.out.println("---------size = " + size);
    //                            return retList;
    //                        }
    //
    //                    }
    //
    //                }
    //            }
    //        }
    //        System.out.println("---------size = " + size);
    //        return retList;
    //    }
    
    public static void main(String[] args) {
        // basic testing code to get started
        //        One away word Strings for for "i" are:
        //        [bi, ci, di, hi, li, mi, ni, pi, si, ti, id, if, in, io, ir, is, it, a, b, c, d, e, f, g, h, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z]
        
        String word = "i";
        // Pass NearbyWords any Dictionary implementation you prefer
        Dictionary d = new DictionaryHashSet();
        DictionaryLoader.loadDictionary(d, "data/dict.txt");
        NearbyWords w = new NearbyWords(d);
        List<String> l = w.distanceOne(word, true);
        System.out.println("One away word Strings for for \"" + word + "\" are:");
        System.out.println(l + "\n");
        
        word = "tailo";
        List<String> suggest = w.suggestions(word, 10);
        System.out.println("Spelling Suggestions for \"" + word + "\" are:");
        System.out.println(suggest);
        
        word = "nama";
        List<String> suggest1 = w.suggestions(word, 20
        );
        System.out.println("Spelling Suggestions for \"" + word + "\" are:");
        System.out.println(suggest1);
        
        word = "kangaro";
        List<String> suggest2 = w.suggestions(word, 10);
        System.out.println("Spelling Suggestions for \"" + word + "\" are:");
        System.out.println(suggest2);
        
        word = "pool";
        List<String> suggest3 = w.suggestions(word, 10);
        System.out.println("Spelling Suggestions for \"" + word + "\" are:");
        System.out.println(suggest3);
    }
    
}
/** Standard answer: distanceOne(curr, false);
 * ---------size = 28714
 * Spelling Suggestions for "tailo" are:
 * [tailor, tails, tail, trails, trail, tamil, tailed, talon, tailors, jailor]
 * ---------size = 12468
 * Spelling Suggestions for "nama" are:
 * [kama, lama, mama, rama, name, nam, manama, panama, asama, drama, enema, fnma, llama, obama, norma, nyasa, nadia, magma, alma, gamma]
 * ---------size = 7165085
 * Spelling Suggestions for "kangaro" are:
 * [kangaroo, kangaroos, hangars, hangar, angara, saguaro, vanguard, kasparov, bangor, calgary]
 *
 *
 * */

/** my answer: distanceOne(curr, false);
 * ---------size = 28713
 * Spelling Suggestions for "tailo" are:
 * [tailor, tails, tail, trails, trail, tamil, tailed, talon, tailors, jailor]
 * ---------size = 12467
 * Spelling Suggestions for "nama" are:
 * [kama, lama, mama, rama, name, nam, manama, panama, asama, drama, enema, fnma, llama, obama, norma, nyasa, nadia, magma, alma, gamma]
 * ---------size = 7165084
 * Spelling Suggestions for "kangaro" are:
 * [kangaroo, kangaroos, hangars, hangar, angara, saguaro, vanguard, kasparov, bangor, calgary]
 *
 * */
/** my answer: distanceOne(curr, true);
 * ---------size = 10
 * Spelling Suggestions for "tailo" are:
 * [tailor, tails, tail, tailors, jailor, sailor, taylor, trails, bails, fails]
 * ---------size = 20
 * Spelling Suggestions for "nama" are:
 * [kama, lama, mama, rama, name, nam, karma, kara, llama, lamar, lamas, lima, lana, lara, lava, lamb, lame, lamp, lams, lam]
 * ---------size = 3
 * Spelling Suggestions for "kangaro" are:
 * [kangaroo, kangaroos]
 *
 *
 * */
/**standard: distanceOne(curr, true);
 *---------size = 11
 * Spelling Suggestions for "tailo" are:
 * [tailor, tails, tail, tailors, jailor, sailor, taylor, trails, bails, fails]
 * ---------size = 21
 * Spelling Suggestions for "nama" are:
 * [kama, lama, mama, rama, name, nam, karma, kara, llama, lamar, lamas, lima, lana, lara, lava, lamb, lame, lamp, lams, lam]
 * ---------size = 3
 * Spelling Suggestions for "kangaro" are:
 * [kangaroo, kangaroos]
 *
 *
 * */
