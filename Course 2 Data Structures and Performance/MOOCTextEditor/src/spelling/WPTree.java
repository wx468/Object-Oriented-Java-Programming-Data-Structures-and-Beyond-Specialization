/**
 *
 */
package spelling;

//import java.util.ArrayList;

import java.util.LinkedList;
import java.util.List;
/***************************************************************
 *  Name:    Wei Xu
 *
 *  Date: Jan 9th, 2020
 *
 *  Description:  --------------------Week 6.2------------------------------
 *                 WPTree implements WordPath by dynamically creating a tree of words
 *                 during a Breadth First Search of Nearby words to create a path between two words.
 ****************************************************************/

/**
 *
 *
 * @author UC San Diego Intermediate MOOC team
 *
 */
public class WPTree implements WordPath {
    
    // this is the root node of the WPTree
    private WPTreeNode root;
    // used to search for nearby Words
    private NearbyWords nw;
    
    // This constructor is used by the Text Editor Application
    // You'll need to create your own NearbyWords object here.
    public WPTree() {
        this.root = null;
        // TODO initialize a NearbyWords object
        Dictionary d = new DictionaryHashSet();
        DictionaryLoader.loadDictionary(d, "data/dict.txt");
        this.nw = new NearbyWords(d);
    }
    
    //This constructor will be used by the grader code
    public WPTree(NearbyWords nw) {
        this.root = null;
        this.nw = nw;
    }
    
    // see method description in WordPath interface
    
    /**
     * WEI XU'S ALGORITHM:
     *
     * root=node(word1);
     * Create a queue for node; queue.add(root);
     * Create a list for collect all children; allChildren.add(root);
     * while (!queue is empty) {
     *     curr = queue.remove(0);
     *    Create a list for distanceone(curr,true);
     *    for (String i: distanceone){
     *        if(!allChildren.contain(i)){
     *           WTPNODE node = curr.addChild(i)
     *           queue.add (node);//current should not change;
     *           if (node.getword==word2){
     *               return node.
     *           }
     *           add i to the allchildren;
     *           }
     *    }
     * }
     *
     *
     * Spelling Suggestions for "pool" are:
     * [spool, poole, pools, cool, fool, tool, wool, poll, pooh, poop]
     * */
    
    public List<String> findPath(String word1, String word2) {
        // TODO: Implement this method.
        //List<String> retList = new LinkedList<>();
        if (!nw.dict.isWord(word2)) {
            return new LinkedList<>();
        }
        
        root = new WPTreeNode(word1, null);
        WPTreeNode curr;
        List<WPTreeNode> queue = new LinkedList<>();
        List<String> allChildren = new LinkedList<>();
        queue.add(root);
        allChildren.add(root.getWord());
        
        while (!queue.isEmpty()) {
            curr = queue.remove(0);
            List<String> distanceOne = nw.distanceOne(curr.getWord(), true);
            //System.out.println("--------------------------" + distanceOne);
            for (String i : distanceOne) {
                if (!allChildren.contains(i)) {
                    WPTreeNode node = curr.addChild(i);
                    queue.add(node);
                    //System.out.println("1 i: " + i + "\tcurr: " + curr + "\t allchildren : " + allChildren + "\tqueue: " + printQueue(queue));
                    
                    if (node.getWord().equals(word2)) {
                        //System.out.println("path: " + node.buildPathToRoot());
                        
                        return node.buildPathToRoot();
                    }
                    allChildren.add(i);
                    //System.out.println("2 i: " + i + "\tcurr: " + curr + "\t allchildren : " + allChildren + "\tqueue: " + printQueue(queue));
                }
            }
        }
        return new LinkedList<String>();
    }
    
    // Method to print a list of WPTreeNodes (useful for debugging)
    private String printQueue(List<WPTreeNode> list) {
        String ret = "[ ";
        
        for (WPTreeNode w : list) {
            ret += w.getWord() + ", ";
        }
        ret += "]";
        return ret;
    }
    
}

/* Tree Node in a WordPath Tree. This is a standard tree with each
 * node having any number of possible children.  Each node should only
 * contain a word in the dictionary and the relationship between nodes is
 * that a child is one character mutation (deletion, insertion, or
 * substitution) away from its parent
 */
class WPTreeNode {
    
    private String word;
    private List<WPTreeNode> children;
    private WPTreeNode parent;
    
    /** Construct a node with the word w and the parent p
     *  (pass a null parent to construct the root)
     * @param w The new node's word
     * @param p The new node's parent
     */
    public WPTreeNode(String w, WPTreeNode p) {
        this.word = w;
        this.parent = p;
        this.children = new LinkedList<WPTreeNode>();
    }
    
    /** Add a child of a node containing the String s
     *  precondition: The word is not already a child of this node
     * @param s The child node's word
     * @return The new WPTreeNode
     */
    public WPTreeNode addChild(String s) {
        WPTreeNode child = new WPTreeNode(s, this);
        this.children.add(child);
        return child;
    }
    
    /** Get the list of children of the calling object
     *  (pass a null parent to construct the root)
     * @return List of WPTreeNode children
     */
    public List<WPTreeNode> getChildren() {
        return this.children;
    }
    
    /** Allows you to build a path from the root node to
     *  the calling object
     * @return The list of strings starting at the root and
     *         ending at the calling object
     */
    public List<String> buildPathToRoot() {
        WPTreeNode curr = this;
        List<String> path = new LinkedList<String>();
        while (curr != null) {
            path.add(0, curr.getWord());
            curr = curr.parent;
        }
        return path;
    }
    
    /** Get the word for the calling object
     *
     * @return Getter for calling object's word
     */
    public String getWord() {
        return this.word;
    }
    
    /** toString method
     *
     * @return The string representation of a WPTreeNode
     */
    public String toString() {
        String ret = "Word: " + word + ", parent = ";
        if (this.parent == null) {
            ret += "null.\n";
        } else {
            ret += this.parent.getWord() + "\n";
        }
        ret += "[ ";
        for (WPTreeNode curr : children) {
            ret += curr.getWord() + ", ";
        }
        ret += (" ]\n");
        return ret;
    }
    
}

