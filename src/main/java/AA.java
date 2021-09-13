import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import utils.IOUtils;

public class AA {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("D:/Desktop/GOAnno.txt"));
        String str = null;
        HashMap<String,String> go = new HashMap<>();

        while ((str = br.readLine()) != null){
            String[] split = str.split("\t");
//            System.out.println(Arrays.toString(split));
            go.put(split[0], split[1]);
        }

        BufferedReader br1 = IOUtils.getTextReader("D:/Desktop/GOMAP2gene_buildMap.txt.gz");
        BufferedWriter bw = new BufferedWriter(new FileWriter("D:/Desktop/test.txt"));
        String str1 = null;

        str1 = br1.readLine();
        while ((str1 = br1.readLine()) != null){
            String[] split = str1.split("\t");
//            System.out.println(Arrays.toString(split));
            for (String s : split) {
                bw.write(s + "\t");
            }
            bw.write(go.get(split[0]));
            bw.write("\n");
        }
        bw.close();



    }
}
