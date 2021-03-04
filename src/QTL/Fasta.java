package QTL;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Fasta {
    private ArrayList<ArrayList<String>> block = null;
    private ArrayList<String> geneName;
    //to do list: write the function of GeneName
    private ArrayList<String> filterGeneName;
    private ArrayList<ArrayList<String>> filterBlock = null;
    private ArrayList<ArrayList<String>> shortBlock = null;
    private ArrayList<String> forwardSequence = null;
    private ArrayList<String> backSequence = null;
    private ArrayList<String> shortSequence = null;
//    ArrayList<Integer> startPos;

    public ArrayList<ArrayList<String>> getBlock() {
        return block;
    }

    public void setBlock(ArrayList<ArrayList<String>> block) {
        this.block = block;
    }

    public ArrayList<String> getGeneName() {
        return geneName;
    }

    public void setGeneName(ArrayList<String> geneName) {
        this.geneName = geneName;
    }

    public ArrayList<String> getFilterGeneName() {
        return filterGeneName;
    }

    public void setFilterGeneName(ArrayList<String> filterGeneName) {
        this.filterGeneName = filterGeneName;
    }

    public  ArrayList<ArrayList<String>> getFilterBlock() {
        return filterBlock;
    }

    public void setFilterBlock(ArrayList<ArrayList<String>> filterBlock) {
        this.filterBlock = filterBlock;
    }

    public ArrayList<ArrayList<String>> getShortBlock() {
        return shortBlock;
    }

    public void setShortBlock(ArrayList<ArrayList<String>> shortBlock) {
        this.shortBlock = shortBlock;
    }

    public ArrayList<String> getForwardSequence() {
        return forwardSequence;
    }

    public void setForwardSequence(ArrayList<String> forwardSequence) {
        this.forwardSequence = forwardSequence;
    }

    public ArrayList<String> getBackSequence() {
        return backSequence;
    }

    public void setBackSequence(ArrayList<String> backSequence) {
        this.backSequence = backSequence;
    }

    public ArrayList<String> getShortSequence() {
        return shortSequence;
    }

    public void setShortSequence(ArrayList<String> shortSequence) {
        this.shortSequence = shortSequence;
    }

    public Fasta() {}

    public void setBlock(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String str;
        while ((str = br.readLine()) != null){
            if (str.contains(">")){
                ArrayList<String> temp = new ArrayList<>();
                geneName.add(str.split(" ")[0]);
                temp.add(str);
                while (!(str = br.readLine()).contains(">")){
                    temp.add(str);
                }
                this.block.add(temp);
                break;
            }
        }
    }

    public void setBlocks(String filename) throws IOException {
        this.block = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String str;
        str = br.readLine();
        while (str != null){
            if (str.contains(">")){
                ArrayList<String> temp = new ArrayList<>();
                StringBuilder sb = new StringBuilder();
                temp.add(str);
                while ((str = br.readLine()) != null){
                    if (!str.contains(">")){
                        sb.append(str);
//                        temp.add(str);
                    }else {break;}
                }
                temp.add(sb.toString());
                this.block.add(temp);
            }
        }
    }

    // to identity short(<150bp), long(>350bp) sequence, the results will be added to member variables
    public void filter(){
        this.filterBlock = new ArrayList<>();
        this.shortBlock = new ArrayList<>();
        this.shortSequence = new ArrayList<>();
        this.filterGeneName = new ArrayList<>();
        for (ArrayList<String> gene:this.block) {
//            String temp = gene.get(0).split("\t")[0].split(">")[0].split(".")[0];
//             this.geneName.add(gene.get(0).split("\t")[0].split(">")[0].split(".")[0]);
             if (gene.get(1).length() > 350){
                 filterBlock.add(gene);
                 filterGeneName.add(gene.get(0).split("\t")[0]);
             }else if (gene.get(1).length() > 150){
                 shortBlock.add(gene);
                 shortSequence.add(gene.get(1));
             }
        }
    }

    // to subSequence the random350bp, and we can get 150bp front and 150bp back, these 2 will be saved in Member Variables, we can get it by ft.getForwardSequence() & ft.getBackSequence()
    public void subSequence(int bp){
        this.forwardSequence = new ArrayList<>();
        this.backSequence = new ArrayList<>();
        for (ArrayList<String> gene : this.filterBlock) {
            String forward = (String) gene.get(1).subSequence(0, bp);
            this.forwardSequence.add(forward);
            this.backSequence.add((String) gene.get(1).subSequence(gene.get(1).length()-150, gene.get(1).length()));
        }
    }

    //simulation random variance on a sequence
    public static ArrayList<String> randomVariation(ArrayList<String> seq){
//        for (String eachSeq: seq) {
//            eachSeq = random(eachSeq);
//        }
        ArrayList<String> res = new ArrayList<>();
        for (int i = 0; i < seq.size(); i++) {
//            String temp = random(seq.get(i));
/////            res.add(random(seq.get(i), NormalDistribution(0.1,0.1)));
        }
        return res;
    }

    // to subSequencing the sequence >= 350bp, start at p, end at (p+350), used for filterBlock.
    public static ArrayList<String> randomSequence(ArrayList<ArrayList<String>> filterBlock, ArrayList<Integer> startPos){
        ArrayList<String> res = new ArrayList<>();
//        ArrayList<Integer> startPos = new ArrayList<>();
//        for (ArrayList<String> gene: filterBlock) {
//            int p = getRandom(0, gene.get(1).length()-351);
//            res.add((String) gene.get(1).subSequence(p, p+350));
//            startPos.add(p);
//        }
        for (int i = 0; i < filterBlock.size(); i++) {
            res.add((String) filterBlock.get(i).get(1).subSequence(startPos.get(i), startPos.get(i)+350));
        }
        return res;
    }

    public static ArrayList<String> randomSequences(ArrayList<ArrayList<String>> filterBlock, ArrayList<Integer> startPos){
        ArrayList<String> res = new ArrayList<>();
//        ArrayList<Integer> startPos = new ArrayList<>();
//        for (ArrayList<String> gene: filterBlock) {
//            int p = getRandom(0, gene.get(1).length()-351);
//            res.add((String) gene.get(1).subSequence(p, p+350));
//            startPos.add(p);
//        }
        for (int i = 0; i < filterBlock.size(); i++) {
            res.add((String) filterBlock.get(i).get(1).subSequence(startPos.get(i), startPos.get(i)+350));
        }
        return res;
    }

    public static ArrayList<Integer> getRandomStartPos(ArrayList<ArrayList<String>> filterBlock){
        ArrayList<Integer> startPos = new ArrayList<>();
        for (ArrayList<String> gene: filterBlock) {
            int p = getRandom(0, gene.get(1).length()-351);
            startPos.add(p);
        }
        return  startPos;
    }

    public static String random(String seq, ArrayList<Double> q){
        StringBuilder sb = new StringBuilder(seq);
        for (int i = 0; i < seq.length(); i++) {
            double p = Math.random();
            if (p <= 0.25 * q.get(i)){sb.setCharAt(i, 'A'); }
            else if (p <= 0.5 * q.get(i)){sb.setCharAt(i, 'T');}
            else if (p <= 0.75 * q.get(i)){sb.setCharAt(i, 'C');}
            else if (p <= q.get(i)){sb.setCharAt(i, 'G');}
        }
//        seq=sb.toString();
        return sb.toString();
    }

//    public static char random(char seq, double q){
//        char res = seq;
//        double p = Math.random();
//        if (p <= 0.25 * q){ res = 'A'; }
//        else if (p <= 0.5 * q){res=  'T';}
//        else if (p <= 0.75 * q){res = 'C';}
//        else if (p <= q){res = 'G';}
//
//        return res;
//    }

    public static int getRandom(int min, int max){
        int num = min + (int)(Math.random() * (max-min+1));
        return num;
    }

    public static int[] randomCommon(int min, int max, int n){
        if (n > (max - min + 1) || max < min) {
            return null;
        }
        int[] result = new int[n];
        int count = 0;
        while(count < n) {
            int num = (int) (Math.random() * (max - min)) + min;
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if(num == result[j]){
                    flag = false;
                    break;
                }
            }
            if(flag){
                result[count] = num;
                count++;
            }
        }
        return result;
    }

    public static double NormalDistribution(double u,double v){
        java.util.Random random = new java.util.Random();
        return Math.sqrt(v)*random.nextGaussian()+u;
    }

    public static double getP(int Q){
        return -10 * Math.log10(Q);
    }

    public static ArrayList<ArrayList<Double>> getP(ArrayList<ArrayList<Integer>> Q){
        ArrayList<ArrayList<Double>> res = new ArrayList<>();
        for (int i = 0; i < Q.size(); i++) {
            ArrayList<Double> temp = new ArrayList<>();
            for (int j = 0; j < Q.get(i).size(); j++) {
                temp.add(getP(Q.get(i).get(j)));
            }
            res.add(temp);
        }
        return res;
    }

    public static ArrayList<String> readFASTQ(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        ArrayList<String> res = new ArrayList<>();
//        String str;
        while((br.readLine()) != null){
            br.readLine();
            br.readLine();
            res.add(br.readLine());
        }
        return res;
    }
}