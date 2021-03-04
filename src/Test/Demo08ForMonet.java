package Test;

import java.io.IOException;

public class Demo08ForMonet {
    public static void main(String[] args) throws IOException {
//        TriangularMatrix tm = new TriangularMatrix("H:/Nature/2020PreExperiment/JNW/Correlation.txt");
//        tm.calculate();
//        tm.calSTD();
//        System.out.println(tm.getStd());
//        System.out.println(tm.getAvr());
//        tm.ZNorm("H:/Nature/2020PreExperiment/JNW/monet_zz.txt");
//
//        TriangularMatrix tm2 = new TriangularMatrix("H:/Nature/2020PreExperiment/JNW/monet_zz.txt");
//        tm2.one2One("H:/Nature/2020PreExperiment/JNW/genelist.txt", "H:/Nature/2020PreExperiment/JNW/RV_Monet_121_abs.txt");



//        //for GR, construct a normalized network then clustering, and compared to RV
//        ExpressionMatrix em = new ExpressionMatrix("H:/Nature/2020PreExperiment/JNW/RR/RR_limma_52535.txt");
//        em.writeCorrMatrix("H:/Nature/2020PreExperiment/JNW/RR/RR_limma_52535_correlation.txt");
//
//        TriangularMatrix tm = new TriangularMatrix("H:/Nature/2020PreExperiment/JNW/RR/RR_limma_52535_correlation.txt");
//        tm.calculate();
//        tm.calSTD();
//        System.out.println(tm.getStd());
//        System.out.println(tm.getAvr());
//        tm.ZNorm("H:/Nature/2020PreExperiment/JNW/RR/RR_monet_zz.txt");

        TriangularMatrix tm2 = new TriangularMatrix("H:/Nature/2020PreExperiment/JNW/RR/RR_monet_zz.txt");
//        tm2.one2One("H:/Nature/2020PreExperiment/JNW/genelist.txt", "H:/Nature/2020PreExperiment/JNW/GR/GR_Monet_121_abs.txt");
        // ignore the genelist parameter.
        tm2.one2One("H:/Nature/2020PreExperiment/JNW/RR/RR_genelist_52535.txt", "H:/Nature/2020PreExperiment/JNW/RR/RR_Monet_52535_121_nonabs_3.txt");

    }
}
