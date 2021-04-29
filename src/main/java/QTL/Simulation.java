package QTL;

import com.google.common.primitives.Ints;
import Test.IOUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static QTL.FindPos.readRange;

public class Simulation {
    public static void main(String[] args) throws IOException {
        Fasta ft = new Fasta();
        FindPos fp = new FindPos();
        String[] ATCG = new String[]{"A", "T", "C", "G"};
//        ft.setBlockss("H:/Nature/2020PreExperiment/JNW/src/QTL/subTrans.fasta");
        ft.setBlocks("H:/Nature/2020PreExperiment/result/QTL/IWGSC_v1.1_HC_20170706_transcripts.fasta");
        ArrayList<ArrayList<String>> tt = ft.getBlock();
//        System.out.println(tt.get(2));
        ft.filter();
        ArrayList<Integer> startPos = Fasta.getRandomStartPos(ft.getFilterBlock());
        ArrayList<String> random350 = Fasta.randomSequences(ft.getFilterBlock(), startPos);

        int[] slice = Fasta.randomCommon(0,ft.getFilterBlock().size(),10000);
        ArrayList<String> resName = new ArrayList<>();
        ArrayList<String> resSequence = new ArrayList<>();
        ArrayList<String> resSequenceNotMutation = new ArrayList<>();
//        System.out.println(Arrays.toString(slice));
        ArrayList<ArrayList<Double>> pp = new ArrayList<>();
        ArrayList<String> asc = new ArrayList<>();
        int end = 150;
        String filePath = "H:/Nature/2020PreExperiment/result/QTL/20210121/";
        int avr = 30;
        int sd = 3;
        StringBuilder sbf = new StringBuilder();
        sbf.append(filePath);
        sbf.append("avr");
        sbf.append(avr);
        sbf.append("_sd");
        sbf.append(sd);
//        String fileName1 = sbf.append("_front.fastq").toString();
        String fileName1 = sbf.toString() + "_front.fastq";
        String fileName2 = sbf.append("_back.fastq").toString();

        ArrayList<ArrayList<Double>> Q = new ArrayList<>();
        for (int i = 0; i < slice.length; i++) {
            ArrayList<Double> temp = new ArrayList<>();
            for (int j = 0; j < 350; j++) {
                temp.add(Fasta.NormalDistribution(avr, sd));
            }
            Q.add(temp);
        }

        for (int i = 0; i < Q.size(); i++) {
            ArrayList<Double> temp = new ArrayList<>();
            for (int j = 0; j < Q.get(i).size(); j++) {
                double exp = (-Q.get(i).get(j)/10);
                temp.add(Math.pow(10, exp));
//                temp.add((double) (10^((int) (-Q.get(i).get(j)/10))));
//                System.out.println(Math.pow(10, exp));
            }
            pp.add(temp);
        }


        for (int num:slice) {
            resName.add(ft.getFilterGeneName().get(num));
            resSequenceNotMutation.add(random350.get(num));
            String tempStr = Fasta.random(random350.get(num), pp.get(Ints.indexOf(slice, num)));
            resSequence.add(tempStr);
        }
        System.out.println(resSequenceNotMutation.get(0));
        System.out.println(resSequence.get(0));
//        for (int i = 0; i < pp.size(); i++) {
//            StringBuilder sb = new StringBuilder();
//            for (int j = 0; j < pp.get(i).size(); j++) {
//                sb.append(fp.transferACS(pp.get(i).get(j)));
//            }
//            asc.add(sb.toString());
//        }

        //alignment
//        for (int i = 0; i < resSequence.size(); i++) {
//            StringBuilder sb = new StringBuilder();
//            for (int j = 0; j < resSequence.get(i).length(); j++) {
//                if (resSequence.get(i).charAt(j) == resSequenceNotMutation.get(i).charAt(j)){
//                    sb.append("I");
//                }else {
//                    sb.append(fp.transferACS(Q.get(i).get(j)));
//                    System.out.println(fp.transferACS(Q.get(i).get(j)));
//                }
//            }
//            asc.add(sb.toString());
//        }
        for (int i = 0; i < resSequence.size(); i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < resSequence.get(i).length(); j++) {
                sb.append(fp.transferACS(Q.get(i).get(j)));
//                System.out.println(fp.transferACS(Q.get(i).get(j)));

            }
            asc.add(sb.toString());
        }



