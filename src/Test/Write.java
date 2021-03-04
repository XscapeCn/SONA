package Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Write {

    public static void one2One(String Filename, String Filename2) throws IOException {
        BufferedReader br1 = new BufferedReader(new FileReader(Filename));
        BufferedWriter bw = new BufferedWriter(new FileWriter(Filename2));
        String str;
        int count = 1;
//        List geneList = readGeneList("geneList.txt");
        br1.readLine();
//        bw.write("gene1" + "," + "gene2" +"," + "corr");
        bw.newLine();
        while ((str = br1.readLine()) != null) {
            double[] drr = Cal.getDrr(str);
            for (int i = 0; i < drr.length; i++) {
                if (drr[i] > 2){bw.write(count + "\t" + i + "\t" + drr[i] + "\n");}
            }
            count++;
        }
        br1.close();
        bw.close();
        return ;
    }

    public static List readGeneList(String filename) throws IOException{
        BufferedReader br1 = new BufferedReader(new FileReader(filename));
        String str = null;
        List gene = new ArrayList<String>();
        while ((str = br1.readLine()) !=null) {
            gene.add(str);
        }
        br1.close();
        return gene;
    }
}
