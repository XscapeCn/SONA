package utils;

import org.neo4j.driver.*;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.neo4j.driver.Values.parameters;
import static utils.IOUtils.getTextContent;
import static utils.IOUtils.getTextReader;

public class Test{

    public final String importLocation = "D:/MyNeo4jData/WEGA/import/";

    public static void main(String[] args) throws IOException {
        Test t = new Test();
        List<String> textContent = getTextContent("D:/Desktop/geneList.txt");

        BufferedWriter bw = new BufferedWriter(new FileWriter(t.importLocation + "test.csv"));
        bw.write("ID,index"+ "\n");
        for (int i = 0; i < textContent.size(); i++) {
            bw.write(textContent.get(i));
            bw.write(",");
            bw.write(i + "\n");

        }
        bw.flush();
        bw.close();

        Driver driver = GraphDatabase.driver(
                "bolt://localhost:7687", AuthTokens.basic("neo4j", "lulab415"));

        try ( Session session = driver.session(SessionConfig.forDatabase( "S4Leaf" )) )
        {
//
//            session.run(":use S4Leaf ",
//                    parameters()
//            );
//
//
            session.run("LOAD csv with headers from 'file:///test.csv' as line "+
                            "CREATE  (a:Gene{ID:line.ID, index:line.index})",
                    parameters()
            );

        }

//        try ( Session session = driver.session() )
//        {
//            for (int i = 0; i < textContent.size(); i++) {
////                String gene = textContent.get(i);
//                session.run("CREATE (g:gene) " +
//                                "SET g.index = $index " +
//                                "SET g.name = $gene",
//                        parameters( "gene", textContent.get(i),"index",i )
//                );
//            }
//        }
        driver.close();
    }

}
