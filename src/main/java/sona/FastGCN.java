package sona;

import com.aparapi.Kernel;
import com.aparapi.Range;
import scala.util.Using;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

public class FastGCN {

//    public static void calCorr(ExpressionMatrix em){
//       calCorr(em.getExpression());
//    }

//    public static double[] calCorr(List<List<Double>> expression1){
//        return calCorr(expression1,null, expression1.size());
//    }
//
//    public static double[] calCorr(List<List<Double>> expression1, BufferedWriter bw){
//        return calCorr(expression1,bw, expression1.size());
//    }
//
//    public static double[] calCorr(ExpressionMatrix em, BufferedWriter bw){
//
//        return calCorr(em.getExpression(),bw, em.getExpression().size());
//    }

    public static double[] calCorr(double[][] myArray) throws InterruptedException {
        return calCorr(myArray,null,0, myArray.length);
    }

    public static void calCorr(double[][] myArray, BufferedWriter bw) throws InterruptedException {
        calCorr(myArray, bw,0, myArray.length);
    }

    public static double[] calCorr(double[][] myArray, BufferedWriter bw, int start, int block) throws InterruptedException {
        int geneNumber = myArray.length;
        DecimalFormat formatDouble = new DecimalFormat("#.####");
        double[] res;
        int count = 0;
        if (geneNumber - start > block){
            for (int i = start; i < start + 5000; i++) {
                count += i;
            }
        }else {
            for (int i = start; i < geneNumber; i++) {
                count += i;
            }
        }

        res = new double[count];
        int rest =  (start/2)*(start-1);

        Kernel kernel = new Kernel() {
            @Override
            public void run() {
                int row = getGlobalId(0);
                int col = getGlobalId(1);
                if (row + start > col){
                    double sx = 0;
                    double sy = 0;
                    double sxx = 0;
                    double syy = 0;
                    double sxy = 0;
                    int n = myArray[row].length;
                    for(int i = 0; i < n; ++i) {
                        double x = myArray[row + start][i];
                        double y = myArray[col][i];
                        sx += x;
                        sy += y;
                        sxx += x * x;
                        syy += y * y;
                        sxy += x * y;
                    }
                    res[((row+start))*(row + start-1)/2 - rest + col] = (sxy / n - sx * sy / n / n) / (Math.sqrt(sxx / n -  sx * sx / n / n)) / (Math.sqrt(syy / n -  sy * sy / n / n));
                }
            }
        };
        Range range;
        range = Range.create2D(block, geneNumber);

        long time = System.currentTimeMillis();
        kernel.execute(range);
        System.out.println("Calculating time: " + (System.currentTimeMillis() - time) + "ms");

        kernel.dispose();

        try{
            System.out.println("Start writing file...");
            time = System.currentTimeMillis();
            for (double a : res) {
                bw.write(String.valueOf(a));
                bw.write("\n");
            }
            System.out.println("Writing time: " + (System.currentTimeMillis() - time) + "ms" + "\n");
        }catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
        return res;
    }

    public static double[][] transferToArray(List<List<Double>> expression){
        int size = expression.size();
        double[][] myArray = new double[size][];
        for (int i=0; i < size; i++){
            myArray[i] = expression.get(i).stream().mapToDouble(Double::floatValue).toArray();
        }
        return myArray;
    }

    public static void calCorrDistribute(List<List<Double>> expression, BufferedWriter bw, int block) throws InterruptedException {
        double[][] doubles = transferToArray(expression);
        for (int i = 0; i < expression.size(); i += block) {
            System.out.println("Start calculating " + i);
            calCorr(doubles, bw, i, block);
        }
    }

    public static void calCorrDistribute(ExpressionMatrix em, BufferedWriter bw, int block) throws InterruptedException {
        List<List<Double>> expression = em.getExpression();
        calCorrDistribute(expression, bw, block);
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        ExpressionMatrix em = new ExpressionMatrix("D:/Desktop/ScriptsInNetwork/Data/Expression/S4Leaf.txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter("D:/Desktop/test.txt"));
        calCorrDistribute(em,bw, 5000);
        bw.close();
    }
}
