import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GetGeneCorrInWholeGenome {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(args[0]));
        String gene = args[1];
        String str;
        str = br.readLine();
        List<List<Double>> res = new ArrayList<>();
        List<String> geneList = new ArrayList<>();
        int count = 0;
        int index = 0;

        while ( (str = br.readLine()) != null){
            String[] split = str.split("\t");
            List<Double> temp = new ArrayList<>();
            for (int i = 4; i < split.length; i++) {
                temp.add(Double.parseDouble(split[i]));
            }
            geneList.add(split[3]);
//            System.out.println(split[3]);
            res.add(temp);
        }

        index = geneList.indexOf(gene);
        System.out.println(index);
        BufferedWriter bw = new BufferedWriter(new FileWriter(args[2]));

        List<Double> corr = new ArrayList<>();

        for (int i = 0; i < res.size(); i++) {
            double correlation = myCorrelation(res.get(index), res.get(i));
//            corr.add(correlation(res.get(index), res.get(i)));
            if (correlation >= 0.5){
                bw.write(gene + "\t" + geneList.get(i) + "\t" + correlation);
                bw.write("\n");
            }
        }
        bw.close();
        br.close();
    }


    public static double myCorrelation(List<Double> xs, List<Double> ys) {
        //TODO: check here that arrays are not null, of the same length etc
        double sx = 0.0;
        double sy = 0.0;
        double sxx = 0.0;
        double syy = 0.0;
        double sxy = 0.0;
        int n = Math.min(xs.size(), ys.size());
        for(int i = 0; i < n; ++i) {
            double x = xs.get(i);
            double y = ys.get(i);
            sx += x;
            sy += y;
            sxx += x * x;
            syy += y * y;
            sxy += x * y;
        }
        // covariation
        double cov = sxy / n - sx * sy / n / n;
        // standard error of x
        double sigmaX = Math.sqrt(sxx / n -  sx * sx / n / n);
        // standard error of y
        double sigmaY = Math.sqrt(syy / n -  sy * sy / n / n);
        // correlation is just a normalized covariation
        return cov / sigmaX / sigmaY;
    }
}
