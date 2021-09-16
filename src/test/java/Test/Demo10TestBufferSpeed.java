package Test;

import sona.ExpressionMatrix;

import java.io.IOException;

public class Demo10TestBufferSpeed {
    public static void main(String[] args) throws IOException {
        ExpressionMatrix em = new ExpressionMatrix("H:/Nature/2020PreExperiment/result/GR/GR_limma_10000.txt");

        long startTime2=System.currentTimeMillis();
        em.writeCorrMatrixA("H:/Nature/2020PreExperiment/result/GR/test_limma_10000.txt");
        long endTime2=System.currentTimeMillis();
        System.out.println("程序运行时间： "+(endTime2-startTime2)+"ms");//41s

        ExpressionMatrix em2 = new ExpressionMatrix("H:/Nature/2020PreExperiment/result/GR/GR_limma_10000.txt");
        long startTime=System.currentTimeMillis();
        em2.writeCorrMatrixA();
        long endTime=System.currentTimeMillis();
        System.out.println("程序运行时间： "+(endTime-startTime)+"ms");//41s or 20s
    }
}
