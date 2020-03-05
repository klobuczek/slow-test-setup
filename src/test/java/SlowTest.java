import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.neo4j.configuration.connectors.BoltConnector;
import org.neo4j.harness.Neo4j;
import org.neo4j.harness.Neo4jBuilders;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SlowTest {
    private Neo4j neo4j;

    @BeforeAll
    void prepareTestDatabase() {
        neo4j = getNeo4j();
        neo4j.close();
        long startTime = System.nanoTime();
        neo4j = getNeo4j();
        report("startup",startTime);
    }

    private Neo4j getNeo4j() {
        return Neo4jBuilders.newInProcessBuilder().withConfig(BoltConnector.enabled, false).build();
    }

    @AfterAll
    void destroyTestDatabase() {
        long startTime = System.nanoTime();
        neo4j.close();
        report("shutdown", startTime);
    }

    private void report(String name, long startTime) {
        System.out.printf("%s: %f seconds\n", name, (System.nanoTime()-startTime)*1e-9);
    }

    @Test
    void dummy() {}
}
