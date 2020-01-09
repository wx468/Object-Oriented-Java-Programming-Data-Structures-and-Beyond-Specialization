package document;

import java.util.List;

/***************************************************************
 *  Name:    Wei Xu
 *
 *  Date: Jan 1st, 2020
 *
 *  Description:  ---------------------------
 *                Get number of words, sentences, syllables using
 *  *             regular expressions.
 ****************************************************************/

public class BasicDocument extends Document {
    /**
     * Create a new BasicDocument object
     *
     * @param text The full text of the Document.
     */
    public BasicDocument(String text) {
        super(text);
    }
    
    
    /**
     * Get the number of words in the document.
     * A "word" is defined as a contiguous string of alphabetic characters
     * i.e. any upper or lower case characters a-z or A-Z.  This method completely
     * ignores numbers when you count words, and assumes that the document does not have
     * any strings that combine numbers and letters.
     * <p>
     * Check the examples in the main method below for more information.
     * <p>
     * This method should process the entire text string each time it is called.
     *
     * @return The number of words in the document.
     */
    @Override
    public int getNumWords() {
        List<String> words = this.getTokens("[a-zA-Z]+");
        return words.size();
    }
    
    /**
     * Get the number of sentences in the document.
     * Sentences are defined as contiguous strings of characters ending in an
     * end of sentence punctuation (. ! or ?) or the last contiguous set of
     * characters in the document, even if they don't end with a punctuation mark.
     * <p>
     * Check the examples in the main method below for more information.
     * <p>
     * This method should process the entire text string each time it is called.
     *
     * @return The number of sentences in the document.
     */
    @Override
    public int getNumSentences() {
        List<String> sentences = this.getTokens("[^.!?]+");
        return sentences.size();
    }
    
    /**
     * Get the total number of syllables in the document (the stored text).
     * To count the number of syllables in a word, it uses the following rules:
     * Each contiguous sequence of one or more vowels is a syllable,
     * with the following exception: a lone "e" at the end of a word
     * is not considered a syllable unless the word has no other syllables.
     * You should consider y a vowel.
     * <p>
     * Check the examples in the main method below for more information.
     * <p>
     * This method should process the entire text string each time it is called.
     *
     * @return The number of syllables in the document.
     */
    @Override
    public int getNumSyllables() {
        int totalCount = 0;
        List<String> words = getTokens("[a-zA-Z]+");
        for (String w : words) {
            //            System.out.println("\n-------------test countSyllables--------------");
            //
            //            System.out.println(w);
            totalCount += countSyllables(w);
        }
        return totalCount;
    }
    
    
    /* The main method for testing this class.
     * You are encouraged to add your own tests.  */
    public static void main(String[] args) {
        /* Each of the test cases below uses the method testCase.  The first
         * argument to testCase is a Document object, created with the string shown.
         * The next three arguments ar be the number of syllables, words and sentences
         * in the string, respectively.  You can use these examples to help clarify
         * your understanding of how to count syllables, words, and sentences.
         */
        testCase(new BasicDocument("This is a test.  How many???  "
                        + "Senteeeeeeeeeences are here... there should be 5!  Right?"),
                16, 13, 5);
        testCase(new BasicDocument(""), 0, 0, 0);
        testCase(new BasicDocument("sentence, with, lots, of, commas.!  "
                + "(And some poaren)).  The output is: 7.5."), 15, 11, 4);
        testCase(new BasicDocument("many???  Senteeeeeeeeeences are"), 6, 3, 2);
        testCase(new BasicDocument("Here is a series of test sentences. Your program should "
                + "find 3 sentences, 33 words, and 49 syllables. Not every word will have "
                + "the correct amount of syllables (example, for example), "
                + "but most of them will."), 49, 33, 3);
        testCase(new BasicDocument("Segue"), 2, 1, 1);
        testCase(new BasicDocument("Sentence"), 2, 1, 1);
        testCase(new BasicDocument("Sentences?!"), 3, 1, 1);
        testCase(new BasicDocument("Lorem ipsum dolor sit amet, qui ex choro quodsi moderatius, nam dolores explicari forensibus ad."),
                32, 15, 1);
        testCase(new BasicDocument("eeeeeeeee."),
                1, 1, 1);
        String word = "this";
        for (int i = 0; i < word.length() - 1; i++) {//no need search the last index here;
            if (word.charAt(i) == 'i' || word.charAt(i) == 'a') {
                System.out.println(i + "\t" + word.charAt(i));
            }
            
        }
        
        
    }
    
}
