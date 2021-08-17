package GeneFeature;

//import xujun.analysis.rnaseq.GeneFeature;
import utils.SNPMapping;

import java.util.ArrayList;
import java.util.Arrays;

public class Demo01 {
    public static void main(String[] args) {
        GeneFeature gf = new GeneFeature("H:/Nature/2020PreExperiment/JNW/Supplement/wheat_v1.1_Lulab.gff3");
        ArrayList<String> subGeneName = new ArrayList<>();
        ArrayList<int[]> subGeneRange = new ArrayList<>();
        for (int i = 0; i < gf.getGeneNumber(); i++) {
            String temp = gf.getGeneName(i);
            if (temp.contains("4A")){
                subGeneName.add(temp);
                int[] tt = new int[]{gf.getGeneStart(i), gf.getGeneEnd(i)};
                subGeneRange.add(tt);
            }
        }
        for (int i = 0; i < 10; i++) {
            System.out.println(subGeneName.get(i));
            System.out.println(Arrays.toString(subGeneRange.get(i)));
            System.out.println(subGeneRange.get(i)[1]);
        }

        System.out.println(Arrays.toString(SNPMapping.bSearch(subGeneRange, 90000)));
        int[] temp = SNPMapping.bSearch(subGeneRange, 90000);
        String[] result = new String[temp.length];
        for (int i = 0; i < temp.length; i++) {
            result[i] = subGeneName.get(temp[i]);
        }
        System.out.println(Arrays.toString(SNPMapping.mapping("4A", 90000)));

    }
}
