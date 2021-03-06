package spelling;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/***************************************************************
 *  Name:    Wei Xu
 *
 *  Date: Jan 7th, 2020
 *
 *  Description:  --------------------Week 5------------------------------
 *                 AUTOCOMPLETE IMPLEMENT TREE AND TRIE DATA STRUCTURE
 ****************************************************************/

public class AutoCompleteDictionaryTrie implements Dictionary, AutoComplete {
    
    private TrieNode root;
    private int size;
    
    
    public AutoCompleteDictionaryTrie() {
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
    
    public boolean addWord(String word) {
        //TODO: Implement this method.
        TrieNode current = root;
        char[] alphas = word.toLowerCase().toCharArray();
        for (int i = 0; i < alphas.length; i++) {
            
            if (current.getChild(alphas[i]) == null) {
                current = current.insert(alphas[i]);
                //System.out.println("1: " + word + "\t" + current.getText() + "\tchlid: " + current.getChild(alphas[i]) + "\tchlidset: " + current.getValidNextCharacters() + "\troot: " + root.getValidNextCharacters());
                
                if (i == alphas.length - 1) {
                    current.setEndsWord(true);
                    size++;
                    // System.out.println("3: " + word + "\t" + current.getText() + "\tchlid: " + current.getChild(alphas[i]) + "\tchlidset: " + current.getValidNextCharacters() + "\troot: " + root.getValidNextCharacters() + "\tsize: " + size);
                }
            } else {
                current = current.getChild(alphas[i]);
                
                //System.out.println("2: " + word + "\t" + current.getText() + "\tchlid: " + current.getText() + "\tchlidset: " + current.getValidNextCharacters() + "\troot: " + root.getValidNextCharacters());
                if (i == alphas.length - 1 && !current.endsWord()) {
                    current.setEndsWord(true);
                    size++;
                    // System.out.println("4: " + word + "\t" + current.getText() + "\tchlid: " + current.getChild(alphas[i]) + "\tchlidset: " + current.getValidNextCharacters() + "\troot: " + root.getValidNextCharacters() + "\tsize: " + size);
                    
                }
            }
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
        // TODO: Implement this method
        TrieNode current = root;
        
        char[] alphas = s.toLowerCase().toCharArray();
        for (int i = 0; i < alphas.length; i++) {
            if (current.getChild(alphas[i]) == null) {
                return false;
            } else {
                current = current.getChild(alphas[i]);
                if (i == alphas.length - 1 && current.endsWord()) {
                    return true;
                }
            }
        }
        return false;
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
        queue.add(current);
        while (!queue.isEmpty()) {
            current = queue.remove();
            if (current.endsWord()) {
                predictions.add(current.getText());
                
                System.out.println(predictions.toString());
                if (predictions.size() == numCompletions) return predictions;
            }
            for (char i : current.getValidNextCharacters()) {
                queue.add(current.getChild(i));
            }
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
