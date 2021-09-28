package Test.Aparapi;

import com.aparapi.Kernel;
import com.aparapi.Range;
import sona.ExpressionMatrix;
import java.util.ArrayList;
import java.util.List;


public class FastGCN {
    public static void main(String[] args) {
//        ExpressionMatrix em1 = new ExpressionMatrix("D:/Desktop/ScriptsInNetwork/Data/Expression/S4Leaf.txt");
        ExpressionMatrix em = new ExpressionMatrix("D:/Desktop/ScriptsInNetwork/Data/Expression/S4Leaf.txt");
        System.out.println("cols" + "\t" + "GPU" + "\t" + "CPU");

//        List<Integer> index = new ArrayList<>();
//        for (int i = 0; i < em.getExpression().size(); i++) {
//            index.add(i*(i+1)/2 + 1);
//        }
//
//
//        em1.setExpression(500);
//        System.out.print(500 + "\t");
//        compare(em1,index);
//        System.out.print(500 + "\t");
//        compare(em1,index);


//        int[] cols = new int[]{50000,25600,12800,6400,3200,1600,800,400,200,100};
        int[] cols = new int[]{51200,25600,12800,6400,6400,4800,4800,3200,3200,2400,2400,1600,1600,1200,1200,800,800,600,600,400,200,100};
//        int[] cols = new int[]{3200,3200,1600,1600,800,800,400,200,100};
//        System.out.println("cols" + "\t" + "GPU" + "\t" + "CPU");

        for (int col : cols) {
            System.out.print(col + "\t");
            em.setExpression(col);
            em.writeRelateFile();
//            compare(em);
        }

    }

    public static int bSearch(List<Integer> aa, int find){
        int start = 0;
        int end = aa.size() - 1;
        int middle = 0;
        while(start <= end){
            middle = (start + end) / 2;
            if(aa.get(middle) <=find && aa.get(middle+1) > find ){
                return middle+1;
            }else if(aa.get(middle +1) < find){
                start = middle + 2;
            }else{
                end = middle -1;
            }
        }
        return -1;
    }

    public static void compare(ExpressionMatrix em){
        List<List<Double>> expression1 = em.getExpression();
        final double[][] myArray = new double[expression1.size()][];
        for (int i=0;i<expression1.size(); i++){
            myArray[i] = expression1.get(i).stream().mapToDouble(Double::floatValue).toArray();
        }
        int gene = expression1.size();
//        int gene =50000;w
        int count = (gene/2)*(gene-1);
        final double[] aa = {0};

//        final double[] e = new double[count];
        Kernel kernel = new Kernel() {
            @Override
            public void run() {
//                int gid = getGlobalId();
//                int row = 0;
                int row = getGlobalId(0);
                int col = getGlobalId(1);

                if (row > col){
                    double sx = 0;
                    double sy = 0;
                    double sxx = 0;
                    double syy = 0;
                    double sxy = 0;
                    int n = myArray[row].length;
//                    int n = Math.min(myArray[row].length, myArray[col].length);
                    for(int i = 0; i < n; ++i) {
                        double x = myArray[row][i];
                        double y = myArray[col][i];
                        sx += x;
                        sy += y;
                        sxx += x * x;
                        syy += y * y;
                        sxy += x * y;
                    }
//                    double cov = sxy / n - sx * sy / n / n;
//                    double sigmaX = Math.sqrt(sxx / n -  sx * sx / n / n);
//                    double sigmaY = Math.sqrt(syy / n -  sy * sy / n / n);
                    aa[0] += (sxy / n - sx * sy / n / n) / (Math.sqrt(sxx / n -  sx * sx / n / n)) / (Math.sqrt(syy / n -  sy * sy / n / n));
                }

//                for (int i = 0; i < gene; i++) {
//                    if ( gid +1 > i*(i-1)/2 && gid +1 <= i*(i+1)/2){
//                        row = i;
//                    }
//                }

//                double aa = (sxy / n - sx * sy / n / n) / (Math.sqrt(sxx / n -  sx * sx / n / n)) / (Math.sqrt(syy / n -  sy * sy / n / n));
//                e[gid] = cov/sigmaX/sigmaY;
//                e[gid]= (sxy / n - sx * sy / n / n) / (Math.sqrt(sxx / n -  sx * sx / n / n)) / (Math.sqrt(syy / n -  sy * sy / n / n));
            }
        };

        Kernel kernel2 = new Kernel() {
            @Override
            public void run() {
                int gid = getGlobalId();
                int row = gid/gene + 3000;
                int col = gid%gene;
                if (row > col){
                    double sx = 0;
                    double sy = 0;
                    double sxx = 0;
                    double syy = 0;
                    double sxy = 0;
                    int n = myArray[row].length;
                    for(int i = 0; i < n; ++i) {
                        double x = myArray[row][i];
                        double y = myArray[col][i];
                        sx += x;
                        sy += y;
                        sxx += x * x;
                        syy += y * y;
                        sxy += x * y;
                    }
                    aa[0] += (sxy / n - sx * sy / n / n) / (Math.sqrt(sxx / n -  sx * sx / n / n)) / (Math.sqrt(syy / n -  sy * sy / n / n));
                }
            }
        };

//        if (gene <= 30000){
//            Range range = Range.create(gene*gene);
//            long time = System.currentTimeMillis();
//            kernel.execute(range); // Running the Kernel
//            System.out.print(System.currentTimeMillis() - time);
//            System.out.print("\t");
//            kernel.dispose();
//            System.out.print("\n");
//        }else {
//            Range range = Range.create(3000 * gene);
//            Range range2 = Range.create((gene - 3000) * gene);
//            long time = System.currentTimeMillis();
//            kernel.execute(range);
//            System.out.print("Do kernel2");
//            System.out.print(System.currentTimeMillis() - time);
//            kernel.dispose();
//
//            kernel2.execute(range2);
//            System.out.print(System.currentTimeMillis() - time);
//            System.out.print("\t");
//            kernel2.dispose();
//            System.out.print("\n");
//        }

        Range range = Range.create2D(gene,gene);
//        Range range2 = Range.create(50000*50000);
////        System.out.println("Starting GPU computation");
//
        long time = System.currentTimeMillis();
        kernel.execute(range); // Running the Kernel
//        System.out.println("Task finished in " + (System.currentTimeMillis() - time) + "ms");
        System.out.print(System.currentTimeMillis() - time);
        System.out.print("\t");
        kernel.dispose();

        long time2 = System.currentTimeMillis();
        em.calTest();
//        System.out.println("Task finished in " + (System.currentTimeMillis() - time2) + "ms");
        System.out.print(System.currentTimeMillis() - time2);
        System.out.print("\n");
    }
}


