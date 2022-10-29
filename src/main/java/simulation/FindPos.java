package simulation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FindPos {
    private HashMap<Integer, String> asc;

    public FindPos(){
//        HashMap<Integer, String> map = new HashMap<>();
        HashMap map = new HashMap<Integer, String>();
        List<String> temp = new ArrayList<>();
        map.put(0, "!");
        map.put(1, "\"");
        map.put(2, "#");
        map.put(3, "$");
        map.put(4, "%");
        map.put(5, "&");
        map.put(6, "'");
        map.put(7, "(");
        map.put(8, ")");
        map.put(9, "*");
        map.put(10, "+");
        map.put(11, ",");
        map.put(12, "-");
        map.put(13, ".");
        map.put(14, "/");
        map.put(15, "0");
        map.put(16, "1");
        map.put(17, "2");
        map.put(18, "3");
        map.put(19, "4");
        map.put(20, "5");
        map.put(21, "6");
        map.put(22, "7");
        map.put(23, "8");
        map.put(24, "9");
        map.put(25, ":");
        map.put(26, ";");
        map.put(27, "<");
        map.put(28, "=");
        map.put(29, ">");
        map.put(30, "?");
        map.put(31, "@");
        map.put(32, "A");
        map.put(33, "B");
        map.put(34, "C");
        map.put(35, "D");
        map.put(36, "E");
        map.put(37, "F");
        map.put(38, "G");
        map.put(39, "H");
        map.put(40, "I");
        this.asc = map;
    }

    public HashMap<Integer, String> getAsc() {
        return asc;
    }

    public static ArrayList<String> readFasta(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        ArrayList<String> res = new ArrayList<>();
        String temp;
        res.add(br.readLine());
        StringBuilder sb = new StringBuilder();
        while ((temp = br.readLine()) != null){
            sb.append(temp);
        }
        res.add(sb.toString());
        return res;
    }

    public static ArrayList<String> readFasta(ArrayList<String> block) {
//        BufferedReader br = new BufferedReader(new FileReader(filename));
        ArrayList<String> res = new ArrayList<>();
        res.add(block.get(0));
        String temp;
//        res.add(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < block.size(); i++) {
            sb.append(block.get(i));
        }
        res.add(sb.toString());
        return res;
    }

    public static ArrayList<int[]> readGap(String file){
        String temp = file.split(" ")[6];
        String temp2 =  temp.split(":")[1];
        String[] temp3 = temp2.split(",");
        ArrayList<int[]> res = new ArrayList<>();
        for (String ss:temp3) {
            String[] temp4 = ss.split("-");
            int[] temp5 = new int[2];
            for (int i = 0; i < temp5.length; i++) {
                temp5[i] = Integer.parseInt(temp4[i]);
            }
            res.add(temp5);
        }
        return res;
    }

    public static int[] qwe(int[] aa) {
        int a = aa[0];
        int b = aa[1];
        int[] xx=new int[b-a +1];
        for (int i = 0; i <xx.length; i++) {
            xx[i] = a;
            a++;
        }
        return xx;
    }

    public static ArrayList<Integer> combine(int[] aa, int[] bb){
        ArrayList<Integer> res = new ArrayList<>();
        for (int num:aa) {
            res.add(num);
        }
        for (int num:bb) {
            res.add(num);
        }
        return res;
    }

    public static ArrayList<Integer> combine(ArrayList<Integer> aa, int[] bb){
        for (int num:bb) {
            aa.add(num);
        }
        return aa;
    }

    public static ArrayList<Integer> readRange(String file){
        ArrayList<int[]> temp = readGap(file);
        ArrayList<int[]> tt = new ArrayList<>();
        for (int[] xx : temp) {
            tt.add(qwe(xx));
        }
        ArrayList<Integer> res = new ArrayList<>();
        if (tt.size() ==1){
            res = combine(tt.get(0), new int[]{});
        } else if (tt.size() ==2){
            res = combine(tt.get(0), tt.get(1));
        }else {
            res = combine(tt.get(0), tt.get(1));
            for (int i = 2; i < tt.size(); i++) {
                res = combine(res, tt.get(i));
            }
        }
        return res;
    }

    public String transferACS (Double x){
//        if (x == 0  ){
//            return "I";
//        }else {
//            int res = (int) Math.round(-10 * Math.log10(x));
//            return this.asc.get(res);
//        }
        int xx = (int) Math.round(x);
        String res = this.asc.get(xx);
        return res;
    }

    public String transferACS (int x){
//        if (x == 0  ){
//            return "I";
//        }else {
//            int res = (int) Math.round(-10 * Math.log10(x));
//            return this.asc.get(res);
//        }

        String res = this.asc.get(x);
        return res;
    }

    public ArrayList<ArrayList<Integer>> readASC(ArrayList<String> asc){
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        for (String seq : asc) {
            ArrayList<Integer> temp = new ArrayList<>();
            for (int i = 0; i < seq.length(); i++) {
                temp.add(getKey(String.valueOf(seq.charAt(i))));
            }
//            System.out.println(temp);
            res.add(temp);
        }
        return res;
    }

    public int getKey(String value){
        for (int i = 0; i < this.asc.size(); i++) {
            if (this.asc.get(i).equals(value)){
//                System.out.println(i);
                return i;
            }
            continue;
        }
        return 0;
    }
}