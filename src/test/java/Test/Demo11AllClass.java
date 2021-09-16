package Test;

import sona.ExpressionMatrix;

import java.io.IOException;

public class Demo11AllClass {
    public static void main(String[] args) throws IOException {
//        long lrs=System.currentTimeMillis();
//        ExpressionMatrix lr = new ExpressionMatrix("H:/Nature/2020PreExperiment/result/LR/LR_limma_51573.txt");
//        lr.writeCorrMatrixA("H:/Nature/2020PreExperiment/result/LR/LR_limma_51573_correlation.txt");
//        long lre =System.currentTimeMillis(); //获取结束时间
//        System.out.println("LR: "+(lre-lrs)+"ms");//41s

        long lvs=System.currentTimeMillis();
        ExpressionMatrix lv = new ExpressionMatrix("H:/Nature/2020PreExperiment/result/LV/LV_limma_48419.txt");
        lv.writeCorrMatrixA("H:/Nature/2020PreExperiment/result/LV/LV_limma_48419_correlation.txt");
        long lve =System.currentTimeMillis();
        System.out.println("LV: "+(lve-lvs)+"ms");
        lv = null;

        long lss=System.currentTimeMillis();
        ExpressionMatrix ls = new ExpressionMatrix("H:/Nature/2020PreExperiment/result/LS/LS_limma_49148.txt");
        ls.writeCorrMatrixA("H:/Nature/2020PreExperiment/result/LS/LS_limma_49148_correlation.txt");
        long lse =System.currentTimeMillis();
        System.out.println("LS: "+(lse-lss)+"ms");
        ls = null;

        long srs=System.currentTimeMillis();
        ExpressionMatrix sr = new ExpressionMatrix("H:/Nature/2020PreExperiment/result/SR/SR_limma_44142.txt");
        sr.writeCorrMatrixA("H:/Nature/2020PreExperiment/result/SR/SR_limma_44142_correlation.txt");
        long sre =System.currentTimeMillis();
        System.out.println("SR: "+(sre-srs)+"ms");
        sr = null;
    }
}