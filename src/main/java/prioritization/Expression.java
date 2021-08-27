package prioritization;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Expression {
    private ArrayList<String> geneList;
    private ArrayList<ArrayList<Double>> expressionMatrix;

    public ArrayList<String> getGeneList() {
        return geneList;
    }

    public ArrayList<ArrayList<Double>> getExpressionMatrix() {
        return expressionMatrix;
    }

    public Expression(String geneFile, String expressionFile) throws IOException {
        BufferedReader gf = new BufferedReader(new FileReader(geneFile));
        String temp;
        while ((temp = gf.readLine()) != null){
            this.geneList.add(temp);
        }
        gf.close();

        BufferedReader ef = new BufferedReader(new FileReader(expressionFile));
        String temp2;
        ArrayList<Double> dou;
        while ((temp2 = ef.readLine()) != null){
            dou = getDFromS(temp2);
            this.expressionMatrix.add(dou);
        }
        ef.close();
    }

    public static ArrayList<Double> getDFromS(String str){
        String[] arr = str.split("\t");
        ArrayList<Double> drr = null;
        for (String ss : arr) {
            if (ss != null) {
                if (ss.equals("∞") || ss.equals("-∞")){
                    ss = "10";
                }
                drr.add(Double.parseDouble(ss));
            }
        }
        return drr;
    }

    public ArrayList<ArrayList<Double>> getExpressionFromGene(ArrayList<String> candidateGeneList) {
        ArrayList<ArrayList<Double>> expression = null;
        int i=0;
        for (String gene: candidateGeneList) {
            i = this.geneList.indexOf(gene);
            expression.add(this.expressionMatrix.get(i));
        }
        return expression;
    }

    public void rm(){
        this.expressionMatrix = null;
    }

    public List<List<Object>> getGeneAndExpression(String filename) {
        List<List<Object>> res = new ArrayList<>();
        List<Object> temp;
        List<String> gene = new ArrayList<>();
        List<List<Double>> expression = new ArrayList<>();
        String str;
        try{
            BufferedReader br = new BufferedReader(new FileReader(filename));
            while((str = br.readLine()) != null){
                if (!str.equals("***")){
                    gene.add(str.split("\t")[0]);
                    expression.add(getDrrExcept1(str));
                }else {
                    temp = new ArrayList<>();
                    temp.add(gene);
                    temp.add(expression);
                    res.add(temp);
                }
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
            System.exit(1);
        }
//        temp.add(gene);
//        temp.add(expression);
        return res;
    }

    public static List<Double> getDrrExcept1(String str){
        String[] arr = str.split("\t");
        List<Double> drr = new ArrayList<>();
//        double[] drr = new double[arr.length -1];
//        int i = 0;
        for (int j = 1; j < arr.length; j++) {
            if (arr[j] != null){
                if (arr[j].equals("∞") || arr[j].equals("-∞")){
                    arr[j] = "10";
                }
                drr.add(Double.parseDouble(arr[j]));
//                drr[i++] = Double.parseDouble(arr[j]);
            }
        }
        return drr;
    }


}
