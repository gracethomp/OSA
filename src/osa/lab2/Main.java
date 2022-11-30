package osa.lab2;

import java.text.DecimalFormat;
import java.util.*;

public class Main {
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_CYAN = "\u001B[36m";

    public static void doPairedComparisons(HashMap<Integer, Integer> hashMap, List<Integer> integers) {
        System.out.println('\n' + ANSI_CYAN + "Method of paired comparisons".toUpperCase(Locale.ROOT));
        outputData(hashMap, integers, "Initial estimates:");
        HashMap<Integer, Integer> hashMap1 = new HashMap<>();
        System.out.print('\n' + ANSI_BLUE + "Binary matrix:" + ANSI_RESET + "\n\t");
        for (Integer integer : integers)
            System.out.print("Z" + hashMap.get(integer) + "\t");
        System.out.print("\n");
        for (int i = 0; i < integers.size(); i++) {
            int result = 0;
            for(int j = 0; j < integers.size(); j++) {
                if (j == 0)
                    System.out.print("Z" + hashMap.get(integers.get(i)) + " ");
                if(i != j) {
                    int a = integers.get(i) > integers.get(j) ? 1:0;
                    result += a;
                    hashMap1.put(hashMap.get(integers.get(i)), result);
                    System.out.print("\t" + a + " ");
                }
                else
                    System.out.print("\t-  ");
            }
            System.out.println("\n");
        }
        outputResultPairedMethod(hashMap, integers, hashMap1);
    }

    public static void doSuccessiveComparisons(HashMap<Integer, Integer> hashMap, List<Integer> integers) {
        System.out.println('\n' + ANSI_CYAN + "Method of successive comparisons".toUpperCase(Locale.ROOT));
        outputData(hashMap, integers, "Initial estimates:");
        integers.sort(Comparator.reverseOrder());
        for(int i = 0; i < integers.size(); i++) {
            for (int j = 1; j < integers.size(); j++) {
                for (int c = 2; c < integers.size(); c++) {
                    if(integers.get(i) <= (integers.get(j) + integers.get(c)) && (i<j)) {
                        int temp = hashMap.remove(integers.get(i));
                        integers.set(i, integers.get(i) + 10);
                        hashMap.put(integers.get(i), temp);
                    }
                }
            }
        }
        outputData(hashMap, integers, "Estimates after revaluation: ");
        System.out.println(ANSI_BLUE + '\n' + "Target weights");
        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        for(Integer a : integers)
            System.out.println(ANSI_RESET + "V" + hashMap.get(a) + ": " + decimalFormat.format(a/integers.
                    stream().mapToDouble(a1 -> a1).sum()) + ';');
    }

    public static void outputData(HashMap<Integer, Integer> hashMap, List<Integer> integers, String title) {
        System.out.println('\n' + ANSI_BLUE + title);
        for(Integer a : integers)
            System.out.println(ANSI_RESET + "p" + hashMap.get(a) + ": " + a);
    }

    public static void outputResultPairedMethod(HashMap<Integer, Integer> hashMap,
                                                List<Integer> integers, HashMap<Integer, Integer> hashMap1) {
        System.out.println(ANSI_BLUE + "The price of each target:" + ANSI_RESET);
        for(Integer a : hashMap.keySet()) {
            System.out.println("C" + hashMap.get(a) + ": " + hashMap1.get(hashMap.get(a)));
        }
        System.out.println(ANSI_BLUE + '\n' + "Target weights");
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        for(Integer a : hashMap1.keySet())
            System.out.println(ANSI_RESET + "V" + a + ": " +
                    decimalFormat.format((double) hashMap1.get(a)/integers.size()) + ';');

    }

    public static void main(String[] args) {
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        hashMap.put(30, 1);
        hashMap.put(100, 2);
        hashMap.put(75, 3);
        List<Integer> integers = new ArrayList<>(hashMap.keySet());
        doPairedComparisons(hashMap, integers);
        doSuccessiveComparisons(hashMap, integers);
    }
}
