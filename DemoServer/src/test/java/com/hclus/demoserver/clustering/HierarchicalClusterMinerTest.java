package com.hclus.demoserver.clustering;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HierarchicalClusterMinerTest {

    /**
     * Testa il metodo getDepth.
     */
    @Test
    public void testGetDepth() {
        try {
            HierarchicalClusterMiner miner = new HierarchicalClusterMiner(3);
            assertEquals(3, miner.getDepth(), "La profondità dovrebbe essere 3.");
        } catch (InvalidDepthException e) {
            fail("Eccezione sollevata: " + e.getMessage());
        }
    }

    /**
     * Testa il metodo getDepth con profondità non valida.
     */
    @Test
    void testInvalidDepth() {
        InvalidDepthException exception = assertThrows(
                InvalidDepthException.class, () -> new HierarchicalClusterMiner(0),
                "Dovrebbe sollevare un'eccezione per profondità non valida"
        );
        assertEquals("Profondità non valida!\n", exception.getMessage(), "Il messaggio di errore dovrebbe essere quello atteso.");
    }

}
