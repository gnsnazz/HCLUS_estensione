package com.hclus.demoserver.clustering;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DendrogramTest {
    /** Dendrogramma da testare.*/
    private Dendrogram dendrogram;
    /** Attributo che rappresenta un oggetto ClusterSet da testare. */
    private ClusterSet clusterSet1;
    /** Attributo che rappresenta un oggetto ClusterSet da testare. */
    private ClusterSet clusterSet2;

    /**
     * Inizializza un nuovo dendrogramma prima di ogni test.
     */
    @BeforeEach
    void setUp(){
        try {
            dendrogram = new Dendrogram(3);
        } catch (InvalidDepthException e) {
            fail(InvalidDepthException.class + " non dovrebbe essere lanciata.");
        }
        clusterSet1 = new ClusterSet(2);
        clusterSet2 = new ClusterSet(2);
    }

    /**
     * Testa il metodo getDepth verificando se il dendrogramma è stato creato correttamente.
     */
    @Test
    void testDendrogramCreation() {
        assertEquals(3, dendrogram.getDepth(), "La profondità del dendrogramma dovrebbe essere 3.");
    }

    /**
     * Testa il metodo getClusterSet.
     */
    @Test
    void testSetClusterSet() {
        dendrogram.setClusterSet(clusterSet1, 0);
        dendrogram.setClusterSet(clusterSet2, 1);

        assertEquals(clusterSet1, dendrogram.getClusterSet(0), "Il ClusterSet al livello 0 dovrebbe essere clusterSet1.");
        assertEquals(clusterSet2, dendrogram.getClusterSet(1), "Il ClusterSet al livello 1 dovrebbe essere clusterSet2.");
    }

    /**
     * Testa il caso in cui si prova a creare un dendrogramma con profondità non valida.
     */
    @Test
    void testInvalidDepth() {
        InvalidDepthException exception = assertThrows(
                InvalidDepthException.class, () -> new Dendrogram(0), "Dovrebbe sollevare un'eccezione per profondità non valida"
        );
        assertEquals("Profondità non valida!\n", exception.getMessage(), "Il messaggio di errore dovrebbe essere quello atteso.");
    }

    /**
     * Testa il metodo toString.
     */
    @Test
    void testToString() {
        assertNotNull(dendrogram.toString(), "La rappresentazione del dendrogramma dovrebbe essere non nulla.");
    }

}
