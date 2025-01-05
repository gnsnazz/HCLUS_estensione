package com.hclus.demoserver.clustering;

import com.hclus.demoserver.data.Data;
import com.hclus.demoserver.data.Example;
import com.hclus.demoserver.data.NoDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class ClusterTest {
    /** Cluster da testare. */
    private Cluster cluster;

    /**
     * Inizializza un nuovo cluster prima di ogni test.
     */
    @BeforeEach
    void setUp() {
        cluster = new Cluster();
    }

    /**
     * Testa il metodo addData.
     */
    @Test
    void testAddData() {
        cluster.addData(1);
        cluster.addData(2);
        assertEquals(2, cluster.getSize());
    }

    /**
     * Testa il metodo getSize.
     */
    @Test
    void testGetSize() {
        cluster.addData(1);
        cluster.addData(2);
        assertEquals(2, cluster.getSize());
    }

    /**
     * Testa il metodo iterator.
     */
    @Test
    void testIterator() {
        cluster.addData(1);
        cluster.addData(2);
        Iterator<Integer> iterator = cluster.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next());
        assertEquals(2, iterator.next());
        assertFalse(iterator.hasNext());
    }

    /**
     * Testa il metodo clone.
     *
     * @throws CloneNotSupportedException se la clonazione non è supportata
     */
    @Test
    void testClone() throws CloneNotSupportedException {
        cluster.addData(1);
        cluster.addData(2);
        Cluster clonedCluster = cluster.clone();
        assertNotSame(cluster, clonedCluster);
        assertEquals(cluster.getSize(), clonedCluster.getSize());
        assertEquals(cluster.toString(), clonedCluster.toString());
    }

    /**
     * Testa il metodo mergeCluster.
     */
    @Test
    void testMergeCluster() {
        Cluster cluster1 = new Cluster();
        cluster1.addData(1);
        cluster1.addData(2);

        Cluster cluster2 = new Cluster();
        cluster2.addData(3);
        cluster2.addData(4);

        Cluster mergedCluster = cluster1.mergeCluster(cluster2);
        assertEquals(4, mergedCluster.getSize());
        assertTrue(mergedCluster.toString().contains("1"));
        assertTrue(mergedCluster.toString().contains("2"));
        assertTrue(mergedCluster.toString().contains("3"));
        assertTrue(mergedCluster.toString().contains("4"));
    }

    /**
     * Testa il metodo toString senza dati.
     */
    @Test
    void testToStringWithoutData() {
        cluster.addData(1);
        cluster.addData(2);
        assertEquals("1,2", cluster.toString());
    }

    /**
     * Testa il metodo toString con dati.
     *
     * @throws NoDataException se non ci sono dati
     */
    @Test
    void testToStringWithData() throws NoDataException {
        Data data = new Data("exampleTab");

        cluster.addData(0);
        cluster.addData(1);

        Example example1 = data.getExample(0);
        Example example2 = data.getExample(1);

        String expectedString = "<[" + example1.toString() + "]><[" + example2.toString() + "]>";
        assertEquals(expectedString, cluster.toString(data));
    }

}
