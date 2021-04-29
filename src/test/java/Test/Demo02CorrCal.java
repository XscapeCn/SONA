package Test;

import java.io.IOException;

public class Demo02CorrCal {
    public static void main(String[] args) throws IOException {
        String originalFile = "zz.txt";
        String triM = "test.txt";
        String rankFile = "test2.txt";
        ExpressionMatrix em = new ExpressionMatrix(originalFile);
        em.writeCorrMatrix(triM);
//        Cal.writeCorrMatrix("geneSmall.txt", "Correlation.txt", 1000000);
//        System.out.println(corrMatrix[6][5]);
        TriangularMatrix tm = new TriangularMatrix(triM);
        tm.one2One("geneList.txt", rankFile, 3);
    }
}
