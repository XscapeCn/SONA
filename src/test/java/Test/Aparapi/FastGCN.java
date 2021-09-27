package Test.Aparapi;

import com.aparapi.Kernel;
import com.aparapi.Range;
import sona.ExpressionMatrix;

import java.util.ArrayList;
import java.util.List;

public class FastGCN {
    public static void main(String[] args) {
        ExpressionMatrix em1 = new ExpressionMatrix("D:/Desktop/ScriptsInNetwork/Data/Expression/S4Leaf.txt");
        ExpressionMatrix em = new ExpressionMatrix("D:/Desktop/ScriptsInNetwork/Data/Expression/S4Leaf.txt");
        System.out.println("cols" + "\t" + "GPU" + "\t" + "CPU");
        em1.setExpression(500);
        System.out.print(500 + "\t");
        compare(em1);
        System.out.print(500 + "\t");
        compare(em1);

//        int[] cols = new int[]{51200,25600,12800,6400,3200,1600,800,400,200,100};
//        int[] cols = new int[]{51200,25600,12800,6400,6400,4800,4800,3200,3200,2400,2400,1600,1600,1200,1200,800,800,600,600,400,200,100};
        int[] cols = new int[]{3200,3200,1600,1600,800,800,400,200,100};
//        System.out.println("cols" + "\t" + "GPU" + "\t" + "CPU");

        for (int col : cols) {
            System.out.print(col + "\t");
            em.setExpression(col);
            compare(em);
        }

    }
    public static void compare(ExpressionMatrix em){
        List<List<Double>> expression1 = em.getExpression();

        final double[][] myArray = new double[expression1.size()][];

        for (int i=0;i<expression1.size(); i++){
            myArray[i] = expression1.get(i).stream().mapToDouble(Double::floatValue).toArray();
        }

        int gene = expression1.size();
        int count = (gene/2)*(gene-1);
        final double[] aa = {0};

//        final double[] e = new double[count];
        Kernel kernel = new Kernel() {
            @Override
            public void run() {
                int gid = getGlobalId();
                int row = 0;

                for (int i = 0; i < gene; i++) {
                    if ( gid +1 > i*(i-1)/2 && gid +1 <= i*(i+1)/2){
                        row = i;
                    }
                }
                int col = row-(row*(row+1)/2- gid);

                double sx = 0;
                double sy = 0;
                double sxx = 0;
                double syy = 0;
                double sxy = 0;
                int n = Math.min(myArray[row].length, myArray[col].length);
                for(int i = 0; i < n; ++i) {
                    double x = myArray[row][i];
                    double y = myArray[col][i];
                    sx += x;
                    sy += y;
                    sxx += x * x;
                    syy += y * y;
                    sxy += x * y;
                }
                double cov = sxy / n - sx * sy / n / n;
                double sigmaX = Math.sqrt(sxx / n -  sx * sx / n / n);
                double sigmaY = Math.sqrt(syy / n -  sy * sy / n / n);
                aa[0] += cov/sigmaX/sigmaY;
//                double aa = (sxy / n - sx * sy / n / n) / (Math.sqrt(sxx / n -  sx * sx / n / n)) / (Math.sqrt(syy / n -  sy * sy / n / n));
//                e[gid] = cov/sigmaX/sigmaY;

//                e[gid]= (sxy / n - sx * sy / n / n) / (Math.sqrt(sxx / n -  sx * sx / n / n)) / (Math.sqrt(syy / n -  sy * sy / n / n));
            }
        };
        Range range = Range.create(count);
//        System.out.println("Starting GPU computation");

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


