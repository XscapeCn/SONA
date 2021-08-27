package fineMapping;

import java.io.*;
import java.util.ArrayList;

public class Method02Overlap {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("C:/Users/Administrator/Desktop/0.05_pvalue.bed"));
        BufferedReader br1 = new BufferedReader(new FileReader("C:/Users/Administrator/Desktop/24.cis_qtl_pairs_sig.txt"));

        String str = null;
        ArrayList<String> gwas = new ArrayList<>();
        while ((str = br.readLine()) != null) {
            String[] arr = str.split("\t");
            StringBuilder sb = new StringBuilder();
            sb.append("24_");
            sb.append(arr[1]);
            gwas.add(sb.toString());
        }
        System.out.println(gwas);
        br.close();

        String str1 = null;
        br1.readLine();
        ArrayList<String> eqtl = new ArrayList<>();
        ArrayList<String> geneName = new ArrayList<>();
        while ((str1 = br1.readLine()) != null) {
            String[] arr = str1.split("\t");
            eqtl.add(arr[2]);
            geneName.add(arr[1]);
        }
        br1.close();
        System.out.println(eqtl);
        System.out.println(geneName);


        gwas.retainAll(eqtl);
        BufferedWriter bw = new BufferedWriter(new FileWriter("C:/Users/Administrator/Desktop/test.txt"));
        for (int i = 0; i < gwas.size(); i++) {
            bw.write(gwas.get(i));
        }
        bw.close();

    }
}