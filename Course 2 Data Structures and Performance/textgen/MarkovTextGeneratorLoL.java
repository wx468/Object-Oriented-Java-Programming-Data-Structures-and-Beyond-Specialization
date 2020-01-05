package textgen;

import java.util.*;

/***************************************************************
 *  Name:    Wei Xu
 *
 *  Date: Jan 4th, 2020
 *
 *  Description:  --------------------------------------------------
 *                word1
 *                |
 *                word2
 *                |
 *                word3 (word1,2,3 as a whole is a linked list)
 *                      also, word1,2,3 are linked list seperately.
 *                ex:
 *                      word1: LLNODE(startword, follow1-follow2-follow3...)
 *                     (similar a row in hashmap)
 *
 *                linked list, so find the wordn at index n is low efficiency.
 ****************************************************************/

public class MarkovTextGeneratorLoL implements MarkovTextGenerator {
    
    // The list of words with their next words
    private List<ListNode> wordList;
    
    // The starting "word"
    private String starter;
    
    // The random number generator
    private Random rnGenerator;
    
    public MarkovTextGeneratorLoL(Random generator) {
        wordList = new LinkedList<ListNode>();
        starter = "";
        rnGenerator = generator;
    }
    
    private int findOf(String w) {
        for (int i = 0; i < wordList.size(); i++) {
            if (wordList.get(i).getWord().equals(w)) {
                return i;
            }
        }
        return -1;
    }
    
    private ListNode findNode(String w) {
        for (int i = 0; i < wordList.size(); i++) {
            if (wordList.get(i).getWord().equals(w)) {
                return wordList.get(i);
            }
        }
        return null;
    }
    
    /**
     * set "starter" to be the first word in the text
     * set "prevWord" to be starter
     * for each word "w" in the source text starting at the second word
     * check to see if "prevWord" is already a node in the list
     * if "prevWord" is a node in the list
     * add "w" as a nextWord to the "prevWord" node
     * else
     * add a node to the list with "prevWord" as the node's word
     * add "w" as a nextWord to the "prevWord" node
     * set "prevWord" = "w"
     * add starter to be a next word for the last word in the source text.
     */
    @Override
    // WEI XU METHOD 2
    
    public void train(String sourceText) {
        String[] words1 = sourceText.split("[\\s]+");
        // add starter to be a next word for the last word in the source text.
        List<String> words = new ArrayList<String>(Arrays.asList(words1));
        words.add(words1[0]);
        starter = words1[0];
        String prevWord = starter;
        for (int i = 1; i < words.size(); i++) {
            ListNode node = findNode(prevWord);//todo:it's a reference? but not a new  Listnode? so no need set back?
            if (node == null) {
                node = new ListNode(prevWord);
                wordList.add(node);
            }
            node.addNextWord(words.get(i));//todo: why hashmap need set back value, linkedlist don't need?
            prevWord = words.get(i);
        }
    }
    


    
/*    @Override
// WEI XU METHOD 1
    public void train(String sourceText) {
        // TODO: Implement this method
        //		BasicDocument bd = new BasicDocument(sourceText);
        //		List<String> words = bd.getToken //todo:why this method not exist?

        String[] words1 = sourceText.split("[^a-zA-Z]+");
        String[] words = new String[words1.length + 1];
        for (int i = 0; i < words1.length; i++) {
            words[i] = words1[i];
        }
        words[words.length - 1] = words[0];
        starter = words[0];
        wordList.add(new ListNode(starter));
        String word = starter;
        for (int i = 1; i < words.length; i++) {
            int index = findOf(word);
            if (index == -1) {
                wordList.add(new ListNode(word));
                index = findOf(word);// the last
                System.out.println("not in the : i = " + i + " index = " + index + "\t" + words[i] + "\t" + wordList);

                ListNode a = wordList.get(index);//todo:it's a reference? but not a new  Listnode? so no need set back?
                a.addNextWord(words[i]);
                wordList.set(index, a);
                System.out.println("after set  : i = " + i + " index = " + index + "\t" + words[i] + "\t" + wordList);

            } else {
                ListNode a = wordList.get(index);
                a.addNextWord(words[i]);
                wordList.set(index, a);//todo: why cannot just write "wordList.get(index).addNextWord(word)"?
                System.out.println("in the list: i = " + i + "\t" + words[i] + "\t" + a);
                //Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.

            }
            word = words[i];

        }
        // ((LinkedList) wordList).getLast();
        //        ListNode last = (ListNode) ((LinkedList) wordList).getLast();
        //        last.addNextWord(starter);
        System.out.println(wordList);
    }*/
    
