import java.io.*;

public class GetMean {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(args[0]));
        int a = Integer.parseInt(args[1]);

        String str= br.readLine();
        String[] split = str.split("\t");
        int count = 0;
        int sum = 0;

        for (int i = a; i < split.length; i++) {
            sum += Double.parseDouble(split[i]);
            count++;
        }

        System.out.println(sum/count);

    }
}
