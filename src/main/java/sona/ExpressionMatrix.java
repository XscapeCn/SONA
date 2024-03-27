package sona;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.stat.regression.SimpleRegression;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import utils.IOUtils;
import utils.MathUtils;
import utils.NetworkUtils;
import utils.TransGeneToNum;

//import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

public class ExpressionMatrix{
    private int stage;
    private String tissue;
    private String filename;
    private List<String> sample = new ArrayList<>();
    private List<String> geneName = new ArrayList<>();
    private List<Integer> gene = new ArrayList<>();
    private List<List<Double>> expression;
    private String outPath = "";
    private String[] outFile;


    public ExpressionMatrix(String filename){
        initialize(filename);
    }

    public ExpressionMatrix(String filename, String outPath){
        initialize(filename);
        this.outPath = outPath;
    }

    public ExpressionMatrix(String filename, String outPath, int col){
        initialize(filename);
        this.outPath = outPath;
    }

    public String getFilename() {
        return filename;
    }

    public List<List<Double>> getExpression() {
        return this.expression;
    }

    public void setExpression(int a) {
        this.expression = expression.subList(0,a);
    }

    public String getTissue() {
        return tissue;
    }

    public List<String> getSample() {
        return sample;
    }

    public List<String> getGeneName() {
        return geneName;
    }

    public List<Integer> getGene() {
        return gene;
    }

    public String getOutPath() {
        return outPath;
    }

    public void setOutPath(String outPath) {
        this.outPath = outPath;
    }

    public int getStage() {
        return stage;
    }

    public String[] getOutFile(){return outFile;};

