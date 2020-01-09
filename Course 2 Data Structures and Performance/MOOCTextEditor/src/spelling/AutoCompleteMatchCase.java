package spelling;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/***************************************************************
 *  Name:    Wei Xu
 *
 *  Date: Jan 8th, 2020
 *
 *  Description:  --------------------Week 5------------------------------
 *                 Improvement of AutoCompleteDictionaryTrie class,
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

public class AutoCompleteMatchCase implements Dictionary, AutoComplete {
    
    private TrieNode root;
    private int size;
    
    
    public AutoCompleteMatchCase() {
        root = new TrieNode();
    }
    
    
    /**
     * Insert a word into the trie.
     * For the basic part of the assignment (part 2), you should convert the
     * string to all lower case before you insert it.
     * <p>
     * This method adds a word by creating and linking the necessary trie nodes
     * into the trie, as described outlined in the videos for this week. It
     * should appropriately use existing nodes in the trie, only creating new
     * nodes when necessary. E.g. If the word "no" is already in the trie,
     * then adding the word "now" would add only one additional node
     * (for the 'w').
     *
     * @return true if the word was successfully added or false if it already exists
     * in the dictionary.
     */
    
    //Third TRY: Works
    public boolean addWord(String word) {
        //TODO: Implement this method.
        TrieNode current = root;
        char[] alphas = word.toCharArray();
        for (char i : alphas) {
            
            if (current.getChild(i) == null) {
                current = current.insert(i);
            } else {
                current = current.getChild(i);
            }
        }
        if (!current.endsWord()) {
            current.setEndsWord(true);
            size++;
        }
        return true;
    }
    
    /*
     * Return the number of words in the dictionary.  This is NOT necessarily the same
     * as the number of TrieNodes in the trie.
     */
    public int size() {
        //TODO: Implement this method
        return size;
    }
    
    
    @Override
    public boolean isWord(String s) {
        TrieNode current = root;
        char[] alphas = s.toLowerCase().toCharArray();
        for (char i : alphas) {
            if (current.getChild(i) == null) {
                return false;
            } else {
                current = current.getChild(i);
            }
        }
        return current.endsWord();
    }
    
    /**
     * Return a list, in order of increasing (non-decreasing) word length,
     * containing the numCompletions shortest legal completions
     * of the prefix string. All legal completions must be valid words in the
     * dictionary. If the prefix itself is a valid word, it is included
     * in the list of returned words.
     * <p>
     * The list of completions must contain
     * all of the shortest completions, but when there are ties, it may break
     * them in any order. For example, if there the prefix string is "ste" and
     * only the words "step", "stem", "stew", "steer" and "steep" are in the
     * dictionary, when the user asks for 4 completions, the list must include
     * "step", "stem" and "stew", but may include either the word
     * "steer" or "steep".
     * <p>
     * If this string prefix is not in the trie, it returns an empty list.
     *
     * @param prefix         The text to use at the word stem
     * @param numCompletions The maximum number of predictions desired.
     * @return A list containing the up to numCompletions best predictions
     */
    @Override
    public List<String> predictCompletions(String prefix, int numCompletions) {
        // TODO: Implement this method
        List<String> predictions = new LinkedList<>();
        // This method should implement the following algorithm:
        // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
        //    empty list
        // 2. Once the stem is found, perform a breadth first search to generate completions
        //    using the following algorithm:
        //    Create a queue (LinkedList) and add the node that completes the stem to the back
        //       of the list.
        //    Create a list of completions to return (initially empty)
        //    While the queue is not empty and you don't have enough completions:
        //       remove the first Node from the queue
        //       If it is a word, add it to the completions list
        //       Add all of its child nodes to the back of the queue
        // Return the list of completions
        if (numCompletions == 0) {
            return Collections.emptyList();
        }
        TrieNode current = root;
        if (!prefix.equals("")) {
            char[] alphas = prefix.toLowerCase().toCharArray();
            for (int i = 0; i < alphas.length; i++) {
                if (current.getChild(alphas[i]) == null) {
                    return Collections.emptyList();
                } else {
                    current = current.getChild(alphas[i]);
                }
            }
        }
        
        Queue<TrieNode> queue = new LinkedList<>();
        queue.add(current);//if stem is "he" then current.getText = "he"
        
        //        System.out.println("---------1-test for queue : " + current.getText() + "----------");
        //        for (TrieNode c : queue) {
        //            System.out.println(c.getText());
        //        }
        
        while (!queue.isEmpty()) {
            current = queue.remove();
            if (current.endsWord()) {
                predictions.add(current.getText());
                
                //System.out.println(predictions.toString());
                if (predictions.size() == numCompletions) return predictions;
            }
            for (char i : current.getValidNextCharacters()) {
                queue.add(current.getChild(i));
            }
            //            System.out.println("--------2--test for queue : " + current.getText() + "----------");
            //
            //            for (TrieNode c : queue) {
            //                System.out.println(c.getText());
            //            }
        }
        
        return predictions;
    }
    
    // For debugging
    public void printTree() {
        printNode(root);
    }
    
    /**
     * Do a pre-order traversal from this node down
     */
    public void printNode(TrieNode curr) {
        if (curr == null)
            return;
        
        System.out.println(curr.getText());
        
        TrieNode next = null;
        for (Character c : curr.getValidNextCharacters()) {
            next = curr.getChild(c);
            printNode(next);
        }
    }
    
    
}
