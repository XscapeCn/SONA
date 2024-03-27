package sona;

import java.util.Date;

public class Entrance {


    public static void main(String[] args) {
        Date date = new Date();
        System.out.println("Start time: " + date.toString());
//        ExpressionMatrix em = new ExpressionMatrix("F:/S1coleoptile_nor.txt", "F:/");
        ExpressionMatrix em = new ExpressionMatrix(args[0], args[1]);

        em.writeRelateFile();
//        ExpressionMatrix em1 = new ExpressionMatrix("F:/S1root_nor.txt", "F:/");
//        em1.writeRelateFile();
//        ExpressionMatrix em2 = new ExpressionMatrix("F:/S2leaf_nor.txt", "F:/");
//        em2.writeRelateFile();
//        ExpressionMatrix em3 = new ExpressionMatrix("F:/S2root_nor.txt", "F:/");
//        em3.writeRelateFile();
//        ExpressionMatrix em4= new ExpressionMatrix("F:/S3leaf_nor.txt", "F:/");
//        em4.writeRelateFile();
//        ExpressionMatrix em5 = new ExpressionMatrix("F:/S4Awn_nor.txt", "F:/");
//        em5.writeRelateFile();
//        ExpressionMatrix em6 = new ExpressionMatrix("F:/S4spike_nor.txt", "F:/");
//        em6.writeRelateFile();
//        ExpressionMatrix em7 = new ExpressionMatrix("F:/S4stem_nor.txt", "F:/");
//        em7.writeRelateFile();
//        ExpressionMatrix em8 = new ExpressionMatrix("F:/S4leaf_nor.txt", "F:/");
//        em8.writeRelateFile();
//        ExpressionMatrix em9 = new ExpressionMatrix("F:/S5Anthers_nor.txt", "F:/");
//        em9.writeRelateFile();
//        ExpressionMatrix em10 = new ExpressionMatrix("F:/S6grain_nor.txt", "F:/");
//        em10.writeRelateFile();
//        ExpressionMatrix em11 = new ExpressionMatrix("F:/S6leaf_nor.txt", "F:/");
//        em11.writeRelateFile();
//        ExpressionMatrix em12 = new ExpressionMatrix("F:/S7grain_nor.txt", "F:/");
//        em12.writeRelateFile();
//        ExpressionMatrix em13 = new ExpressionMatrix("F:/S7leaf_nor.txt", "F:/");
//        em13.writeRelateFile();
//        ExpressionMatrix em14 = new ExpressionMatrix("F:/S8grain_nor.txt", "F:/");
//        em14.writeRelateFile();
//        ExpressionMatrix em15 = new ExpressionMatrix("F:/S8leaf_nor.txt", "F:/");
//        em15.writeRelateFile();
//        ExpressionMatrix em16 = new ExpressionMatrix("F:/S8grain_nor.txt", "F:/");
//        em16.writeRelateFile();
//        ExpressionMatrix em17 = new ExpressionMatrix("F:/S9leaf_nor.txt", "F:/");
//        em17.writeRelateFile();
//        ExpressionMatrix em18 = new ExpressionMatrix("F:/S5leaf_nor.txt", "F:/");
//        em18.writeRelateFile();
//        em.writeRelateFile();
//        ConnectNeo cn = new ConnectNeo("bolt://localhost:7687", "neo4j", "lulab415", "s4awn", em.getOutFile());
//        cn.loadGeneName();
//        cn.loadRelation();
//        cn.close();
//
        Date date1 = new Date();
        System.out.println("End timF: " + date1.toString());
    }
}
