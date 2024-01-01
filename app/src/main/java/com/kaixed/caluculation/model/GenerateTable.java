package com.kaixed.caluculation.model;

public class GenerateTable {
    public static String[][] createAddition() {
        String[][] addition = new String[101][101];
        for (int i = 0; i <= 100; i++) {
            for (int j = 0; j <= 100; j++) {
                int result = i + j;
                addition[i][j] = i + "+" + j ;
            }
        }
        return addition;
    }

    public static String[][] createSubtraction() {
        String[][] subtraction = new String[101][101];
        for (int i = 0; i <= 100; i++) {
            for (int j = 0; j <= 100; j++) {
                int result = i - j;
                subtraction[i][j] = i + "-" + j ;
            }
        }
        return subtraction;
    }

    public static String[][] createMutiply(){
        String [][] mutiply = new String[10][10];
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                int result = i*j;
                mutiply[i][j] = i + "*" + j ;
            }
        }
        return mutiply;
    }
}
