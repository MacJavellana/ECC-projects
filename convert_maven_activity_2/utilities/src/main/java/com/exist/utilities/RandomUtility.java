package com.exist.utilities;

public class RandomUtility {
    public static String randChars() {
        StringBuilder aschii = new StringBuilder();
        int min = 33, max = 126;
        for (int i = 0; i < 3; i++) {
            int randomNum = (int)(Math.random() * (max - min + 1)) + min;
            aschii.append((char)randomNum);
        }
        return aschii.toString();
    }
}
