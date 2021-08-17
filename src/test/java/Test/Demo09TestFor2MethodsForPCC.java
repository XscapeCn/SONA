package Test;

import utils.ExpressionMatrix;

public class Demo09TestFor2MethodsForPCC {
    public static void main(String[] args) {
        double[] aa = new double[]{4.5333,4.3970,4.2655,3.19136099583334,1.88366099583334,1.49493099583334,4.51690099583334,1.15538099583334};
        double[] bb = new double[]{8.83242621825396,9.49758621825396,12.469886218254,8.38949621825396,5.49116621825396,4.92864621825396,8.10230621825396,6.20371621825396};

        long startTime=System.currentTimeMillis();
        double res = 0;
        for (int i = 0; i < 800000000; i++) {
            res = ExpressionMatrix.correlation(aa,bb);
        }
        long endTime=System.currentTimeMillis(); //获取结束时间
        System.out.println("程序运行时间： "+(endTime-startTime)+"ms");

        System.out.println("=======================");
        long startTime2=System.currentTimeMillis();
        double res2 = 0;
        for (int i = 0; i <  800000000; i++) { res2 = ExpressionMatrix.myCorrelation(aa,bb);
        }
        long endTime2=System.currentTimeMillis(); //获取结束时间
        System.out.println("程序运行时间： "+(endTime2-startTime2)+"ms");
        System.out.println(res);
        System.out.println(res2);
    }
}
