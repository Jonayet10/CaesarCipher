package edu.caltech.cs2.project01;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.ArrayList;

public class SubstitutionCipher {
    private String cipherText;
    private Map<Character, Character> key;

    // Use this Random object to generate random numbers in your code,
    // but do not modify this line.
    private static final Random RANDOM = new Random();

    /**
     * Construct a SubstitutionCipher with the given cipher text and key
     * @param cipherText the cipher text for this substitution cipher
     * @param key the map from cipher text characters to plaintext characters
     */

    public SubstitutionCipher(String cipherText, Map<Character, Character> key) {
        this.key = key;
        this.cipherText = cipherText;
    }

    /**
     * Construct a SubstitutionCipher with the given cipher text and a randomly
     * initialized key.
     * @param ciphertext the cipher text for this substitution cipher
     */
    public SubstitutionCipher(String ciphertext) {
        this.cipherText = ciphertext;
        this.key = getIdentity();
        SubstitutionCipher randomCipher = new SubstitutionCipher(cipherText,this.key);
        for (int i = 0; i < 10000; i++) {
            randomCipher = randomCipher.randomSwap();
        }
        this.key = randomCipher.key;
    }

    public Map<Character,Character> getIdentity() {
        Map<Character,Character> output = new HashMap<>();
        for (char i = 'A'; i <= 'Z'; i++) {
            output.put(i,i);
        }
        return output;
    }

    /**
     * Returns the unedited cipher text that was provided by the user.
     * @return the cipher text for this substitution cipher
     */
    public String getCipherText() {
        return this.cipherText;
    }

    /**
     * Applies this cipher's key onto this cipher's text.
     * That is, each letter should be replaced with whichever
     * letter it maps to in this cipher's key.
     * @return the resulting plain text after the transformation using the key
     */
    public String getPlainText() {
        String output = "";
        for (int i = 0; i < cipherText.length(); i++) {
            char replace = key.get(cipherText.charAt(i));
            output += replace;
        }
        return output;
    }

    /**
     * Returns a new SubstitutionCipher with the same cipher text as this one
     * and a modified key with exactly one random pair of characters exchanged.
     *
     * @return the new SubstitutionCipher
     */
    public SubstitutionCipher randomSwap() {
        Map<Character,Character> newKey = new HashMap<>(this.key);

        char[] ALPHABET = {
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
                'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
                'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        int alphaIdx1 = RANDOM.nextInt(26);
        int alphaIdx2 = RANDOM.nextInt(26);
        char mapIdx1 = ALPHABET[alphaIdx1];
        char mapIdx2 = ALPHABET[alphaIdx2];
        while (mapIdx1 == mapIdx2) {
            alphaIdx2 = RANDOM.nextInt(26);
            mapIdx2 = ALPHABET[alphaIdx2];
        }
        char idx1Val = this.key.get(mapIdx1);
        newKey.put(mapIdx1, newKey.get(mapIdx2));
        newKey.put(mapIdx2, idx1Val);

        return new SubstitutionCipher(cipherText, newKey);
    }

    /**
     * Returns the "score" for the "plain text" for this cipher.
     * The score for each individual quadgram is calculated by
     * the provided likelihoods object. The total score for the text is just
     * the sum of these scores.
     * @param likelihoods the object used to find a score for a quadgram
     * @return the score of the plain text as calculated by likelihoods
     */
    public double getScore(QuadGramLikelihoods likelihoods) {
        String plainText = this.getPlainText();
        String quadGram = "";
        double sum = 0;
        ArrayList<Double> logLikelihoods = new ArrayList<>();
        for (int i = 0; i < plainText.length() - 3; i++) {
            quadGram = plainText.substring(i, i + 4);
            logLikelihoods.add(likelihoods.get(quadGram));
            sum += likelihoods.get(quadGram);
        }
        return sum;
    }

    /**
     * Attempt to solve this substitution cipher through the hill
     * climbing algorithm. The SubstitutionCipher this is called from
     * should not be modified.
     * @param likelihoods the object used to find a score for a quadgram
     * @return a SubstitutionCipher with the same ciphertext and the optimal
     *  found through hill climbing
     */
    public SubstitutionCipher getSolution(QuadGramLikelihoods likelihoods) {
        SubstitutionCipher cipher = new SubstitutionCipher(this.cipherText);
        int i = 0;
        while (i != 1000) {
             SubstitutionCipher cipher2 = cipher.randomSwap();
             if (cipher2.getScore(likelihoods) > cipher.getScore(likelihoods)) {
                 cipher = cipher2;
                 i = 0;
            } else {
                 i ++;
             }
        }
        return cipher;
    }
}
