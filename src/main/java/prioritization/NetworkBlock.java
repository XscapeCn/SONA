package prioritization;

import utils.ExpressionMatrix;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;


public class NetworkBlock {
    private ArrayList<ArrayList<ArrayList<Double>>> genExpression;
    private ArrayList<ArrayList<String>> geneList;

    //constructor
    public NetworkBlock(){}
    public NetworkBlock(ArrayList<ArrayList<ArrayList<Double>>> genExpression, ArrayList<ArrayList<String>> geneList){
        this.genExpression = genExpression;
        this.geneList = geneList;
    }

    //getter and setter
    public ArrayList<ArrayList<ArrayList<Double>>> getGenExpression(){
        return this.genExpression;
    }

    public ArrayList<ArrayList<String>> getGeneList(){return this.geneList;}

    // triable arraylist locus(index) -> gene(index) -> expression
    public ArrayList<ArrayList<Integer>> init(){
        //first loop

        ArrayList<ArrayList<Integer>> init = new ArrayList<>();
        ArrayList<ArrayList<Double>> network = new ArrayList<>();
        ArrayList<Integer> res = new ArrayList<>();
        int degree;
        for (int i = 0; i < 5000; i++) {
            ArrayList<Integer> temp = new ArrayList<>();
            for (int j = 0; j < this.genExpression.size(); j++) {
//            init.add(genExpression.get(i).get((int)(Math.random()*genExpression.get(i).size())));
                int index = (int) (Math.random()*this.genExpression.get(j).size());
                temp.add(index);
//                network.add(this.genExpression.get(i).get((index)));
            }
            init.add(temp);
        }
        degree = getDegree(network);
        return init;
    }

    //for locus
    public static ArrayList<ArrayList<Double>> transfer(ArrayList<Integer> index, ArrayList<ArrayList<ArrayList<Double>>> genExpression){
        ArrayList<ArrayList<Double>> res = new ArrayList<>();
        for (int i = 0; i < index.size(); i++) {
            int a = index.get(i);
            ArrayList<Double> temp = genExpression.get(i).get(a);
            res.add(temp);
        }
        return res;
    }

    public static int getDegree(ArrayList<ArrayList<Double>> candidateExpression){
        ArrayList<Double> result = new ArrayList<>();
        for (int i = 0; i < candidateExpression.size(); i++) {
            for (int j = i +1; j < candidateExpression.size(); j++) {
                double temp = ExpressionMatrix.correlation(candidateExpression.get(i), candidateExpression.get(j));
                if (temp>0.2 | temp<-0.2){
                    result.add(temp);
                }
            }
        }
        return result.size();
    }

    public static int getDegree(double[][] candidateExpression){
        ArrayList<Double> result = new ArrayList<>();
        for (int i = 0; i < candidateExpression.length; i++) {
            for (int j = i +1; j < candidateExpression.length; j++) {
                double temp = ExpressionMatrix.correlation(candidateExpression[i], candidateExpression[j]);
                if (temp>0.2 | temp<-0.2){
                    result.add(temp);
                }
            }
        }
        return result.size();
    }
}


class Population extends ArrayList<ArrayList<Integer>>{
    //5000-locus-geneIndex
    private ArrayList<ArrayList<Integer>> index;

    private double avrDegree;
    private ArrayList<Integer> sumDegree = new ArrayList<>();
    //10 locus, 5 gene/locus
    private ArrayList<ArrayList<Integer>> tureLocusCount = new ArrayList<>();

    public Population(ArrayList<ArrayList<Integer>> init, ArrayList<ArrayList<ArrayList<Double>>> genExpression){
        this.index = init;

        double temp = 0;
        for (ArrayList<Integer> locus: this.index) {
            int degree = NetworkBlock.getDegree(NetworkBlock.transfer(locus, genExpression));
            sumDegree.add(degree);
            temp += degree;
        }
        this.avrDegree = (temp/(this.index.size()));
        for (int i = 0; i < genExpression.size(); i++) {
            ArrayList<Integer> temp2 = new ArrayList<>();
            for (int j = 0; j < genExpression.get(i).size(); j++) {
                temp2.add(j);
            }
//            temp2.add(genExpression.get(i).size());
            this.tureLocusCount.add(temp2);
        }
    }

    public Population() {}

    public ArrayList<ArrayList<Integer>> getIndex() {
        return index;
    }

    public double getAvrDegree(){
        return this.avrDegree;
    }

    public ArrayList<ArrayList<Integer>> getTureLocusCount() {
        return tureLocusCount;
    }

