import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class GeneDic {
    public HashMap<Integer,String> geneList = new HashMap<>();

    public GeneDic(){
        try{
            BufferedReader br = new BufferedReader(new FileReader("others/geneList.txt"));
            int num = 1;
            String str;
            while ( (str = br.readLine()) != null){
                this.geneList.put(num, str);
                num ++;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        GeneDic gd = new GeneDic();
        HashMap<Integer, String> geneList = new GeneDic().geneList;
        System.out.println(geneList.get(12));
    }
}
