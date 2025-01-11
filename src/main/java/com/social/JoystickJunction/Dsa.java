package com.social.JoystickJunction;

public class Dsa {
    public static void main(String[] args) {

        int[][] shifts = new int[3][3];
        int[] shift1 = new int[]{0,1,0};
        shifts[0] = shift1;
        int[] shift2 = new int[]{1,2,1};
        shifts[1] = shift2;
        int[] shift3 = new int[]{0,2,1};
        shifts[2] = shift2;
        System.out.println(shiftingLetters( "abc", shifts));
    }

    public static String shiftingLetters(String s, int[][] shifts) {

        for(int i = 0; i < shifts.length; i++) {
            String temp = "";
            for (int j = 0; j < s.length(); j++) {
                if(j >= shifts[i][0] && j <= shifts[i][1]) {
                    if (shifts[i][2] == 1) {
                        char inc = (char)(((int)s.charAt(j) + 1) % 26);
                        temp += inc;
                    } else {
                        int value = (int)s.charAt(j);
                        int round = value % 122;
                        int decreased = round - 1;
                        char finalVal = (char)decreased;

                        temp += finalVal;
                    }
                }
                temp = temp + s.charAt(j);
            }
            s = temp;
        }
        return s;
    }
}
