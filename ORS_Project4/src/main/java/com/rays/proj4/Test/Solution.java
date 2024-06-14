package com.rays.proj4.Test;

import java.util.Scanner;

public class Solution {
    static int sumOfArray(int[] arr) {
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        return sum;
    }
    
    static String sumOfString(String[] arr){
        StringBuilder str = new StringBuilder();
        for (String string : arr) {
            str.append(string);
        }
        return str.toString();
    }

    public static void main(String args[]) throws Exception {
        
        Scanner scanner = new Scanner(System.in);

        String[] integers = scanner.nextLine().split(" ");

        int[] array = new int[integers.length];

        for (int i = 0; i < integers.length; i++) {
            array[i] = Integer.parseInt(integers[i]);
        }

        int sum = sumOfArray(array);
        System.out.println(sum);
        
        scanner.close();
    }
}