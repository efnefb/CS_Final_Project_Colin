public class Colin {

    //Encyrption
    public static String Encrypt_Colin(String ptxt){
        //An array of plaintext chars
        String[] plainLetters = ptxt.split("");

        //An array of corresponding encrypted chars
        char[] procLetters = new char[plainLetters.length];

        //Converting
        for (int i=0; i<plainLetters.length; i++){
            int beforeAscii = (int) plainLetters[i].charAt(0);
            int afterAscii = f(beforeAscii);
            procLetters[i] = (char) afterAscii;
        }

        String procText = "";
        for (char c: procLetters) procText += c;
        return procText;
    }

    //Decryption
    public static String Decrypt_Colin(String proctxt){
        //An array of encyrpted chars
        String[] procLetters = proctxt.split("");

        //An array of corresponding decrypted chars
        char[] plainLetters = new char[procLetters.length];

        for (int i=0; i<procLetters.length; i++){
            int afterAscii = (int) procLetters[i].charAt(0);
            int beforeAscii = f_inverse(afterAscii);
            plainLetters[i] = (char) beforeAscii;
        }

        String plainText = "";
        for (char c: plainLetters) plainText += c;
        return plainText;
    }

    //Function to map plain Ascii value to encrypted Ascii value
    public static int f(int x){
        return ((5 * (x-31)) % 96) + 31;
    }

    //Function to map encrypted Ascii value back to plain Ascii value
    public static int f_inverse(int y){
        return ((77*(y-31)) % 96) + 31;
    }

}