        ArrayList<String> resForwardSequence = new ArrayList<>();
        ArrayList<String> resBackSequence = new ArrayList<>();
        ArrayList<String> ascForwardSequence = new ArrayList<>();
        ArrayList<String> ascBackSequence = new ArrayList<>();
        for (String seq : resSequence) {
            String forward = (String) seq.subSequence(0, end);
            //to upper
            StringBuilder sb1 = new StringBuilder();
            for (int i = 0; i < forward.length(); i++) {
                if (forward.charAt(i) == 'a'){
                    sb1.append('A');
                }else if (forward.charAt(i) == 'g'){
                    sb1.append('G');
                }else if (forward.charAt(i) == 'c'){
                    sb1.append('C');
                }else if (forward.charAt(i) == 't'){
                    sb1.append('T');
                }else if(forward.charAt(i) == 'N'){
                    sb1.append(ATCG[(int) (ATCG.length * Math.random())]);
                }else {
                    sb1.append(forward.charAt(i));
                }
            }
            resForwardSequence.add(sb1.toString());
            StringBuilder sb = new StringBuilder();
            seq = seq.toUpperCase();
            for (int i =  seq.length()-1; i > (seq.length()-151); i--) {
                if (seq.charAt(i) == 'A') {
                    sb.append('T');continue;
                }
                if (seq.charAt(i) == 'T') {
                    sb.append('A');continue;
                }
                if (seq.charAt(i) == 'G') {
                    sb.append('C');continue;
                }
                if (seq.charAt(i) == 'C') {
                    sb.append('G');continue;
                }
//                else if (seq.charAt(i) == 'T'){
//                    sb.append('A');
//                }else if (seq.charAt(i) == 'C'){
//                    sb.append('G');
//                }else if (seq.charAt(i) == 'G'){
//                    sb.append('C');
//                }else if (seq.charAt(i) == 'a'){
//                    sb.append('T');
//                }else if (seq.charAt(i) == 't'){
//                    sb.append('A');
//                }else if (seq.charAt(i) == 'c'){
//                }else
//                if (seq.charAt(i) == 'g'){
//                    sb.append('C');
//                }else            sb.append('G');
                if (seq.charAt(i) == 'N'){
                    sb.append(ATCG[(int) (ATCG.length * Math.random())]);
//                    sb.append('N');
                }

//                sb.append(seq.charAt(i));
            }
            resBackSequence.add(sb.toString());
        }
        for (String seq:asc) {
            ascForwardSequence.add((String) seq.subSequence(0, end));
            StringBuilder sb = new StringBuilder();
            for (int i = seq.length()-1; i > seq.length()-151; i--) {
                sb.append(seq.charAt(i));
            }
            ascBackSequence.add(sb.toString());
        }


        ArrayList<ArrayList<Integer>> geneRange = new ArrayList<>();
        for (ArrayList<String> block: ft.getFilterBlock()) {
            ArrayList<Integer> res2 = readRange(block.get(0));
            geneRange.add(res2);
        }

        BufferedWriter br3 = new BufferedWriter(new FileWriter("H:/Nature/2020PreExperiment/result/QTL/20210121/information_130.txt"));
        br3.write("slice" + "\t" + "startPhyPos" + "\t" + "endPhyPos" + "\t" + "Chro");
        br3.newLine();
        int ii= 0;
        for (int num:slice) {
            br3.write(num + "\t" + geneRange.get(num).get(startPos.get(num)) + "\t" + geneRange.get(num).get(startPos.get(num) + 350) + "\t" + resName.get(ii).split(" ")[0]);
            br3.newLine();
            ii++;
        }
        br3.flush();
        br3.close();

        BufferedWriter bwf = new BufferedWriter(new FileWriter(fileName1));
        for (int i = 0; i < resForwardSequence.size(); i++) {
            bwf.write("@" + i + " 1:");
            bwf.newLine();
            bwf.write(resForwardSequence.get(i));
            bwf.newLine();
            bwf.write("+");
            bwf.newLine();
            bwf.write(ascForwardSequence.get(i));
            bwf.newLine();
        }
        bwf.close();

