package document;

/***************************************************************
 *  Name:    Wei Xu
 *
 *  Date: Jan 1st, 2020
 *
 *  Description:  --------------------------------------------------
 *                Abstract class
 *                after several testing, improve 1 is faster than improve 2,
 *                faster than standard answer.
 *
 ****************************************************************/


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Document {
    
    private String text;
    
    /**
     * Create a new document from the given text.
     * Because this class is abstract, this is used only from subclasses.
     *
     * @param text The text of the document.
     */
    protected Document(String text) {
        this.text = text;
    }
    
    /**
     * Returns the tokens that match the regex pattern from the document
     * text string.
     *
     * @param pattern A regular expression string specifying the
     *                token pattern desired
     * @return A List of tokens from the document text that match the regex
     * pattern
     */
    protected List<String> getTokens(String pattern) {
        ArrayList<String> tokens = new ArrayList<String>();
        Pattern tokSplitter = Pattern.compile(pattern);
        Matcher m = tokSplitter.matcher(text);
        
        while (m.find()) {
            tokens.add(m.group());
        }
        return tokens;
    }
    
    //Standard Answer.
 /*   protected static int countSyllables(String word) {
        //System.out.print("Counting syllables in " + word + "...");
        int numSyllables = 0;
        boolean newSyllable = true;
        String vowels = "aeiouy";
        char[] cArray = word.toCharArray();
        for (int i = 0; i < cArray.length; i++) {
            if (i == cArray.length - 1 && Character.toLowerCase(cArray[i]) == 'e'
                    && newSyllable && numSyllables > 0) {
                numSyllables--;
            }
            if (newSyllable && vowels.indexOf(Character.toLowerCase(cArray[i])) >= 0) {
                newSyllable = false;
                numSyllables++;
            } else if (vowels.indexOf(Character.toLowerCase(cArray[i])) < 0) {
                newSyllable = true;
            }
        }
        //System.out.println( "found " + numSyllables);
        return numSyllables;
    }*/
    
    // Wei Xu Answer Improve 2:
/*    protected int countSyllables(String word) {
        int count = 0;
        String vowels = "aeiouy";
        String wordlower = word.toLowerCase();
        boolean consonant = true;
        for (int i = 0; i < word.length(); i++) {
            if (i == word.length() - 1 && wordlower.charAt(i) == 'e' && count > 0 && consonant) {
                count--;
                //System.out.println("last : " + (word.length() - 1) + "\t" + word.charAt(word.length() - 1) + "\t" + count + "\t" + vowels.indexOf(Character.toLowerCase(word.length() - 2)));
            }
            
            //check consonant first, then check the next letter if its a vowel;
            
            int index = vowels.indexOf(wordlower.charAt(i));
            if (index == -1) {
                consonant = true;
                
                // System.out.println(i + "\t" + word.charAt(i) + "\t" + consonant);
            } else if (consonant && index != -1) {
                count++;
                consonant = false;
                //System.out.println(i + "\t" + word.charAt(i) + "\t" + consonant + "count= " + count);
                
            }
            
            
        }
        
        return count;
    }*/
    
    /**
     * This is a helper function that returns the number of syllables
     * in a word.  You should write this and use it in your
     * BasicDocument class.
     * <p>
     * You will probably NOT need to add a countWords or a countSentences
     * method here.  The reason we put countSyllables here because we'll
     * use it again next week when we implement the EfficientDocument class.
     * <p>
     * For reasons of efficiency you should not create Matcher or Pattern
     * objects inside this method. Just use a loop to loop through the
     * characters in the string and write your own logic for counting
     * syllables.
     *
     * @param word The word to count the syllables in
     * @return The number of syllables in the given word, according to
     * this rule: Each contiguous sequence of one or more vowels is a syllable,
     * with the following exception: a lone "e" at the end of a word
     * is not considered a syllable unless the word has no other syllables.
     * You should consider y a vowel.
     */
    
    // Wei Xu Answer Improve 1:
    protected int countSyllables(String word) {
        int count = 0;
        String vowels = "aeiouy";
        String wordlower = word.toLowerCase();
        for (int i = 0; i < word.length(); i++) {
            
            //check vowel first, then check the previous letter if its a consonant;
            if (vowels.indexOf(wordlower.charAt(i)) != -1) {
                count++;
                //System.out.println(i + "\t" + word.charAt(i) + "\t" + "count1= " + count);
                
                if (i > 0 && vowels.indexOf(wordlower.charAt(i - 1)) != -1) {
                    count--;
                    //System.out.println(i + "\t" + word.charAt(i) + "\t" + "count1= " + count);
                    
                }
            }
            if (i == word.length() - 1 && wordlower.charAt(i) == 'e' && count > 1 && vowels.indexOf(wordlower.charAt(word.length() - 2)) == -1) {
                count--;
                //System.out.println(word.length() - 1 + "\t" + word.charAt(word.length() - 1) + "\t" + count + "\t" + vowels.indexOf(Character.toLowerCase(word.length() - 2)));
            }
        }
        
        return count;
    }
    
    //Wei Xu Answer 1
/*    protected int countSyllables(String word) {
        int count = 0;
        String vowels = "aeiouy";
        //failed, should be 1,but get 2
        //qui
        //1	u	1
        //2	i	2
        for (int i = 0; i < word.length(); i++) {//no need search the last index here;
            int index = vowels.indexOf(Character.toLowerCase(word.charAt(i)));
            if (index != -1) {
                count++;
                // System.out.println(i + "\t" + word.charAt(i) + "\t" + count);
                for (int j = i + 1; j < word.length(); j++) {
                    if (vowels.indexOf(Character.toLowerCase(word.charAt(j))) == -1 || j == word.length() - 1) {
                        i = j;
                        break;
                    }
                }
            }
        }
        if (word.charAt(word.length() - 1) == 'e' &&
                vowels.indexOf(Character.toLowerCase(word.charAt(word.length() - 2))) == -1 &&
                count > 1) {//|| (vowels.indexOf(word.length() - 1) != -1 && vowels.indexOf(word.length() - 2) != -1)) {
            count--;
            //System.out.println(word.length() - 1 + "\t" + word.charAt(word.length() - 1) + "\t" + count + "\t" + vowels.indexOf(Character.toLowerCase(word.length() - 2)));
            
        }
        
        return count;
    }*/
    
    /**
     * A method for testing
     *
     * @param doc       The Document object to test
     * @param syllables The expected number of syllables
     * @param words     The expected number of words
     * @param sentences The expected number of sentences
     * @return true if the test case passed.  False otherwise.
     */
    public static boolean testCase(Document doc, int syllables, int words, int sentences) {
        System.out.println("Testing text: ");
        System.out.print(doc.getText() + "\n....");
        boolean passed = true;
        int syllFound = doc.getNumSyllables();
        int wordsFound = doc.getNumWords();
        int sentFound = doc.getNumSentences();
        if (syllFound != syllables) {
            System.out.println("\nIncorrect number of syllables.  Found " + syllFound
                    + ", expected " + syllables);
            passed = false;
        }
        if (wordsFound != words) {
            System.out.println("\nIncorrect number of words.  Found " + wordsFound
                    + ", expected " + words);
            passed = false;
        }
        if (sentFound != sentences) {
            System.out.println("\nIncorrect number of sentences.  Found " + sentFound
                    + ", expected " + sentences);
            passed = false;
        }
        
        if (passed) {
            System.out.println("passed.\n");
        } else {
            System.out.println("FAILED.\n");
        }
        return passed;
    }
    
    
    /**
     * Return the number of words in this document
     */
    public abstract int getNumWords();
    
    /**
     * Return the number of sentences in this document
     */
    public abstract int getNumSentences();
    
    /**
     * Return the number of syllables in this document
     */
    public abstract int getNumSyllables();
    
    /**
     * Return the entire text of this document
     */
    public String getText() {
        return this.text;
    }
    
    /**
     * return the Flesch readability score of this document
     */
    public double getFleschScore() {
        //System.out.println("here");
        // TODO: You will play with this method in week 1, and
        double flesch = 206.835 - 1.015 * ((double) getNumWords() / getNumSentences()) - 84.6 * ((double) getNumSyllables() / getNumWords());
        return flesch;
    }
    
    
}
