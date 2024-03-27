package Phasing;

import java.io.*;
import java.util.*;
import Phasing.KernelDensityEstimation;
import com.sun.javafx.image.IntPixelGetter;
import scala.Int;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.List;
import java.util.stream.*;
import java.util.zip.GZIPInputStream;

import static Phasing.KernelDensityEstimation.*;


public class PhaseMpileUp {
    public static List<String> sample = new ArrayList<>();
    public static int length = 0;
    public static String name = "";
    public static List<String> name_nlr = new ArrayList<>();

    
    
    static class Pair {
        private int depth;
        private String align;
        public Pair(int depth, String align){
            this.depth = depth;
            this.align = align;
        }

        public int getDepth() {
            return depth;
        }

        public String getAlign() {
            return align;
        }

        public void setDepth(int depth) {
            this.depth = depth;
        }

        public void setAlign(String align) {
            this.align = align;
        }
    }

    public static List<List<Pair>> getSum(String file) throws IOException{

        GZIPInputStream gzip = new GZIPInputStream(new FileInputStream(file));
        BufferedReader br = new BufferedReader(new InputStreamReader(gzip));

//        BufferedReader br = new BufferedReader(new FileReader(file));
        int num = 0;
        String str;
        List<List<Pair>> sum = new ArrayList<>();

        while ( (str = br.readLine()) != null){
            String[] tmp =  str.split("\t");

            for (int i = 0; i < sum.size(); i++) {
                sum.get(i).add(new Pair(Integer.parseInt(tmp[3 + i*3]), tmp[4 + i*3]));
            }

            if (num == 0){
                name = tmp[0];
                int count = (tmp.length-3)/3;
                for (int i = 0; i <count; i++) {
                    List<Pair> res = new ArrayList<Pair>();
                    res.add(new Pair(Integer.parseInt(tmp[3]), tmp[4]));
                    sum.add(res);
                }
            }
            num ++;
        }
        br.close();
        length=num +1;

        return sum;
    }

    public static int sum (List<Integer> a){
        if (a.size() > 0) {
            int sum = 0;

            for (Integer i : a) {
                sum += i;
            }
            return sum;
        }
        return 0;
    }

    public static double mean (List<Integer> a){
        int sum = sum(a);
        double mean = 0;
        mean = sum / (a.size() * 1.0);
        return mean;
    }
    public static double median (List<Integer> a){
        int middle = a.size()/2;

        if (a.size() % 2 == 1) {
            return a.get(middle);
        } else {
            return (a.get(middle-1) + a.get(middle)) / 2.0;
        }
    }
    public static double var (List<Integer> a){
        double sum = 0;
        double mean = mean(a);

        for (Integer i : a)
            sum += Math.pow((i - mean), 2);

        int df = a.size() -1;

        return sum/df; // sample
    }




    public static int getStat(String str){
        String res = str.replace(".","").replace(",","").replaceAll("\\^.", "").replace("$","");
        return res.length();
    }

    private static Map.Entry<Character, Integer> findMostRepeatedCharacter(String input) {
        Map<Character, Integer> charCountMap = new HashMap<>();

        for (char c : input.toCharArray()) {
            charCountMap.put(c, charCountMap.getOrDefault(c, 0) + 1);
        }

        Map.Entry<Character, Integer> maxEntry = null;

        for (Map.Entry<Character, Integer> entry : charCountMap.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }

        return maxEntry;
    }

    public static int getStat(List<Pair> arr){
        int res = 0;


        for (int i = 150; i < arr.size()-150; i++) {
            Map.Entry<Character, Integer> result = findMostRepeatedCharacter(arr.get(i).getAlign().toUpperCase().replace(".","").replace(",","").replaceAll("\\^.", "").replace("$",""));

            if (result!=null){
                if(result.getValue() > 3 ||  arr.get(i).getDepth() < 2){
//                    System.out.println(i+"\t"+arr.get(i).getAlign());
                    res+=1;
                }
            }
//            res += getStat(arr.get(i).getAlign());
        }
        return res;
    }

