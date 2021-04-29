package QTL;

import java.util.ArrayList;

public class Demo03Test {
    public static void main(String[] args) {
        FindPos fp = new FindPos();
        ArrayList<String> aa = new ArrayList<>();
        aa.add("#");
        aa.add(",");
//        String a = "F";
        System.out.println(fp.readASC(aa));
        System.out.println(fp.getAsc().get(2));
        System.out.println(fp.getAsc().get(2).equals("#"));
    }
}
