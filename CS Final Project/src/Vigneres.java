public class Vigneres{
    //List of valid punctuation marks
    static char[] punctuations = {' ', '.', ',', '!', ';', '-', '(', ')', '?', '–'};

    //Assumes all text inputted is alphanumeric with accepted punctuation. Assumes key has no spaces and has no 0.

    //Encryption
    public static String Encrypt_Vigneres(String key, String ptxt) {
        //Making array of plain letters
        String[] plainLetters = ptxt.split(""); //(Assumes plaintext already in lower case)

        //Determining shift amounts
        int[] shiftAmounts = keyPositions(plainLetters, key);

        //Converting plaintext into processed text
        char[] procLetters = new char[plainLetters.length];

        for (int i = 0; i < plainLetters.length; i++) {
            char plainC = plainLetters[i].charAt(0);
            //if not punctuation
            if (shiftAmounts[i] > 0) { //punctuation check is here (>0)
                int beforeAscii = (int) plainC;
                int afterAscii = 0;
                int shift = shiftAmounts[i];
                if (beforeAscii > 96) { //if the plaintext char is a Letter
                    beforeAscii -= 96;
                    afterAscii = (beforeAscii + shift);
                    if (afterAscii > 26) afterAscii -= 26;
                    afterAscii += 96;
                } else { //if the plaintext char is a number
                    beforeAscii -= 48;
                    afterAscii = (beforeAscii + shift) % 10;
                    afterAscii += 48;
                }
                char newChar = (char) afterAscii;
                procLetters[i] = newChar;
            } else {
                for (int j = 0; j < punctuations.length; j++) {
                    if (punctuations[j] == plainC) procLetters[i] = plainC;
                }
            }
        }
        String procText = "";
        for (char fin_char : procLetters) procText += fin_char;
        return procText;
    }


    //Decryption
    public static String Decrypt_Vigneres(String key, String proctxt) {
        //Making an array of processed numbers
        String[] procLetters = proctxt.split("");

        //Determining shift amounts
        int[] shiftAmounts = keyPositions(procLetters, key);

        //Converting processed text into plain text
        char[] plainLetters = new char[procLetters.length];
        for (int i = 0; i < procLetters.length; i++) {
            char procC = procLetters[i].charAt(0);
            //If not punctuation
            if (shiftAmounts[i] > 0) { //punctuation check is here (>0)
                int afterAscii = (int) procC;
                int beforeAscii = 0;
                int backShift = shiftAmounts[i];
                if (afterAscii > 96) { //If the processed text character is a letter
                    beforeAscii = afterAscii - 96;
                    beforeAscii -= backShift;
                    if (beforeAscii <= 0) beforeAscii += 26;
                    beforeAscii += 96;
                } else { //If the processed text character is a number
                    beforeAscii = afterAscii - 48;
                    beforeAscii -= backShift;
                    while (beforeAscii < 0) beforeAscii += 10;
                    beforeAscii += 48;
                }
                char oldChar = (char) beforeAscii;
                plainLetters[i] = oldChar;
            } else {
                for (int j = 0; j < punctuations.length; j++) {
                    if (punctuations[j] == procC) plainLetters[i] = procC;
                }
            }
        }
        String plainText = "";
        for (char fin_char : plainLetters) plainText += fin_char;
        return plainText;
    }

    //Determining shift amounts
    public static int[] keyPositions(String[] rawLetters, String ref) {
        //Making the key cover
        String refStr = "";
        int x = 0;
        for (int i = 0; i < rawLetters.length; i++) {
            //If a character is punctuation
            boolean isPunct = false;
            for (char punct : punctuations) {
                if (rawLetters[i].equals(Character.toString(punct))) {
                    refStr += punct;
                    isPunct = true;
                }
            }
            //If a character is not punctuation
            if (!isPunct) {
                refStr += ref.charAt(x);
                if (x <= ref.length() - 2) x++;
                else x = 0;
            }
        }

        //Making array of key letter positions
        String[] refLetters = refStr.split("");
        int[] refPositions = new int[refLetters.length];

        for (int i = 0; i < refLetters.length; i++) {
            char c = refLetters[i].charAt(0);
            //Is punctuation?
            boolean isPunct = false;
            for (char y : punctuations) {
                if (c == y) isPunct = true;
            }
            //if not punctuation
            if (!isPunct) {
                int ascii = (int) c;
                if (ascii - 96 > 0) refPositions[i] = ascii - 96; //if letter, get position
                else refPositions[i] = ascii - 48; //if number, use itself
            }
            //if it is punctuation
            else {
                for (int j = 0; j < punctuations.length; j++) {
                    if (punctuations[j] == c) refPositions[i] = -1 * (j + 1);
                }
            }
        }
        return refPositions;
    }

}