    public static int getEmptyAlign(List<Pair> arr){
        int res = 0;


        for (int i = 150; i < arr.size()-150; i++) {
//            Map.Entry<Character, Integer> result = findMostRepeatedCharacter(arr.get(i).getAlign().toUpperCase().replace(".","").replace(",","").replaceAll("\\^.", "").replace("$",""));


                if(arr.get(i).getDepth() < 2 && arr.get(i).getAlign().contains("*")){
//                    System.out.println(i+"\t"+arr.get(i).getAlign());
                    res+=1;
                }

//            res += getStat(arr.get(i).getAlign());
        }
        return res;
    }

    public static void getSample(String file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String str;
        while ( (str = br.readLine()) != null){
            sample.add(str);
        }
        br.close();
    }

    public static int findMinIndex(List<Integer> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("List can not be empty");
        }
        int minIndex = 0;
        int minValue = list.get(0);

        for (int i = 1; i < list.size(); i++) {
            if (list.get(i) < minValue) {
                minValue = list.get(i);
                minIndex = i;
            }
        }
        return minIndex;
    }

    public static <T> double[] transferArray(List<T> res){
        double[] doubleArray = new double[res.size()];
        for (int i = 0; i < res.size(); i++) {
//            doubleArray[i] = (int) res.get(i);
            doubleArray[i] = (double) (Integer) res.get(i);
        }
        return doubleArray;
    }

//    public static double[] transferArray(List<Double> res){
//        double[] doubleArray = new double[res.size()];
//        for (int i = 0; i < res.size(); i++) {
//            doubleArray[i] = res.get(i);
//        }
//        return doubleArray;
//    }

    public static List<List<Integer>> getFile(String file) throws IOException {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> tmp = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String str;
        int count = 0;
        while ( (str = br.readLine()) != null){
            String[] strs = str.split("\t");


            if(count == 0){
                sample.addAll(Arrays.asList(strs).subList(1, strs.length));
            }else {
                for (int i = 1; i < strs.length; i++) {
                    tmp.add(Integer.parseInt(strs[i]) );
                }
                name_nlr.add(strs[0]);
                res.add(tmp);
                tmp = new ArrayList<>();
            }
            count += 1;

        }
        br.close();
        return res;
    }


