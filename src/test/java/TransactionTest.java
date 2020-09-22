import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;
import org.neo4j.harness.Neo4j;
import org.neo4j.harness.Neo4jBuilders;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransactionTest {
    private Neo4j neo4j;

    @BeforeAll
    void prepareTestDatabase() {
        neo4j = Neo4jBuilders.newInProcessBuilder().build();
    }

    @Test
    void mixedTransaction() {
        try (Driver driver = GraphDatabase.driver(neo4j.boltURI()); Session session = driver.session()) {
            session.writeTransaction(tx -> {
                tx.run("CALL db.constraints()");
                tx.run("CREATE CONSTRAINT ON (p:Person) ASSERT p.name IS UNIQUE");
                return null;
            });
        }
    }
}
