package GWAS;


import utils.ExpressionMatrix;
import utils.TriangularMatrix;

import java.io.IOException;

public class GA {
    public static void main(String[] args) throws IOException {
        ExpressionMatrix ga = new ExpressionMatrix("./GWAS/GA/RV_GA.txt");
        ga.writeCorrMatrix("./GWAS/GA/RV_GA_em.txt", 13);
        TriangularMatrix tm = new TriangularMatrix("./GWAS/GA/RV_GA_em.txt");
        tm.one2One("./GWAS/GA/geneList_filtered.txt", "./GWAS/GA/RV_GA_tm.txt");
    }
}
