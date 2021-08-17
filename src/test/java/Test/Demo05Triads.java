package Test;

import utils.TriangularMatrix;

import java.io.IOException;

public class Demo05Triads {
    public static void main(String[] args) throws IOException {
//        ExpressionMatrix em = new ExpressionMatrix("./triads/triadsBSorted1000.txt");
//        em.writeCorrMatrix("./triads/triadsBD.txt", "./triads/triadsDSorted1000.txt", 1001);

//        ExpressionMatrix em = new ExpressionMatrix("triads+Sorted.txt");
//        em.writeCorrMatrix("Corr_triads_sorted_600.txt",602);

//        TriangularMatrix tm = new TriangularMatrix("Correlation.txt");
//        tm.calculate();
//        tm.calSTD();
//        System.out.println(tm.getAvr());
//        System.out.println(tm.getLength());
//        System.out.println(tm.getStd());
//        System.out.println(tm.getSum());

//        double std = tm.getStd();
//        System.out.println(mean);
//        System.out.println(std);

        TriangularMatrix tm = new TriangularMatrix("./GWAS/GA/RV_GA_em.txt");
        tm.ZNorm("./GWAS/GA/RV_GA_em_z.txt", 0.0327, 0.3097);

    }
}
