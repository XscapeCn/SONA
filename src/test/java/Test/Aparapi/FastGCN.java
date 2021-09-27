package Test.Aparapi;

import com.aparapi.Kernel;
import com.aparapi.Range;
import sona.ExpressionMatrix;
import java.util.List;

public class FastGCN {
    public static void main(String[] args) {
        ExpressionMatrix em = new ExpressionMatrix("D:/Desktop/ScriptsInNetwork/Data/Expression/S4Leaf.txt");
        List<List<Double>> expression1 = em.getExpression();
        final double[][] myArray = new double[expression1.size()][];

        for (int i=0;i<expression1.size(); i++){
            myArray[i] = expression1.get(i).stream().mapToDouble(Double::floatValue).toArray();
        }

        int gene = expression1.size();
        int count = (gene/2)*(gene-1);
        final double[] ii = {0};

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
//                myCorrelation(myArray[row],myArray[col]);
//                e[gid] = myCorrelation(myArray[row],myArray[col]);

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
                ii[0] += (sxy / n - sx * sy / n / n) / (Math.sqrt(sxx / n -  sx * sx / n / n)) / (Math.sqrt(syy / n -  sy * sy / n / n));

//                e[gid]= (sxy / n - sx * sy / n / n) / (Math.sqrt(sxx / n -  sx * sx / n / n)) / (Math.sqrt(syy / n -  sy * sy / n / n));
            }
        };
        Range range = Range.create(count);
        System.out.println("Starting GPU computation");
        long time = System.currentTimeMillis();
//        kernel.execute(gene*gene);
        kernel.execute(range); // Running the Kernel
        System.out.println("Task finished in " + (System.currentTimeMillis() - time) + "ms");
        kernel.dispose();

        long time2 = System.currentTimeMillis();
        em.calTest();
        System.out.println("Task finished in " + (System.currentTimeMillis() - time2) + "ms");

    }

}