    /**
     * set "currWord" to be the starter word
     * set "output" to be ""
     * add "currWord" to output
     * while you need more words
     * find the "node" corresponding to "currWord" in the list
     * select a random word "w" from the "wordList" for "node"
     * add "w" to the "output"
     * set "currWord" to be "w"
     * increment number of words added to the list
     */
    @Override
    public String generateText(int numWords) {
        // TODO: Implement this method
        if (wordList.isEmpty()) {
            return "";
        }
        if (numWords == 0) {
            return "";
        }
        String currWord = starter;
        String output = "";
        output += currWord;
        
        while (numWords > 1) {
            String word = findNode(currWord).getRandomNextWord(rnGenerator);
            output += " " + word;
            //System.out.println(word + "\t" + output);
            currWord = word;
            numWords--;
        }
        return output;
    }
    
    
    // Can be helpful for debugging
    @Override
    public String toString() {
        String toReturn = "";
        for (ListNode n : wordList) {
            toReturn += n.toString();
        }
        return toReturn;
    }
    
    /**
     * Retrain the generator from scratch on the source text
     */
    @Override
    public void retrain(String sourceText) {
        // TODO: Implement this method.
        wordList.clear();
        //starter = null;
        //rnGenerator = null;
        
        train(sourceText);
    }
    
    // TODO: Add any private helper methods you need here.
    
    
    /**
     * This is a minimal set of tests.  Note that it can be difficult
     * to test methods/classes with randomized behavior.
     *
     * @param args
     */
    public static void main(String[] args) {
        // feed the generator a fixed random value for repeatable behavior
        MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
        //String textString = "hi there hi Leo";
        //String textString = "";
        
        String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
        System.out.println(textString);
        gen.train(textString);
        System.out.println(gen);
        System.out.println("Generator: " + gen.generateText(0));
        String textString2 = "You say yes, I say no, " +
                "You say stop, and I say go, go, go, " +
                "Oh no. You say goodbye and I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello. " +
                "I say high, you say low, " +
                "You say why, and I say I don't know. " +
                "Oh no. " +
                "You say goodbye and I say hello, hello, hello. " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello. " +
                "Why, why, why, why, why, why, " +
                "Do you say goodbye. " +
                "Oh no. " +
                "You say goodbye and I say hello, hello, hello. " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello. " +
                "You say yes, I say no, " +
                "You say stop and I say go, go, go. " +
                "Oh, oh no. " +
                "You say goodbye and I say hello, hello, hello. " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello, hello, hello,";
        System.out.println(textString2);
        gen.retrain(textString2);
        System.out.println(gen);
        System.out.println(gen.generateText(20));
    }
    
}

/**
 * Links a word to the next words in the list
 * You should use this class in your implementation.
 */
class ListNode {
    // The word that is linking to the next words
    private String word;
    
    // The next words that could follow it
    private List<String> nextWords;
    
    ListNode(String word) {
        this.word = word;
        nextWords = new LinkedList<String>();
    }
    
    public String getWord() {
        return word;
    }
    
    public void addNextWord(String nextWord) {
        nextWords.add(nextWord);
    }
    
    public String getRandomNextWord(Random generator) {
        // TODO: Implement this method
        // The random number generator should be passed from
        // the MarkovTextGeneratorLoL class
        int rand = generator.nextInt(this.nextWords.size());
        //System.out.println("rand = " + rand);
        return nextWords.get(rand);
    }
    
    public String toString() {
        String toReturn = word + ": ";
        for (String s : nextWords) {
            toReturn += s + "->";
        }
        toReturn += "\n";
        return toReturn;
    }
    
}


