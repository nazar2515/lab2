package com.company;

public class Main {
    public static void main(String[] args) {
        int dim = 10000000;
        int threadNum = 4;

        ArrClass arrClass = new ArrClass(dim, threadNum);
        arrClass.findMin();

        System.out.println("Min value: " + arrClass.getMin());
        System.out.println("Min index: " + arrClass.getMinIndex());
    }
}