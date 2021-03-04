package QTL;

import com.google.common.primitives.Ints;
import Test.IOUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static QTL.FindPos.readRange;

public class Demo08FunctionR1AllRandom {
    public static void main(String[] args) throws IOException {
        Fasta ft = new Fasta();
        FindPos fp = new FindPos();
        ft.setBlocks("H:/Nature/2020PreExperiment/result/QTL/IWGSC_v1.1_HC_20170706_transcripts.fasta");
        ft.filter();

//        BufferedReader br = new BufferedReader(new FileReader("H:/Nature/2020PreExperiment/result/QTL/20210127/para1-6-para2-6_information.txt"));
//        int[] slice = new int[10000];
//        br.readLine();
//        String str;
//        int index = 0;
//        while((str = br.readLine()) != null){
//            String temp = str.split("\t")[0];
//            slice[index] = Integer.valueOf(temp);
//            index++;
////            System.out.println(slice[index-1]);
//        }
//        br.close();



        ArrayList<double[]> para1 = new ArrayList<>();
//        para1.add(new double[]{0.0003089255537614363, -0.09288985502782533,37.03678576404285});
//        para1.add(new double[]{0.0002077099735215275, -0.08364862989041287,36.97704520344682});
//        para1.add(new double[]{0.0003095285879928645, -0.10504501709159565,36.9461850090580});
//        para1.add(new double[]{0.0003086982841662942, -0.10892741347511262,36.745600702976105});
//        para1.add(new double[]{0.0004127805405222001, -0.13471416997303176,37.02530836576626});
//        para1.add(new double[]{0.00031156496028229125,-0.1254729448356193 ,36.96556780517023});

        for (int i = 1; i < 38; i++) {
            double a = -(i/Math.pow(149,2));
            double b = -1;
            double c = 37;
            para1.add(new double[]{a,b,c});
        }

        for (int i = 0; i < 10; i++) {
            int[] slice = Fasta.randomCommon(0,ft.getFilterBlock().size(),10000);
            ArrayList<Integer> startPos = Fasta.getRandomStartPos(ft.getFilterBlock());
            for (int j = 0; j < para1.size(); j++) {
                writeSimulationFile(fp, ft, para1.get(j), j, i, slice, startPos);
            }
        }

    }

    public static  void writeSimulationFile(FindPos fp, Fasta ft, double[] para, int aa, int iii, int[] slice, ArrayList<Integer> startPos) throws IOException {
//        ArrayList<Integer> startPos = Fasta.getRandomStartPos(ft.getFilterBlock());
        String[] ATCG = new String[]{"A", "T", "C", "G"};
        ArrayList<String> random350 = Fasta.randomSequences(ft.getFilterBlock(), startPos);

//        int[] slice = Fasta.randomCommon(0,ft.getFilterBlock().size(),10000);
        ArrayList<String> resName = new ArrayList<>();
        ArrayList<String> resSequence = new ArrayList<>();
        ArrayList<String> resSequenceNotMutation = new ArrayList<>();

        ArrayList<ArrayList<Double>> pp = new ArrayList<>();
        ArrayList<String> asc = new ArrayList<>();
        int end = 150;
        String filePath = "H:/Nature/2020PreExperiment/result/QTL/20210222/";
        filePath = filePath + iii + "/";
        File file=new File(filePath);
        file.mkdirs();


        StringBuilder sbf = new StringBuilder();
        sbf.append(filePath);
        sbf.append("para1-");
        sbf.append(aa+1);
//        sbf.append("-para2-");
//        sbf.append(bb+1);
//        String fileName1 = sbf.append("_front.fastq").toString();
//        String fileName1 = sbf.toString() + "_R1.fq.gz";
        String information = sbf.toString() + "_information.txt";
        String fileName2 = sbf.toString() + "_R2.fq.gz";

//        ArrayList<Double> temp1 = new ArrayList<>();
//        for (int j = 1; j < 151; j++) {
//            double num = getRandomFromSD(getQuadratic(para, j), sds.get(j-1));
//            System.out.println("=========");
//            System.out.println(num);
//            System.out.println(getQuadratic(para,j));
//            temp1.add(num);
//        }
//        for (int j = 0; j < 50; j++) {
//            temp1.add(40.0);
//        }
//        for (int j = 151; j > 1; j--) {
//            temp1.add(getQuadratic(para2, j));
//        }

        ArrayList<ArrayList<Double>> Q = new ArrayList<>();
        for (int i = 0; i < slice.length; i++) {
            ArrayList<Double> temp1 = new ArrayList<>();
            for (int j = 0; j < 200; j++) {

                ///
//                double num = getQuadratic(para, j, 1);
                ///

//                System.out.println(getQuadratic(para,j, i));
                temp1.add(40.0);
            }
            for (int j = 150; j > 0; j--) {
                double num = getQuadratic(para, j,1);
                temp1.add(num);
            }

            Q.add(temp1);
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
                if (Q.get(i).get(j) <= 40 ){
                    sb.append(fp.transferACS(Q.get(i).get(j)));
                } else{
                    sb.append('I');
                }
//                sb.append(fp.transferACS(Q.get(i).get(j)));
//                System.out.println(fp.transferACS(Q.get(i).get(j)));
            }
            asc.add(sb.toString());
        }



//        ArrayList<String> resForwardSequence = new ArrayList<>();
        ArrayList<String> resBackSequence = new ArrayList<>();
//        ArrayList<String> ascForwardSequence = new ArrayList<>();
        ArrayList<String> ascBackSequence = new ArrayList<>();
        for (String seq : resSequence) {
            String forward = (String) seq.subSequence(0, end);
            //to upper
//            StringBuilder sb1 = new StringBuilder();
//            for (int i = 0; i < forward.length(); i++) {
//                if (forward.charAt(i) == 'a'){
//                    sb1.append('A');
//                }else if (forward.charAt(i) == 'g'){
//                    sb1.append('G');
//                }else if (forward.charAt(i) == 'c'){
//                    sb1.append('C');
//                }else if (forward.charAt(i) == 't'){
//                    sb1.append('T');
//                }else if(forward.charAt(i) == 'N'){
//                    sb1.append(ATCG[(int) (ATCG.length * Math.random())]);
//                }else {
//                    sb1.append(forward.charAt(i));
//                }
//            }
//            resForwardSequence.add(sb1.toString());
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
//            ascForwardSequence.add((String) seq.subSequence(0, end));
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

//        BufferedWriter bwf = IOUtils.getTextGzipWriter(fileName1);
//        for (int i = 0; i < resForwardSequence.size(); i++) {
//            bwf.write("@" + i + " 1:");
//            bwf.write("\n");
//            bwf.write(resForwardSequence.get(i));
//            bwf.write("\n");
//            bwf.write("+");
//            bwf.write("\n");
//            bwf.write(ascForwardSequence.get(i));
//            bwf.write("\n");
//        }
//        bwf.close();

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

    public static double getQuadratic(double[] a, double x){
        return a[0]*x*x + a[1]*x + a[2];
//        return a*x*x + b*x + c;
    }

    public static double getQuadratic(double[] a, double x, int i){
        return a[0]*(x+a[1])*(x+a[1]) + a[2];
//        return a[0]*x*x + a[1]*x + a[2];
//        return a*x*x + b*x + c;
    }

    public static double getRandomFromSD(double avr, double sd){
        if (sd == 0){
            return avr;
        }else {
            double temp;
            double max = avr + sd;
            double min = avr -sd;
            if (max > 40){
                max = 40;
            }
            temp = (Math.random() * (max - min)) + min;
            return temp;
        }
    }

}