        BufferedWriter bwb = new BufferedWriter(new FileWriter(fileName2));
        for (int i = 0; i < resBackSequence.size(); i++) {
            bwb.write("@" + i + " 2:");
            bwb.newLine();
            bwb.write(resBackSequence.get(i));
            bwb.newLine();
            bwb.write("+");
            bwb.newLine();
            bwb.write(ascBackSequence.get(i));
            bwb.newLine();
        }
        bwb.close();
























//        BufferedWriter br = new BufferedWriter(new FileWriter("H:/Nature/2020PreExperiment/result/QTL/20210121/front_130.txt"));
//        BufferedWriter brAsc = new BufferedWriter(new FileWriter("H:/Nature/2020PreExperiment/result/QTL/20210121/front_asc_130.txt"));
//        for (int i = 0; i < resForwardSequence.size(); i++) {
//            br.write(resForwardSequence.get(i));
//            brAsc.write(ascForwardSequence.get(i));
//            brAsc.newLine();
//            br.newLine();
////            System.out.println(resForwardSequence.get(i));
//        }
//        br.flush();
//        br.close();
//        brAsc.flush();
//        brAsc.close();
//
//        BufferedWriter br1 = new BufferedWriter(new FileWriter("H:/Nature/2020PreExperiment/result/QTL/20210121/back_130.txt"));
//        BufferedWriter brAsc1 = new BufferedWriter(new FileWriter("H:/Nature/2020PreExperiment/result/QTL/20210121/back_asc_130.txt"));
//        for (int i = 0; i < resBackSequence.size(); i++) {
//            br1.write(resBackSequence.get(i));
//            brAsc1.write(ascBackSequence.get(i));
//            brAsc1.newLine();
//            br1.newLine();
//        }
//        br1.flush();
//        br1.close();
//        brAsc1.flush();
//        brAsc1.close();

    }

    public void writeSimulationFile(double avr, double sd, FindPos fp, Fasta ft) throws IOException {
        ArrayList<Integer> startPos = Fasta.getRandomStartPos(ft.getFilterBlock());
        String[] ATCG = new String[]{"A", "T", "C", "G"};
        ArrayList<String> random350 = Fasta.randomSequences(ft.getFilterBlock(), startPos);

        int[] slice = Fasta.randomCommon(0,ft.getFilterBlock().size(),10000);
        ArrayList<String> resName = new ArrayList<>();
        ArrayList<String> resSequence = new ArrayList<>();
        ArrayList<String> resSequenceNotMutation = new ArrayList<>();
//        System.out.println(Arrays.toString(slice));
        ArrayList<ArrayList<Double>> pp = new ArrayList<>();
        ArrayList<String> asc = new ArrayList<>();
        int end = 150;
        String filePath = "H:/Nature/2020PreExperiment/result/QTL/20210121/";


        StringBuilder sbf = new StringBuilder();
        sbf.append(filePath);
        sbf.append("avr");
        sbf.append(avr);
        sbf.append("-sd");
        sbf.append(sd);
//        String fileName1 = sbf.append("_front.fastq").toString();
        String fileName1 = sbf.toString() + "_R1.fastq.gz";
        String information = sbf.toString() + "information.txt";
        String fileName2 = sbf.toString() + "_R2.fastq.gz";

        ArrayList<ArrayList<Double>> Q = new ArrayList<>();
        for (int i = 0; i < slice.length; i++) {
            ArrayList<Double> temp = new ArrayList<>();
            for (int j = 0; j < 350; j++) {
                temp.add(Fasta.NormalDistribution(avr, sd));
            }
            Q.add(temp);
        }

        for (int i = 0; i < Q.size(); i++) {
            ArrayList<Double> temp = new ArrayList<>();
            for (int j = 0; j < Q.get(i).size(); j++) {
                double exp = (-Q.get(i).get(j)/10);
                temp.add(Math.pow(10, exp));
//                temp.add((double) (10^((int) (-Q.get(i).get(j)/10))));
//                System.out.println(Math.pow(10, exp));
            }
            pp.add(temp);
        }


        for (int num:slice) {
            resName.add(ft.getFilterGeneName().get(num));
            resSequenceNotMutation.add(random350.get(num));
            String tempStr = Fasta.random(random350.get(num), pp.get(Ints.indexOf(slice, num)));
            resSequence.add(tempStr);
        }
        System.out.println(resSequenceNotMutation.get(0));
        System.out.println(resSequence.get(0));
//        for (int i = 0; i < pp.size(); i++) {
//            StringBuilder sb = new StringBuilder();
//            for (int j = 0; j < pp.get(i).size(); j++) {
//                sb.append(fp.transferACS(pp.get(i).get(j)));
//            }
//            asc.add(sb.toString());
//        }

        //alignment
//        for (int i = 0; i < resSequence.size(); i++) {
//            StringBuilder sb = new StringBuilder();
//            for (int j = 0; j < resSequence.get(i).length(); j++) {
//                if (resSequence.get(i).charAt(j) == resSequenceNotMutation.get(i).charAt(j)){
//                    sb.append("I");
//                }else {
//                    sb.append(fp.transferACS(Q.get(i).get(j)));
//                    System.out.println(fp.transferACS(Q.get(i).get(j)));
//                }
//            }
//            asc.add(sb.toString());
//        }
        for (int i = 0; i < resSequence.size(); i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < resSequence.get(i).length(); j++) {
                sb.append(fp.transferACS(Q.get(i).get(j)));
//                System.out.println(fp.transferACS(Q.get(i).get(j)));

            }
            asc.add(sb.toString());
        }



        ArrayList<String> resForwardSequence = new ArrayList<>();
        ArrayList<String> resBackSequence = new ArrayList<>();
        ArrayList<String> ascForwardSequence = new ArrayList<>();
        ArrayList<String> ascBackSequence = new ArrayList<>();
        for (String seq : resSequence) {
            String forward = (String) seq.subSequence(0, end);
            //to upper
            StringBuilder sb1 = new StringBuilder();
            for (int i = 0; i < forward.length(); i++) {
                if (forward.charAt(i) == 'a'){
                    sb1.append('A');
                }else if (forward.charAt(i) == 'g'){
                    sb1.append('G');
                }else if (forward.charAt(i) == 'c'){
                    sb1.append('C');
                }else if (forward.charAt(i) == 't'){
                    sb1.append('T');
                }else if(forward.charAt(i) == 'N'){
                    sb1.append(ATCG[(int) (ATCG.length * Math.random())]);
                }else {
                    sb1.append(forward.charAt(i));
                }
            }
            resForwardSequence.add(sb1.toString());
            StringBuilder sb = new StringBuilder();
            seq = seq.toUpperCase();
            for (int i =  seq.length()-1; i > (seq.length()-151); i--) {
                if (seq.charAt(i) == 'A') {
                    sb.append('T');continue;
                }
                if (seq.charAt(i) == 'T') {
                    sb.append('A');continue;
                }
                if (seq.charAt(i) == 'G') {
                    sb.append('C');continue;
                }
                if (seq.charAt(i) == 'C') {
                    sb.append('G');continue;
                }
//                else if (seq.charAt(i) == 'T'){
//                    sb.append('A');
//                }else if (seq.charAt(i) == 'C'){
//                    sb.append('G');
//                }else if (seq.charAt(i) == 'G'){
//                    sb.append('C');
//                }else if (seq.charAt(i) == 'a'){
//                    sb.append('T');
//                }else if (seq.charAt(i) == 't'){
//                    sb.append('A');
//                }else if (seq.charAt(i) == 'c'){
//                }else
//                if (seq.charAt(i) == 'g'){
//                    sb.append('C');
//                }else            sb.append('G');
                if (seq.charAt(i) == 'N'){
                    sb.append(ATCG[(int) (ATCG.length * Math.random())]);
//                    sb.append('N');
                }

//                sb.append(seq.charAt(i));
            }
            resBackSequence.add(sb.toString());
        }
        for (String seq:asc) {
            ascForwardSequence.add((String) seq.subSequence(0, end));
            StringBuilder sb = new StringBuilder();
            for (int i = seq.length()-1; i > seq.length()-151; i--) {
                sb.append(seq.charAt(i));
            }
            ascBackSequence.add(sb.toString());
        }


        ArrayList<ArrayList<Integer>> geneRange = new ArrayList<>();
        for (ArrayList<String> block: ft.getFilterBlock()) {
            ArrayList<Integer> res2 = readRange(block.get(0));
            geneRange.add(res2);
        }

        BufferedWriter br3 = new BufferedWriter(new FileWriter(information));
        br3.write("slice" + "\t" + "startPhyPos" + "\t" + "endPhyPos" + "\t" + "Chro");
        br3.newLine();
        int ii= 0;
        for (int num:slice) {
            br3.write(num + "\t" + geneRange.get(num).get(startPos.get(num)) + "\t" + geneRange.get(num).get(startPos.get(num) + 350) + "\t" + resName.get(ii).split(" ")[0]);
            br3.newLine();
            ii++;
        }
        br3.flush();
        br3.close();

//        BufferedWriter bwf = new BufferedWriter(new FileWriter(fileName1));
        BufferedWriter bwf = IOUtils.getTextGzipWriter(fileName1);
        for (int i = 0; i < resForwardSequence.size(); i++) {
            bwf.write("@" + i + " 1:");
            bwf.write("\n");
            bwf.write(resForwardSequence.get(i));
            bwf.write("\n");
            bwf.write("+");
            bwf.write("\n");
            bwf.write(ascForwardSequence.get(i));
            bwf.write("\n");
        }
        bwf.close();

//        BufferedWriter bwb = new BufferedWriter(new FileWriter(fileName2));
        BufferedWriter bwb = IOUtils.getTextGzipWriter(fileName2);
        for (int i = 0; i < resBackSequence.size(); i++) {
            bwb.write("@" + i + " 2:");
            bwb.write("\n");
            bwb.write(resBackSequence.get(i));
            bwb.write("\n");
            bwb.write("+");
            bwb.write("\n");
            bwb.write(ascBackSequence.get(i));
            bwb.write("\n");
        }
        bwb.close();
    }
}
