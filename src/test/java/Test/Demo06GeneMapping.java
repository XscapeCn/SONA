package Test;

import utils.SNPMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Demo06GeneMapping {
    public static void main(String[] args) throws IOException {
        int[][] intB=new int[8][];
        intB[0]=new int[]{1,3};  //3
        intB[1]=new int[]{5,9};  //3
        intB[2]=new int[]{11,20};
        intB[3]=new int[]{22,23};
        intB[4]=new int[]{25,29};
        intB[5]=new int[]{50,55};
        intB[6]=new int[]{60,61};
        intB[7]=new int[]{70,75};
        //2
        int a = 3;
        int b = 9;
//        Integer[] c = FindInPartiallySortedMatrix.Find(9, intB);
//        Integer[] d = FindInPartiallySortedMatrix.Find(1, intB);
//        Integer[] e = FindInPartiallySortedMatrix.Find(21, intB);
//        System.out.println(Arrays.toString(c));
//        System.out.println(Arrays.toString(d));
//        System.out.println(Arrays.toString(e));
//
//        System.out.println(Arrays.toString(SNPMapping.bSearch(intB, 1)));
//        System.out.println(Arrays.toString(SNPMapping.bSearch(intB, 3)));
//        System.out.println(Arrays.toString(SNPMapping.bSearch(intB, 4)));
//        System.out.println(Arrays.toString(SNPMapping.bSearch(intB, 5)));
//        System.out.println(Arrays.toString(SNPMapping.bSearch(intB, 9)));
//        System.out.println(Arrays.toString(SNPMapping.bSearch(intB, 10)));
//        System.out.println(Arrays.toString(SNPMapping.bSearch(intB, 11)));
//        System.out.println(Arrays.toString(SNPMapping.bSearch(intB, 20)));
//        System.out.println(Arrays.toString(SNPMapping.bSearch(intB, 21)));
//        System.out.println(Arrays.toString(SNPMapping.bSearch(intB, 22)));
//        System.out.println(Arrays.toString(SNPMapping.bSearch(intB, 23)));
//        System.out.println(Arrays.toString(SNPMapping.bSearch(intB, 24)));
//        System.out.println(Arrays.toString(SNPMapping.bSearch(intB, 25)));
//        System.out.println(Arrays.toString(SNPMapping.bSearch(intB, 29)));
//        System.out.println(Arrays.toString(SNPMapping.bSearch(intB, 30)));
//        System.out.println(Arrays.toString(SNPMapping.bSearch(intB, 50)));
//        System.out.println(Arrays.toString(SNPMapping.bSearch(intB, 55)));
//        System.out.println(Arrays.toString(SNPMapping.bSearch(intB, 56)));
//        System.out.println(Arrays.toString(SNPMapping.bSearch(intB, 76)));
//        String[] test = SNPMapping.mapping("4A", 740000, 1);
//        GeneFeature gf = new GeneFeature("H:/Nature/2020PreExperiment/JNW/Supplement/wheat_v1.1_Lulab.gff3");
//        int geneChromosome = gf.getGeneChromosome(gf.getGeneIndex("TraesCS1A02G292800"));
//        System.out.println(geneChromosome);
        String[] test2 = SNPMapping.mapping("3A", 191529539, 1);
        System.out.println(Arrays.toString(test2));
        System.out.println("========");

        ArrayList<String[]> result = new ArrayList<>();
        result = SNPMapping.getTriads(test2);
        for (int i = 0; i < result.size(); i++) {
            System.out.println(Arrays.toString(result.get(i)));
        }
    }
}
