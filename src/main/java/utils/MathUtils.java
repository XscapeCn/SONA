package utils;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.stat.regression.SimpleRegression;

import java.util.List;
import java.util.ArrayList;

public class MathUtils {
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

    public static double[] getDrr(String[] arr){
//        String[] arr = str.split("\t");
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

    public static double[] getDrr(String str, String regex){
        String[] arr = str.split(regex);
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

    public static List<Double> getDrrA(String str){
        String[] arr = str.split("\t");
        List<Double> drr = new ArrayList<>();
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

    public static List<Double> getDrrA(String[] arr){
//        String[] arr = str.split("\t");
        List<Double> drr = new ArrayList<>();
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

    public static List<Double> getDrrA(String str,String regex){
        String[] arr = str.split(regex);
        List<Double> drr = new ArrayList<>();
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

    public static String classify(double score){
        String a;
        if (-1.5 < score & score < 1.5) { a = "D"; }
        else if (-2.0 < score & score < 2.0) { a = "C"; }
        else if (-2.5 < score & score < 2.5) { a = "B"; }
        else if (-3.0 < score & score < 3.0) { a = "A"; }
        else { a = "S"; }
        return a;
    }
    public static double myCorrelation(List<Double> xs, List<Double> ys) {
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
        //        double cov = sxy / n - sx * sy / n / n;
//        double sigmaX = Math.sqrt(sxx / n -  sx * sx / n / n);
//        double sigmaY = Math.sqrt(syy / n -  sy * sy / n / n);
        return (sxy / n - sx * sy / n / n) / (Math.sqrt(sxx / n -  sx * sx / n / n)) / (Math.sqrt(syy / n -  sy * sy / n / n));
    }

    public static double myCorrelation(double[] xs, double[] ys) {
        //TODO: check here that arrays are not null, of the same length etc
        double sx = 0;
        double sy = 0;
        double sxx = 0;
        double syy = 0;
        double sxy = 0;
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
        //        double cov = sxy / n - sx * sy / n / n;
//        double sigmaX = Math.sqrt(sxx / n -  sx * sx / n / n);
//        double sigmaY = Math.sqrt(syy / n -  sy * sy / n / n);
        return (sxy / n - sx * sy / n / n) / (Math.sqrt(sxx / n -  sx * sx / n / n)) / (Math.sqrt(syy / n -  sy * sy / n / n));
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

    public static double correlation(List<Double> xArray, List<Double> yArray) {
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
}
