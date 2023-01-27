package org.example;

import java.util.Random;

public class StringGenerator {

    public static String randomStringCreator(){

        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        int length = 7;

        for(int i = 0; i < length; i++) {


            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            sb.append(randomChar);
        }

        String randomString = sb.toString();
        return randomString;

    }
}
