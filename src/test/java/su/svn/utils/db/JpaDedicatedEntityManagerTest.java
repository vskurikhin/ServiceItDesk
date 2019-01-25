package su.svn.utils.db;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import javax.persistence.*;

public class JpaDedicatedEntityManagerTest extends JpaEntityManagerTest
{
    protected static EntityManagerFactory emf;
    protected static EntityManager entityManager;

    @BeforeAll
    public static void init()
    {
         emf = Persistence.createEntityManagerFactory("mnf-pu-test");

         return;
    }

    @Override
    public EntityManager getEntityManager()
    {
        return entityManager;
    }

    @BeforeEach
    public void initializeDatabase()
    {
        entityManager = emf.createEntityManager();
        recreateDatabase();
    }

    @AfterEach
    public void closeEntityManager()
    {
        entityManager.clear();
        entityManager.close();
    }

    @AfterAll
    public static void tearDown()
    {
        emf.close();
    }
}
