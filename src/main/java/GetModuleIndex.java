import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetModuleIndex {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(args[0]));
        BufferedReader br1 = new BufferedReader(new FileReader(args[1]));

        String str ;
        str = br.readLine();
        List<String> geneList = new ArrayList<>();
        List<String[]> dic = new ArrayList<>();
        List<String> index = new ArrayList<>();
        while (  (str = br.readLine())!= null){
            geneList.add(str);
        }

        while ( (str = br1.readLine())!= null){
            String[] split = str.split("\t");
            dic.add(split);
            index.add(split[0]);
        }


        for (String gene : geneList) {
            int ii = index.indexOf(gene);
            System.out.println(gene + "  //  " + Arrays.toString(dic.get(ii)));
        }
    }
}