//    public static void main(String[] args) throws IOException {
//
////        BufferedWriter bw = new BufferedWriter(new FileWriter("./lib/round6/result_round6.txt"));
////
////        List<List<Double>> res = getFile("./lib/round6/result.txt");
////
////        for (int i = 0; i < res.size(); i++) {
////            double threshold = merge(transferArray(res.get(i)), 0.5);
////            for (int k = 0; k < res.get(i).size(); k++) {
////                double ttt =  0.02 * (length-300);
////                ttt = threshold;
//////                if (res.get(i).get(k) < ttt){
//////                    System.out.println(name_nlr.get(i)+ "\t"+ sample.get(k) + "\t" + res.get(i).get(k) +"\t"+  ttt +"\t" +  length);
////                    bw.write(name_nlr.get(i)+ "\t"+ sample.get(k) + "\t" + res.get(i).get(k) +"\t"+  ttt +"\t");
////                    bw.newLine();
//////                }
////            }
////        }
//
//
//        String input1 = args[0];
//        String input2 = args[1];
//        String output = args[2];
//
////        String input1 = "./lib/round9/ADM_g10000.fa.txt.gz";
////        String input2 = "./lib/round9/ADM_g10000.fa.list";
////        String output = "./lib/round9/result_round9_tmp.txt";
//
//
//        // Reference name
////        getSample("./lib/round9/ADM_g10000.fa.list");
//        getSample(input2);
//
//        // Result name
////        BufferedWriter bw = new BufferedWriter(new FileWriter("./lib/round9/result_round9_tmp.txt"));
//        BufferedWriter bw = new BufferedWriter(new FileWriter(output));
//        bw.write("NLR_ID"+ "\t"+ "Taxa_name" + "\t" + "Error_count" +"\t"+  "Threshold_peak" +"\t"+  "Threshold_seq" +"\t" +  "Ref_length_raw"  +"\t" + "Empty_align"+"\t" + "Mean"+"\t" + "Median" +"\t" + "Variance");
//        bw.newLine();
////        for (int i = 1; i < 62; i++) {
//
//            //
////            String file = "./lib/round9/ADM_g10000.fa.txt.gz";
//            List<List<Pair>> sum = getSum(input1);
//
//            List<Integer> res = new ArrayList<>();
//
//            List<Double> avr = new ArrayList<>();
//            List<Double> var = new ArrayList<>();
//            List<Double> med = new ArrayList<>();
//
//
//            for (int j = 0; j < sum.size(); j++) {
//                res.add(getStat(sum.get(j)));
//
//                List<Integer> tmp = new ArrayList<>();
//                for (int i = 0; i < sum.get(j).size(); i++) {
//                    tmp.add(sum.get(j).get(i).getDepth());
//                }
//                for (int i = 0; i < 150; i++) {
//                    tmp.remove(i);
//                }
//
//
//
//
//                avr.add(mean(tmp));
//                med.add(median(tmp));
//                var.add(var(tmp));
//            }
//
//
//
//
//
//            List<Integer> empty = new ArrayList<>();
//
//            for (int j = 0; j < sum.size(); j++) {
//                empty.add(getEmptyAlign(sum.get(j)));
//            }
//
//            double threshold = merge(transferArray(res), 1);
//
//            for (int k = 0; k < res.size(); k++) {
//                double seq_error =  0.01 * (length-300);
//                seq_error =  (double) Math.round(seq_error * 100) / 100;
////                ttt = threshold;
////                if (res.get(k) < ttt){
//                    bw.write(name+ "\t"+ sample.get(k) + "\t" + res.get(k) +"\t" +  (double) Math.round(threshold * 100) / 100 +"\t" +  seq_error +"\t" +  length  +"\t" + empty.get(k)+"\t" + (double) Math.round( avr.get(k) * 100) / 100 +"\t" + med.get(k) +"\t" + var.get(k));
//                    bw.newLine();
////                }
//            }
//            int minIndex = findMinIndex(res);
//
////        }
//        bw.close();
//    }

    public static void main(String[] args) throws IOException {
        String input1 = args[0];
        String output = args[1];

//        String input1 = "./lib/round6/result.txt";
//        String output = "./lib/round6/result_2.txt";

        BufferedWriter bw = new BufferedWriter(new FileWriter(output));
//
        List<List<Integer>> res = getFile(input1);
        bw.write("NLR_ID"+ "\t"+ "Taxa_name" + "\t" + "Error_count" +"\t"+  "Threshold_peak" +"\t"+"Mean"	+"\t"+"Median"+"\t"+	"Variance");
        bw.newLine();
//        NLR_ID	Taxa_name	Error_count	Threshold_peak	Threshold_seq	Ref_length_raw	Empty_align	Mean	Median	Variance
//
        for (int i = 0; i < res.size(); i++) {
            double threshold = merge(transferArray(res.get(i)), 0.5);
            double mean = mean(res.get(i));
            double med = median(res.get(i));
            double var = var(res.get(i));

            //                avr.add(mean(tmp));
//                med.add(median(tmp));
//                var.add(var(tmp));



            for (int k = 0; k < res.get(i).size(); k++) {
                double ttt =  0.02 * (length-300);
                ttt = threshold;
//                if (res.get(i).get(k) < ttt){
//                    System.out.println(name_nlr.get(i)+ "\t"+ sample.get(k) + "\t" + res.get(i).get(k) +"\t"+  ttt +"\t" +  length);
                    bw.write(name_nlr.get(i)+ "\t"+ sample.get(k) + "\t" + res.get(i).get(k) +"\t"+  ttt +"\t" + mean +"\t" + med +"\t" + var);
                    bw.newLine();
//                }
            }
        }
        bw.close();



    }
}
