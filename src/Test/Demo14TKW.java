package Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Demo14TKW {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("H:/Nature/2020PreExperiment/result/TKWTest/pos_tkw.csv"));
        String str = null;
        ArrayList<ArrayList<String>> sum = new ArrayList<>();
        while ((str= br.readLine()) != null){
            ArrayList<String> res = new ArrayList<>();
            String[] temp = str.split(",");
            for (int i = 0; i < temp.length; i++) {
                res.add(temp[i]);
            }
            sum.add(res);
        }


        int elIn = sum.get(0).indexOf("el");
        int posIn = sum.get(0).indexOf("pos");
        int chromosomeIn = sum.get(0).indexOf("chromosome");

        ArrayList<String> chromosome = new ArrayList<>();
        ArrayList<Integer> position = new ArrayList<>();

        for (int i = 0; i < sum.size(); i++) {
            if (sum.get(i).get(elIn).equals("gene")){
                chromosome.add(sum.get(i).get(chromosomeIn));
                position.add(Integer.valueOf(sum.get(i).get(posIn)));
            }
        }


        List<List<String>> originGenes = new ArrayList<>();
        for (int i = 0; i <  position.size(); i++) {
            String[] test = SNPMapping.mapping(chromosome.get(i), position.get(i), 20);
            List<String> res = new ArrayList<>();
            for (int j = 0; j < test.length; j++) {
                res.add(test[j]);
                System.out.println(test[j]);
            }
            originGenes.add(res);
        }


        BufferedReader br1 = new BufferedReader(new FileReader("H:/Nature/2020PreExperiment/result/RV/RV_test.txt"));
        br1.readLine();
        ArrayList<String> geneList = new ArrayList<>();
        ArrayList<ArrayList<Double>> geneExpression = new ArrayList<>();
        String str2 = new String();
        while((str2 = br1.readLine()) != null){
            String[] temp = str2.split("\t");
            geneList.add(temp[0]);
            ArrayList<Double> res = new ArrayList<>();
            for (int i = 1; i < temp.length; i++) {
                res.add(Double.valueOf((temp[i])));
            }
            geneExpression.add(res);
        }

        List<List<String>> genes = new ArrayList<>();


        BufferedWriter bw2 = new BufferedWriter(new FileWriter("H:/Nature/2020PreExperiment/result/RV/expression.txt"));
        for (int i = 0; i < originGenes.size(); i++) {
            List<String> temp = new ArrayList<>();
            for (int j = 0; j < originGenes.get(i).size(); j++) {
                int index = geneList.indexOf(originGenes.get(i).get(j));
                if (index != -1){
                    for (int k = 0; k < geneExpression.get(index).size(); k++) {
                        bw2.write(String.valueOf(geneExpression.get(index).get(k)));
                        bw2.write("\t");
                    }
                    bw2.newLine();
                    temp.add(originGenes.get(i).get(j));
                }
            }
            genes.add(temp);
            bw2.write("***");
            bw2.newLine();
        }
        bw2.close();

        BufferedWriter bw = new BufferedWriter(new FileWriter("H:/Nature/2020PreExperiment/result/RV/genes.txt"));

        for (int i = 0; i < genes.size(); i++) {
            for (int j = 0; j < genes.get(i).size(); j++) {
                bw.write(genes.get(i).get(j));
                bw.newLine();
            }
            bw.write("***");
            bw.newLine();
        }
        bw.close();
    }
}
