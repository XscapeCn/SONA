package Prioritization.NM;

import Test.TriangularMatrix;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Demo02 {
    public static void main(String[] args) throws IOException {
        BufferedReader br1 = new BufferedReader(new FileReader("H:/Nature/2020PreExperiment/result/RR/prixFixe/geneList.txt"));
        String str;
        ArrayList<ArrayList<String>> geneList = new ArrayList<>();
        ArrayList<String> temp = new ArrayList<>();
        while ((str = br1.readLine()) != null){
            if (str.equals("###")){
                geneList.add(temp);
                temp.clear();
            }else {
                temp.add(str);
            }
        }

        BufferedReader br2 = new BufferedReader(new FileReader("H:/Nature/2020PreExperiment/result/RR/prixFixe/expression.txt"));
        String str2 = null;
        ArrayList<ArrayList<ArrayList<Double>>> genExpression = new ArrayList<>();
        ArrayList<ArrayList<Double>> temp2 = new ArrayList<>();


        while ((str2 = br2.readLine()) != null){
            if (str2.equals("###")){
                genExpression.add(temp2);
                temp2 = new ArrayList<>();
            }else {
                ArrayList<Double> temp3 = new ArrayList<>();
                double[] drr = TriangularMatrix.getDrr(str2);
                for (int i = 0; i < drr.length; i++) {
                    temp3.add(drr[i]);
                }
                temp2.add(temp3);
            }
        }




        NetworkBlock nb = new NetworkBlock(genExpression,geneList);
//        System.out.println(nb.init());
        Population P = new Population(nb.init(), nb.getGenExpression());
//        System.out.println("============");
//        System.out.println(P.getTureLocusCount());
//        System.out.println("============");
//        System.out.println(P.getSumDegree());
        Population F = new Population();

        double delta = 1;
        int count = 0;
        while (delta > P.getAvrDegree() * 0.005){
            F = new Population();
            F = P.inherit(nb.getGenExpression());
            System.out.println("The " + count + " generation");
            System.out.println("F generation: " + F.getAvrDegree());
            System.out.println("P generation: " + P.getAvrDegree());
            delta = F.getAvrDegree() - P.getAvrDegree();
            System.out.println("Delta: " + delta);
            System.out.println("=========================");
            P = F;
            count++;
        }
        System.out.println(count -1 );

        Network n = new Network(F, nb.getGenExpression());
        ArrayList<ArrayList<Integer>> arrayLists = n.getDegreePerGene();
        ArrayList<ArrayList<String>> arrayLists1 = n.rankBaseScore();
        for (int i = 0; i < arrayLists.size(); i++) {
            System.out.println(arrayLists.get(i));

            System.out.println(arrayLists1.get(i));
            System.out.println("------------");
        }
//        System.out.println(arrayLists);


    }
}
