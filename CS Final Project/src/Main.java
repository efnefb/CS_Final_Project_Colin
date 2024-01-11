import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;
import java.io.PrintStream;

public class Main {

    public static void main(String[] args) {
        int continue_or_end = 1;
        String plaintext = get_plaintext();
        System.out.println("This is the plaintext: " + plaintext);

        String processed_text = "";

        while (continue_or_end == 1) {
            processed_text = execute_conversions(plaintext);
            continue_or_end = continueOrEnd();
            plaintext = processed_text;
        }

        //Store finished text in a file?
        boolean isInt = false;
        int option = 1;
        while (!isInt){
            try {
                System.out.println("Do you want to store this text in a file? 1) Yes, 2)No");
                Scanner scan = new Scanner(System.in);
                option = scan.nextInt();
                if (option==1 || option==2) isInt = true;
                else System.out.println("Invalid. You must enter either 1 or 2.");
            } catch (InputMismatchException e){
                System.out.println("Invalid. You must enter a number.");
            }
        }

        if (option==1){
            Scanner scan1 = new Scanner(System.in);
            try {
                PrintStream storeText = new PrintStream("storedText.txt");
                storeText.println(processed_text);
            } catch (FileNotFoundException e) {
                System.out.println("File not found. Please enter a valid filename.");
            }
        }


        //Start over or end
        boolean isInt1 = false;
        int option1 = 1;
        while (!isInt1){
            try {
                System.out.println("Do you want to start again with a new piece of text? 1) Yes, 2)No");
                Scanner scan2 = new Scanner(System.in);
                option1 = scan2.nextInt();
                if (option1==1 || option1==2) isInt1 = true;
                else System.out.println("Invalid. You must enter either 1 or 2.");
            } catch (InputMismatchException e){
                System.out.println("Invalid. You must enter a number.");
            }
        }

        if (option1 == 1) main(args);
        else System.out.println("Thank you. Have a very nice day :)");

    }

    public static String get_plaintext(){
        //Ask user – Read plaintext in from a file or type it in yourself?
        int optionA = 1;

        boolean isInt = false;
        while(!isInt) {
            try {
                System.out.println("This is a program to encrypt and decrypt text, using either Vignere's algorithm or Colin's algorithm.\n" +
                        "To start, you should have some plaintext to work with. If you want to read the plaintext in from a file, press 1,\n" +
                        "and if you want to type the plaintext in manually, press 2.\n" +
                        "Enter here: ");
                Scanner scan = new Scanner(System.in);
                optionA = scan.nextInt();
                if (optionA == 1 || optionA == 2) isInt = true;
                else System.out.println("Invalid. You must enter only either 1 or 2.");
            } catch (InputMismatchException e){
                System.out.println("Invalid. You must enter a number.");
            }

        }

        //Getting the plaintext
        String plaintext = "";

        boolean plaintext_is_valid = false;
        while (!plaintext_is_valid) {
            if (optionA == 1) { //plaintext from a file
                boolean valid = false;
                while (!valid) {
                    System.out.println("Enter the name of the file: ");
                    Scanner scan1 = new Scanner(System.in);
                    String filename = scan1.nextLine();
                    try {
                        File ptxt_file = new File(filename);
                        Scanner scanFile = new Scanner(ptxt_file);
                        while (scanFile.hasNextLine()) {
                            String line = scanFile.nextLine();
                            plaintext += line;
                        }
                        valid = true;
                    } catch (FileNotFoundException e) {
                        System.out.println("File not found. Please enter a valid filename.");
                    }
                }
            } else { //plaintext manually entered
                System.out.println("Now you will enter the plaintext. " +
                        "\nPlease note that only alphanumeric characters are allowed,\n" +
                        "as well as any of the following characters: " +
                        "' ', '.', ',', '!', ';', '-', '(', ')', '?', '–'\n" +
                        "Any other characters are invalid. Enter the plaintext: ");
                Scanner scan = new Scanner(System.in);
                plaintext = scan.nextLine();
            }
            if (checkValid_ptxt(plaintext)) plaintext_is_valid = true;
            else System.out.println("Invalid. The plaintext can only consist of alphanumeric characters, as well as punctuation from the approved list.");
        }

        return plaintext;
    }

    public static String execute_conversions(String plaintext){
        //Ask user – Which operation do they want to do?
        int optionB = 1;

        boolean isInt1 = false;
        while (!isInt1){
            System.out.println("What would you like to do with the text? " +
                    "\n1) Encrypt using Vignere's Cipher, \n2) Encrypt using Colin's Cipher, "
                    + "\n3) Decrypt using Vignere's Cipher, \n4) Decrypt using Colin's Cipher. Enter your choice: ");
            Scanner scan3 = new Scanner(System.in);
            try{
                optionB = scan3.nextInt();
                if (optionB == 1 || optionB == 2 || optionB == 3 || optionB == 4) isInt1 = true;
                else System.out.println("Invalid. You must enter either 1, 2, 3, or 4.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid. You must enter an integer.");
            }
        }

        //This is where the processed text will be stored
        String processed_text = "";


        //If user chose Vigneres, ask user for a key
        String key = "";

        if (optionB == 1 || optionB == 3){
            boolean keyValid = false;
            while (!keyValid){
                System.out.println("Enter a key (only include either letters or digits. Do not include the number 0 or any spaces): ");
                Scanner scan4 = new Scanner(System.in);
                key = scan4.nextLine().toLowerCase();
                if (checkValid_key(key)) keyValid = true;
                else System.out.println("Invalid. Enter a valid key.");
            }
        }

        //Process the text, store the processed text inside "processed_text" variable
        if (optionB == 1) processed_text = Vigneres.Encrypt_Vigneres(key,plaintext);
        if (optionB == 2) processed_text = Colin.Encrypt_Colin(plaintext);
        if (optionB == 3) processed_text = Vigneres.Decrypt_Vigneres(key,plaintext);
        if (optionB == 4) processed_text = Colin.Decrypt_Colin(plaintext);

        System.out.println("This is the processed text:\n" + processed_text);

        return processed_text;
    }



    public static boolean checkValid_ptxt(String ptxt){
        char[] validPunct = {' ', '.', ',', '!', ';', '-', '(', ')', '?', '–'};

        boolean validSoFar = false;

        int i = 0;
        while (i<ptxt.length()){
            for (int p=0; p<validPunct.length; p++){
                if (ptxt.charAt(i) == validPunct[p]) {
                    validSoFar = true;
                    break;
                }
            }
            if (Character.isLetterOrDigit(ptxt.charAt(i))) validSoFar = true;

            if (validSoFar) i++;
            else return false;
        }

        return true;
    }

    public static boolean checkValid_key(String key){
        for (char k: key.toCharArray()) {
            if (!Character.isLetterOrDigit(k)) return false;
        }

        for (int i=0; i<key.length(); i++){
            if (key.charAt(i) == ' ' || key.charAt(i) == '0') return false;
        }
        return true;
    }

    public static int continueOrEnd(){
        int option = 1;
        boolean isInt = false;
        while (!isInt){
            try{
                System.out.println("Would you like to keep working with this text? 1) Yes, 2)No");
                Scanner scan = new Scanner(System.in);
                option = scan.nextInt();
                if (option==1 || option==2) isInt=true;
                else System.out.println("Invalid. You must enter only either 1 or 2");
            } catch (InputMismatchException e){
                System.out.println("Invalid. You must enter a number.");
            }
        }
        return option;
    }


}

