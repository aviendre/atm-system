package main;

/**
 * Java file to encrypt and decrypt string for security purposes
 * This was made with Caeser encryption as the method with a pre-set
 * key in the program file that will be hidden to the user.
 */
public class CaeserEncryption {
    private static final int CIPHERKEY = -4;

    /**
     * Function to check if a character is in the a set given of string
     * @param str (String) Set of string to check against
     * @param ch (Char) Char to check if it's in the string
     * @return (boolean) true if the char is in the string
     */
    private static boolean contains(String str,char ch){
        return str.indexOf(ch) != -1;
    }

    /**
     * Encrypt a given string based on the preset key in the top
     * @param message (String) String to encrypt
     * @return encrypted string
     */
    public static String encrypt(String message){
        String list = "abcdefghijklmnopqrstuvwxyz0123456789";
        String encMessage = "";
        int charPos, keyVal;
        message = message.toLowerCase();

        for (int i = 0; i < message.length();i++) {
            if(contains(list,message.charAt(i))){
                charPos = list.indexOf(message.charAt(i));
                keyVal = (CIPHERKEY + charPos) % 36;
                if(keyVal < 0 ){ keyVal = list.length() + keyVal; }
                encMessage += list.charAt(keyVal);
            } else { 
                encMessage += message.charAt(i);
                continue;
            }
        }
        return encMessage;
    }

    /**
     * Decrypt a given string based on the preset key in the top
     * @param message (String) String to decrypt
     * @return decrypted string
     */
    public static String decrypt(String message){
        String list = "abcdefghijklmnopqrstuvwxyz0123456789";
        String decMessage = "";
        int charPos, keyVal;
        message = message.toLowerCase();

        for (int i = 0; i < message.length();i++) {
            if(contains(list,message.charAt(i))){
                charPos = list.indexOf(message.charAt(i));
                keyVal = (charPos - CIPHERKEY) % 36;
                if(keyVal < 0 ){ keyVal = list.length() + keyVal; }
                decMessage += list.charAt(keyVal);
            } else { 
                decMessage += message.charAt(i);
                continue; 
            }
        }      
        return decMessage;
    }
}
