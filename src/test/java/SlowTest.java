import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.neo4j.harness.Neo4j;
import org.neo4j.harness.Neo4jBuilders;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SlowTest {
    private Neo4j neo4j;

    @BeforeAll
    void prepareTestDatabase() {
        long startTime = System.nanoTime();
        neo4j = Neo4jBuilders.newInProcessBuilder().build();
        report("startup",startTime);
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
