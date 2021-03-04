package Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Demo01FileToArray {
    public static void main(String[] args) throws Exception {
//        FileReader geneList = new FileReader("100gene.txt");
        double[][] str = getFileToArray("geneList.txt");
        System.out.println(str[4][3]);
        System.out.println("==========");
        System.out.println(Arrays.deepToString(str));
//        System.out.println(str.length);
//        for (String[] strings : str) {
//            System.out.println(strings.length);
//        }
//        ArrayList<ArrayList<Integer>> twoDimension = new ArrayList<ArrayList<Integer>>();

//        bw.close();

        //The correlation function is Right.
        double corr = Cal.correlation(str[3], str[4]);
        System.out.println(corr);
    }
    
    public static String[][] getMatrix(FileReader f, int de) throws IOException {

        BufferedReader br = new BufferedReader(f);
        String line = null;
        String[][] d = new String[de][];
        int i = 0;
        while ((line = br.readLine()) != null){
            String[] al = line.split(",");
            d[i] = al;
            i++;
        }
        br.close();
        return d;
    }

//    private static List<double[]> getFile(String pathName) throws Exception {
    private static double[][] getFileToArray(String pathName) throws Exception {
        File file = new File(pathName);
        if (!file.exists())
            throw new RuntimeException("Not File!");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String str;
        List<double[]> list = new ArrayList<double[]>();
        while ((str = br.readLine()) != null) {
            int s = 0;
            String[] arr = str.split(",");
            double[] dArr = new double[arr.length];
            for (String ss : arr) {
                if (ss != null) {
                    dArr[s++] = Double.parseDouble(ss);
                }
            }
            list.add(dArr);
        }
//        int max = 0;
//        for (double[] doubles : list) {
//            if (max < doubles.length)
//                max = doubles.length;
//        }
        double[][] array = new double[list.size()][];
        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i);
        }
//        return list;
        br.close();
        return array;
    }
}
