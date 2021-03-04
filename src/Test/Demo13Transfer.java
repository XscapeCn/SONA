package Test;

import java.io.*;

public class Demo13Transfer {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("C:/Users/Administrator/Desktop/pos.txt"));
        BufferedWriter bw = new BufferedWriter(new FileWriter("C:/Users/Administrator/Desktop/pos.csv"));

        bw.write("chr,pos,el,chr");
        bw.newLine();
        String str ;
        while ((str=br.readLine()) != null){
            String[] temp = str.split("_");
            StringBuilder sb = new StringBuilder();
            sb.append(temp[0]);
            sb.append(",");
            sb.append(temp[1]);
            sb.append(",");
            sb.append("gene");
            sb.append(",");
            sb.append("3A");
            bw.write(sb.toString());
            bw.newLine();
        }
        bw.close();
        br.close();
    }
}
