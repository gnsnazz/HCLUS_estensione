package com.hclus.demoserver.clustering;

import com.hclus.demoserver.data.Data;
import com.hclus.demoserver.data.NoDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClusterSetTest {
    /** Attributo che rappresenta un oggetto ClusterSet da testare. */
    private ClusterSet clusterSet;

    /**
     * Metodo che inizializza un oggetto ClusterSet prima di ogni test.
     */
    @BeforeEach
    void setUp() {
        clusterSet = new ClusterSet(2);
    }

    /**
     * Testa il metodo add() e get().
     */
    @Test
    public void testAddAndGet() {
        Cluster c1 = new Cluster();
        Cluster c2 = new Cluster();
        clusterSet.add(c1);
        clusterSet.add(c2);
        assertEquals(c1, clusterSet.get(0));
        assertEquals(c2, clusterSet.get(1));
    }

    /**
     * Metodo che testa il metodo toString() verificando che restituisca una stringa non nulla.
     */
    @Test
    public void testToString() {
        assertNotNull(clusterSet.toString());
    }

    /**
     * Metodo che testa il metodo toString(data) verificando che restituisca una stringa non nulla.
     */
    @Test
    public void testToStringWithData() throws NoDataException {
        Data data = new Data("exampleTab");
        assertNotNull(clusterSet.toString(data));
    }

}
