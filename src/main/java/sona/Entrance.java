package sona;

import java.util.Date;

public class Entrance {


    public static void main(String[] args) {
        Date date = new Date();
        System.out.println("Start time: " + date.toString());
        ExpressionMatrix em = new ExpressionMatrix("D:/Desktop/ScriptsInNetwork/Data/Expression/S4Awn.txt", "D:/MyNeo4jData/WEGA/import/");
//        ExpressionMatrix em = new ExpressionMatrix(args[0], args[1]);
        em.writeNeoFile();
//        ConnectNeo cn = new ConnectNeo("bolt://localhost:7687", "neo4j", "lulab415", "s4awn", em.getOutFile());
//        cn.loadGeneName();
//        cn.loadRelation();
//        cn.close();

        Date date1 = new Date();
        System.out.println("End time: " + date1.toString());
    }
}