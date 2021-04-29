package Prioritization.NM;

import java.util.ArrayList;

public class Demo01 {
    public static void main(String[] args) {
        double[] t1 = new double[]{1.1, 3.3, 5.5, 7.7};
        double[] t2 = new double[]{1, 3, 5, 7};
        double[] t4 = new double[]{10,5,4,3};
        double[] t3 = new double[]{1,1,1,1,1,1,1};

//        double[][] res = new double[][]{t2,t3,t4};

        ArrayList<Double> tt1 = new ArrayList<>();
        for (int i = 0; i < t1.length; i++) {
            tt1.add(t1[i]);
        }
        ArrayList<Double> tt2 = new ArrayList<>();
        for (int i = 0; i < t2.length; i++) {
            tt2.add(t2[i]);
        }
        ArrayList<Double> tt3 = new ArrayList<>();
        for (int i = 0; i < t3.length; i++) {
            tt3.add(t3[i]);
        }
        ArrayList<Double> tt4 = new ArrayList<>();
        for (int i = 0; i < t4.length; i++) {
            tt4.add(t4[i]);
        }

        ArrayList<ArrayList<Double>> res = new ArrayList<>();
        res.add(tt1);
        res.add(tt2);
        res.add(tt3);
        res.add(tt4);

        int degree = NetworkBlock.getDegree(res);
        System.out.println(degree);
    }
}
