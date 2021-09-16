package utils;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.TransactionWork;

import java.util.ArrayList;
import java.util.List;

import static org.neo4j.driver.Values.parameters;

public class NetworkUtils {

    public NetworkUtils(){}

    public class HelloWorldExample implements AutoCloseable {
        private final Driver driver;

        public HelloWorldExample( String uri, String user, String password ) {
            driver = GraphDatabase.driver( uri, AuthTokens.basic( user, password ) );
        }

        @Override
        public void close() throws Exception {
            driver.close();
        }

        public void printGreeting( final String message )
        {
            try ( Session session = driver.session() ) {
                String greeting = session.writeTransaction( new TransactionWork<String>() {
                    @Override
                    public String execute( Transaction tx ) {
                        Result result = tx.run( "CREATE (a:Greeting) " +
                                        "SET a.message = $message " +
                                        "RETURN a.message + ', from node ' + id(a)",
                                parameters( "message", message ) );
                        return result.single().get( 0 ).asString();
                    }
                } );
                System.out.println( greeting );
            }
        }

        public void makeRunStatement(){
            List<String> pre = new ArrayList<>();
        }

        public void printGreetin( final String message ) {
            try ( Session session = driver.session() )


            {
                session.run("CREATE (gene:Greeting) " +
                        "SET a.message = $message ");
//                String greeting = session.writeTransaction( new TransactionWork<String>()
//                {
//                    @Override
//                    public String execute( Transaction tx )
//                    {
//                        Result result = tx.run( "CREATE (a:Greeting) " +
//                                        "SET a.message = $message " +
//                                        "RETURN a.message + ', from node ' + id(a)",
//                                parameters( "message", message ) );
//                        return result.single().get( 0 ).asString();
//                    }
            };
//                System.out.println( greeting );
        }
    }

    public static String[] getContextFromName(String file){
        String[] split = file.split("/");
        String a = split[split.length -1].split("\\.")[0];
//        String [] res =
        return new String[]{a+"_node.csv", a+"_relate.csv"};

    }


}


