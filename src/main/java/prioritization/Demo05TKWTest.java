package prioritization;

import sona.TriangularMatrix;

import java.io.*;
import java.util.ArrayList;

public class Demo05TKWTest {
    public static void main(String[] args) throws IOException {
        BufferedReader br1 = new BufferedReader(new FileReader("H:/Nature/2020PreExperiment/result/RV/genes.txt"));
        String str;
        ArrayList<ArrayList<String>> geneList = new ArrayList<>();
        ArrayList<String> temp = new ArrayList<>();
        while ((str = br1.readLine()) != null){
            if (str.equals("***")){
                geneList.add(temp);
                temp = new ArrayList<>();
            }else {
                temp.add(str);
            }
        }

        BufferedReader br2 = new BufferedReader(new FileReader("H:/Nature/2020PreExperiment/result/RV/expression.txt"));
        String str2;
        ArrayList<ArrayList<ArrayList<Double>>> genExpression = new ArrayList<>();
        ArrayList<ArrayList<Double>> temp2 = new ArrayList<>();

        while ((str2 = br2.readLine()) != null){
            if (str2.equals("***")){
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


        double delta = 100000;
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


        Network n = new Network(F, nb.getGenExpression());
        ArrayList<ArrayList<Integer>> arrayLists = n.getDegreePerGene();
        ArrayList<ArrayList<String>> arrayLists1 = n.rankBaseScore();
        for (int i = 0; i < arrayLists.size(); i++) {
            System.out.println(arrayLists.get(i));
            System.out.println(arrayLists1.get(i));
            System.out.println(geneList.get(i));
            System.out.println("------------");
        }
//        System.out.println(arrayLists);
    }

    public static Population writeLog(NetworkBlock nb, Population P, String filename){
        int count = 0;
        double delta = Integer.MAX_VALUE;
        Population F = null;
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
            while (delta > P.getAvrDegree() * 0.005){
                F = new Population();
                F = P.inherit(nb.getGenExpression());
                double fAvrDegree = F.getAvrDegree();
                double pAvrDegree = P.getAvrDegree();
                bw.write("The " + count + " generation");
                bw.write("\n");
                bw.write("F generation: " + fAvrDegree);
                bw.write("\n");
                bw.write("P generation: " + pAvrDegree);
                delta = fAvrDegree - pAvrDegree;
                bw.write("\n");
                bw.write("Delta: " + delta);
                bw.write("\n");
                bw.write("\n");
                P = F;
            }
            bw.close();
        } catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
        return F;
    }

    public static void writeResult(NetworkBlock nb, Population F, String filename, ArrayList<ArrayList<String>> geneList){
        Network n = new Network(F, nb.getGenExpression());
        ArrayList<ArrayList<Integer>> arrayLists = n.getDegreePerGene();
        ArrayList<ArrayList<String>> arrayLists1 = n.rankBaseScore();
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
            for (int i = 0; i < arrayLists.size(); i++) {
                for (int j = 0; j < arrayLists.get(i).size(); j++) {
                    bw.write(arrayLists.get(i).get(j));
                    bw.write("\t");
                }
                bw.write("\n");
                for (int j = 0; j < arrayLists1.get(i).size(); j++) {
                    bw.write(arrayLists1.get(i).get(j));
                    bw.write("\t");
                }
                bw.write("\n");
                for (int j = 0; j < geneList.get(i).size(); j++) {
                    bw.write(geneList.get(i).get(j));
                    bw.write("\t");
                }
//                System.out.println(arrayLists.get(i));
//                System.out.println(arrayLists1.get(i));
//                System.out.println(geneList.get(i));
                bw.write("\n");
                System.out.println("------------");
                bw.write("\n");
            }
            bw.close();
        }catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }

}