package QTL;

import com.google.common.primitives.Ints;
import utils.IOUtils;

import java.io.*;
import java.util.ArrayList;

import static QTL.FindPos.readRange;

public class Demo04Function {
    public static void main(String[] args) throws IOException {
        Fasta ft = new Fasta();
        FindPos fp = new FindPos();
        ft.setBlocks("H:/Nature/2020PreExperiment/result/QTL/IWGSC_v1.1_HC_20170706_transcripts.fasta");
        ft.filter();

        BufferedReader br = new BufferedReader(new FileReader("H:/Nature/2020PreExperiment/result/QTL/20210127/para1-6-para2-6_information.txt"));
        int[] slice = new int[10000];
//        ArrayList<Integer> startPos = new ArrayList<>();
        br.readLine();
        String str;
        int index = 0;
        while((str = br.readLine()) != null){
            String temp = str.split("\t")[0];
//            String temp1 = str.split("\t")[1];
            slice[index] = Integer.valueOf(temp);
//            startPos.add(Integer.valueOf(temp1));
            index++;
//            System.out.println(slice[index-1]);
        }
        br.close();
//        System.out.println(startPos.size());

        ArrayList<Integer> startPos = Fasta.getRandomStartPos(ft.getFilterBlock());

        String ascFilePath = "H:/Nature/2020PreExperiment/result/QTL/";
//        String[] ascR1 = new String[]{"SiPAS_sub_R1.fq", "SiPASR_sub_R1.fq"};
        String[] ascR1 = new String[]{"SiPAS_sub_R1.fq", "SiPASR_sub_R1.fq", "SiPASU_sub_R1.fq", "SiPASUR_sub_R1.fq"};
//        String[] ascR2 = new String[]{"SiPAS_sub_R2.fq", "SiPASR_sub_R2.fq"};
        String[] ascR2 = new String[]{"SiPAS_sub_R2.fq", "SiPASR_sub_R2.fq", "SiPASU_sub_R2.fq", "SiPASUR_sub_R2.fq"};

//        ArrayList<String> asc1 = new ArrayList<>();
//        ArrayList<String> asc2 = new ArrayList<>();

        for (int i = 0; i < ascR1.length; i++) {
            ArrayList<String> asc1 = Fasta.readFASTQ(ascFilePath + ascR1[i]);
            for (int j = 0; j < ascR2.length; j++) {
                ArrayList<String> asc2NotReverse = Fasta.readFASTQ(ascFilePath + ascR2[j]);
                ArrayList<String> asc2 = new ArrayList<>();
                for (int k = 0; k < asc2NotReverse.size(); k++) {
                    String temp = new StringBuffer(asc2NotReverse.get(k)).reverse().toString();
                    asc2.add(temp);
                }
                writeSimulationFile(fp, ft, asc1, asc2, i , j, slice, startPos);
            }
        }


//        for (int i = 0; i < para1.size(); i++) {
//            for (int j = 0; j < para2.size(); j++) {
//                writeSimulationFile(fp, ft, para1.get(i), para2.get(j), i,j);
//            }
//        }
    }

    public static  void writeSimulationFile(FindPos fp, Fasta ft, ArrayList<String> asc1, ArrayList<String> asc2,  int aa, int bb, int[] slice,ArrayList<Integer> startPos) throws IOException {
//        ArrayList<Integer> startPos = Fasta.getRandomStartPos(ft.getFilterBlock());
        String[] ATCG = new String[]{"A", "T", "C", "G"};
        ArrayList<String> random350 = Fasta.randomSequences(ft.getFilterBlock(), startPos);

//        int[] slice = Fasta.randomCommon(0,ft.getFilterBlock().size(),10000);
        ArrayList<String> resName = new ArrayList<>();
        ArrayList<String> resSequence = new ArrayList<>();
        ArrayList<String> resSequenceNotMutation = new ArrayList<>();
//        System.out.println(Arrays.toString(slice));
        ArrayList<ArrayList<Double>> pp = new ArrayList<>();
        ArrayList<String> asc = new ArrayList<>();
        int end = 150;
        String filePath = "H:/Nature/2020PreExperiment/result/QTL/20210128/";

        StringBuilder sbf = new StringBuilder();
        sbf.append(filePath);
        sbf.append("R1-");
        sbf.append(aa+1);
        sbf.append("-R2-");
        sbf.append(bb+1);


        String fileName1 = sbf.toString() + "_R1.fq.gz";
        String information = sbf.toString() + "_information.txt";
        String fileName2 = sbf.toString() + "_R2.fq.gz";



        for (int i = 0; i < asc1.size(); i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(asc1.get(i));
            for (int j = 0; j < 50; j++) {
                sb.append("F");
            }
            sb.append(asc2.get(i));
            asc.add(sb.toString());
        }


        ArrayList<ArrayList<Integer>> Q = new ArrayList<>();
        for (int i = 0; i < asc.size(); i++) {
            ArrayList<Integer> temp = new ArrayList<>();
            for (int j = 0; j < asc.get(i).length(); j++) {
                temp.add(fp.getKey(String.valueOf(asc.get(i).charAt(j))));
            }
            Q.add(temp);
        }

        for (int i = 0; i < Q.size(); i++) {
            ArrayList<Double> temp = new ArrayList<>();
            for (int j = 0; j < Q.get(i).size(); j++) {
                double exp = (-Q.get(i).get(j)/10);
                temp.add(Math.pow(10, exp));
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

//        for (int i = 0; i < resSequence.size(); i++) {
//            StringBuilder sb = new StringBuilder();
//            for (int j = 0; j < resSequence.get(i).length(); j++) {
//                if (Q.get(i).get(j) <= 40 ){
//                    sb.append(fp.transferACS(Q.get(i).get(j)));
//                } else{
//                    sb.append('I');
//                }
//            }
//            asc.add(sb.toString());
//        }

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

    public static double getQuadratic(double[] a, double x){
        return a[0]*x*x + a[1]*x + a[2];
    }
}