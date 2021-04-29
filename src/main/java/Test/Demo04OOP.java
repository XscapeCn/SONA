package Test;

import java.io.IOException;

public class Demo04OOP {

    public static void main(String[] args) throws IOException {
        String originalFile = "H:/Nature/2020PreExperiment/JNW/RV_limma_sample.txt";
        String triM = "H:/Nature/2020PreExperiment/JNW/RV_limma_sample_em.txt";
        ExpressionMatrix em = new ExpressionMatrix("H:/Nature/2020PreExperiment/JNW/RV_limma_sample.txt");
        em.writeCorrMatrix("H:/Nature/2020PreExperiment/JNW/RV_limma_sample_em.txt");
    }
}