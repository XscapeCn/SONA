package Test;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.stat.regression.SimpleRegression;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

//import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

public class ExpressionMatrix{
    private String filename;
    private ArrayList<ArrayList<Double>> expression;

    public ExpressionMatrix(String filename) throws IOException {
        this.filename = filename;
        this.expression = new ArrayList<>();
        BufferedReader br1 = new BufferedReader(new FileReader(this.filename));
        String str = null;
        while ((str = br1.readLine()) != null) {
            ArrayList<Double> temp = TriangularMatrix.getDrrA(str);
            this.expression.add(temp);
        }
    }

//    public ExpressionMatrix() {}

    public String getFilename() {
        return filename;
    }

    public ArrayList<ArrayList<Double>> getExpression() {
        return expression;
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
                temp[count2] = correlation(drr, TriangularMatrix.getDrr(str2));
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

    public void writeCorrMatrixA(String writeFileName) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(writeFileName));
        bw.newLine();
        DecimalFormat formatDouble = new DecimalFormat("#.####");
        for (int i = 1; i < this.expression.size(); i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < i; j++) {
                sb.append(formatDouble.format(myCorrelation(this.expression.get(j), this.expression.get(i))));
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
                myCorrelation(this.expression.get(j), this.expression.get(i));
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
                temp[count2] = correlation(drr, TriangularMatrix.getDrr(str2));
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

    //write full size correlation matrix
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
                temp.add(correlation(drr, TriangularMatrix.getDrr(str2)));
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
                temp[count] = correlation(drr, TriangularMatrix.getDrr(str2));
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
        return ;
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
                temp[count2] = correlation(drr, TriangularMatrix.getDrr(str2));
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

    public static double correlation(final double[] xArray, final double[] yArray) {
        SimpleRegression regression = new SimpleRegression();
        if (xArray.length != yArray.length) {
            throw new DimensionMismatchException(xArray.length, yArray.length);
        } else if (xArray.length < 2) {
            throw new MathIllegalArgumentException(LocalizedFormats.INSUFFICIENT_DIMENSION,
                    xArray.length, 2);
        } else {
            for(int i=0; i<xArray.length; i++) {
                regression.addData(xArray[i], yArray[i]);
            }
            return regression.getR();
        }
    }

    public static double correlation(ArrayList<Double> xArray, ArrayList<Double> yArray) {
        SimpleRegression regression = new SimpleRegression();
        if (xArray.size() != yArray.size()) {
            throw new DimensionMismatchException(xArray.size(), yArray.size());
        } else if (xArray.size() < 2) {
            throw new MathIllegalArgumentException(LocalizedFormats.INSUFFICIENT_DIMENSION,
                    xArray.size(), 2);
        } else {
            for(int i=0; i<xArray.size(); i++) {
                regression.addData(xArray.get(i), yArray.get(i));
            }
            return regression.getR();
        }
    }


    //below is the PCC calculation method written by myself
    public static double myCorrelation(double[] xs, double[] ys) {
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
    public static double myCorrelation(ArrayList<Double> xs, ArrayList<Double> ys) {
        //TODO: check here that arrays are not null, of the same length etc
        double sx = 0.0;
        double sy = 0.0;
        double sxx = 0.0;
        double syy = 0.0;
        double sxy = 0.0;
        int n = Math.min(xs.size(), ys.size());
        for(int i = 0; i < n; ++i) {
            double x = xs.get(i);
            double y = ys.get(i);
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
}