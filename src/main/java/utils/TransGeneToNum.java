package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TransGeneToNum {
    private List<String> gene = new ArrayList<>();


    public TransGeneToNum(){
        BufferedReader br = IOUtils.getTextReader("E:/Song/Desktop/Data/geneList.txt");
//        BufferedReader br = IOUtils.getTextReader("/data1/home/songxu/task/WGCNA/Data/Expression/geneList.txt");
        try{
            String str;
            while ((str = br.readLine()) != null) {
                gene.add(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public int getIndexOfGene(String geneName){
        return gene.indexOf(geneName);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("E:/Song/Desktop/Data/geneList.txt"));
        List<String> gene = new ArrayList<>();
        List<List<String>> res = new ArrayList<>();

        String str;

        while ((str = br.readLine()) != null) {
            gene.add(str);
        }

        BufferedReader br1 = new BufferedReader(new FileReader("D:/Desktop/ScriptsInNetwork/Results/Modules/AwnM12.txt"));
        String str1;

        while ((str1 = br1.readLine()) != null) {
            List<String> temp = new ArrayList<>();
            String[] split = str1.split("\t");
            int num = gene.indexOf(split[0]);
            temp.add(split[0]);
//            temp.add(split[1]);
            temp.add(String.valueOf(num));
            res.add(temp);
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter("D:/Desktop/agene.txt"));
        for (List<String> re : res) {
            for (String a :
                    re) {
                bw.write(a + "\t");
            }
            bw.write("\n");
        }
        bw.close();

    }
}