    public ArrayList<Integer> getSumDegree() {
        return sumDegree;
    }

    public Population inherit(ArrayList<ArrayList<ArrayList<Double>>> genExpression){
        ArrayList<ArrayList<Integer>> tempPop = new ArrayList<>();
        for (ArrayList<Integer> network: this.index) {
            ArrayList<Integer> newNetwork = new ArrayList<>();
            for (int i = 0; i < tureLocusCount.size(); i++) {
                newNetwork.add(mutation(network.get(i), this.tureLocusCount.get(i), 0.05));
            }
            tempPop.add(network);
        }

        ArrayList<Integer> tempDeg = new ArrayList<>();
        for (ArrayList<Integer> locus: tempPop) {
            int degree = NetworkBlock.getDegree(NetworkBlock.transfer(locus, genExpression));
            tempDeg.add(degree);
        }

        tempPop = new ArrayList<>();

        for (int i = 0; i < this.index.size(); i++) {
            tempPop.add(randomSelect(tempDeg));
        }

        Population F = new Population(tempPop, genExpression);
        return F;
    }

    public int mutation(int index, ArrayList<Integer> locus, double p){
        double seed = Math.random();
        if (seed < p){
            locus.remove(Integer.valueOf(index));
            return (int) (Math.random() * locus.size());
        } else {return index;}
    }

    public ArrayList<Integer> randomSelect(ArrayList<Integer> degree){
        ArrayList<Integer> temp = new ArrayList<>();
        for (Integer single: degree) {
            for (int i = 0; i < single; i++) {
                temp.add(single);
            }
        }
        ArrayList<Integer> parentA = this.index.get(temp.get((int) (Math.random() * temp.size())));
        ArrayList<Integer> parentB = this.index.get(temp.get((int) (Math.random() * temp.size())));
        ArrayList<Integer> res = mating(parentA, parentB);
        return res;
    }

    public static ArrayList<Integer>  mating(ArrayList<Integer> parentA, ArrayList<Integer> parentB){
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 0; i < parentA.size(); i++) {
            double seed = Math.random();
            if (seed < 0.5){
                res.add(parentA.get(i));
            } else {res.add(parentB.get(i));}
        }
        return res;
    }
}

class Network extends ArrayList<Integer>{
    private ArrayList<ArrayList<Integer>> tureLocusCount;
    private ArrayList<Integer> network;
    private ArrayList<ArrayList<Integer>> degreePerGene;

    public ArrayList<Integer> getNetwork() {
        return network;
    }

    public Network(Population F, ArrayList<ArrayList<ArrayList<Double>>> genExpression){
        this.network = F.getIndex().get((int) (F.size() * Math.random()));
        this.tureLocusCount = F.getTureLocusCount();
        this.degreePerGene = degreePerGene(genExpression);
    }

    public ArrayList<ArrayList<Integer>> getDegreePerGene() {
        return degreePerGene;
    }

    public ArrayList<ArrayList<Integer>> degreePerGene(ArrayList<ArrayList<ArrayList<Double>>> genExpression){

        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        for (int i = 0; i < tureLocusCount.size(); i++) {
            ArrayList<Integer> temp = new ArrayList<>();
            ArrayList<Integer> prix = this.network;
            for (int j = 0; j < tureLocusCount.get(i).size(); j++) {
                prix.set(i, tureLocusCount.get(i).get(j));
                temp.add(NetworkBlock.getDegree(NetworkBlock.transfer(prix, genExpression)));
            }
            res.add(temp);
        }
        return res;
    }

    public ArrayList<ArrayList<String>> rankBaseScore(){
        ArrayList<ArrayList<String>> res = new ArrayList<>();
        for (int i = 0; i < tureLocusCount.size(); i++) {
            res.add(rank(this.degreePerGene.get(i)));
        }
        return res;
    }

    public static ArrayList<String> rank(ArrayList<Integer> tt){
        ArrayList<Integer> sortedScore = new ArrayList<>();
        for (int i = 0; i < tt.size(); i++) {
            sortedScore.add(tt.get(i));
        }
        Collections.sort(sortedScore);
        ArrayList<Integer> temp = new ArrayList<>();
        for (int i = 0; i < tt.size(); i++) {
            temp.add(sortedScore.indexOf(tt.get(i)));
        }
        ArrayList<String>  res = new ArrayList<>();

        DecimalFormat decFor = new DecimalFormat("0.00");
        for (int i = 0; i < tt.size(); i++) {
            res.add(decFor.format(sortedScore.indexOf(tt.get(i))/(double) (temp.size() -1)));
        }
        return res;
    }
}