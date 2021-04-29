package Test;

//import javafx.beans.binding.Bindings;

import java.io.*;
import java.text.DecimalFormat;

public class Cal {

    public static double correlation(double[] xs, double[] ys) {
        //TODO: check here that arrays are not null, of the same length etc
        double sx = 0.0;
        double sy = 0.0;
        double sxx = 0.0;
        double syy = 0.0;
        double sxy = 0.0;
        int n = Math.min(xs.length, ys.length);
        for(int i = 0; i < n; ++i) {
            double x = xs[i];
            double y = ys[i];
            sx += x;
            sy += y;
            sxx += x * x;
            syy += y * y;
            sxy += x * y;
        }
        // covariation
        double cov = sxy / n - sx * sy / n / n;
        // standard error of x
        double sigmaX = Math.sqrt(sxx / n -  sx * sx / n / n);
        // standard error of y
        double sigmaY = Math.sqrt(syy / n -  sy * sy / n / n);
        // correlation is just a normalized covariation
        return cov / sigmaX / sigmaY;
    }

    public static double[][] getCorrMatrix(String Filename, String Filename2, int size) throws IOException {
        BufferedReader br1 = new BufferedReader(new FileReader(Filename));
        BufferedWriter bw = new BufferedWriter(new FileWriter(Filename2));
        int count = 1;
        String str = null;
        double [][] corrMatrix = new double[size][];
        while ((str = br1.readLine()) != null) {
            double[] drr = getDrr(str);
//            System.out.print("drr");
//            System.out.println(Arrays.toString(drr));
//            System.out.println("Big" + count);
//            while ((count2 < count) & (str2 = f2.readLine()) != null){
//                String[] arr2 = str2.split(",");
//                double[] drr2 = new double[arr2.length];
//                int ii = 0;
//                for (String ss2 : arr2) {
//                    if (ss2 != null) {
//                        drr2[ii++] = Double.parseDouble(ss2);
//                    }
//                }
//                corrMatrix[count][count2] = Correlation(drr, drr2);
//                count2++;
//            }

//            List<double[]> list = new ArrayList<double[]>();
            BufferedReader br2 = new BufferedReader(new FileReader(Filename));
//            int count2 = 0;
            String str2 = null;
            double[] temp = new double[count-1];
//            while ((str2 = f2.readLine()) != null) {
//                if (count2 < count){
//                    double[] drr2 = getDrr(str2);
//                    //接受corr值。
//                    double corr = Correlation(drr,drr2);
//                    temp[count2] = corr;
//                    count2++;
//                }else {
//                    f2.close();
//                    //Stream closed
//                }
//            }
            for (int count2 = 0; count2 < count-1; count2++) {
                str2 = br2.readLine();
                temp[count2] = correlation(drr, getDrr(str2));
            }
            DecimalFormat formatDouble = new DecimalFormat("#.####");
            corrMatrix[count-1] = temp;
            for (double v : temp) {
                bw.write(formatDouble.format(v) + "\t");
            }
            bw.newLine();
            count++;
            br2.close();
        }
        br1.close();
        bw.close();
        return corrMatrix;
    }

    public static double[] getDrr(String str){
        String[] arr = str.split("\t");
        double[] drr = new double[arr.length];
        int i = 0;
        for (String ss : arr) {
            if (ss != null) {
                drr[i++] = Double.parseDouble(ss);
            }
        }
        return drr;
    }

    public static void writeCorrMatrix(String Filename, String Filename2, int size) throws IOException {
        BufferedReader br1 = new BufferedReader(new FileReader(Filename));
        BufferedWriter bw = new BufferedWriter(new FileWriter(Filename2));
        int count = 1;
        String str = null;
        while ((str = br1.readLine()) != null) {
            double[] drr = getDrr(str);
            BufferedReader br2 = new BufferedReader(new FileReader(Filename));
            String str2 = null;
            double[] temp = new double[count-1];
            for (int count2 = 0; count2 < count-1; count2++) {
                str2 = br2.readLine();
                temp[count2] = correlation(drr, getDrr(str2));
            }
            DecimalFormat formatDouble = new DecimalFormat("#.####");
            for (double v : temp) {
                bw.write(formatDouble.format(v) + "\t");
            }
            bw.newLine();
            count++;
            br2.close();
        }
        br1.close();
        bw.close();
        return ;
    }

    public static double getMean(String Filename) throws IOException {
        BufferedReader br1 = new BufferedReader(new FileReader(Filename));
        String str = null;
        double sum = 0;
        int length = 0;
        br1.readLine();
        while ((str = br1.readLine()) != null) {
            double[] drr = getDrr(str);
            for (int i = 0; i < drr.length; i++) {
                sum = sum + drr[i];
            }
            length = length + drr.length;
        }
        return sum/length;
    }

    public static int getLength(String Filename) throws IOException {
        BufferedReader br1 = new BufferedReader(new FileReader(Filename));
        String str = null;
        int length = 0;
        br1.readLine();
        while ((str = br1.readLine()) != null) {
            double[] drr = getDrr(str);
            length = length + drr.length;
        }
        return length;
    }

    public static double getSTD(String Filename, double sum) throws IOException{
        BufferedReader br1 = new BufferedReader(new FileReader(Filename));
        String str = null;
        br1.readLine();
        double total = 0;
        int length = 0;
        while ((str = br1.readLine()) != null) {
            double[] drr = getDrr(str);
            for(int i=0;i<drr.length;i++){
                total += (drr[i]-sum)*(drr[i]-sum);   //求出方差，如果要计算方差的话这一步就可以了
            }
            length = length + drr.length;
        }
        return Math.sqrt(total/length);
    }

    public static double getZ(double x, double avr, double std){
        return (x-avr)/std;
    }

    public static void ZNorm(String Filename, String Filename2) throws IOException {
        BufferedReader br1 = new BufferedReader(new FileReader(Filename));
        br1.readLine();
        BufferedWriter bw = new BufferedWriter(new FileWriter(Filename2));
        bw.newLine();
        String str = null;
        double avr = getMean(Filename);
        double std = getSTD(Filename, avr);
        while ((str = br1.readLine()) != null) {
            double[] drr = getDrr(str);
            double[] zrr = new double[drr.length];
            for (int i = 0; i < drr.length; i++) {
                zrr[i] = getZ(drr[i], avr, std);
            }
            for (double v : zrr) {
                DecimalFormat formatDouble = new DecimalFormat("#.####");
                bw.write(formatDouble.format(v) + "\t");
            }
            bw.newLine();
        }
        bw.close();
        br1.close();
    }

}