    //    public ExpressionMatrix() {}
    public void initialize(String filename){
        this.filename = filename;
        this.expression = new ArrayList<>();
        this.outFile = NetworkUtils.getContextFromName(filename);
//        this.stage = Integer.parseInt(filename.substring(1,3));
//        this.tissue = filename.split(".txt")[1].substring(1);
        BufferedReader br = IOUtils.getTextReader(this.filename);
        TransGeneToNum tg = new TransGeneToNum();
        try {
            String str = null;
            str = br.readLine();
//            System.out.println(str);
            readSample(str);
            while ((str = br.readLine()) != null) {
//                System.out.println(str);
                String[] split = str.split("\t");
//                System.out.println(str);
                geneName.add(split[0]);
                gene.add(tg.getIndexOfGene(split[0]));
                List<Double> temp = new ArrayList<>();
                for (int i = 1; i < split.length; i++) {
                    temp.add(Double.parseDouble(split[i]));
                }
                this.expression.add(temp);
            }
            br.close();
        }catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }

    }

    public void readSample(String str){
        String[] splits = str.split("\t");
        for (int i = 4; i < splits.length; i++) {
            sample.add(splits[i]);
        }
    }

    public void write(String writeFileName){
        String file = this.outPath + writeFileName;
        BufferedWriter bw = IOUtils.getTextWriter(file);

        DecimalFormat formatDouble = new DecimalFormat("#.####");
        try{
            bw.write("StartID,EndID,Corr \n");
            for (int i = 0; i < expression.size(); i++) {
                for (int j = 0; j < i; j++) {
                    double correlation = MathUtils.correlation(expression.get(i), expression.get(j));
                    bw.write(formatDouble.format(correlation) + ",");
                }
                bw.write("\n");
            }
            bw.close();
        }catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void writeNeoFile(){
        writeNodeFile();
        writeRelateFile();

    }

    public void writeNodeFile(){
        String file = this.outPath + outFile[0];
        BufferedWriter bw = IOUtils.getTextWriter(file);
//        DecimalFormat formatDouble = new DecimalFormat("#.####");
        try{
            bw.write(":LABEL,geneID,geneIn:ID\n");
            for (int i = 0; i < geneName.size(); i++) {
                bw.write("gene,"+geneName.get(i) + "," + gene.get(i)+ "\n");
            }
            bw.close();
        }catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }

    }

    public void calTest(){
        for (int i = 0; i < expression.size(); i++) {
            for (int j = 0; j < i; j++) {
//                    double correlation = MathUtils.correlation(expression.get(i), expression.get(j));
                double correlation = MathUtils.myCorrelation(expression.get(i), expression.get(j));
//                    double correlation = Math.random();
            }
        }
    }

    public void writeRelateFile(){
        String file = this.outPath + outFile[1];
        BufferedWriter bw = IOUtils.getTextWriter(file);
        DecimalFormat formatDouble = new DecimalFormat("#.####");
        try{
            bw.write(":TYPE,:Start_ID,:End_ID,Corr\n");
            for (int i = 0; i < expression.size(); i++) {
                for (int j = 0; j < i; j++) {
//                    double correlation = MathUtils.correlation(expression.get(i), expression.get(j));
                    double correlation = MathUtils.myCorrelation(expression.get(i), expression.get(j));
//                    double correlation = Math.random();
                    if (Math.abs(correlation) > 0.1){
                        bw.write("t," + gene.get(i)+","+gene.get(j)+",");
                        bw.write(formatDouble.format(correlation));
                        bw.write("\n");
                    }
                }
            }
            bw.close();
        }catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void writeRelateFileInMultipleThread(int thread){
    }


    //write triangular correlation matrix
    public void writeCorrMatrix(String writeFileName) throws IOException {
        BufferedReader br1 = new BufferedReader(new FileReader(this.filename));
        BufferedWriter bw = new BufferedWriter(new FileWriter(writeFileName));
        int count = 1;
        String str = null;
        while ((str = br1.readLine()) != null) {
            double[] drr = TriangularMatrix.getDrr(str);
            BufferedReader br2 = new BufferedReader(new FileReader(this.filename));
            String str2 = null;
            double[] temp = new double[count-1];
            for (int count2 = 0; count2 < count-1; count2++) {
                str2 = br2.readLine();
                temp[count2] = MathUtils.correlation(drr, TriangularMatrix.getDrr(str2));
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
//        return ;
    }

    public void conCurrentCorrelation(int a, int b){
        List<Double> res = new ArrayList<>();
        for (int i = 0; i < b-a; i++) {

            for (int j = 0; j < i; j++) {
                double correlation = MathUtils.myCorrelation(expression.get(i+a), expression.get(j+a));
                res.add(correlation);
            }
        }
    }

    public static void main(String[] args) {
//        classify(100,10);
        int start = 10;
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 30; col++) {
                if (row + start> col){
                    System.out.println(row + ", " + col + ", "  + (((row+start))*(row + start-1)/2 - (start)*(start-1)/2  + col));
                }
            }
        }
        int aa = 0;

        for (int i = 10; i < 20; i++) {
            aa +=i;
        }
        System.out.println(aa);

    }

    public static void  classify(int sum, int a){
        int yu = sum%a;
        int count = sum/a;
        if (yu != 0){
            for (int i = 1; i < count-1; i++) {
//                String s = ((a * i) - 1) + " ";
                System.out.println( a*(i-1) + " " + ((a*i)-1) + " ");
            }
            System.out.println(a*(count) + " " + sum + " ");
        }else {
            for (int i = 1; i < count+1; i++) {
//                String s = ((a * i) - 1) + " ";
                System.out.println( a*(i-1) + " " + ((a*i)-1) + " ");
            }
        }
    }

    public void writeCorrMatrixA(String writeFileName) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(writeFileName));
        bw.newLine();
        DecimalFormat formatDouble = new DecimalFormat("#.####");
        for (int i = 1; i < this.expression.size(); i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < i; j++) {
                sb.append(formatDouble.format(MathUtils.myCorrelation(this.expression.get(j), this.expression.get(i))));
                sb.append('\t');
            }
            bw.write(sb.toString());
            bw.newLine();
        }
        bw.close();
    }

    public void writeCorrMatrixA() throws IOException {
        for (int i = 1; i < this.expression.size(); i++) {
            for (int j = 0; j < i; j++) {
                MathUtils.myCorrelation(this.expression.get(j), this.expression.get(i));
            }
        }
    }
    //correlation matrix between 2 files
    public void writeCorrMatrix(String writeFileName, String secondExpressionFilename) throws IOException {
        BufferedReader br1 = new BufferedReader(new FileReader(this.filename));
        BufferedWriter bw = new BufferedWriter(new FileWriter(writeFileName));
        int count = 1;
        String str = null;
        while ((str = br1.readLine()) != null) {
            double[] drr = TriangularMatrix.getDrr(str);
            BufferedReader br2 = new BufferedReader(new FileReader(secondExpressionFilename));
            String str2 = null;
            double[] temp = new double[count-1];
            for (int count2 = 0; count2 < count-1; count2++) {
                str2 = br2.readLine();
                temp[count2] = MathUtils.correlation(drr, TriangularMatrix.getDrr(str2));
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

    public void writeCorrMatrix(String writeFileName, int size) throws IOException {
        BufferedReader br1 = new BufferedReader(new FileReader(this.filename));
        BufferedWriter bw = new BufferedWriter(new FileWriter(writeFileName));
        String str;
        while ((str = br1.readLine()) != null) {
            double[] drr = TriangularMatrix.getDrr(str);
            BufferedReader br2 = new BufferedReader(new FileReader(this.filename));
            String str2;
            ArrayList<Double> temp = new ArrayList<>();
//            double[] temp = new double[size];
            int count = 0;
            while ((str2 = br2.readLine()) != null){
                temp.add(MathUtils.correlation(drr, TriangularMatrix.getDrr(str2)));
//                temp[count] = correlation(drr, TriangularMatrix.getDrr(str2));
                count++;
            }
            DecimalFormat formatDouble = new DecimalFormat("#.####");
            for (double v : temp) {
                bw.write(formatDouble.format(v) + " ");
            }
            bw.newLine();
            br2.close();
        }
        br1.close();
        bw.close();
        return ;
    }

//    public void writeCorrMatrix(String writeFileName, int size) throws IOException {
//        BufferedWriter bw = new BufferedWriter(new FileWriter(writeFileName));
//        bw.newLine();
//        for (int i = 1; i < this.expression.size(); i++) {
//            StringBuilder sb = new StringBuilder();
//            for (int j = 0; j < this.expression.size(); j++) {
//                sb.append(mCorrelation(this.expression.get(j), this.expression.get(i)));
//                sb.append('\t');
//            }
//            bw.write(sb.toString());
//            bw.newLine();
//        }
//        bw.close();
//    }

    public void writeCorrMatrix(String writeFileName, String secondExpressionFilename, int size) throws IOException {
        BufferedReader br1 = new BufferedReader(new FileReader(this.filename));
        BufferedWriter bw = new BufferedWriter(new FileWriter(writeFileName));
        String str;
        while ((str = br1.readLine()) != null) {
            double[] drr = TriangularMatrix.getDrr(str);
            BufferedReader br2 = new BufferedReader(new FileReader(secondExpressionFilename));
            String str2;
            double[] temp = new double[size];
            int count = 0;
            while ((str2 = br2.readLine()) != null){
                temp[count] = MathUtils.correlation(drr, TriangularMatrix.getDrr(str2));
                count++;
            }
            DecimalFormat formatDouble = new DecimalFormat("#.####");
            for (double v : temp) {
                bw.write(formatDouble.format(v) + "\t");
            }
            bw.newLine();
            br2.close();
        }
        br1.close();
        bw.close();
//        return ;
    }

    public double[][] getCorrMatrix(String writeFileName, int geneCount) throws IOException {
        BufferedReader br1 = new BufferedReader(new FileReader(this.filename));
        BufferedWriter bw = new BufferedWriter(new FileWriter(writeFileName));
        int count = 1;
        String str = null;
        double [][] corrMatrix = new double[geneCount][];
        while ((str = br1.readLine()) != null) {
            double[] drr = TriangularMatrix.getDrr(str);
            BufferedReader br2 = new BufferedReader(new FileReader(this.filename));
//            int count2 = 0;
            String str2 = null;
            double[] temp = new double[count-1];
            for (int count2 = 0; count2 < count-1; count2++) {
                str2 = br2.readLine();
                temp[count2] = MathUtils.correlation(drr, TriangularMatrix.getDrr(str2));
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

}