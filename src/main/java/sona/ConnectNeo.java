package sona;

import org.neo4j.driver.*;

import static org.neo4j.driver.Values.parameters;

public class ConnectNeo {
    private Driver driver;
    private String database;
    private String nodeFile;
    private String relateFile;

    public ConnectNeo(String uri, String username, String passwd, String db){
        driver = GraphDatabase.driver(
                uri, AuthTokens.basic(username, passwd));

        this.database = db;

    }

    public ConnectNeo(String uri, String username, String passwd, String db, String[] file){
        driver = GraphDatabase.driver(
                uri, AuthTokens.basic(username, passwd));

        this.database = db;
        nodeFile = file[0];
        relateFile = file[1];

    }





    public void loadGeneName(){
        String line = pasteLine(nodeFile);
        try ( Session session = driver.session(SessionConfig.forDatabase( this.database )))
        {
            session.run(line +
                            "CREATE  (a:Gene{geneID:line.geneID, geneIn:line.geneIn})",
                    parameters());
        }
    }

    public void loadRelation(){
        String line = pasteLine(relateFile);
        try ( Session session = driver.session(SessionConfig.forDatabase( this.database )))
        {
            session.run(line +
                            "MATCH (from:Gene{geneIn:line.StartID}),(to:Gene{geneIn:line.EndID})" +
                            "MERGE (from)-[r:Corr{corr:line.Corr}]->(to)",
                    parameters());
//            System.out.println(line);
//            System.out.println("MATCH (from:Gene{IN:toInteger(line.StartID)}),(to:Gene{IN:toInteger(line.EndID)})");
//            System.out.println("MERGE (from)-[r:Corr{corr:toFloat(line.Corr)}]->(to)");
        }
    }

    public String pasteLine(String file){
        return "LOAD csv with headers from 'file:///" + file + "' as line ";
    }

    public void close(){
        driver.close();
    }
}