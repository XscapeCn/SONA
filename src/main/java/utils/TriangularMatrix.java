package utils;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class TriangularMatrix {
    private String filename;
    private double sum;
    private int length;
    private double avr;
    private double std;

    public TriangularMatrix() {}

    public TriangularMatrix(String filename) throws IOException {
        this.filename = filename;
        BufferedReader br1 = new BufferedReader(new FileReader(this.filename));
        String str;
        br1.readLine();
        while ((str = br1.readLine()) != null) {
            double[] drr = getDrr(str);
            for (int i = 0; i < drr.length; i++) {
                this.sum += drr[i];
            }
            this.length += drr.length;
        }
        this.avr = this.sum/this.length;
        br1.close();

        BufferedReader br2 = new BufferedReader(new FileReader(this.filename));
        String str2;
        br2.readLine();
        double total = 0;
        while ((str2 = br2.readLine()) != null) {
            double[] drr = getDrr(str2);
            for(int i=0;i<drr.length;i++){
                total += (drr[i]-this.avr)*(drr[i]-this.avr);   //求出方差，如果要计算方差的话这一步就可以了
            }
        }
        this.std = Math.sqrt(total/(this.length-1));
        br2.close();
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public double getSum() {
        return sum;
    }

    public int getLength() {
        return length;
    }

    public double getAvr() {
        return avr;
    }

    public double getStd() {
        return std;
    }

//    public void setBr() throws FileNotFoundException {
//        this.br1 = new BufferedReader(new FileReader(this.filename));
//    }

    public void calculate() throws IOException {
        BufferedReader br1 = new BufferedReader(new FileReader(this.filename));
        String str;
        br1.readLine();
        while ((str = br1.readLine()) != null) {
            double[] drr = getDrr(str);
            for (int i = 0; i < drr.length; i++) {
                this.sum += drr[i];
            }
            this.length += drr.length;
        }
        this.avr = this.sum/this.length;
        br1.close();
    }

    public void calSTD() throws IOException{
        BufferedReader br1 = new BufferedReader(new FileReader(this.filename));
        String str;
        br1.readLine();
        double total = 0;
        while ((str = br1.readLine()) != null) {
            double[] drr = getDrr(str);
            for(int i=0;i<drr.length;i++){
                total += (drr[i]-this.avr)*(drr[i]-this.avr);   //求出方差，如果要计算方差的话这一步就可以了
            }
        }
        this.std = Math.sqrt(total/(this.length-1));
        br1.close();
    }

    public void one2One(String geneFile, String Filename2) throws IOException {
        //filename2 is the name of file which will be written, one2One means "geneA" + "geneB" + "corr"
        BufferedReader br1 = new BufferedReader(new FileReader(this.filename));
        BufferedWriter bw = new BufferedWriter(new FileWriter(Filename2));
        String str;
        int count = 1;
        List geneList = readGeneList(geneFile);
        br1.readLine();
//        bw.write(":START_ID" + "\t" + ":END_ID" +"\t" + "zs:double" + "\t" + ":TYPE" + "\n");
//        bw.newLine();
        while ((str = br1.readLine()) != null) {
            double[] drr = getDrr(str);
//            if(count >0){
                for (int i = 0; i < drr.length; i++) {
                    if (drr[i] > 3){bw.write(count + "\t" + i + "\t" + drr[i] + "\n");}
//                    bw.write(count + "\t" + i + "\t" + drr[i] + "\n");
//                    bw.write(geneList.get(count) + "\t" + geneList.get(i) + "\t" + drr[i] + "\n");
//                    bw.newLine();
                }
//            }
            count++;
        }
        br1.close();
        bw.close();
    }

    public void one2One(String geneFile, String Filename2, int p) throws IOException {
        //filename2 is the name of file which will be written, one2One means "geneA" + "geneB" + "corr"
        BufferedReader br1 = new BufferedReader(new FileReader(this.filename));
        BufferedWriter bw = new BufferedWriter(new FileWriter(Filename2));
        String str;
        int count = 1;
        List geneList = readGeneList(geneFile);
        br1.readLine();
        bw.write("gene1" + "\t" + "gene2" +"\t" + "corr");
        bw.newLine();
        while ((str = br1.readLine()) != null) {
            double[] drr = getDrr(str);
            if(count >0){
                for (int i = 0; i < drr.length; i++) {
                    bw.write(geneList.get(count) + "\t" + geneList.get(i) + "\t" + classify(getZ(drr[i])));
                    bw.newLine();
                }
            }
            count++;
        }
        br1.close();
        bw.close();
    }

    public void one2One(String geneFile, String Filename2, double p) throws IOException {
        //filename2 is the name of file which will be written, one2One means "geneA" + "geneB" + "corr"
        BufferedReader br1 = new BufferedReader(new FileReader(this.filename));
        BufferedWriter bw = new BufferedWriter(new FileWriter(Filename2));
        String str;
        int count = 1;
        List geneList = readGeneList(geneFile);
        br1.readLine();
        bw.write(":START_ID" + "," + ":END_ID" +"," + "zs:double" + "," + ":TYPE" + "\n");
//        bw.newLine();
        while ((str = br1.readLine()) != null) {
            double[] drr = getDrr(str);
//            if(count >0){
            for (int i = 0; i < drr.length; i++) {
                if (drr[i] > 2){bw.write(count + "," + i + "," + drr[i] + "," + "p"+ "\n");}else if (drr[i] < -2){
                    bw.write(count + "," + i + "," + drr[i] + "," + "n" +  "\n");}
//                    bw.write(count + "\t" + i + "\t" + drr[i] + "\n");
//                    bw.write(geneList.get(count) + "\t" + geneList.get(i) + "\t" + drr[i] + "\n");
//                    bw.newLine();
            }
//            }
            count++;
        }
        br1.close();
        bw.close();
    }



    public void ZNorm(String Filename2) throws IOException {
        BufferedReader br1 = new BufferedReader(new FileReader(this.filename));
        br1.readLine();
        BufferedWriter bw = new BufferedWriter(new FileWriter(Filename2));
        bw.newLine();
        String str;
        while ((str = br1.readLine()) != null) {
            double[] drr = getDrr(str);
            double[] zrr = new double[drr.length];
            for (int i = 0; i < drr.length; i++) {
                //加入了一步标准化，未单独写成方法。
                drr[i] = 0.5*(Math.log((1+drr[i])/(1-drr[i])));
                zrr[i] = getZ(drr[i]);
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

    public void ZNorm(String Filename2, double avr, double std) throws IOException {
        BufferedReader br1 = new BufferedReader(new FileReader(this.filename));
//        br1.readLine();
        BufferedWriter bw = new BufferedWriter(new FileWriter(Filename2));
        bw.newLine();
        String str;
        while ((str = br1.readLine()) != null) {
            double[] drr = getDrr(str);
            double[] zrr = new double[drr.length];
            for (int i = 0; i < drr.length; i++) {
                //加入了一步标准化，未单独写成方法。
                drr[i] = 0.5*(Math.log((1+drr[i])/(1-drr[i])));
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

    //classify the z-score to rank like "D C B A S"
    public static String classify(double score){
        String a;
        if (-1.5 < score & score < 1.5) { a = "D"; }
        else if (-2.0 < score & score < 2.0) { a = "C"; }
        else if (-2.5 < score & score < 2.5) { a = "B"; }
        else if (-3.0 < score & score < 3.0) { a = "A"; }
        else { a = "S"; }
        return a;
    }

    public static List readGeneList(String filename) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String str = null;
        List gene = new ArrayList<String>();
        while ((str = br.readLine()) !=null) {
            gene.add(str);
        }
        br.close();
        return gene;
    }

    public static double[] getDrr(String str){
        String[] arr = str.split("\t");
        double[] drr = new double[arr.length];
        int i = 0;
        for (String ss : arr) {
            if (ss != null) {
                if (ss.equals("∞") || ss.equals("-∞")){
                    ss = "10";
                }
                drr[i++] = Double.parseDouble(ss);
            }
        }
        return drr;
    }

    public static ArrayList<Double> getDrrA(String str){
        String[] arr = str.split("\t");
        ArrayList<Double> drr = new ArrayList<>();
        int i = 0;
        for (String ss : arr) {
            if (ss != null) {
                if (ss.equals("∞") || ss.equals("-∞")){
                    ss = "10";
                }
                drr.add(Double.parseDouble(ss));
            }
        }
        return drr;
    }

    public double getZ(double x){
        return (x-this.avr)/this.std;
    }

    public double getZ(double x, double avr, double std){
        return (x-avr)/std;
    }
}